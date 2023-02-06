package org.fit.hiai.javaparser;

import com.github.javaparser.ast.Node;
import org.fit.hiai.constants.ProjectConstants;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: May 8, 2019
 * Institution: Florida Institute of Technology
 * Purpose: To compare two ASTs by node type
 */

public class ASTComparer extends AbstractASTComparer {
    @Override
    public NodeCompareResult process(Node node1, Node node2, int level) {

        String node1Type = node1==null ? ProjectConstants.MISSING_ATTRIBUTE_WILDCARD : node1.getMetaModel().getTypeName();
        String node2Type = node2==null ? ProjectConstants.MISSING_ATTRIBUTE_WILDCARD : node2.getMetaModel().getTypeName();

        if(node1Type.equals(node2Type))
            return new NodeCompareResult(node1Type, level, true);
        else
            return new NodeCompareResult(node1Type, level, false);
    }
}
