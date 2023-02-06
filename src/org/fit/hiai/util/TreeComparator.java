package org.fit.hiai.util;

import org.fit.hiai.javaparser.TreeCompareResult;

import java.util.Comparator;

public class TreeComparator implements Comparator<TreeCompareResult> {
    @Override
    public int compare(TreeCompareResult o1, TreeCompareResult o2) {
        return Double.compare(o2.getSimilarity(), o1.getSimilarity()); //rotate to sort in DESC order
    }
}
