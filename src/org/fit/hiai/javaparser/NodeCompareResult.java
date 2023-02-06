package org.fit.hiai.javaparser;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 5/8/19
 * Institution: Florida Institute of Technology
 * Purpose: Process the results of tree matching
 */

public class NodeCompareResult {
    private String nodeType;
    private int level;
    private Boolean match;

    public NodeCompareResult(){}
    public NodeCompareResult(String nodeType, int level, Boolean match){
        this.nodeType = nodeType;
        this.level = level;
        this.match = match;
    }

    public String getNodeType() {
        return nodeType;
    }

    public int getLevel() {
        return level;
    }

    public Boolean getMatch() {
        return match;
    }
}
