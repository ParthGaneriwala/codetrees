package org.fit.hiai.main;

import org.fit.hiai.constants.ProjectConstants;
import org.fit.hiai.db.DBManager;
import org.fit.hiai.javaparser.CompilationUnitCodeParser;
import org.fit.hiai.javaparser.FilePathResolver;
import org.fit.hiai.javaparser.TextualTreeVisualizer;
import org.fit.hiai.util.Utils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 4/28/19
 * Institution: Florida Institute of Technology
 * Purpose: Create ARFF file from java files by using a set of node types as attributes and calculating their frequency.
 */
public class JavaFileARFFProcessor {

    public static void main(String[] args) {

        File projectDir = new File(System.getProperty("user.dir"));
        String visitorFilePath = projectDir + "/data/visitable_tree_nodes.txt";
        String arffHeader, codeRefHeader;
        String fullProgamArffOutputFile = projectDir + "/data/arff_data/weka_source_tree_data.arff";
        String fullCodeRefOutputFile = projectDir + "/data/arff_data/weka_source_tree_ref_data.txt";

        int count = 0;


        arffHeader = Utils.createARFFHeaderFromFile(visitorFilePath, ProjectConstants.WEKA_AST_RELATION_NAME);
        codeRefHeader = "ID, ENCODED_SRC_CODE\n";

//        System.out.println(arffHeader);
        Utils.writeToFile(fullCodeRefOutputFile, codeRefHeader, ProjectConstants.OVER_WRITE_MODE);
        Utils.writeToFile(fullProgamArffOutputFile, arffHeader, ProjectConstants.OVER_WRITE_MODE);


        FilePathResolver javaPathResolver = new FilePathResolver();

        //Process whole java files

        File wekaDir = new File(projectDir + "/data/weka");
        File javaMl = new File(projectDir + "/data/javaml-0.1.7-src");
        File moa = new File(projectDir + "/data/moa");
        List<File>  fileList = new ArrayList<>(Arrays.asList(wekaDir, javaMl, moa));

        List<File> srcFiles = javaPathResolver.resolvePathsFromList(fileList, ".java");
//        List<File> srcFiles = javaPathResolver.resolvePaths(wekaDir, ".java");

        //loop through all java source file in the directory/subdirectory and process each one

        System.out.println("Now processing " + srcFiles.size() + " Java file(s) .... ");

        for(int i = 0; i < srcFiles.size(); i++) { //srcFiles.size()
//         System.out.println(javaFilePath);
            File javaFilePath = srcFiles.get(i);
            CompilationUnitCodeParser javaCodeParser = null;
            String textualTree;
            String hashMapArff;
            String encodedSrcCode;
            String codeRefData;
            LinkedHashMap<String, Integer> astMap;

            try {
                javaCodeParser = new CompilationUnitCodeParser(javaFilePath); // used to parse an entire compilation unit

            } catch (Exception e) {
                e.printStackTrace();
            }

            if(javaCodeParser != null) {
                textualTree = TextualTreeVisualizer.visualizeAST(javaCodeParser.getCunit()); // for displaying an indented representation of the AST
//                astMap = Utils.createIntHashMapFromFile(visitorFilePath);
//                String astStr;
                astMap = populateASTMap(javaCodeParser, visitorFilePath); //populate a hashMap using the parser of the given java file
                astMap.put("ID", i); //Add an ID field to the hashmap to establish link between instance and source code
//                System.out.println(javaCodeParser.getCunit().toString() +"\n--------------------\n");

                //encode source code and save it to a separate file for reference
                encodedSrcCode = Utils.encodeBase64(javaCodeParser.getCunit().toString());
                codeRefData = i +", " + encodedSrcCode +"\n";
                Utils.writeToFile(fullCodeRefOutputFile, codeRefData, ProjectConstants.APPEND_MODE); // write code references to file in the form: ID, base64EncodeSrcCode
                hashMapArff = Utils.hashMapToARFF(astMap);
//                System.out.println(hashMapArff);
//                System.out.println(textualTree);
                Utils.writeToFile(fullProgamArffOutputFile, hashMapArff, ProjectConstants.APPEND_MODE);
                count++;

            }
        }

        System.out.println( count + " instance(s) of ARFF data saved to file");


    }

