package org.fit.hiai.samilarity;

import com.github.javaparser.ast.body.MethodDeclaration;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 5/1/19
 * Institution: Florida Institute of Technology
 * Purpose: Easy storage/passing of code alignment results
 */

public class AlignmentResult {
    private double score;
    private String seq1, seq2;
    private MethodDeclaration Seq1SrcCode = null, seq2SrcCode = null;
    private int numMatches, lcs;
    private String seq1SrcCodePath, seq2SrcCodePath;

    public AlignmentResult(double score, String seq1, String seq2, int numMatches, int lcs){
        this.score = score;
        this.seq1 = seq1;
        this.seq2 = seq2;
        this.numMatches = numMatches;
        this.lcs = lcs;
    }

    public AlignmentResult(){}

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getSeq1() {
        return seq1;
    }

    public void setSeq1(String seq1) {
        this.seq1 = seq1;
    }

    public String getSeq2() {
        return seq2;
    }

    public void setSeq2(String seq2) {
        this.seq2 = seq2;
    }

    public MethodDeclaration getSeq1SrcCode() {
        return Seq1SrcCode;
    }

    public void setSeq1SrcCode(MethodDeclaration seq1SrcCode) {
        Seq1SrcCode = seq1SrcCode;
    }

    public MethodDeclaration getSeq2SrcCode() {
        return seq2SrcCode;
    }

    public void setSeq2SrcCode(MethodDeclaration seq2SrcCode) {
        this.seq2SrcCode = seq2SrcCode;
    }

    public int getNumMatches() {
        return numMatches;
    }

    public void setNumMatches(int numMatches) {
        this.numMatches = numMatches;
    }

    public String getSeq1SrcCodePath() {
        return seq1SrcCodePath;
    }

    public void setSeq1SrcCodePath(String seq1SrcCodePath) {
        this.seq1SrcCodePath = seq1SrcCodePath;
    }

    public String getSeq2SrcCodePath() {
        return seq2SrcCodePath;
    }

    public void setSeq2SrcCodePath(String seq2SrcCodePath) {
        this.seq2SrcCodePath = seq2SrcCodePath;
    }

    public int getLcs() {
        return lcs;
    }
}
