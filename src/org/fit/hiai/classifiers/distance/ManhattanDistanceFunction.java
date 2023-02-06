package org.fit.hiai.classifiers.distance;

import org.fit.hiai.classifiers.DistanceMeasure;
import weka.core.Attribute;
import weka.core.Instance;

/**
 *
 */
public class ManhattanDistanceFunction implements DistanceMeasure
{
    public double dist(Instance i1, Instance i2)
    {
        double[] attributeDistance = new double[i1.numAttributes()];
        double sum = 0.0;

        for (int i = 0; i < i1.numAttributes(); i++)
        {

            Attribute att = i2.attribute(i);
            double value1 = i1.value(att);
            double value2 = i2.value(att);

            if (att.isNominal())
            {

                String nom1 = att.value((int)value1);
                String nom2 = att.value((int)value2);

                if(nom1.equalsIgnoreCase(nom2))
                    attributeDistance[i] = 0; //if attributes match
                else
                    attributeDistance[i] = 1; //no match


            }
            else if (att.isNumeric())
            {
                attributeDistance[i] = Math.abs(value1 - value2);  //Manhattan(a,b) = Σ abs(a[i] - b[i])
            }

            sum += attributeDistance[i];
        }



        return sum;
    }

}
