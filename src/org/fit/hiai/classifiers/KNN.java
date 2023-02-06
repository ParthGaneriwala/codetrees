package org.fit.hiai.classifiers;


import org.fit.hiai.classifiers.distance.EuclidianDistanceFunction;
import org.fit.hiai.classifiers.distance.ManhattanDistanceFunction;
import org.fit.hiai.classifiers.distance.StraightClassChooser;
import org.fit.hiai.classifiers.distance.WeightedClassChooser;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.neighboursearch.KDTree;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class KNN
{
    NumberFormat intf = new DecimalFormat("0000");
    NumberFormat douf = new DecimalFormat("0.000");

    double[] bestScores = null;
    Instance[] bestInstances = null;
    ClassChooser chooser = null;
    DistanceMeasure distanceMeasure = null;

    /**
     * The KNN class sets up and runs a 10-fold cross validation test on the data provided with the accompanying distance and
     * classchooser methods.
     *
     * @param trainingfile
     * @param testingfile
     * @param distanceMeasure
     * @param chooser
     * @param K
     */
    public KNN(String trainingfile, String testingfile, DistanceMeasure distanceMeasure, ClassChooser chooser, int K)
    {
        this.chooser = chooser;
        this.distanceMeasure = distanceMeasure;

        DataSource training = null;
        DataSource testing = null;
        try
        {
            // read in the data
            training = new DataSource(trainingfile);
            Instances trainingData = training.getDataSet();
            if (trainingData.classIndex() == -1)
                trainingData.setClassIndex(trainingData.numAttributes() - 1);

            testing = new DataSource(testingfile);
            Instances fullTestingData = testing.getDataSet();
            if (fullTestingData.classIndex() == -1)
                fullTestingData.setClassIndex(fullTestingData.numAttributes() - 1);

            /** the naive algorithm runs very slowly, so we'll put the training data into
             *  a KDTree to reduce the number of distances we compute.
             */
            KDTree tree = new KDTree();
            tree.setInstances(trainingData);


            // initialize storage to keep track of top K values and instances
            bestScores = new double[K];
            bestInstances = new Instance[K];

            for (int i = 0; i < K; i++)
            {
                bestScores[i] = Double.MAX_VALUE;
            }

            long startTime = System.currentTimeMillis();
            int overallCorrect = 0;

            // Do a 10-fold cross validation
            for (int test = 0; test< 10; test++)
            {
                /** select a set of test instances from the test data **/
                Instances testInstances = fullTestingData.testCV(10,test);
                int numTestSamples = testInstances.size();

                /** run the KNN algorithm using the test data against the training data, returning the number correct **/
                int numCorrect = getNumCorrect(tree, K, test, testInstances, numTestSamples);

                overallCorrect += numCorrect;
                System.out.println();
            }

            // here is our overall performance
            System.out.println("Overall Correct: " + overallCorrect + "/" + fullTestingData.size() + " = " + douf.format(overallCorrect*1.0/fullTestingData.size()) + " " + ((System.currentTimeMillis()-startTime)/1000));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * The KNN class sets up and runs a 10-fold cross validation test on the data provided with the accompanying distance and
     * classchooser methods.
     *
     * @param trainingData instances
     * @param testingData instances
     * @param distanceMeasure
     * @param chooser
     * @param K
     */
    public KNN(Instances trainingData, Instances testingData, DistanceMeasure distanceMeasure, ClassChooser chooser, int K)
    {
        this.chooser = chooser;
        this.distanceMeasure = distanceMeasure;


        try
        {

            if (trainingData.classIndex() == -1)
                trainingData.setClassIndex(trainingData.numAttributes() - 1);

            if (testingData.classIndex() == -1)
                testingData.setClassIndex(testingData.numAttributes() - 1);

            /** the naive algorithm runs very slowly, so we'll put the training data into
             *  a KDTree to reduce the number of distances we compute.
             */
            KDTree tree = new KDTree();
            tree.setInstances(trainingData);


            // initialize storage to keep track of top K values and instances
            bestScores = new double[K];
            bestInstances = new Instance[K];

            for (int i = 0; i < K; i++)
            {
                bestScores[i] = Double.MAX_VALUE;
            }

            long startTime = System.currentTimeMillis();
            int overallCorrect = 0;

//            // Do a 10-fold cross validation
//            for (int test = 0; test< 10; test++)
//            {
//                /** select a set of test instances from the test data **/
//                Instances testInstances = testingData.testCV(1,test);
//                int numTestSamples = testInstances.size();
//
//                /** run the KNN algorithm using the test data against the training data, returning the number correct **/
//                int numCorrect = getNumCorrect(tree, K, test, testInstances, numTestSamples);
//
//                overallCorrect += numCorrect;
//                System.out.println();
//            }

            int numTestSamples = testingData.size();

            System.out.println(trainingData.size() +" training instance(s)");
            System.out.println(testingData.size() +" testing instance(s)");
//
            /** run the KNN algorithm using the test data against the training data, returning the number correct **/
            int numCorrect = getNumCorrect(tree, K, 0, testingData, numTestSamples);
            overallCorrect += numCorrect;



            // here is our overall performance
            System.out.println("Overall Correct: " + numCorrect + "/" + testingData.size() + " = " + douf.format(overallCorrect*1.0/testingData.size()) + " " + ((System.currentTimeMillis()-startTime)/1000) + " sec.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method
     * @param tree
     * @param k
     * @param test
     * @param testInstances
     * @param numTestSamples
     * @return
     * @throws Exception
     */
    private int getNumCorrect(KDTree tree, int k, int test, Instances testInstances, int numTestSamples) throws Exception
    {

        long testStartTime = System.currentTimeMillis();
        int numCorrect = 0;


        for (Instance target : testInstances)
        {

            /** the naive algorithm runs very slowly, so we'll put the training data into
             *  a KDTree to reduce the number of distances we compute.
             *
             *  Here, we select the closest 200 points (using WEKA's built-in euclidian distance) to
             *  reduce the number of samples we need to look at.
             *
             *  Comment out the next two lines, and uncomment the third line to
             *  make a run without the KDTree.
             */
            Instances nearestInstances = tree.kNearestNeighbours(target, 200);
//             Instances trainingData = tree.getInstances();
            for (Instance neighbor : nearestInstances)
//            for (Instance neighbor : trainingData)
            {

                /**
                 *
                 * C O M P U T E    T H E   D I S T A N C E ! ! ! !
                 *
                 */
                double score = distanceMeasure.dist(target, neighbor);

                // now see where that distance score fits within the top K distances
                if (score < bestScores[k - 1])
                {
                    // find the place to put it
                    for (int j = 0; j < k; j++)
                    {
                        if (score < bestScores[j])
                        {
                            for (int h = k - 1; h > j; h--)
                            {
                                bestScores[h] = bestScores[h - 1];
                                bestInstances[h] = bestInstances[h - 1];
                            }

                            bestScores[j] = score;
                            bestInstances[j] = neighbor;

                            break;
                        }
                    }
                }

//                System.out.println("Distance score: " +score);
            }

            /**
             * Given the K nearest neighbors we have found, let's compute the maximum
             * using the chooser function passed in.
             */
            Double maxClass = chooser.getClassFromKNeighbors(bestScores, bestInstances, k);

            if (target.classValue() == maxClass)
            {
                numCorrect++;
            }

            System.out.println("\rFold " + intf(test) + " | Correct: " + intf(numCorrect) + "/" + numTestSamples + " = " + douf.format(numCorrect*1.0/numTestSamples) + " " + intf((System.currentTimeMillis()-testStartTime)/1000) + " sec.");

            //reset the variables in case of multiple folds
//            for (int i = 0; i < k; i++)
//            {
//                bestScores[i] = Double.MAX_VALUE;
//                bestInstances[i] = null;
//            }

//            System.out.println()
        }
        return numCorrect;
    }


     private String report()
    {
        return "";
    }

    private String intf(Number val)
    {
        String ret = intf.format(val).replaceAll("\\G0", " ");

        if ( ret.equals("    "))
            return "   0";
        else
            return ret;
    }




    /**
     * The main function
     * @param args
     */
    public static void main(String[] args)
    {
        KNN knn;
        System.out.println("\nMANHATTAN STRAGIHT-CLASS");
        knn = new KNN("./data/KDDTrain+.arff","./data/KDDTest+.arff", new ManhattanDistanceFunction(), new StraightClassChooser(), 15);

        System.out.println("\nMANHATTAN WEIGHTED-CLASS");
        knn = new KNN("./data/KDDTrain+.arff","./data/KDDTest+.arff", new ManhattanDistanceFunction(), new WeightedClassChooser(), 15);

        System.out.println("\nEUCLIDIAN STRAGIHT-CLASS");
        knn = new KNN("./data/KDDTrain+.arff","./data/KDDTest+.arff", new EuclidianDistanceFunction(), new StraightClassChooser(), 15);

        System.out.println("\nEUCLIDIAN WEIGHTED-CLASS");
        knn = new KNN("./data/KDDTrain+.arff","./data/KDDTest+.arff", new EuclidianDistanceFunction(), new WeightedClassChooser(), 15);
    }

    public Instance[] getBestInstances(){
        return bestInstances;
    }

    public double[] getBestScores() {
        return bestScores;
    }
}
