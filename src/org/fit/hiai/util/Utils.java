package org.fit.hiai.util;

import org.fit.hiai.constants.ProjectConstants;
import weka.core.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: April 27, 2019
 * Institution: Florida Institute of Technology
 * Purpose: utilities for java projects
 */


public class Utils {
    public static String getDataFromFile(String fileName) {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String getDataFromFile(File theFile) {
        String data = "";
        try {
            data = new String(Files.readAllBytes(theFile.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void writeToFile(String fileName, String data, boolean openInAppendMode){

        File file = new File(fileName);
        PrintWriter out;
        BufferedWriter bw;
        FileWriter fw;

        // if file doesn't exists, create it
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file.getAbsoluteFile(), openInAppendMode);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println(data);

            out.close();
            bw.close();
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createCunitVisitorGetters(String filePath) {

        String baseVisitor = "/**\n" +
        "* This method extracts all%2$ss from the ast\n" +
        "* @return\n" +
        "*/\n\n" +
        "" +
        "public List<%1$s> get%1$ss() {\n" +
        "        List<%1$s> statements = new ArrayList<>();\n" +
        "\n" +
        "        if(cunit != null) {\n" +
        "            new VoidVisitorAdapter<Object>() {\n" +
        "                @Override\n" +
        "                public void visit(%1$s n, Object arg) {\n" +
        "                    super.visit(n, arg);\n" +
        "                    statements.add(n);\n" +
        "                }\n" +
        "            }.visit(cunit, null);\n" +
        "        }\n" +
        "        return statements;\n" +
        "}\n\n";
        File file = new File(filePath);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String visitableNode;
            while ((visitableNode = br.readLine()) != null) {
                String outStr = String.format(baseVisitor, visitableNode, splitCamelCaseString(visitableNode));
                System.out.println(outStr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createMethodVisitorGetters(String filePath) {

        String baseVisitor = "/**\n" +
                "* This method extracts all%2$ss from a given method\n" +
                "* @return\n" +
                "*/\n\n" +
                "" +
                "public List<%1$s> get%1$ssInMethod(){\n" +
                "        List<%1$s> statements = new ArrayList<>();\n" +
                "\n" +
                "        srcMethod.accept(new VoidVisitorAdapter<Void>() {\n" +
                "            @Override\n" +
                "            public void visit(%1$s n, Void arg) {\n" +
                "                super.visit(n, arg);\n" +
                "                n.removeComment();\n" +
                "                statements.add(n);\n" +
                "            }\n" +
                "        }, null);\n" +
                "\n" +
                "        return statements;\n" +
                "\n" +
                "    }\n\n";
        File file = new File(filePath);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String visitableNode;
            while ((visitableNode = br.readLine()) != null) {
                String outStr = String.format(baseVisitor, visitableNode, splitCamelCaseString(visitableNode));
                System.out.println(outStr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String createARFFHeaderFromFile(String filePath, String relationName) {

        StringBuilder arffData = new StringBuilder(ProjectConstants.ARFF_RELATION_PREFIX + "'" + relationName + "' \n\n");
        File file = new File(filePath);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
            String visitableNode;
            while ((visitableNode = br.readLine()) != null) {
                arffData.append(ProjectConstants.ARFF_ATTRIBUTE_PREFIX)
                        .append("'").append(visitableNode)
                        .append("'")
                        .append(" ")
                        .append(ProjectConstants.INTEGER_ATTRIBUTE_VALUE)
                        .append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //add an attribute for class
        arffData.append(ProjectConstants.ARFF_ATTRIBUTE_PREFIX)
                .append("'").append("classification")
                .append("'")
                .append(" ")
                .append(ProjectConstants.INTEGER_ATTRIBUTE_VALUE)
                .append("\n");

        //add an attribute for ID to map instance to source code
        arffData.append(ProjectConstants.ARFF_ATTRIBUTE_PREFIX)
                .append("'").append("ID")
                .append("'")
                .append(" ")
                .append(ProjectConstants.INTEGER_ATTRIBUTE_VALUE)
                .append("\n");

        //add the data label
        arffData.append("\n").append(ProjectConstants.ARFF_DATA_PREFIX).append("\n");

        return arffData.toString();

    }


    public static String splitCamelCaseString(String s){

        //first, split by dot
        String[] terms = s.split("\\.");
        StringBuilder outStr = new StringBuilder();

        for(String str : terms) {
            String [] camelSplits = str.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
            for(String splitStr : camelSplits){

                //if string longer than 1 and is not a number, add to list
                if(splitStr.length() > 1 && !splitStr.chars().allMatch( Character::isDigit ))
                    outStr.append(" ").append(splitStr);
            }
        }
        return outStr.toString();
    }



    public static LinkedHashMap<String, Integer> createIntHashMapFromFile(String filePath) {

        File file = new File(filePath);
        BufferedReader br;
        LinkedHashMap<String, Integer> nodeMap = new LinkedHashMap<>();

        try {
            br = new BufferedReader(new FileReader(file));
            String visitableNode;
            while ((visitableNode = br.readLine()) != null) {
                nodeMap.put(visitableNode, 0); //initialize all nodes to 0
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodeMap;
    }

    public static LinkedHashMap<Integer, String> readFileToHashMap(String filePath) {

        File file = new File(filePath);
        BufferedReader br;
        LinkedHashMap<Integer, String> nodeMap = new LinkedHashMap<>();
        String[]  lineParts;

        try {
            br = new BufferedReader(new FileReader(file));
            String inStr;
            br.readLine(); //skip header
            while ((inStr = br.readLine()) != null) {
                lineParts = inStr.split(",");
                if (lineParts.length == 2) {
//                    System.out.println("ID: '" + lineParts[0] + "'; Code: '" + lineParts[1].trim() +"'");
                    nodeMap.put(new Integer(lineParts[0]), lineParts[1].trim()); //initialize hashmap with integer key an string value
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodeMap;
    }


    /**
     * Create a hashmap and store a binary representation of a node type of the form (nodeName, binaryRepresentation)
     * @param filePath
     * @return
     */
    public static LinkedHashMap<String, String> binaryEncodeFileToHashMap(String filePath) {

        File file = new File(filePath);
        BufferedReader br;
        LinkedHashMap<String, String> nodeMap = new LinkedHashMap<>();
        int i = 0;

        try {
            br = new BufferedReader(new FileReader(file));
            String inStr;
            String binaryRepr;
            while ((inStr = br.readLine()) != null) {
                binaryRepr = Integer.toBinaryString(i); // convert int to binary
                binaryRepr = new String(new char[ 8 - binaryRepr.length()]).replace("\0", "0") + binaryRepr; //pad with 0s to get a 8-bit representation
//                System.out.println(i + "-->" +binaryRepr);
                nodeMap.put(inStr.trim(), binaryRepr);
                i++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodeMap;
    }

    /**
     * Create a hashmap and store a binary representation of a node type of the form (binaryRepresentation, nodeName)
     * Creating a decoding in a hashmap is faster than looping to lookup nodeName by binaryRepresentation
     * Could have used BiMap (Bi-directional map) from Commons Collections, but this requires importing an extra library
     * @param filePath
     * @return
     */
    public static LinkedHashMap<String, String> binaryDecodeFileToHashMap(String filePath) {

        File file = new File(filePath);
        BufferedReader br;
        LinkedHashMap<String, String> nodeMap = new LinkedHashMap<>();
        int i = 0;

        try {
            br = new BufferedReader(new FileReader(file));
            String inStr;
            String binaryRepr;
            while ((inStr = br.readLine()) != null) {
                binaryRepr = Integer.toBinaryString(i); // convert int to binary
                binaryRepr = new String(new char[ 8 - binaryRepr.length()]).replace("\0", "0") + binaryRepr; //pad with 0s to get a 8-bit representation
//                System.out.println(i + "-->" +binaryRepr);
                nodeMap.put(binaryRepr, inStr.trim());
                i++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodeMap;
    }

    public static String hashMapToARFF(LinkedHashMap<String, Integer> astMap){
        String arffStr = "";
        StringBuilder arffBuilder = new StringBuilder();
        astMap.forEach((k, v) -> arffBuilder.append(v).append(", ")); //use lambda expression to iterate hashmap
//        for (Map.Entry<String, Integer> entry : astMap.entrySet()) {
//            arffBuilder.append(", ");
//        }

        arffStr = arffBuilder.toString();
        arffStr = arffStr.replaceAll("\\s*\\p{Punct}+\\s*$", ""); //remove ending punctuation
        return arffStr;
    }



    /**
        This method converts the hashmap (LinkedHashMap) of values to a weka instance.
        First, we need to build the set of attributes containing the possible values.
        Second, create an instances object.
        Third, create an instance and use the set of attributes to populate the instance values
     */
    public static Instances hashMaptoWekaInstance(LinkedHashMap<String, Integer> astMap) {

        int numAttrs, count;

        ///// CREATE THE ATTRIBUTES FIRST ////////
        ArrayList<Attribute> attrs = new ArrayList<>();
        count = 0;

        for (Map.Entry<String, Integer> entry : astMap.entrySet()) {
            attrs.add(new Attribute(entry.getKey(), count));
            count++;
        }


//        for (Map.Entry<String, Integer> entry : astMap.entrySet()) {
//            System.out.println(entry.getKey() + "-->" +entry.getValue() );
//        }

        numAttrs = attrs.size();

        //Create a property and store the program code inside for output of top match/neighbor
//        Properties p2 = new Properties();
//        p2.setProperty("encodedProgCode", attrs.get(numAttrs - 1).name());
//        ProtectedProperties prop2 = new ProtectedProperties(p2);

//        System.out.println("converting hashmap to weka instance. Num attrs: " + numAttrs +" count: " + count);

//        System.out.println("Num attributes: " + numAttrs + "; num of general attributes: " + numGeneralAttrs);
//        for(Attribute attr : attrs)
//            System.out.println(attr.name());

        // Create an empty training set of size 1
        Instances insTrainingSet = new Instances("ASTSample", attrs, 1);
        // set the last attribute as the target variable
        insTrainingSet.setClassIndex(numAttrs - 1);

        // Create the instance
        Instance instanceExample = new DenseInstance(numAttrs);
        ArrayList<Integer> astValues = new ArrayList<>(astMap.values());

        for(int i = 0; i < numAttrs; i++){
            Attribute a = attrs.get(i);
            instanceExample.setValue(a, astValues.get(i));
        }

//        Attribute classAttr = attrs.get(count);
        // cannot set attribute to ? (missing value) for classification
//        System.out.println(classAttr.name());
//        instanceExample.setValue(classAttr, ProjectConstants.QUESTION_MARK);

        insTrainingSet.add(instanceExample);
        return insTrainingSet;
    }


    public static String decodeBase64Code(String encodedStr){
        byte[] codeBytes = Base64.getDecoder().decode(encodedStr);
        return new String(codeBytes);
    }

    public static String encodeBase64(String strToEncode){
        return Base64.getEncoder().encodeToString(strToEncode.getBytes());
    }

    public static int findInstanceID(Instances dataset, Instance instance, List<String> excludedAttrs){
        int id = -1, matches = 0, numAttrs = dataset.numAttributes();

        for(int i = 0; i < dataset.size(); i++){
            matches = 0;

//            System.out.println("dataset instance " + dataset.instance(i).toString());
//            System.out.println("query instance " + instance.toString());

            for(int j = 0; j < dataset.numAttributes(); j++){
//                System.out.println("data instance value:  " + dataset.instance(i).value(j) +"; query instance value: " + instance.value(j));
                if(dataset.instance(i).value(j) == instance.value(j) && !excludedAttrs.contains(dataset.instance(i).attribute(j).name()))
                    matches++;
            }


            if(matches == numAttrs)
                id = i;
        }

        return id;
    }

    /**
     * Get the binary representation of a list of AST nodes within a given method
     * @param encodedNodes
     * @param methodNodes
     * @return
     */
    public static String[] getNodeStructureSeq(LinkedHashMap<String, String> encodedNodes, List<String> methodNodes){
        String[] nodeStructSeq = new String[methodNodes.size()];


        for(int i = 0; i < methodNodes.size(); i++){
            nodeStructSeq[i] = encodedNodes.get(methodNodes.get(i)); //get the binary representation of a node
        }

        return nodeStructSeq;

    }

}
