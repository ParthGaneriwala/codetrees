package org.fit.hiai.samilarity;

import org.fit.hiai.constants.ProjectConstants;


import java.util.Arrays;


/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 4/30/19
 * Institution: Florida Institute of Technology
 * Purpose: Align two representations of source code based on encoded (ordered) node types. Uses the Needleman-Wunsch DP algorithm
 * Original sequence alignment code taken from: https://github.com/SleekPanther/sequence-alignment/blob/master/SequenceAlignment.java
 * Code updated to align arrays of strings instead of arrays of characters as in traditional seq alignment
 * */

public class CodeAlignment {

    private int[][] memoTable;
    private int[][][] predecessorIndexes;	//stores the index where the value at memoTable[i][j] came from (diagonal, above or left)

    private int gapPenalty;
    private int numberNumberMismatchPenalty;
    private int numMatches;
    private int lcs;

    public CodeAlignment(int gapPenalty){

        this.gapPenalty = gapPenalty;
        this.numMatches = 0;
        this.lcs = 0;
    }

    public CodeAlignment() {
        this(2);
    }

    public void calculateAndPrintOptimalAlignment(String[] seq1, String[] seq2){
        calcOptimalAlignment(seq1, seq2, true);
    }

    public AlignmentResult calcOptimalAlignment(String[] seq1, String[] seq2, boolean printResults) {
//        String[] seq1 = sanitizeSequence(sequence1Original);
//        String[] seq2 = sanitizeSequence(sequence2Original);

        //Initialize 2D arrays for memoization
        memoTable = new int[seq1.length][seq2.length];
        predecessorIndexes = new int[seq1.length][seq2.length][2];

        //Array bounds are < seq1.length() (not <= ) since both sequences have a blank space @ the start
        //Fill 0th column
        for (int i = 0; i < seq1.length; i++) {	// base case: j = 0
            memoTable[i][0] = i * this.gapPenalty;
            predecessorIndexes[i][0][0] = i - 1;
            predecessorIndexes[i][0][1] = 0;
        }
        //Fill 0th row
        for (int j = 0; j < seq2.length; j++) {	// base case: i = 0
            memoTable[0][j] = j * this.gapPenalty;
            predecessorIndexes[0][j][0] = 0;
            predecessorIndexes[0][j][1] = j - 1;
        }
        //Set upper left with negative predecessor since it has no predecessor
        predecessorIndexes[0][0][0] = -1;
        predecessorIndexes[0][0][1] = -1;


        //Fill rest of memo table
        for (int j = 1; j < seq2.length; j++) {
            for (int i = 1; i < seq1.length; i++) {
//                int alignedStrWithStrPenalty = mismatchPenalty(seq1[i], seq2[j]) + memoTable[i - 1][j - 1];	//case1: seq1[i] & seq2[j] aligned with each other
                int alignedStrWithStrPenalty =  memoTable[i - 1][j - 1];	//case1: seq1[i] & seq2[j] aligned with each other
                int seq1StrWithGap = this.gapPenalty + memoTable[i - 1][j];		//case2: seq1 with gap
                int seq2StrWithGap = this.gapPenalty + memoTable[i][j - 1];		//case3: seq2 with gap

                //Calculate the min of 3 values & store predecessors
                if (alignedStrWithStrPenalty <= seq1StrWithGap && alignedStrWithStrPenalty <= seq2StrWithGap) {			//case1 is the min
                    memoTable[i][j] = alignedStrWithStrPenalty;
                    predecessorIndexes[i][j][0] = i - 1;
                    predecessorIndexes[i][j][1] = j - 1;
                }
                else if (seq1StrWithGap <= alignedStrWithStrPenalty && seq1StrWithGap <= seq2StrWithGap) {	//case2 is the min
                    memoTable[i][j] = seq1StrWithGap;
                    predecessorIndexes[i][j][0] = i - 1;
                    predecessorIndexes[i][j][1] = j;
                }
                else {	//case3 is the min
                    memoTable[i][j] = seq2StrWithGap;
                    predecessorIndexes[i][j][0] = i;
                    predecessorIndexes[i][j][1] = j - 1;
                }
            }
        }

        int minimumPenalty = memoTable[seq1.length-1][seq2.length-1];
        AlignmentResult alignmentResult = findAlignment(seq1, seq2, memoTable); //find the alignment
        alignmentResult.setScore(minimumPenalty);

        if(printResults){
            System.out.println("Aligning \n\"" + Arrays.toString(seq1) + "\"\n with \n\""+Arrays.toString(seq2) + "\"");
            System.out.println("Memoization table");
            printTable(memoTable);
            System.out.println("\nPredecessor table (where the values came from)");
            printTable3D(predecessorIndexes);

            System.out.println("\n" + minimumPenalty + "\t is the Minimum penalty for aligning \""+ Arrays.toString(seq1) +"\" with \""+ Arrays.toString(seq2) +"\"");

            System.out.println("\nOptimal Alignment:\n" + alignmentResult.getSeq1() + "\n" + alignmentResult.getSeq2() +"\n\n");
        }


        return alignmentResult;

    }

