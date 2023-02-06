package org.fit.hiai.classifiers;

import weka.core.Instance;

/**
 *
 */
public interface DistanceMeasure
{
    public double dist(Instance i1, Instance i2);
}
