package org.fit.hiai.main;

import com.github.javaparser.ast.body.MethodDeclaration;
import org.fit.hiai.constants.ProjectConstants;
import org.fit.hiai.javaparser.CompilationUnitCodeParser;
import org.fit.hiai.javaparser.FilePathResolver;
import org.fit.hiai.javaparser.StatementExtractor;
import org.fit.hiai.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 4/30/19
 * Institution: Florida Institute of Technology
 * Purpose: Extract and process all methods from a set of java files and save to individual files
 */
public class ProgramMethodsProcessor {

    public static void main(String[] args) {

        File projectDir = new File(System.getProperty("user.dir"));
        String visitorFilePath = projectDir + "/data/visitable_tree_nodes.txt";
        String javaMethodsDataDir = projectDir + "/data/java_test_data/methods/";
        int count = 0;
        FilePathResolver javaPathResolver = new FilePathResolver();


        File wekaDir = new File(projectDir + "/data/weka"); // weka
        File moaDir = new File(projectDir + "/data/moa"); // moa (massive online analysis)
        File javamlDir = new File(projectDir + "/data/javaml-0.1.7-src"); // javaml
        File sourcererDir = new File("/Users/fitzroi/Documents/datasets/repo/4/"); //sourcerer data set samples


        // Choose a directory and retrieve all Java files
        List<File> srcFiles = javaPathResolver.resolvePaths(sourcererDir, ".java");
        String activePackage = "sourcerer_general/"; /*"/javaml_methods/";*/ /* "/weka_methods/"; /* ; "/moa_methods/; */
        StatementExtractor statementExtractor;


        // Loop through all Java source files in the directory/subdirectory and process each one

        System.out.println("Now processing " + srcFiles.size() + " Java file(s) .... ");

        for(int i = 0; i < srcFiles.size(); i++) { //srcFiles.size()
//         System.out.println(javaFilePath);
            File javaFilePath = srcFiles.get(i);
            CompilationUnitCodeParser javaCodeParser = null;

            UUID uuid;
            String methodFileName;
            int dotPos = 0;
            String inputCode = "";

            try {
                javaCodeParser = new CompilationUnitCodeParser(javaFilePath);

            } catch (Exception e) {
                System.out.println("Could not parse file: " + javaFilePath.toPath());
//                inputCode = new String(Files.readAllBytes(javaFilePath.toPath()));
//                    System.out.println(inputCode);
                e.printStackTrace();
            }

            if(javaCodeParser != null) {

                if(i > ProjectConstants.MAX_NUM_FILES) //stop at a certain number of files for experimentation purposes
                    break;
                //Extract and save each method to a separate file
                List<MethodDeclaration> methods = javaCodeParser.getMethodDeclarations();
                for(MethodDeclaration method : methods) {

                    method = (MethodDeclaration) method.removeComment();
                    //make sure the method is not just a declaration and has a full method body

//                    if(method.toString().length() > ProjectConstants.MIN_METHOD_LENGTH) {
                    int loc =  new StatementExtractor().countExpressionStmts(method);

                    System.out.println("number of statements:" + loc);

                    if( loc >= ProjectConstants.MIN_STATEMENT_COUNT && loc < ProjectConstants.MAX_STATEMENT_COUNT) { // save only methods that contain a certain number of statements
                        uuid = UUID.randomUUID();
                        methodFileName = javaFilePath.getName();
                        dotPos = methodFileName.indexOf(".");
                        methodFileName = (dotPos == -1) ? methodFileName : methodFileName.substring(0, dotPos); // extract filename before extension
                        methodFileName += ("." + method.getName() + "." + uuid.toString() + ".txt"); //e.g.: filename.getUsers.7dc53df5-703e-49b3-8670-b1c468f47f1f.txt

                        Utils.writeToFile(javaMethodsDataDir + activePackage + methodFileName, method.toString(), ProjectConstants.OVER_WRITE_MODE); //save the method to its own file
                    }
                }
            }
        }

    }




}
