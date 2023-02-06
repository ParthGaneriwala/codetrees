package org.fit.hiai.classifiers;

import weka.core.Instance;

/**
 *
 */
public interface ClassChooser
{
    public Double getClassFromKNeighbors(double bestScores[], Instance[] bestInstances, int k);
}
