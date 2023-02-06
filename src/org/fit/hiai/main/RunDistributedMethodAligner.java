package org.fit.hiai.main;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.fit.hiai.constants.ProjectConstants;
import org.fit.hiai.javaparser.FilePathResolver;
import org.fit.hiai.javaparser.MethodNodesExtractor;
import org.fit.hiai.javaparser.MethodTreeVisualizer;
import org.fit.hiai.samilarity.AlignmentResult;
import org.fit.hiai.samilarity.CodeAlignment;
import org.fit.hiai.samilarity.DistributedCodeAligner;
import org.fit.hiai.util.CodeAlignmentComparator;
import org.fit.hiai.util.CodeAlignmentLCSComparator;
import org.fit.hiai.util.Utils;

import java.io.File;
import java.util.*;

/**
* CodeTrees
* Author: FitzRoi
* Date Created: 5/16/19
* Institution: Florida Institute of Technology
* Purpose: Find the most similar method to a given method using ordered (encoded for consistency and efficiency) AST nodes.
 * based on the Needleman-Waunch sequence alignment algorithm
*/
public class RunDistributedMethodAligner {

    public static void main(String[] args) {

        File projectDir = new File(System.getProperty("user.dir"));
        String visitorFilePath = projectDir + "/data/visitable_tree_nodes.txt";
        String javaMethodsDataDir = projectDir + "/data/java_test_data/methods";
        String javaDataFolder = projectDir + "/data";
        int k = 5;
        long startTime;

//        String sampleJavaFile = javaMethodsDataDir + "/weka_methods/AbstractClustererTest.testRegression.f887c51d-cef9-4ee9-8e67-ce3284eaaf28.txt";
//        String sampleJavaFile = javaMethodsDataDir + "/weka_methods/RandomTree.buildClassifier.3c473bc3-552a-468a-99ac-6feb6692a143.txt";

//        String sampleJavaFile = javaMethodsDataDir + "/moa_methods/;AbstractBayesianClassifier_compact.buildClassifier.78080dc5-b836-476d-bf48-8056d6162909.txt";
        String sampleJavaFile = javaDataFolder  + "/java_test_data/method_groups/file_writing/buffered_writer.txt";
        String sampleJavaFile2 = javaMethodsDataDir + "/weka_methods/AbstractAssociatorTest.testDatasetIntegrity.6d5d6009-38a2-4196-9095-d560e37d3800.txt";

        List<String> methodNodes1, methodNodes2;
        String[] nodeStructSeq1, nodeStructSeq2;
        String methodSample1 = Utils.getDataFromFile(sampleJavaFile); //read a query method from file

        MethodNodesExtractor methodNodesExtractor = new MethodNodesExtractor();

        ArrayList<AlignmentResult> bestAlignments;


        LinkedHashMap<String, String> encodedNodes = Utils.binaryEncodeFileToHashMap(visitorFilePath); // the encoding of all visitable nodes within a JavaParser AST
        LinkedHashMap<String, String> decodedNodes = Utils.binaryDecodeFileToHashMap(visitorFilePath); // the decoding of all visitable nodes within a JavaParser AST


        FilePathResolver filesResolver = new FilePathResolver();
        //exclude some directories to satisfy memory constraints
//        List<String> excludedDirs = new ArrayList<>(Arrays.asList("weka_methods","moa_methods", "javaml_methods")); //initialize arraylist with multiple folders
        List<String> excludedDirs = new ArrayList<>(Collections.singletonList("sourcerer_general")); //initialize arraylist with one folder to exclude
        List<File> methodTxtFiles = filesResolver.resolvePaths(new File(javaMethodsDataDir), ".txt", excludedDirs); // find txt files in the given directory

        MethodDeclaration method1 = (MethodDeclaration) JavaParser.parseBodyDeclaration(methodSample1); // cast BodyDeclaration as subclass MethodDeclaration



        DistributedCodeAligner distCodeAligner;
        int numTasks = 20;
        startTime = System.currentTimeMillis();

        if(methodTxtFiles.size() > ProjectConstants.MAX_ALIGNMENT_FILES) {
            List<File> sublist = new ArrayList<>(methodTxtFiles.subList(0, ProjectConstants.MAX_ALIGNMENT_FILES));
            distCodeAligner = new DistributedCodeAligner(new LinkedList<>(sublist), numTasks, method1, encodedNodes);
        }
        else {
            distCodeAligner = new DistributedCodeAligner(new LinkedList<>(methodTxtFiles), numTasks, method1, encodedNodes);
        }
//        ArrayList<AlignmentResult> allAlignments = distCodeAligner.spawnCodeAligners();
        methodTxtFiles.clear();

        System.out.println("Now processing " + distCodeAligner.getFileListSize() + " text file(s) of java methods .... ");

        bestAlignments = distCodeAligner.spawnCodeAligners();

        System.out.println("Distributed Method Aligner Took: " + ((System.currentTimeMillis() - startTime)/1000) +" sec on " + methodTxtFiles.size() +" files");

        //print out all alignments having the lowest score
        if (bestAlignments.size() > 0) {
            startTime = System.currentTimeMillis();
            bestAlignments.sort(new CodeAlignmentComparator().thenComparing(new CodeAlignmentLCSComparator())); // sort by penalty then by LCS
            System.out.println("Sorting Alignments by Score took : " + ((System.currentTimeMillis() - startTime)/1000) +" sec");
            System.out.println("================= QUERY ================= ");
            System.out.println(method1.toString());
            System.out.println("================= QUERY TREE ================= ");
            String codeTree = MethodTreeVisualizer.visualizeAST(method1);
            System.out.println(codeTree);

            for(int indx = 0; indx < k; indx++){ //print out the top 5 alignments

                System.out.println("Minimum Penalty: " + bestAlignments.get(indx).getScore());
                System.out.println("Number of Matches: " + bestAlignments.get(indx).getNumMatches());
                System.out.println("LCS: " + bestAlignments.get(indx).getLcs());
//                System.out.println(bestAlignments.get(indx).getSeq1());
//                System.out.println(bestAlignments.get(indx).getSeq2());

                System.out.println("================= MATCH ================= ");
                System.out.println("SOURCE: " + bestAlignments.get(indx).getSeq2SrcCodePath());
                System.out.println("========================================= ");
                System.out.println(bestAlignments.get(indx).getSeq2SrcCode());

                System.out.println("================= CODE TREE ================= ");
                codeTree = MethodTreeVisualizer.visualizeAST(bestAlignments.get(indx).getSeq2SrcCode());
                System.out.println(codeTree);

                String[] decodedSeq1 = bestAlignments.get(indx).getSeq1().split(" ");
                String[] decodedSeq2 = bestAlignments.get(indx).getSeq2().split(" ");

                //print the encoded aligned string
                for (int j = 0; j < decodedSeq1.length; j++) {
                    if (!decodedSeq1[j].equals(ProjectConstants.GAP_STRING))
                        System.out.printf("%-30s", decodedNodes.get(decodedSeq1[j])); //space columns about 30 spaces
                    else
                        System.out.printf("%-30s", ProjectConstants.GAP_STRING);

                    System.out.print("\t\t");

                    if (!decodedSeq2[j].equals(ProjectConstants.GAP_STRING))
                        System.out.printf("%-30s", decodedNodes.get(decodedSeq2[j]));
                    else
                        System.out.printf("%-30s", ProjectConstants.GAP_STRING);

                    if(decodedSeq1[j].equals(decodedSeq2[j])) //mark equal nodes
                        System.out.print("***");

                    System.out.print("\n");
                }

            }
        }
    }
}