    private void printTable(int[][] table) {
        for (int[] row : table) {
            for (int value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }

    private void printTable3D(int[][][] table3D) {
        for (int[][] row : table3D) {
            for (int[] xyPair : row) {
                System.out.print(Arrays.toString(xyPair) + "\t");
            }
            System.out.println();
        }
    }

    //Retrace the memoTable to find the actual alignment, not just the minimum cost
    private AlignmentResult findAlignment(String[] seq1, String[] seq2, int[][] memoTable) {
        String seq1Aligned = ""; 	//Holds the actual sequence with gaps added
        String seq2Aligned = "";

//
        int i = seq1.length - 1;
        int j = seq2.length - 1;

        //Retrace the memoTable calculations. Stops when reaches the start of 1 sequence (so additional gaps may still need to be added to the other)
        while (i > 0 && j > 0) {
//            if (memoTable[i][j] - mismatchPenalty(seq1[i], seq2[j]) == memoTable[i - 1][j - 1]) { //case1: both aligned
            if (memoTable[i][j]  == memoTable[i - 1][j - 1]) { //case1: both aligned
                seq1Aligned = seq1[i] +" "  + seq1Aligned;
                seq2Aligned = seq2[j] + " " + seq2Aligned;

                if(seq1[i].equals(seq2[j])) {
                    numMatches++; //increment the number of matches found
                    lcs++; //Increment longest common sub-sequence if matches found in a row
                }
                else{
                    lcs = 0; //reset lcs if a mismatch was found
                }
                i--;
                j--;

            }
            else if (memoTable[i][j] - this.gapPenalty == memoTable[i - 1][j]) { //case2: seq1 with gap
                seq1Aligned = seq1[i] + " " + seq1Aligned;
                seq2Aligned = ProjectConstants.GAP_STRING + " " +seq2Aligned;
                i--;
            }
            else if (memoTable[i][j] - this.gapPenalty == memoTable[i][j - 1]) { //case3: seq2 with gap
                seq2Aligned = seq2[j] + " " + seq2Aligned;
                seq1Aligned = ProjectConstants.GAP_STRING + " " +seq1Aligned;
                j--;
            }
        }
        //Now i==0 or j==0 or both. Finish by adding any additional leading gaps to the start of the sequence whose pointer ISN'T == 0
        while (i > 0) {		//Seq1 reached the beginning, print rest of seq2 & add gaps to seq2
            seq1Aligned = seq1[i] + " " +seq1Aligned;
            seq2Aligned = ProjectConstants.GAP_STRING + " " +seq2Aligned;
            i--;
        }
        while (j > 0) {		//Seq2 reached the beginning, print rest of seq1 & add gaps to seq2
            seq2Aligned = seq2[j] + " " +seq2Aligned;
            seq1Aligned = ProjectConstants.GAP_STRING + " " +seq1Aligned;
            j--;
        }


        return new AlignmentResult(0, seq1Aligned, seq2Aligned, numMatches, lcs);
    }

}
