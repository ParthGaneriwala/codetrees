package org.fit.hiai.javaparser;


import com.github.javaparser.ast.Node;

/**
 * Code Trees
 * Author: FitzRoi
 * Date Created: March 2018
 * Institution: Florida Institute of Technology
 * Purpose: iterate nodes in an AST
 */

public class NodeIterator {
    public interface NodeHandler {
        boolean handle(Node node);
    }

    private NodeHandler nodeHandler;

    public NodeIterator(NodeHandler nodeHandler) {
        this.nodeHandler = nodeHandler;
    }

    public void explore(Node node) {
        if (nodeHandler.handle(node)) {
            for (Node child : node.getChildNodes()) {
                explore(child);
            }
        }
    }
}