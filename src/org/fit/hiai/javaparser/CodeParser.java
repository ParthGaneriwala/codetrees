package org.fit.hiai.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * recomindproject
 * Author: FitzRoi
 * Date Created: March 2018
 * Institution: Florida Institute of Technology
 * Purpose: Partial fulfillment of PhD
 * Recomind is a RECommender system to help programmers design with security in MIND.

 * This class uses the open source javaparser to parse java code and extract important
 * information such as methodCalls and statements
 */

public class CodeParser {

    protected File javaFile = null;
    protected CompilationUnit cunit = null;

    public CodeParser() {
    }

    public CodeParser(File javaFile) throws FileNotFoundException {
        this.javaFile = javaFile;
        this.cunit = JavaParser.parse(this.javaFile);
    }

    public CodeParser(String javaFileText) {
        try {
            this.cunit = JavaParser.parse(javaFileText);
        } catch (Exception ex) {
            System.err.println("Could not parse " + javaFileText);
        }

    }

    /**
     * This method extracts all the method calls from the ast
     * @return
     */
    public Set<MethodCallExpr> getMethodCalls() {
        Set<MethodCallExpr> methodCalls = new HashSet<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MethodCallExpr n, Object arg) {
                    super.visit(n, arg);
                    methodCalls.add(n);
                }
            }.visit(cunit, null);
        }
        return methodCalls;
    }


    /**
     * This method extracts all the import declarations from the ast
     * @return
     */
    public Set<ImportDeclaration> getImports() {
        Set<ImportDeclaration> imports = new HashSet<>();
        new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(ImportDeclaration n, Object arg) {
                super.visit(n, arg);
                imports.add(n);
            }
        }.visit(cunit, null);
        return imports;
    }

    /**
     * This method extracts all the assigned expressions from the ast
     * assigned expressions are of the form: a = b
     * @return
     */

    public List<AssignExpr> getAssignedExprs() {
        List<AssignExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(AssignExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all the statement expressions from the ast
     * @return
     */

    public List<ExpressionStmt> getStatements() {
        List<ExpressionStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ExpressionStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all the declared expressions from the ast
     * assigned expressions are of the form: int a = b
     * @return
     */

    public List<VariableDeclarationExpr> getDeclaredExprs() {
        List<VariableDeclarationExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(VariableDeclarationExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts from the ast all the statement expressions that contain an '=' sign
     * It can be used to check if a variable is being sanitized
     * @return
     */

    public List<ExpressionStmt> getAssignedStatements() {
        List<ExpressionStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ExpressionStmt n, Object arg) {
                    super.visit(n, arg);
                    n.removeComment();
                    if(n.getExpression().toString().contains("="))
                        statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }

    /**
     * This method gets all assignmentStatements within a method.
     * It can be used to check if a variable is being sanitized within a method
     * @param method
     * @return
     */
    public List<ExpressionStmt> getAssignedStmtsInMethod(MethodDeclaration method){
        List<ExpressionStmt> statements = new ArrayList<>();

        method.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ExpressionStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                if(n.getExpression().toString().contains("="))
                    statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method gets all the variables that contain a certain command string
     * @param cmdParam the command string to check against the variables
     * @param exceptStr a string to exclude in the search
     * @return
     */
    public List<ExpressionStmt> getVariableOccurrences(String cmdParam, String exceptStr){
        List<ExpressionStmt> statements = new ArrayList<>();
        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ExpressionStmt n, Object arg) {
                    super.visit(n, arg);
                    String exprStr = n.getExpression().toString();

                    //exclude the statement containing the specified string
                    if(exceptStr != null) {
                        if (exprStr.contains(cmdParam) && !exprStr.contains(exceptStr)) {
                            statements.add(n);
                        }
                    }
                    else {
                        if (exprStr.contains(cmdParam)) {
                            statements.add(n);
                        }
                    }
                }
            }.visit(cunit, null);
        }
        return statements;
    }



    /**
     * Checks if a method is in the set of method calls in a compilationUnit
     * @param methodName
     * @param methodCalls
     * @return
     */
    public boolean inMethodList(String methodName, Set<MethodCallExpr> methodCalls) {
        for (MethodCallExpr methodCallExpr : methodCalls) {
            if (methodCallExpr.getName().toString().equalsIgnoreCase(methodName))
                return true;
        }

        return false;
    }

    /**
     * Checks if a variable is sent to a function where it could be potentially sanitized
     * @param varName
     * @param methodCalls
     * @return
     */
    public boolean inMethodArgsList(String varName, Set<MethodCallExpr> methodCalls) {
        boolean isFound = false;

        for (MethodCallExpr methodCallExpr : methodCalls) {
            NodeList params = methodCallExpr.getArguments();
            for (Object param1 : params) {
                String param = param1.toString();
                if (param.contains(varName))
                    isFound = true;
            }
        }

        return isFound;
    }

    /**
     * checks if the supplied java code is parsed successfully
     * @return
     */
    public boolean isCodeParsed(){
        return cunit != null;
    }


    public CompilationUnit getCunit() {
        return cunit;
    }

}
