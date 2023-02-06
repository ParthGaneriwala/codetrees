package org.fit.hiai.main;

import org.fit.hiai.constants.ProjectConstants;
import org.fit.hiai.db.DBManager;
import org.fit.hiai.javaparser.*;
import org.fit.hiai.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 4/28/19
 * Institution: Florida Institute of Technology
 * Purpose: Create ARFF file from java files by using a set of node types as attributes and calculating their frequency.
 */
public class JavaMethodsARFFProcessor {

    public static void main(String[] args) {

        File projectDir = new File(System.getProperty("user.dir"));
        String visitorFilePath = projectDir + "/data/visitable_tree_nodes.txt";
        String arffHeader, codeRefHeader;
        String programMethodsArffOutputFile = projectDir + "/data/arff_data/weka_program_methods_data.arff";
        String methodCodeRefOutputFile = projectDir + "/data/arff_data/weka_source_methods_ref_data.txt";

        int count = 0;


        //create ARFF from sourcerer datasets
        String pathToSourcererRepo = "/Users/fitzroi/Documents/datasets/repo/4/";
        String sourcererARFFoutputFile = projectDir + "/data/arff_data/sourcerer_repo/sourcerer_sample_arff.arff";
        String sourcererARFFrefOutputFile = projectDir + "/data/arff_data/sourcerer_repo/sourcerer_sample_arff_ref_data.txt";


        arffHeader = Utils.createARFFHeaderFromFile(visitorFilePath, ProjectConstants.WEKA_AST_RELATION_NAME);
        codeRefHeader = "ID, ENCODED_SRC_CODE\n";

//        System.out.println(arffHeader);

//        Utils.writeToFile(programMethodsArffOutputFile, arffHeader, ProjectConstants.OVER_WRITE_MODE);
        Utils.writeToFile(sourcererARFFoutputFile, arffHeader, ProjectConstants.OVER_WRITE_MODE);

        Utils.writeToFile(sourcererARFFrefOutputFile, codeRefHeader, ProjectConstants.OVER_WRITE_MODE);
//        Utils.writeToFile(methodCodeRefOutputFile, codeRefHeader, ProjectConstants.OVER_WRITE_MODE);

        FilePathResolver javaPathResolver = new FilePathResolver();

        //Process java methods extracted from whole java files and saved as text files
//        File methodSamplesDir = new File(projectDir + "/data/java_test_data/methods");
        File methodSamplesDir = new File(projectDir + "/data/java_test_data/methods/sourcerer_general");
        List<File> srcFiles = javaPathResolver.resolvePaths(methodSamplesDir, ".txt");

        //loop through all java source files in the directory/subdirectory and process each one

        System.out.println("Now processing " + srcFiles.size() + " Java method file(s) .... ");
        for(int i = 0; i < srcFiles.size(); i++) { //srcFiles.size()
//         System.out.println(javaFilePath);
            File javaFilePath = srcFiles.get(i);
            JavaMethodsParser javaCodeParser = null;
            String textualTree;
            String hashMapArff;
            String encodedSrcCode;
            String codeRefData;
            LinkedHashMap<String, Integer> astMap;

            try {
                javaCodeParser= new JavaMethodsParser(javaFilePath);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if(javaCodeParser != null) {
                textualTree = TextualTreeVisualizer.visualizeAST(javaCodeParser.getSrcMethod());
//                astMap = Utils.createIntHashMapFromFile(visitorFilePath);
//                String astStr;

//                System.out.println(javaCodeParser.getCunit().toString() +"\n--------------------\n");

                //encode source code and save it to a separate file for reference
//                System.out.println(javaCodeParser.getSrcMethod().toString());

                //only use methods log enough; had a file with only the method declaration without body
                if( new StatementExtractor().countExpressionStmts(javaCodeParser.getSrcMethod()) >= ProjectConstants.MIN_STATEMENT_COUNT) {
//                    System.out.println("Code length: " + javaCodeParser.getSrcMethod().toString().length());
                    astMap = populateASTMap(javaCodeParser, visitorFilePath); //populate a hashMap using the parser of the given java file
                    astMap.put("ID", i); //Add an ID field to the hashmap to establish link between instance and source code
                    encodedSrcCode = Utils.encodeBase64(javaCodeParser.getSrcMethod().toString());
                    codeRefData = i + ", " + encodedSrcCode + "\n";
//                    Utils.writeToFile(methodCodeRefOutputFile, codeRefData, ProjectConstants.APPEND_MODE); // write code references to file in the form: ID, base64EncodeSrcCode
                    Utils.writeToFile(sourcererARFFrefOutputFile, codeRefData, ProjectConstants.APPEND_MODE); // write code references to file in the form: ID, base64EncodeSrcCode


                    hashMapArff = Utils.hashMapToARFF(astMap);
//                System.out.println(hashMapArff);
//                System.out.println(textualTree);
//                    Utils.writeToFile(programMethodsArffOutputFile, hashMapArff, ProjectConstants.APPEND_MODE);
                    Utils.writeToFile(sourcererARFFoutputFile, hashMapArff, ProjectConstants.APPEND_MODE);
                    count++;
                }

            }
        }
        System.out.println( count + " instance(s) of ARFF data saved to file");


    }

    /**
     * This method populates a hashmap by counting different node types in an AST built from a selected program method
     * @param javaCodeParser
     * @param visitorFilePath
     * @return
     */

    public static LinkedHashMap<String, Integer> populateASTMap(JavaMethodsParser javaCodeParser, String visitorFilePath){

        LinkedHashMap<String, Integer> astMap = Utils.createIntHashMapFromFile(visitorFilePath); //use the set of visitable nodes to create hashMap
//                String astStr;


        astMap.put("AnnotationDeclaration", javaCodeParser.getAnnotationDeclarationsInMethod().size());
        astMap.put("AnnotationMemberDeclaration", javaCodeParser.getAnnotationMemberDeclarationsInMethod().size());
        astMap.put("ArrayAccessExpr", javaCodeParser.getArrayAccessExprsInMethod().size());
        astMap.put("ArrayCreationExpr", javaCodeParser.getArrayCreationExprsInMethod().size());
        astMap.put("ArrayCreationLevel", javaCodeParser.getArrayCreationLevelsInMethod().size());
        astMap.put("ArrayInitializerExpr", javaCodeParser.getArrayInitializerExprsInMethod().size());
        astMap.put("ArrayType", javaCodeParser.getArrayTypesInMethod().size());
        astMap.put("AssertStmt", javaCodeParser.getAssertStmtsInMethod().size());
        astMap.put("AssignExpr", javaCodeParser.getAssignExprsInMethod().size());
        astMap.put("BinaryExpr", javaCodeParser.getBinaryExprsInMethod().size());
        astMap.put("BlockComment", javaCodeParser.getBlockCommentsInMethod().size());
        astMap.put("BlockStmt", javaCodeParser.getBlockStmtsInMethod().size());
        astMap.put("BooleanLiteralExpr", javaCodeParser.getBooleanLiteralExprsInMethod().size());
        astMap.put("BreakStmt", javaCodeParser.getBreakStmtsInMethod().size());
        astMap.put("CastExpr", javaCodeParser.getCastExprsInMethod().size());
        astMap.put("CatchClause", javaCodeParser.getCatchClausesInMethod().size());
        astMap.put("CharLiteralExpr", javaCodeParser.getCharLiteralExprsInMethod().size());
        astMap.put("ClassExpr", javaCodeParser.getClassExprsInMethod().size());
        astMap.put("ClassOrInterfaceDeclaration", javaCodeParser.getClassOrInterfaceDeclarationsInMethod().size());
        astMap.put("ClassOrInterfaceType", javaCodeParser.getClassOrInterfaceTypesInMethod().size());
        astMap.put("CompilationUnit", javaCodeParser.getCompilationUnitsInMethod().size());
        astMap.put("ConditionalExpr", javaCodeParser.getConditionalExprsInMethod().size());
        astMap.put("ConstructorDeclaration", javaCodeParser.getConstructorDeclarationsInMethod().size());
        astMap.put("ContinueStmt", javaCodeParser.getContinueStmtsInMethod().size());
        astMap.put("DoStmt", javaCodeParser.getDoStmtsInMethod().size());
        astMap.put("DoubleLiteralExpr", javaCodeParser.getDoubleLiteralExprsInMethod().size());
//                astMap.put("EmptyMemberDeclarationclass", javaCodeParser.getEmptyMemberDeclarationclasss().size());
        astMap.put("EnclosedExpr", javaCodeParser.getEnclosedExprsInMethod().size());
        astMap.put("EnumConstantDeclaration", javaCodeParser.getEnumConstantDeclarationsInMethod().size());
        astMap.put("EnumDeclaration", javaCodeParser.getEnumDeclarationsInMethod().size());
        astMap.put("ExplicitConstructorInvocationStmt", javaCodeParser.getExplicitConstructorInvocationStmtsInMethod().size());
        astMap.put("ExpressionStmt", javaCodeParser.getExpressionStmtsInMethod().size());
        astMap.put("FieldAccessExpr", javaCodeParser.getFieldAccessExprsInMethod().size());
        astMap.put("FieldDeclaration", javaCodeParser.getFieldDeclarationsInMethod().size());
        astMap.put("ForeachStmt", javaCodeParser.getForeachStmtsInMethod().size());
        astMap.put("ForStmt", javaCodeParser.getForStmtsInMethod().size());
        astMap.put("IfStmt", javaCodeParser.getIfStmtsInMethod().size());
        astMap.put("ImportDeclaration", javaCodeParser.getImportDeclarationsInMethod().size());
        astMap.put("InitializerDeclaration", javaCodeParser.getInitializerDeclarationsInMethod().size());
        astMap.put("InstanceOfExpr", javaCodeParser.getInstanceOfExprsInMethod().size());
        astMap.put("IntegerLiteralExpr", javaCodeParser.getIntegerLiteralExprsInMethod().size());
        astMap.put("IntersectionType", javaCodeParser.getIntersectionTypesInMethod().size());
        astMap.put("JavadocComment", javaCodeParser.getJavadocCommentsInMethod().size());
        astMap.put("LabeledStmt", javaCodeParser.getLabeledStmtsInMethod().size());
        astMap.put("LambdaExpr", javaCodeParser.getLambdaExprsInMethod().size());
        astMap.put("LineComment", javaCodeParser.getLineCommentsInMethod().size());
        astMap.put("LocalClassDeclarationStmt", javaCodeParser.getLocalClassDeclarationStmtsInMethod().size());
        astMap.put("LongLiteralExpr", javaCodeParser.getLongLiteralExprsInMethod().size());
        astMap.put("MarkerAnnotationExpr", javaCodeParser.getMarkerAnnotationExprsInMethod().size());
        astMap.put("MemberValuePair", javaCodeParser.getMemberValuePairsInMethod().size());
        astMap.put("MethodCallExpr", javaCodeParser.getMethodCallExprsInMethod().size());
        astMap.put("MethodDeclaration", javaCodeParser.getMethodDeclarationsInMethod().size());
        astMap.put("MethodReferenceExpr", javaCodeParser.getMethodReferenceExprsInMethod().size());
        astMap.put("Name", javaCodeParser.getNamesInMethod().size());
        astMap.put("NameExpr", javaCodeParser.getNameExprsInMethod().size());
        astMap.put("NodeList", javaCodeParser.getNodeListsInMethod().size());
        astMap.put("NormalAnnotationExpr", javaCodeParser.getNormalAnnotationExprsInMethod().size());
        astMap.put("NullLiteralExpr", javaCodeParser.getNullLiteralExprsInMethod().size());
        astMap.put("ObjectCreationExpr", javaCodeParser.getObjectCreationExprsInMethod().size());
        astMap.put("PackageDeclaration", javaCodeParser.getPackageDeclarationsInMethod().size());
        astMap.put("Parameter", javaCodeParser.getParametersInMethod().size());
        astMap.put("PrimitiveType", javaCodeParser.getPrimitiveTypesInMethod().size());
        astMap.put("ReturnStmt", javaCodeParser.getReturnStmtsInMethod().size());
        astMap.put("SimpleName", javaCodeParser.getSimpleNamesInMethod().size());
        astMap.put("SingleMemberAnnotationExpr", javaCodeParser.getSingleMemberAnnotationExprsInMethod().size());
        astMap.put("StringLiteralExpr", javaCodeParser.getStringLiteralExprsInMethod().size());
        astMap.put("SuperExpr", javaCodeParser.getSuperExprsInMethod().size());
        astMap.put("SwitchEntryStmt", javaCodeParser.getSwitchEntryStmtsInMethod().size());
        astMap.put("SwitchStmt", javaCodeParser.getSwitchStmtsInMethod().size());
        astMap.put("SynchronizedStmt", javaCodeParser.getSynchronizedStmtsInMethod().size());
        astMap.put("ThisExpr", javaCodeParser.getThisExprsInMethod().size());
        astMap.put("ThrowStmt", javaCodeParser.getThrowStmtsInMethod().size());
        astMap.put("TryStmt", javaCodeParser.getTryStmtsInMethod().size());
        astMap.put("TypeExpr", javaCodeParser.getTypeExprsInMethod().size());
        astMap.put("TypeParameter", javaCodeParser.getTypeParametersInMethod().size());
        astMap.put("UnaryExpr", javaCodeParser.getUnaryExprsInMethod().size());
        astMap.put("UnionType", javaCodeParser.getUnionTypesInMethod().size());
        astMap.put("UnknownType", javaCodeParser.getUnknownTypesInMethod().size());
        astMap.put("VariableDeclarationExpr", javaCodeParser.getVariableDeclarationExprsInMethod().size());
        astMap.put("VariableDeclarator", javaCodeParser.getVariableDeclaratorsInMethod().size());
        astMap.put("VoidType", javaCodeParser.getVoidTypesInMethod().size());
        astMap.put("WhileStmt", javaCodeParser.getWhileStmtsInMethod().size());
        astMap.put("WildcardType", javaCodeParser.getWildcardTypesInMethod().size());
        astMap.put("classification", 0);
//        System.out.println("Hashmap size: " + astMap.size());
        return astMap;
    }



    public void SaveTreeDataToDB(){
        String dbPath = "";
        DBManager dbManager = new DBManager(dbPath);
        Connection connection;
        Statement stmt;

        try {
            //check if database and tables exist
            if (!dbManager.databaseExists(dbPath)) {
                connection = dbManager.connectToDB();

                //create tables if they do not exist
                String programTreeTableSql = ProjectConstants.CREATE_TASK_TABLE_SQL;
                stmt = connection.createStatement();
                stmt.executeUpdate(programTreeTableSql);
                stmt.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
