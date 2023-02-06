package org.fit.hiai.main;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.fit.hiai.javaparser.FilePathResolver;
import org.fit.hiai.javaparser.TextualTreeVisualizer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: April 27, 2019
 * Institution: Florida Institute of Technology
 * Purpose: Building ASTs from Java Program files
 */

public class ProgramTreeBuilder {

    public static void main(String[] args) throws IOException {


        File projectDir = new File(System.getProperty("user.dir"));
        File wekaDir = new File(projectDir + "/data/weka");

        System.out.println(projectDir);

        FilePathResolver javaPathResolver = new FilePathResolver();
        List<File> srcFiles = javaPathResolver.resolvePaths(wekaDir, ".java");

     for(File javaFilePath : srcFiles) {
//         System.out.println(javaFilePath);
         CompilationUnit cunit = JavaParser.parse(javaFilePath);
         String textualTree = TextualTreeVisualizer.visualizeAST(cunit);
         System.out.println(textualTree);


     }


//        String fileText = Utils.getDataFromFile("./data/java_src_code/sql_sample/SimpleDBHelper.java");
//        CodeParser codeParser = new CodeParser(fileText);
//        Set<MethodCallExpr> methodCalls = codeParser.getMethodCalls();
////        for(MethodCallExpr methodCallExpr : methodCalls)
////            System.out.println(methodCallExpr +"\n");
//
//        resolveMethods(methodCalls, srcFiles);

    }
}
