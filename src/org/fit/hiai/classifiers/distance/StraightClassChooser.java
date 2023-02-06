package org.fit.hiai.classifiers.distance;

import org.fit.hiai.classifiers.ClassChooser;
import weka.core.Instance;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class StraightClassChooser implements ClassChooser
{

    public Double getClassFromKNeighbors(double[] bestScores, Instance[] bestInstances, int k)
    {
        // create a hashmap to count values for each label
        HashMap<Double, Integer> results = new HashMap<>();

        // Compute the best class based on frequency
            for(int i = 0; i < k; i++){
                double classValue = bestInstances[i].classValue();
                int count = results.containsKey(classValue) ? results.get(classValue) : 0;
                results.put(classValue, count + 1);
            }

        Double maxClass = Collections.max(
                results.entrySet(),
                new Comparator<Map.Entry<Double,Integer>>(){
                    @Override
                    public int compare(Map.Entry<Double, Integer> o1, Map.Entry<Double, Integer> o2) {
                        return o1.getValue() > o2.getValue()? 1:-1;
                    }
                }).getKey();

        return maxClass;
    }

}
