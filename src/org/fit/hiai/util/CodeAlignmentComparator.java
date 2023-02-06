package org.fit.hiai.util;

import org.fit.hiai.samilarity.AlignmentResult;

import java.util.Comparator;

public class CodeAlignmentComparator implements Comparator<AlignmentResult> {
    @Override
    public int compare(AlignmentResult o1, AlignmentResult o2) {
        return Double.compare(o1.getScore(), o2.getScore()); // DESC order because lower alignment penalty suggests higher similarity
//        return Double.compare(o1.getLcs(), o2.getLcs()); //
    }
}
