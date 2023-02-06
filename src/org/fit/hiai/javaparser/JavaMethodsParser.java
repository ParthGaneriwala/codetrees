package org.fit.hiai.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.fit.hiai.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 4/28/19
 * Institution: Florida Institute of Technology
 * Purpose: To parse a file containing a java method and extract a list of certain nodes
 */
public class JavaMethodsParser {

    protected File javaFile = null;
    protected MethodDeclaration srcMethod = null;

    public JavaMethodsParser() {}

    public JavaMethodsParser(File javaFile) throws FileNotFoundException {
        this.javaFile = javaFile;
        String fileContents = Utils.getDataFromFile(javaFile);
        this.srcMethod =  (MethodDeclaration) JavaParser.parseBodyDeclaration(fileContents);
        this.srcMethod.removeComment();
    }

    public JavaMethodsParser(String javaFileText) {
        try {
            this.srcMethod = (MethodDeclaration) JavaParser.parseBodyDeclaration(javaFileText);
        } catch (Exception ex) {
            System.err.println("Could not parse " + javaFileText);
        }

    }

    public MethodDeclaration getSrcMethod() {
        return srcMethod;
    }

    /**
     * This method extracts all Annotation Declarations from a given method
     * @return
     */

    public List<AnnotationDeclaration> getAnnotationDeclarationsInMethod(){
        List<AnnotationDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(AnnotationDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Annotation Member Declarations from a given method
     * @return
     */

    public List<AnnotationMemberDeclaration> getAnnotationMemberDeclarationsInMethod(){
        List<AnnotationMemberDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(AnnotationMemberDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Array Access Exprs from a given method
     * @return
     */

    public List<ArrayAccessExpr> getArrayAccessExprsInMethod(){
        List<ArrayAccessExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ArrayAccessExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Array Creation Exprs from a given method
     * @return
     */

    public List<ArrayCreationExpr> getArrayCreationExprsInMethod(){
        List<ArrayCreationExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ArrayCreationExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Array Creation Levels from a given method
     * @return
     */

    public List<ArrayCreationLevel> getArrayCreationLevelsInMethod(){
        List<ArrayCreationLevel> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ArrayCreationLevel n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Array Initializer Exprs from a given method
     * @return
     */

    public List<ArrayInitializerExpr> getArrayInitializerExprsInMethod(){
        List<ArrayInitializerExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ArrayInitializerExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Array Types from a given method
     * @return
     */

    public List<ArrayType> getArrayTypesInMethod(){
        List<ArrayType> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ArrayType n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Assert Stmts from a given method
     * @return
     */

    public List<AssertStmt> getAssertStmtsInMethod(){
        List<AssertStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(AssertStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Assign Exprs from a given method
     * @return
     */

    public List<AssignExpr> getAssignExprsInMethod(){
        List<AssignExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(AssignExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Binary Exprs from a given method
     * @return
     */

    public List<BinaryExpr> getBinaryExprsInMethod(){
        List<BinaryExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(BinaryExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Block Comments from a given method
     * @return
     */

    public List<BlockComment> getBlockCommentsInMethod(){
        List<BlockComment> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(BlockComment n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Block Stmts from a given method
     * @return
     */

    public List<BlockStmt> getBlockStmtsInMethod(){
        List<BlockStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(BlockStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Boolean Literal Exprs from a given method
     * @return
     */

    public List<BooleanLiteralExpr> getBooleanLiteralExprsInMethod(){
        List<BooleanLiteralExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(BooleanLiteralExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Break Stmts from a given method
     * @return
     */

    public List<BreakStmt> getBreakStmtsInMethod(){
        List<BreakStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(BreakStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Cast Exprs from a given method
     * @return
     */

    public List<CastExpr> getCastExprsInMethod(){
        List<CastExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(CastExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Catch Clauses from a given method
     * @return
     */

    public List<CatchClause> getCatchClausesInMethod(){
        List<CatchClause> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(CatchClause n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Char Literal Exprs from a given method
     * @return
     */

    public List<CharLiteralExpr> getCharLiteralExprsInMethod(){
        List<CharLiteralExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(CharLiteralExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Class Exprs from a given method
     * @return
     */

    public List<ClassExpr> getClassExprsInMethod(){
        List<ClassExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ClassExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Class Or Interface Declarations from a given method
     * @return
     */

    public List<ClassOrInterfaceDeclaration> getClassOrInterfaceDeclarationsInMethod(){
        List<ClassOrInterfaceDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Class Or Interface Types from a given method
     * @return
     */

    public List<ClassOrInterfaceType> getClassOrInterfaceTypesInMethod(){
        List<ClassOrInterfaceType> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ClassOrInterfaceType n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Compilation Units from a given method
     * @return
     */

    public List<CompilationUnit> getCompilationUnitsInMethod(){
        List<CompilationUnit> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(CompilationUnit n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Conditional Exprs from a given method
     * @return
     */

    public List<ConditionalExpr> getConditionalExprsInMethod(){
        List<ConditionalExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ConditionalExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Constructor Declarations from a given method
     * @return
     */

    public List<ConstructorDeclaration> getConstructorDeclarationsInMethod(){
        List<ConstructorDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ConstructorDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Continue Stmts from a given method
     * @return
     */

    public List<ContinueStmt> getContinueStmtsInMethod(){
        List<ContinueStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ContinueStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Do Stmts from a given method
     * @return
     */

    public List<DoStmt> getDoStmtsInMethod(){
        List<DoStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(DoStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Double Literal Exprs from a given method
     * @return
     */

    public List<DoubleLiteralExpr> getDoubleLiteralExprsInMethod(){
        List<DoubleLiteralExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(DoubleLiteralExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Enclosed Exprs from a given method
     * @return
     */

    public List<EnclosedExpr> getEnclosedExprsInMethod(){
        List<EnclosedExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(EnclosedExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Enum Constant Declarations from a given method
     * @return
     */

    public List<EnumConstantDeclaration> getEnumConstantDeclarationsInMethod(){
        List<EnumConstantDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(EnumConstantDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Enum Declarations from a given method
     * @return
     */

    public List<EnumDeclaration> getEnumDeclarationsInMethod(){
        List<EnumDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(EnumDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Explicit Constructor Invocation Stmts from a given method
     * @return
     */

    public List<ExplicitConstructorInvocationStmt> getExplicitConstructorInvocationStmtsInMethod(){
        List<ExplicitConstructorInvocationStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ExplicitConstructorInvocationStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Expression Stmts from a given method
     * @return
     */

    public List<ExpressionStmt> getExpressionStmtsInMethod(){
        List<ExpressionStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ExpressionStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Field Access Exprs from a given method
     * @return
     */

    public List<FieldAccessExpr> getFieldAccessExprsInMethod(){
        List<FieldAccessExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(FieldAccessExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Field Declarations from a given method
     * @return
     */

    public List<FieldDeclaration> getFieldDeclarationsInMethod(){
        List<FieldDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(FieldDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Foreach Stmts from a given method
     * @return
     */

    public List<ForeachStmt> getForeachStmtsInMethod(){
        List<ForeachStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ForeachStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all For Stmts from a given method
     * @return
     */

    public List<ForStmt> getForStmtsInMethod(){
        List<ForStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ForStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all If Stmts from a given method
     * @return
     */

    public List<IfStmt> getIfStmtsInMethod(){
        List<IfStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(IfStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Import Declarations from a given method
     * @return
     */

    public List<ImportDeclaration> getImportDeclarationsInMethod(){
        List<ImportDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ImportDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Initializer Declarations from a given method
     * @return
     */

    public List<InitializerDeclaration> getInitializerDeclarationsInMethod(){
        List<InitializerDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(InitializerDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Instance Of Exprs from a given method
     * @return
     */

    public List<InstanceOfExpr> getInstanceOfExprsInMethod(){
        List<InstanceOfExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(InstanceOfExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Integer Literal Exprs from a given method
     * @return
     */

    public List<IntegerLiteralExpr> getIntegerLiteralExprsInMethod(){
        List<IntegerLiteralExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(IntegerLiteralExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Intersection Types from a given method
     * @return
     */

    public List<IntersectionType> getIntersectionTypesInMethod(){
        List<IntersectionType> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(IntersectionType n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Javadoc Comments from a given method
     * @return
     */

    public List<JavadocComment> getJavadocCommentsInMethod(){
        List<JavadocComment> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(JavadocComment n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Labeled Stmts from a given method
     * @return
     */

    public List<LabeledStmt> getLabeledStmtsInMethod(){
        List<LabeledStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(LabeledStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Lambda Exprs from a given method
     * @return
     */

    public List<LambdaExpr> getLambdaExprsInMethod(){
        List<LambdaExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(LambdaExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Line Comments from a given method
     * @return
     */

    public List<LineComment> getLineCommentsInMethod(){
        List<LineComment> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(LineComment n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Local Class Declaration Stmts from a given method
     * @return
     */

    public List<LocalClassDeclarationStmt> getLocalClassDeclarationStmtsInMethod(){
        List<LocalClassDeclarationStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(LocalClassDeclarationStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Long Literal Exprs from a given method
     * @return
     */

    public List<LongLiteralExpr> getLongLiteralExprsInMethod(){
        List<LongLiteralExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(LongLiteralExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Marker Annotation Exprs from a given method
     * @return
     */

    public List<MarkerAnnotationExpr> getMarkerAnnotationExprsInMethod(){
        List<MarkerAnnotationExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MarkerAnnotationExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Member Value Pairs from a given method
     * @return
     */

    public List<MemberValuePair> getMemberValuePairsInMethod(){
        List<MemberValuePair> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MemberValuePair n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Method Call Exprs from a given method
     * @return
     */

    public List<MethodCallExpr> getMethodCallExprsInMethod(){
        List<MethodCallExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodCallExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Method Declarations from a given method
     * @return
     */

    public List<MethodDeclaration> getMethodDeclarationsInMethod(){
        List<MethodDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Method Reference Exprs from a given method
     * @return
     */

    public List<MethodReferenceExpr> getMethodReferenceExprsInMethod(){
        List<MethodReferenceExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodReferenceExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Names from a given method
     * @return
     */

    public List<Name> getNamesInMethod(){
        List<Name> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(Name n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Name Exprs from a given method
     * @return
     */

    public List<NameExpr> getNameExprsInMethod(){
        List<NameExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(NameExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Node Lists from a given method
     * @return
     */

    public List<NodeList> getNodeListsInMethod(){
        List<NodeList> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(NodeList n, Void arg) {
                super.visit(n, arg);
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Normal Annotation Exprs from a given method
     * @return
     */

    public List<NormalAnnotationExpr> getNormalAnnotationExprsInMethod(){
        List<NormalAnnotationExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(NormalAnnotationExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Null Literal Exprs from a given method
     * @return
     */

    public List<NullLiteralExpr> getNullLiteralExprsInMethod(){
        List<NullLiteralExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(NullLiteralExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Object Creation Exprs from a given method
     * @return
     */

    public List<ObjectCreationExpr> getObjectCreationExprsInMethod(){
        List<ObjectCreationExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ObjectCreationExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Package Declarations from a given method
     * @return
     */

    public List<PackageDeclaration> getPackageDeclarationsInMethod(){
        List<PackageDeclaration> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(PackageDeclaration n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Parameters from a given method
     * @return
     */

    public List<Parameter> getParametersInMethod(){
        List<Parameter> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(Parameter n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Primitive Types from a given method
     * @return
     */

    public List<PrimitiveType> getPrimitiveTypesInMethod(){
        List<PrimitiveType> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(PrimitiveType n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Return Stmts from a given method
     * @return
     */

    public List<ReturnStmt> getReturnStmtsInMethod(){
        List<ReturnStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ReturnStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Simple Names from a given method
     * @return
     */

    public List<SimpleName> getSimpleNamesInMethod(){
        List<SimpleName> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(SimpleName n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Single Member Annotation Exprs from a given method
     * @return
     */

    public List<SingleMemberAnnotationExpr> getSingleMemberAnnotationExprsInMethod(){
        List<SingleMemberAnnotationExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(SingleMemberAnnotationExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all String Literal Exprs from a given method
     * @return
     */

    public List<StringLiteralExpr> getStringLiteralExprsInMethod(){
        List<StringLiteralExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(StringLiteralExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Super Exprs from a given method
     * @return
     */

    public List<SuperExpr> getSuperExprsInMethod(){
        List<SuperExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(SuperExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Switch Entry Stmts from a given method
     * @return
     */

    public List<SwitchEntryStmt> getSwitchEntryStmtsInMethod(){
        List<SwitchEntryStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(SwitchEntryStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Switch Stmts from a given method
     * @return
     */

    public List<SwitchStmt> getSwitchStmtsInMethod(){
        List<SwitchStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(SwitchStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Synchronized Stmts from a given method
     * @return
     */

    public List<SynchronizedStmt> getSynchronizedStmtsInMethod(){
        List<SynchronizedStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(SynchronizedStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all This Exprs from a given method
     * @return
     */

    public List<ThisExpr> getThisExprsInMethod(){
        List<ThisExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ThisExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Throw Stmts from a given method
     * @return
     */

    public List<ThrowStmt> getThrowStmtsInMethod(){
        List<ThrowStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ThrowStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Try Stmts from a given method
     * @return
     */

    public List<TryStmt> getTryStmtsInMethod(){
        List<TryStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(TryStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Type Exprs from a given method
     * @return
     */

    public List<TypeExpr> getTypeExprsInMethod(){
        List<TypeExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(TypeExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Type Parameters from a given method
     * @return
     */

    public List<TypeParameter> getTypeParametersInMethod(){
        List<TypeParameter> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(TypeParameter n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Unary Exprs from a given method
     * @return
     */

    public List<UnaryExpr> getUnaryExprsInMethod(){
        List<UnaryExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(UnaryExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Union Types from a given method
     * @return
     */

    public List<UnionType> getUnionTypesInMethod(){
        List<UnionType> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(UnionType n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Unknown Types from a given method
     * @return
     */

    public List<UnknownType> getUnknownTypesInMethod(){
        List<UnknownType> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(UnknownType n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Variable Declaration Exprs from a given method
     * @return
     */

    public List<VariableDeclarationExpr> getVariableDeclarationExprsInMethod(){
        List<VariableDeclarationExpr> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(VariableDeclarationExpr n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Variable Declarators from a given method
     * @return
     */

    public List<VariableDeclarator> getVariableDeclaratorsInMethod(){
        List<VariableDeclarator> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(VariableDeclarator n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Void Types from a given method
     * @return
     */

    public List<VoidType> getVoidTypesInMethod(){
        List<VoidType> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(VoidType n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all While Stmts from a given method
     * @return
     */

    public List<WhileStmt> getWhileStmtsInMethod(){
        List<WhileStmt> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(WhileStmt n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }


    /**
     * This method extracts all Wildcard Types from a given method
     * @return
     */

    public List<WildcardType> getWildcardTypesInMethod(){
        List<WildcardType> statements = new ArrayList<>();

        srcMethod.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(WildcardType n, Void arg) {
                super.visit(n, arg);
                n.removeComment();
                statements.add(n);
            }
        }, null);

        return statements;

    }





}
