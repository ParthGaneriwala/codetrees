package org.fit.hiai.main;

import org.fit.hiai.classifiers.KNN;
import org.fit.hiai.classifiers.distance.EuclidianDistanceFunction;
import org.fit.hiai.classifiers.distance.ManhattanDistanceFunction;
import org.fit.hiai.classifiers.distance.StraightClassChooser;
import org.fit.hiai.classifiers.distance.WeightedClassChooser;
import org.fit.hiai.javaparser.CompilationUnitCodeParser;
import org.fit.hiai.javaparser.JavaMethodsParser;
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
 * Purpose:
 */

public class ProgramMethodClassifier {
    public static void main(String[] args) {
        File projectDir = new File(System.getProperty("user.dir"));
//        String sampleJavaSrcFile = projectDir + "/data/java_test_data/HoeffdingAdaptiveTree.java.txt";
        String javaMethodsDataDir = projectDir + "/data/java_test_data/methods";
//        String sampleJavaMethodFile = javaMethodsDataDir + "/javaml_methods/AbstractBayesianClassifier_compact.buildClassifier.6a19bb5d-693b-4c3a-8d2c-a8161b040011.txt";

//        String sampleJavaMethodFile = javaMethodsDataDir + "/weka_methods/AbstractAssociatorTest.testDatasetIntegrity.6d5d6009-38a2-4196-9095-d560e37d3800.txt";

//        String wekaDataFile = projectDir + "/data/arff_data/weka_program_methods_data.arff";
//        String codeRefDataFile = projectDir + "/data/arff_data/weka_source_methods_ref_data.txt";
        String visitorFilePath = projectDir + "/data/visitable_tree_nodes.txt";



        //test using the sourcerer dataset
        String wekaDataFile = projectDir + "/data/arff_data/sourcerer_repo/sourcerer_sample_arff.arff";
        String codeRefDataFile = projectDir + "/data/arff_data/sourcerer_repo/sourcerer_sample_arff_ref_data.txt";

        String sampleJavaMethodFile = projectDir  + "/data/java_test_data/method_groups/file_writing/buffered_writer.txt";
//        String sampleJavaMethodFile = projectDir  + "/data/java_test_data/method_groups/file_writing/print_writer.txt";
//        String sampleJavaMethodFile = projectDir  + "/data/java_test_data/method_groups/file_writing/file_output_stream.txt";
//        String sampleJavaMethodFile = projectDir  + "/data/java_test_data/method_groups/file_writing/random_access_file.txt";
//        String sampleJavaMethodFile = projectDir  + "/data/java_test_data/method_groups/file_writing/file_channel.txt";
//        String sampleJavaMethodFile = projectDir  + "/data/java_test_data/method_groups/file_writing/data_output_stream.txt";
        String[] kNNOptions = new String[1];

        kNNOptions[0] = "-I";
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

        String queryCode = "", matchedCode = "";

//        Built-in weka classifier
//        weka.classifiers.lazy.IBk kNN = new weka.classifiers.lazy.IBk();
//        options = weka.core.Utils.splitOptions("-K 1 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -D\\\"\"");
//        kNN.setOptions(weka.core.Utils.splitOptions(kNNOptions));

        //Custom KNN classifier
        KNN myKnn;
        JavaMethodsParser queryCodeParser = null;
        JavaMethodsParser resultsCodeParser = null;
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

//            kNN.setOptions(kNNOptions);
//            kNN.buildClassifier(trainingData);
//            Double classified = 0.;
//            classified = kNN.classifyInstance(testInstance);

            queryCodeParser = new JavaMethodsParser(new File(sampleJavaMethodFile));
            queryCode = queryCodeParser.getSrcMethod().toString();
            astMap = JavaMethodsARFFProcessor.populateASTMap(queryCodeParser, visitorFilePath);
            fullTestingData = Utils.hashMaptoWekaInstance(astMap);

            codeRefMap = Utils.readFileToHashMap(codeRefDataFile);

            testingData = Filter.useFilter(fullTestingData, removeFilter);

//            myKnn = new KNN(trainingData, testingData, new ManhattanDistanceFunction(), new StraightClassChooser(), K);
            myKnn = new KNN(trainingData, testingData, new EuclidianDistanceFunction(), new StraightClassChooser(), K);
            bestInstances = myKnn.getBestInstances();
            bestScores = myKnn.getBestScores();

            if(bestInstances.length > 0) {

                bestMatchID = Utils.findInstanceID(trainingData, bestInstances[0], Arrays.asList(excludedAttrs));

                if(bestMatchID != -1){
                    matchedCode = Utils.decodeBase64Code(codeRefMap.get(bestMatchID));
                    resultsCodeParser = new JavaMethodsParser(matchedCode);
                }

            }

            System.out.println("Top " + K + " Best Score(s)");
            for (double bestScore : bestScores) System.out.println(bestScore);

            System.out.println("================= QUERY ================= ");
            System.out.println(queryCode);
            System.out.println("================= TOP MATCH ================= ");
//            System.out.println(matchedCode);

            String candidateTree = TextualTreeVisualizer.visualizeAST(queryCodeParser.getSrcMethod());
            assert resultsCodeParser != null;

            System.out.println(resultsCodeParser.getSrcMethod());
            String matchedTree = TextualTreeVisualizer.visualizeAST(resultsCodeParser.getSrcMethod());

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
