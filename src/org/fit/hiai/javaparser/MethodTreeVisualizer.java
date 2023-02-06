package org.fit.hiai.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: May 3, 2019
 * Institution: Florida Institute of Technology
 * Purpose: Text visualization of an AST tree derived from a method
 */


public class MethodTreeVisualizer {

    private JSONObject jsonTree = new JSONObject();

    public static void main(String[] args) throws FileNotFoundException {
        File projectDir = new File("./data/java_src_code/sql_sample");

        CompilationUnit cunit;
//        CompilationUnit cunit = JavaParser.parse(new File("./data/java_src_code/sql_sample/Login2.java"));

        String sampleCode = "package mypackage;\n" +
                "\n" +
                "public class MyClass {\n" +
                "\n" +
                "    public void Hello() {\n" +
                "        System.out.println(\"Hello\");" +
                "    }\n" +
                "}";
        cunit = JavaParser.parse(sampleCode);

        cunit.accept(new MethodStructVisitor() {

            @Override
            public void out(Node n, int indentLevel) {
                System.out.println(StringUtils.repeat("\t", indentLevel) + n.getClass().getSimpleName());
            }
        }, 0);


        StringBuilder sb = new StringBuilder();
        sb.append("var treeData=[");
        cunit.accept(new MethodStructVisitor() {

            @Override
            public void out(Node n, int indentLevel) {

//                System.out.println(StringUtils.repeat("\t", indentLevel) + n.getClass().getSimpleName());
                sb.append("{");
                sb.append("text: '")
                        .append(n.getClass().getSimpleName())
                        .append("'")
                        .append(",").append("indent: ")
                        .append("").append(indentLevel)
                        .append("");
                sb.append("}, ");
            }
        }, 0);

        System.out.println(sb.toString());
    }

    public String toJSON(CompilationUnit cunit) throws FileNotFoundException {

//        CompilationUnit cunit = JavaParser.parse(new File("./data/java_src_code/sql_sample/Login2.java"));

        JSONArray children = new JSONArray();
        jsonTree.put("children", children);
        cunit.accept(new MethodStructVisitor() {

            @Override
            public void out(Node n, int indentLevel) {

                System.out.println(StringUtils.repeat("\t", indentLevel) + n.getClass().getSimpleName());
                JSONObject newJsonChild = new JSONObject();
                newJsonChild.put("children", new JSONArray());
                newJsonChild.put("text",n.getClass().getSimpleName());

//                if(children.get())
//                if(child.getBranches().size() > 0)
//                    newJsonChild.put("children", newJsonChildren);
            }
        }, 0);
        return jsonTree.toJSONString();

    }

    /**
     * Visualize the AST starting with the compilation Unit
     * @param cunit
     * @return
     */
   public static String visualizeAST(CompilationUnit cunit){

//        cunit.accept(new MethodStructVisitor() {
//
//            @Override
//            public void out(Node n, int indentLevel) {
////                System.out.println(StringUtils.repeat("\t", indentLevel) + n.getClass().getSimpleName());
//            }
//        }, 0);


        StringBuilder sb = new StringBuilder();
        cunit.accept(new MethodStructVisitor() {

            @Override
            public void out(Node n, int indentLevel) {
                sb.append(StringUtils.repeat("\t", indentLevel)).append(n.getClass().getSimpleName()).append("\n");
            }
        }, 0);

        return sb.toString();
    }

    /**
     * Visualize the AST starting with a method
     * @param method
     * @return
     */
    public static String visualizeAST(MethodDeclaration method){
        StringBuilder sb = new StringBuilder();
        method.accept(new MethodStructVisitor() {

            @Override
            public void out(Node n, int indentLevel) {
                sb.append(StringUtils.repeat("\t", indentLevel)).append(n.getClass().getSimpleName()).append("\n");
            }
        }, 0);

        return sb.toString();
    }


}
