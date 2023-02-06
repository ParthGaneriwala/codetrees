package org.fit.hiai.main;

import com.github.javaparser.ast.body.MethodDeclaration;
import org.fit.hiai.javaparser.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 5/2/19
 * Institution: Florida Institute of Technology
 * Purpose: Quickly run simple tests of the algorithms in this project
 */

public class Test {

    public static void main(String[] args) {

        /*
        //Print out the tree representation and method nodes for a given piece of code
        String sampleJavaSrcCode= "package mypackage;\n" +
                " public class MyClass {\n" +
                "   public void Hello() {\n" +
                "     System.out.println(\"Hello\");\n" +
                "  }\n" +
                "}";
        File projectDir = new File(System.getProperty("user.dir"));
        String visitorFilePath = projectDir + "/data/visitable_tree_nodes.txt";

        List<String> sampleFiles = new ArrayList<>();

        LinkedHashMap<String, String> encodedNodes = Utils.binaryEncodeFileToHashMap(visitorFilePath); // the encoding of all visitable nodes within a JavaParser AST

        try {
            CompilationUnitCodeParser codeParser = new CompilationUnitCodeParser(sampleJavaSrcCode); // accepts file or java code
            String codeTree = TextualTreeVisualizer.visualizeAST(codeParser.getCunit());
            System.out.println(codeTree);

            MethodNodesExtractor methodNodesExtractor = new MethodNodesExtractor();
            List<String> methodNodes;
            String[] nodeStructs;

            List<MethodDeclaration> methods = codeParser.getMethodDeclarations();
            for(MethodDeclaration method : methods){
                methodNodes = methodNodesExtractor.getOrderedNodes(method);
                System.out.println(String.join("\n", methodNodes));
                nodeStructs = Utils.getNodeStructureSeq(encodedNodes, methodNodes);
                System.out.println(Arrays.toString(nodeStructs));
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
        */


        /*
        // Create getters for all visitable nodes in the JavaParser AST
        File projectDir = new File(System.getProperty("user.dir"));
        String visitorFilePath = projectDir + "/data/visitable_tree_nodes.txt";
        //Utils.createCunitVisitorGetters(visitorFilePath); //Create and print out a list of getters for all the visitable nodes in the AST
        Utils.createMethodVisitorGetters(visitorFilePath); //Create and print out a list of getters for all the visitable nodes in based on methods
//        System.exit(0); //stop here

    }
    */

        File projectDir = new File(System.getProperty("user.dir"));
        String javaMethodsDataDir = projectDir + "/data/java_test_data/methods";
        String sampleJavaMethodFile1 = javaMethodsDataDir + "/javaml_methods/AbstractBayesianClassifier_compact.buildClassifier.6a19bb5d-693b-4c3a-8d2c-a8161b040011.txt";

        String sampleJavaMethodFile2 = javaMethodsDataDir + "/weka_methods/AbstractClassifier.classifyInstance.9ed1a20e-15cc-4160-919c-41d505605d3f.txt";

        JavaMethodsParser candidateCodeParser = null;

        try {
            candidateCodeParser = new JavaMethodsParser(new File(sampleJavaMethodFile1));
            MethodDeclaration method1 = candidateCodeParser.getSrcMethod();

            candidateCodeParser = new JavaMethodsParser(new File(sampleJavaMethodFile2));
            MethodDeclaration method2 = candidateCodeParser.getSrcMethod();

            String methodTree = MethodTreeVisualizer.visualizeAST(method1);
            System.out.println(methodTree);

            StringBuilder sb = new StringBuilder();


            ASTComparer comparer = new ASTComparer();

            ArrayList<NodeCompareResult> results = comparer.comparePreOrder(method2, method1, 0);

            for(NodeCompareResult r : results){
                String tabs = new String(new char[r.getLevel()]).replace("\0", "\t");
                System.out.println(tabs +r.getNodeType() + " " + r.getMatch());
            }

        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
