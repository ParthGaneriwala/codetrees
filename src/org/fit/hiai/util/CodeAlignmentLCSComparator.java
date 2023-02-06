package org.fit.hiai.util;

import org.fit.hiai.samilarity.AlignmentResult;

import java.util.Comparator;

public class CodeAlignmentLCSComparator implements Comparator<AlignmentResult> {
    @Override
    public int compare(AlignmentResult o1, AlignmentResult o2) {
        return Double.compare(o2.getLcs(), o1.getLcs()); //sort by the longest stream of matches
    }
}
