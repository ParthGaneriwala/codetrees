package org.fit.hiai.main;

import org.fit.hiai.classifiers.KNN;
import org.fit.hiai.classifiers.distance.EuclidianDistanceFunction;
import org.fit.hiai.classifiers.distance.StraightClassChooser;
import org.fit.hiai.javaparser.CompilationUnitCodeParser;
import org.fit.hiai.javaparser.TextualTreeVisualizer;
import org.fit.hiai.util.Utils;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;


/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 4/30/19
 * Institution: Florida Institute of Technology
 * Purpose: Use KNN to Classify a program file using previously generated ARFF data
 * See JavaFileARFFProcessor and JavaMethodsARFFProcessor for more info.
 */

public class ProgramFileClassifier {
    public static void main(String[] args) {
        File projectDir = new File(System.getProperty("user.dir"));
//        String sampleJavaSrcFile = projectDir + "/data/java_test_data/HoeffdingAdaptiveTree.java.txt";

        String sampleJavaSrcFile = projectDir +"/data/moa/src/examples/java/moa/classifiers/DecisionStumpTutorial.java";
        String wekaDataFile = projectDir + "/data/arff_data/weka_source_tree_data.arff";
        String codeRefDataFile = projectDir + "/data/arff_data/weka_source_tree_ref_data.txt";
        String visitorFilePath = projectDir + "/data/visitable_tree_nodes.txt";




        ConverterUtils.DataSource training = null;
        ConverterUtils.DataSource testing = null;
        int[] attrIndicesToRemove;
        int idIndex = -1;
        int K = 15;
        Instances fullTrainingData, trainingData, fullTestingData, testingData;
        Instance[] bestInstances;
        double[] bestScores;
        int testingID, bestMatchID;
        String[] excludedAttrs = new String[]{"ID"};

        String candidateCode = "", matchedCode = "";

//        Built-in weka classifier
//        String[] kNNOptions = new String[1];
//        kNNOptions[0] = "-I";
//        weka.classifiers.lazy.IBk kNN = new weka.classifiers.lazy.IBk();
//        options = weka.core.Utils.splitOptions("-K 1 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -D\\\"\"");
//        kNN.setOptions(weka.core.Utils.splitOptions(kNNOptions));

        //Custom KNN classifier
        KNN myKnn;
        CompilationUnitCodeParser candidateCodeParser = null;
        CompilationUnitCodeParser resultsCodeParser = null;
        LinkedHashMap<String, Integer> astMap;
        LinkedHashMap<Integer, String> codeRefMap;
        int numAttrs;

        try {
            training = new ConverterUtils.DataSource(wekaDataFile);
            fullTrainingData = training.getDataSet(); //dataset containing all attributes

            //filter out attributes (particularly the ID field)
            System.out.println();
            numAttrs = fullTrainingData.numAttributes();

            if (fullTrainingData.attribute( numAttrs - 1).name().equalsIgnoreCase("ID"))
                idIndex = numAttrs - 1;
            attrIndicesToRemove = new int[]{idIndex};

            Remove removeFilter = new Remove();
            removeFilter.setAttributeIndicesArray(attrIndicesToRemove);
            removeFilter.setInvertSelection(false);
            removeFilter.setInputFormat(fullTrainingData);

            trainingData = Filter.useFilter(fullTrainingData, removeFilter);

            if (trainingData.classIndex() == -1)
                trainingData.setClassIndex(trainingData.numAttributes() - 1);

            System.out.println("Num attrs before: " + numAttrs +"; Num attrs after: " + trainingData.numAttributes());

//            kNN.setOptions(kNNOptions);
//            kNN.buildClassifier(trainingData);
//            Double classified = 0.;
//            classified = kNN.classifyInstance(testInstance);

            candidateCodeParser = new CompilationUnitCodeParser(new File(sampleJavaSrcFile));
            candidateCode = candidateCodeParser.getCunit().toString();
            astMap = JavaFileARFFProcessor.populateASTMap(candidateCodeParser, visitorFilePath);
            fullTestingData = Utils.hashMaptoWekaInstance(astMap);

            codeRefMap = Utils.readFileToHashMap(codeRefDataFile);

            testingData = Filter.useFilter(fullTestingData, removeFilter);

//            myKnn = new KNN(trainingData, testingData, new ManhattanDistanceFunction(), new StraightClassChooser(), 3);
            myKnn = new KNN(trainingData, testingData, new EuclidianDistanceFunction(), new StraightClassChooser(), K);
            bestInstances = myKnn.getBestInstances();
            bestScores = myKnn.getBestScores();

            if(bestInstances.length > 0) {
                System.out.println(bestInstances.length + " best instances");

                bestMatchID = Utils.findInstanceID(trainingData, bestInstances[0], Arrays.asList(excludedAttrs));

                if(bestMatchID != -1){
                    matchedCode = Utils.decodeBase64Code(codeRefMap.get(bestMatchID));
                    resultsCodeParser = new CompilationUnitCodeParser(matchedCode);
                }

            }

            for(int i = 0; i < bestScores.length; i++)
                System.out.println(bestScores[i]);

            System.out.println("================= QUERY ================= ");
            System.out.println(candidateCode);
            System.out.println("================= MATCH ================= ");
            System.out.println(matchedCode);

            String candidateTree = TextualTreeVisualizer.visualizeAST(candidateCodeParser.getCunit());
            assert resultsCodeParser != null;
            String matchedTree = TextualTreeVisualizer.visualizeAST(resultsCodeParser.getCunit());

            System.out.println("================= QUERY TREE ================= ");
            System.out.println(candidateTree);
            System.out.println("================= MATCHED TREE ================= ");
            System.out.println(matchedTree);

            System.out.println("================= OTHER NEIGHBORS ================= ");


            for(int i = 0; i < bestScores.length; i++) {
                bestMatchID = Utils.findInstanceID(trainingData, bestInstances[i], Arrays.asList(excludedAttrs));

                if (bestMatchID != -1) {
                    matchedCode = Utils.decodeBase64Code(codeRefMap.get(bestMatchID));
                    System.out.println("======== NEIGHBOR " + (i+1) +"; DISTANCE : " + bestScores[i] +" =========");
                    System.out.println(matchedCode);
//                    resultsCodeParser = new JavaMethodsParser(matchedCode);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
