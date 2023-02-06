package org.fit.hiai.javaparser;

import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.ArrayList;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 5/8/19
 * Institution: Florida Institute of Technology
 * Purpose: Process the results of tree matching
 */

public class TreeCompareResult {
    private MethodDeclaration javaMethod;
    private String javaFilePath;
    private int numMatches;
    private int numMisMatches;
    private double baxterSimilarity;
    private double similarity;
    private ArrayList<NodeCompareResult> nodeComparisons;


    public TreeCompareResult(MethodDeclaration javaMethod, String javaFilePath, ArrayList<NodeCompareResult> nodeComparisons, int numMatches, int numMisMatches){
        this.javaMethod = javaMethod;
        this.javaFilePath = javaFilePath;
        this.numMatches = numMatches;
        this.numMisMatches = numMisMatches;
        this.similarity = computeSimilarity();
        this.nodeComparisons = nodeComparisons;
    }

    private void computeBaxterSimilarity(int numFriendMisMatches){
        this.baxterSimilarity = (2 * numMatches) / (2 * numMatches + numMisMatches + numFriendMisMatches);
    }

    private double computeSimilarity(){
        return numMatches / (numMatches + numMisMatches);
    }

    public MethodDeclaration getJavaMethod() {
        return javaMethod;
    }

    public int getNumMatches() {
        return numMatches;
    }

    public int getNumMisMatches() {
        return numMisMatches;
    }

    public double getBaxterSimilarity() {
        return baxterSimilarity;
    }

    public double getSimilarity() {
        return similarity;
    }

    public String getJavaFilePath() {
        return javaFilePath;
    }

    public ArrayList<NodeCompareResult> getNodeComparisons() {
        return nodeComparisons;
    }

    public void setNodeComparisons(ArrayList<NodeCompareResult> nodeComparisons) {
        this.nodeComparisons = nodeComparisons;
    }

    public void printNodeComparisonTree(){
        for(NodeCompareResult r : nodeComparisons){
            String tabs = new String(new char[r.getLevel()]).replace("\0", "\t");
            System.out.println(tabs + r.getNodeType() + " " + r.getMatch());
        }
    }
}
