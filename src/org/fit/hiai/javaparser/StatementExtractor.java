package org.fit.hiai.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.google.common.base.Strings;

import java.io.File;
import java.io.IOException;

/**
 * Code Trees
 * Author: FitzRoi
 * Date Created: March 2018
 * Institution: Florida Institute of Technology
 * Purpose: to extract statements from an AST for counting purposes, etc.
 */


public class StatementExtractor {

    public StatementExtractor(){}
    private int lineCount = 0;

    public void statementsByLine(File projectDir) {
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));

            try {
                new NodeIterator(node -> {
                    if (node instanceof ExpressionStmt) {
//                            System.out.println(" [Lines " + node.getBeginLine() + " - " + node.getEndLine() + " ] " + node);
                        System.out.println(node +"//////");
                        return false;
                    } else {
                        return true;
                    }
                }).explore(JavaParser.parse(file));
                System.out.println(); // empty line
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }).explore(projectDir);
    }

    public int countExpressionStmts(File javaFile) {

            try {
                new NodeIterator(node -> {
                    if (node instanceof ExpressionStmt) {
                       lineCount++;
                        return false;
                    } else {
                        return true;
                    }
                }).explore(JavaParser.parse(javaFile));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return lineCount;
    }

    public int countExpressionStmts(MethodDeclaration method) {

        try {
            new NodeIterator(node -> {
                if (node instanceof ExpressionStmt) {
                    lineCount++;
                    return false;
                } else {
                    return true;
                }
            }).explore(method);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lineCount;
    }

    public static void main(String[] args) {
        File projectDir = new File("./data/java_src_code/sql_sample");
//        statementsByLine(projectDir);
    }
}
