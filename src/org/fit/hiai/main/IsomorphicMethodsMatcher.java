package org.fit.hiai.main;

import com.github.javaparser.ast.body.MethodDeclaration;
import org.fit.hiai.constants.ProjectConstants;
import org.fit.hiai.javaparser.*;
import org.fit.hiai.util.TreeComparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 5/8/19
 * Institution: Florida Institute of Technology
 * Purpose: Do an isomorphic AST match of java methods
 */
public class IsomorphicMethodsMatcher {

    public static void main(String[] args) throws FileNotFoundException {

        File projectDir = new File(System.getProperty("user.dir"));
        String visitorFilePath = projectDir + "/data/visitable_tree_nodes.txt";
        String javaMethodsDataDirPath = projectDir + "/data/java_test_data/methods/";
        int count = 0;
        int k = 5;
        long startTime;
        FilePathResolver javaPathResolver = new FilePathResolver();
        File methodsDir = new File(javaMethodsDataDirPath); // all extracted Java methods

        String sampleJavaMethodFile1 = javaMethodsDataDirPath + "/javaml_methods/AbstractBayesianClassifier_compact.buildClassifier.6a19bb5d-693b-4c3a-8d2c-a8161b040011.txt";

        List<String> excludedDirs = new ArrayList<>(Collections.singletonList("sourcerer_general"));
        // Choose a directory and retrieve all Java files
        List<File> srcFiles = javaPathResolver.resolvePaths(methodsDir, ".txt", excludedDirs);

        JavaMethodsParser javaCodeParser = null;
        JavaMethodsParser candidateCodeParser = null;
        candidateCodeParser = new JavaMethodsParser(new File(sampleJavaMethodFile1));
        MethodDeclaration candidateMethod = candidateCodeParser.getSrcMethod();

        List<TreeCompareResult> treeCompareResults = new ArrayList<>();

        System.out.println("QUERY");
        System.out.println(sampleJavaMethodFile1);
        System.out.println("=====================================================");
        System.out.println(candidateMethod);


        // Given a query, Loop through all Java source files in the directory/subdirectory and do an isomorphic match

        System.out.println("Now processing " + srcFiles.size() + " Java file(s) .... ");
        int numFiles = (srcFiles.size() > ProjectConstants.MAX_ALIGNMENT_FILES)? ProjectConstants.MAX_ALIGNMENT_FILES : srcFiles.size();

        startTime = System.currentTimeMillis();

        for (int i = 0; i < numFiles; i++) { //srcFiles.size()
//         System.out.println(javaFilePath);
            File javaFilePath = srcFiles.get(i);

            javaCodeParser = new JavaMethodsParser(javaFilePath);

            MethodDeclaration testMethod = javaCodeParser.getSrcMethod();

            ASTComparer comparer = new ASTComparer();

            ArrayList<NodeCompareResult> nodeCompareResults = comparer.comparePreOrder(candidateMethod, testMethod, 0);

            int totalMatches = getMatches(nodeCompareResults);

            treeCompareResults.add(
                    new TreeCompareResult(testMethod,
                            javaFilePath.getPath(),
                            nodeCompareResults,
                            totalMatches,
                            nodeCompareResults.size() - totalMatches
                    )
            );

        }


        System.out.println("Semi-Isomorphic Method Matcher Took: " + ((System.currentTimeMillis() - startTime)/1000) +" sec on " + numFiles +" files");

        //Print out the best result with the highest  similarity score
//        TreeCompareResult r = Collections.max(treeCompareResults, new TreeComparator());
//        System.out.println(r.getJavaMethod().toString());

        startTime = System.currentTimeMillis();
        //Print out the top k results after sorting by similarity score
        treeCompareResults.sort(new TreeComparator());

        System.out.println("Sorting Matches by Similarity took : " + ((System.currentTimeMillis() - startTime)/1000) +" sec");

        for(int i = 0; i < k; i++){
            TreeCompareResult tcr = treeCompareResults.get(i);
            System.out.println(tcr.getJavaFilePath());
            System.out.println("Similarity: " + tcr.getSimilarity());
            System.out.println(tcr.getJavaMethod() +"\n");
            tcr.printNodeComparisonTree();
            System.out.println("=====================================================");
        }

    }

    public static int getMatches(ArrayList<NodeCompareResult> nodeComparisons){
        int count = 0;
        for(NodeCompareResult ncr : nodeComparisons)
            count += (ncr.getMatch() ? 1 : 0);
        return count;
    }


}
