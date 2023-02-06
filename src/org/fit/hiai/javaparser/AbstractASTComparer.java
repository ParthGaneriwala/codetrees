package org.fit.hiai.javaparser;

import com.github.javaparser.ast.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: May 8, 2019
 * Institution: Florida Institute of Technology
 * Purpose: Abstract class to compare two to ASTs
 */

public abstract class AbstractASTComparer {
    public AbstractASTComparer() { }


    public ArrayList<NodeCompareResult> comparePreOrder(Node node1, Node node2, int level) {

        ArrayList<NodeCompareResult> results1 = new ArrayList<>();
        results1.add(this.process(node1, node2, level++));

        List<Node> childNodes1 = new ArrayList<>();
        List<Node> childNodes2 = new ArrayList<>();

        if(node1 != null)
            childNodes1 = node1.getChildNodes();
        if(node2 != null)
            childNodes2= node2.getChildNodes();

        ArrayList<Node> children1 = new ArrayList<>(childNodes1);
        ArrayList<Node> children2 = new ArrayList<>(childNodes2);


        Iterator i1 = children1.iterator();
        Iterator i2 = children2.iterator();

        //both nodes have children
        while (i1.hasNext() && i2.hasNext()) {
            ArrayList<NodeCompareResult> result2 = comparePreOrder((Node) i1.next(), (Node) i2.next(), level);
            results1.addAll(result2);
        }

        //only the first node has children
        while (i1.hasNext() && !i2.hasNext()) {
            ArrayList<NodeCompareResult> result2 = comparePreOrder((Node) i1.next(), null, level);
            results1.addAll(result2);
        }

        //only the second node has children
        while (!i1.hasNext() && i2.hasNext()) {
            ArrayList<NodeCompareResult> result2 = comparePreOrder(null, (Node) i2.next(), level);
            results1.addAll(result2);
        }


        return results1;
    }


    public abstract NodeCompareResult process(Node node1, Node node2, int level);

}