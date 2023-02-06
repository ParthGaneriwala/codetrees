package org.fit.hiai.classifiers.distance;

import org.fit.hiai.classifiers.ClassChooser;
import weka.core.Instance;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class WeightedClassChooser implements ClassChooser
{
    public WeightedClassChooser()
    {

    }
    public Double getClassFromKNeighbors(double[] bestScores, Instance[] bestInstances, int k)
    {
        // create a hashmap to weight values for each label
        HashMap<Double, Double> results = new HashMap<>();

        // weight distances
        for(int i = 0; i < k; i++){
            double classValue = bestInstances[i].classValue();
            double weightedDist = results.containsKey(classValue) ? results.get(classValue) : 0.0;
            double distSquared =  bestScores[i] * bestScores[i];
            weightedDist += distSquared > 0 ? (1/distSquared) : 0.0;
            results.put(classValue, weightedDist);
        }

        // Compute the best class based on weighted selection
        Double maxClass = Collections.max(
                results.entrySet(),
                new Comparator<Map.Entry<Double,Double>>(){
                    @Override
                    public int compare(Map.Entry<Double, Double> o1, Map.Entry<Double, Double> o2) {
                        return o1.getValue() > o2.getValue()? 1:-1;
                    }
                }).getKey();

        return maxClass;
    }

}
