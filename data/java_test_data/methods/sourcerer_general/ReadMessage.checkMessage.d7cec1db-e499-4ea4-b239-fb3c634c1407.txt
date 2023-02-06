public void checkMessage() {
    if (isRuning) {
        return;
    } else {
        isRuning = true;
    }
    Comm mycomm = new Comm(this.getPort());
    long beginTime = System.currentTimeMillis();
    try {
        if (!mycomm.GetIsOpen()) {
            if (mycomm.Open() == 0) {
                if (mycomm.SetPortPara() < 0) {
                    mycomm.ClosePort();
                    System.out.println("-7");
                }
            // end if
            } else {
                System.out.println("-6");
            }
        // end if
        }
        // end if
        // int r = initGsm(mycomm);
        int r = InitGsm(mycomm);
        if (r < 0) {
            log.error("Init GSM error!");
            // System.out.println("Error!");
            return;
        // System.exit(-1);
        }
        String commandstr = "AT+CMGL=4\r";
        // 发送命令
        if (mycomm.WritePort(commandstr) < 0) {
            mycomm.ClosePort();
            r = -2;
        }
        String rbuf1 = mycomm.read(10);
        // System.out.println(rbuf1);
        if (StringUtils.isNotEmpty(rbuf1)) {
            List<MessageBean> messages = parse(rbuf1);
            int messageCount = messages.size();
            long readMsgTime = System.currentTimeMillis();
            log.debug("Read message [" + messageCount + "]proccess time : [" + (readMsgTime - beginTime) + "]");
            if (messageCount > 0) {
                // deal with the message
                for (int i = 0; messageCount > i; i++) {
                    MessageBean message = messages.get(i);
                    log.debug("Recieve the message [" + message.getMessage() + "] from [" + message.getDestaddr() + "]");
                    String barcode = message.getMessage().trim();
                    if (StringUtils.isEmpty(barcode)) {
                        continue;
                    }
                    if (!(message.getDestcode().equals("91"))) {
                        continue;
                    }
                    String replyAddr = message.getDestaddr().trim();
                    if (replyAddr.length() != 11 && replyAddr.length() != 13) {
                        continue;
                    }
                    if (barcode.startsWith("FF")) {
                        barcode = barcode.substring(2);
                        if (barcode.indexOf("||") == -1) {
                            insertVisitor(barcode);
                        } else {
                            String[] barcodes = StringUtils.split(barcode, "||");
                            if (barcodes.length > 1) {
                                insertVisitor(barcodes[0]);
                                insertVisitor(barcodes[1]);
                            }
                        // end if
                        }
                    // end if
                    } else {
                        VisitorManager mgr = (VisitorManager) SpringUtil.getBean("visitorManager");
                        Visitor v = mgr.getVisitorByBarcode(barcode);
                        if (v != null) {
                            if (StringUtils.isNotEmpty(v.getAttr1()) && StringUtils.isNotEmpty(v.getEmail()) && StringUtils.isNotEmpty(v.getMobile())) {
                                saveSms(replyAddr, "您输入的证件编码已经完成注册！");
                            } else {
                                FreeMail mail = FreeMailService.getFreeMail();
                                if (mail == null) {
                                    saveSms("13564375967", "No free mail left!!!！");
                                } else {
                                    v.setEmail(mail.getAddress());
                                    v.setMobile(message.getDestaddr());
                                    mgr.saveVisitor(v);
                                    saveSms(replyAddr, "您已成功注册，工博会免费邮箱登录网址 www.citiz.net, 用户名：[" + mail.getAddress() + "] 密码：[" + mail.getPassword() + "].");
                                }
                            }
                        // end if
                        } else {
                            saveSms(replyAddr, "你输入的证件编码有误，请重发您的证件编码到 " + Configuration.getInstance().getMobile() + "!");
                        }
                    // end if
                    }
                // end if
                }
                // end for
                long dealTime = System.currentTimeMillis();
                log.debug("Reply the message [" + messageCount + "]proccess time : [" + (dealTime - readMsgTime) + "]");
                // delete msg
                for (int i = 1; messageCount >= i; i++) {
                    String delCmdstr = "AT+CMGD=" + i + "\r";
                    if (mycomm.WritePort(delCmdstr) < 0) {
                        log.error("删除短信出错！");
                        r = -2;
                    }
                    rbuf1 = mycomm.read(10);
                    System.out.println(i + "   " + rbuf1);
                }
                long delTime = System.currentTimeMillis();
                log.debug("Delete the message [" + messageCount + "]proccess time : [" + (delTime - dealTime) + "]");
            }
        }
        log.debug("Total proccess time : [" + (System.currentTimeMillis() - beginTime) + "]");
        mycomm.ClosePort();
        isRuning = false;
    // String rbuf1 = mycomm.ReadPort();
    } catch (Exception e) {
        log.error(e);
        isRuning = false;
    // e.printStackTrace();
    } finally {
        mycomm.ClosePort();
        isRuning = false;
    }
}
