public void paint(Graphics2D g, Target target, boolean hasFocus) {
    g.translate(target.getX(), target.getY());
    int width = target.getWidth();
    int height = target.getHeight();
    drawShadow(g, width, height);
    // draw folded paper edge
    int[] xpoints = { 1, width - CORNER_SIZE, width, width, 1 };
    int[] ypoints = { 1, 1, CORNER_SIZE + 1, height, height };
    Polygon p = new Polygon(xpoints, ypoints, 5);
    boolean isSelected = target.isSelected() && hasFocus;
    int thickness = (isSelected) ? 2 : 1;
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    g.setColor(Color.WHITE);
    g.fill(p);
    g.setColor(Color.BLACK);
    g.setStroke(new BasicStroke(thickness));
    g.draw(p);
    g.drawLine(width - CORNER_SIZE, 1, width - CORNER_SIZE, CORNER_SIZE);
    g.drawLine(width - CORNER_SIZE, CORNER_SIZE, width - 2, CORNER_SIZE);
    g.setStroke(new BasicStroke(1));
    // draw the lines in the paper
    for (int yPos = CORNER_SIZE + 10; yPos <= height - 10; yPos += 5) g.drawLine(10, yPos, width - 10, yPos);
    g.translate(-target.getX(), -target.getY());
}
