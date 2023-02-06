package org.fit.hiai.javaparser;

import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ArrayType;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 4/30/19
 * Institution: Florida Institute of Technology
 * Purpose: Extract an ordered list of nodes from a given method
 */


public class MethodNodesExtractor {

    public MethodNodesExtractor(){}

    public List<String> getOrderedNodes(MethodDeclaration m){

        StringBuilder sb = new StringBuilder();
        List<String> childNodes = new ArrayList<>();
        m.accept(new MethodStructVisitor() {

            @Override
            public void out(Node n, int indentLevel) {
//                sb.append(StringUtils.repeat("\t", indentLevel)).append(n.getClass().getSimpleName()).append("\n");
//                sb.append(n.getClass().getSimpleName()).append("\n"); //return a tabbed list of nodes
                childNodes.add(n.getClass().getSimpleName());
            }
        }, 0);

//        return sb.toString();
        return childNodes;
    }
}