    /**
     * This method populates a hashmap by counting different node types in an AST built from the entire program tree
     * @param javaCodeParser
     * @param visitorFilePath
     * @return
     */

    public static LinkedHashMap<String, Integer> populateASTMap(CompilationUnitCodeParser javaCodeParser, String visitorFilePath){

        LinkedHashMap<String, Integer> astMap = Utils.createIntHashMapFromFile(visitorFilePath); //use the set of visitable nodes to create hashMap
//                String astStr;


        astMap.put("AnnotationDeclaration", javaCodeParser.getAnnotationDeclarations().size());
        astMap.put("AnnotationMemberDeclaration", javaCodeParser.getAnnotationMemberDeclarations().size());
        astMap.put("ArrayAccessExpr", javaCodeParser.getArrayAccessExprs().size());
        astMap.put("ArrayCreationExpr", javaCodeParser.getArrayCreationExprs().size());
        astMap.put("ArrayCreationLevel", javaCodeParser.getArrayCreationLevels().size());
        astMap.put("ArrayInitializerExpr", javaCodeParser.getArrayInitializerExprs().size());
        astMap.put("ArrayType", javaCodeParser.getArrayTypes().size());
        astMap.put("AssertStmt", javaCodeParser.getAssertStmts().size());
        astMap.put("AssignExpr", javaCodeParser.getAssignExprs().size());
        astMap.put("BinaryExpr", javaCodeParser.getBinaryExprs().size());
        astMap.put("BlockComment", javaCodeParser.getBlockComments().size());
        astMap.put("BlockStmt", javaCodeParser.getBlockStmts().size());
        astMap.put("BooleanLiteralExpr", javaCodeParser.getBooleanLiteralExprs().size());
        astMap.put("BreakStmt", javaCodeParser.getBreakStmts().size());
        astMap.put("CastExpr", javaCodeParser.getCastExprs().size());
        astMap.put("CatchClause", javaCodeParser.getCatchClauses().size());
        astMap.put("CharLiteralExpr", javaCodeParser.getCharLiteralExprs().size());
        astMap.put("ClassExpr", javaCodeParser.getClassExprs().size());
        astMap.put("ClassOrInterfaceDeclaration", javaCodeParser.getClassOrInterfaceDeclarations().size());
        astMap.put("ClassOrInterfaceType", javaCodeParser.getClassOrInterfaceTypes().size());
        astMap.put("CompilationUnit", javaCodeParser.getCompilationUnits().size());
        astMap.put("ConditionalExpr", javaCodeParser.getConditionalExprs().size());
        astMap.put("ConstructorDeclaration", javaCodeParser.getConstructorDeclarations().size());
        astMap.put("ContinueStmt", javaCodeParser.getContinueStmts().size());
        astMap.put("DoStmt", javaCodeParser.getDoStmts().size());
        astMap.put("DoubleLiteralExpr", javaCodeParser.getDoubleLiteralExprs().size());
//                astMap.put("EmptyMemberDeclarationclass", javaCodeParser.getEmptyMemberDeclarationclasss().size());
        astMap.put("EnclosedExpr", javaCodeParser.getEnclosedExprs().size());
        astMap.put("EnumConstantDeclaration", javaCodeParser.getEnumConstantDeclarations().size());
        astMap.put("EnumDeclaration", javaCodeParser.getEnumDeclarations().size());
        astMap.put("ExplicitConstructorInvocationStmt", javaCodeParser.getExplicitConstructorInvocationStmts().size());
        astMap.put("ExpressionStmt", javaCodeParser.getExpressionStmts().size());
        astMap.put("FieldAccessExpr", javaCodeParser.getFieldAccessExprs().size());
        astMap.put("FieldDeclaration", javaCodeParser.getFieldDeclarations().size());
        astMap.put("ForeachStmt", javaCodeParser.getForeachStmts().size());
        astMap.put("ForStmt", javaCodeParser.getForStmts().size());
        astMap.put("IfStmt", javaCodeParser.getIfStmts().size());
        astMap.put("ImportDeclaration", javaCodeParser.getImportDeclarations().size());
        astMap.put("InitializerDeclaration", javaCodeParser.getInitializerDeclarations().size());
        astMap.put("InstanceOfExpr", javaCodeParser.getInstanceOfExprs().size());
        astMap.put("IntegerLiteralExpr", javaCodeParser.getIntegerLiteralExprs().size());
        astMap.put("IntersectionType", javaCodeParser.getIntersectionTypes().size());
        astMap.put("JavadocComment", javaCodeParser.getJavadocComments().size());
        astMap.put("LabeledStmt", javaCodeParser.getLabeledStmts().size());
        astMap.put("LambdaExpr", javaCodeParser.getLambdaExprs().size());
        astMap.put("LineComment", javaCodeParser.getLineComments().size());
        astMap.put("LocalClassDeclarationStmt", javaCodeParser.getLocalClassDeclarationStmts().size());
        astMap.put("LongLiteralExpr", javaCodeParser.getLongLiteralExprs().size());
        astMap.put("MarkerAnnotationExpr", javaCodeParser.getMarkerAnnotationExprs().size());
        astMap.put("MemberValuePair", javaCodeParser.getMemberValuePairs().size());
        astMap.put("MethodCallExpr", javaCodeParser.getMethodCallExprs().size());
        astMap.put("MethodDeclaration", javaCodeParser.getMethodDeclarations().size());
        astMap.put("MethodReferenceExpr", javaCodeParser.getMethodReferenceExprs().size());
        astMap.put("Name", javaCodeParser.getNames().size());
        astMap.put("NameExpr", javaCodeParser.getNameExprs().size());
        astMap.put("NodeList", javaCodeParser.getNodeLists().size());
        astMap.put("NormalAnnotationExpr", javaCodeParser.getNormalAnnotationExprs().size());
        astMap.put("NullLiteralExpr", javaCodeParser.getNullLiteralExprs().size());
        astMap.put("ObjectCreationExpr", javaCodeParser.getObjectCreationExprs().size());
        astMap.put("PackageDeclaration", javaCodeParser.getPackageDeclarations().size());
        astMap.put("Parameter", javaCodeParser.getParameters().size());
        astMap.put("PrimitiveType", javaCodeParser.getPrimitiveTypes().size());
        astMap.put("ReturnStmt", javaCodeParser.getReturnStmts().size());
        astMap.put("SimpleName", javaCodeParser.getSimpleNames().size());
        astMap.put("SingleMemberAnnotationExpr", javaCodeParser.getSingleMemberAnnotationExprs().size());
        astMap.put("StringLiteralExpr", javaCodeParser.getStringLiteralExprs().size());
        astMap.put("SuperExpr", javaCodeParser.getSuperExprs().size());
        astMap.put("SwitchEntryStmt", javaCodeParser.getSwitchEntryStmts().size());
        astMap.put("SwitchStmt", javaCodeParser.getSwitchStmts().size());
        astMap.put("SynchronizedStmt", javaCodeParser.getSynchronizedStmts().size());
        astMap.put("ThisExpr", javaCodeParser.getThisExprs().size());
        astMap.put("ThrowStmt", javaCodeParser.getThrowStmts().size());
        astMap.put("TryStmt", javaCodeParser.getTryStmts().size());
        astMap.put("TypeExpr", javaCodeParser.getTypeExprs().size());
        astMap.put("TypeParameter", javaCodeParser.getTypeParameters().size());
        astMap.put("UnaryExpr", javaCodeParser.getUnaryExprs().size());
        astMap.put("UnionType", javaCodeParser.getUnionTypes().size());
        astMap.put("UnknownType", javaCodeParser.getUnknownTypes().size());
        astMap.put("VariableDeclarationExpr", javaCodeParser.getVariableDeclarationExprs().size());
        astMap.put("VariableDeclarator", javaCodeParser.getVariableDeclarators().size());
        astMap.put("VoidType", javaCodeParser.getVoidTypes().size());
        astMap.put("WhileStmt", javaCodeParser.getWhileStmts().size());
        astMap.put("WildcardType", javaCodeParser.getWildcardTypes().size());
        astMap.put("classification", 0);
//        System.out.println("Hashmap size: " + astMap.size());
        return astMap;
    }



    public void SaveTreeDataToDB(){
        String dbPath = "";
        org.fit.hiai.db.DBManager dbManager = new DBManager(dbPath);
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
