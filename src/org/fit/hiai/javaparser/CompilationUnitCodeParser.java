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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * CodeTrees
 * Author: FitzRoi
 * Date Created: 4/28/19
 * Institution: Florida Institute of Technology
 * Purpose:  To parse the entire CompilationUnit of an AST
 */

public class CompilationUnitCodeParser {

    protected File javaFile = null;
    protected CompilationUnit cunit = null;

    public CompilationUnitCodeParser() {
    }

    public CompilationUnitCodeParser(File javaFile) {
        this.javaFile = javaFile;
        try {
            this.cunit = JavaParser.parse(this.javaFile);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public CompilationUnitCodeParser(String javaFileText) {
        try {
            this.cunit = JavaParser.parse(javaFileText);
        } catch (Exception ex) {
            System.err.println("Could not parse " + javaFileText);
        }

    }

    public CompilationUnit getCunit(){
        return cunit;
    }

    /**
     * This method extracts all Annotation Declarations from the ast
     * @return
     */

    public List<AnnotationDeclaration> getAnnotationDeclarations() {
        List<AnnotationDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(AnnotationDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Annotation Member Declarations from the ast
     * @return
     */

    public List<AnnotationMemberDeclaration> getAnnotationMemberDeclarations() {
        List<AnnotationMemberDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(AnnotationMemberDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Array Access Exprs from the ast
     * @return
     */

    public List<ArrayAccessExpr> getArrayAccessExprs() {
        List<ArrayAccessExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ArrayAccessExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Array Creation Exprs from the ast
     * @return
     */

    public List<ArrayCreationExpr> getArrayCreationExprs() {
        List<ArrayCreationExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ArrayCreationExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Array Creation Levels from the ast
     * @return
     */

    public List<ArrayCreationLevel> getArrayCreationLevels() {
        List<ArrayCreationLevel> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ArrayCreationLevel n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Array Initializer Exprs from the ast
     * @return
     */

    public List<ArrayInitializerExpr> getArrayInitializerExprs() {
        List<ArrayInitializerExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ArrayInitializerExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Array Types from the ast
     * @return
     */

    public List<ArrayType> getArrayTypes() {
        List<ArrayType> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ArrayType n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Assert Stmts from the ast
     * @return
     */

    public List<AssertStmt> getAssertStmts() {
        List<AssertStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(AssertStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Assign Exprs from the ast
     * @return
     */

    public List<AssignExpr> getAssignExprs() {
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
     * This method extracts all Binary Exprs from the ast
     * @return
     */

    public List<BinaryExpr> getBinaryExprs() {
        List<BinaryExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(BinaryExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Block Comments from the ast
     * @return
     */

    public List<BlockComment> getBlockComments() {
        List<BlockComment> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(BlockComment n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Block Stmts from the ast
     * @return
     */

    public List<BlockStmt> getBlockStmts() {
        List<BlockStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(BlockStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Boolean Literal Exprs from the ast
     * @return
     */

    public List<BooleanLiteralExpr> getBooleanLiteralExprs() {
        List<BooleanLiteralExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(BooleanLiteralExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Break Stmts from the ast
     * @return
     */

    public List<BreakStmt> getBreakStmts() {
        List<BreakStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(BreakStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Cast Exprs from the ast
     * @return
     */

    public List<CastExpr> getCastExprs() {
        List<CastExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(CastExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Catch Clauses from the ast
     * @return
     */

    public List<CatchClause> getCatchClauses() {
        List<CatchClause> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(CatchClause n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Char Literal Exprs from the ast
     * @return
     */

    public List<CharLiteralExpr> getCharLiteralExprs() {
        List<CharLiteralExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(CharLiteralExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Class Exprs from the ast
     * @return
     */

    public List<ClassExpr> getClassExprs() {
        List<ClassExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ClassExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Class Or Interface Declarations from the ast
     * @return
     */

    public List<ClassOrInterfaceDeclaration> getClassOrInterfaceDeclarations() {
        List<ClassOrInterfaceDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Class Or Interface Types from the ast
     * @return
     */

    public List<ClassOrInterfaceType> getClassOrInterfaceTypes() {
        List<ClassOrInterfaceType> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ClassOrInterfaceType n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Compilation Units from the ast
     * @return
     */

    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(CompilationUnit n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Conditional Exprs from the ast
     * @return
     */

    public List<ConditionalExpr> getConditionalExprs() {
        List<ConditionalExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ConditionalExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Constructor Declarations from the ast
     * @return
     */

    public List<ConstructorDeclaration> getConstructorDeclarations() {
        List<ConstructorDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ConstructorDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Continue Stmts from the ast
     * @return
     */

    public List<ContinueStmt> getContinueStmts() {
        List<ContinueStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ContinueStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Do Stmts from the ast
     * @return
     */

    public List<DoStmt> getDoStmts() {
        List<DoStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(DoStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Double Literal Exprs from the ast
     * @return
     */

    public List<DoubleLiteralExpr> getDoubleLiteralExprs() {
        List<DoubleLiteralExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(DoubleLiteralExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Empty Member Declarationclasss from the ast
     * @return
     */

//    public List<EmptyMemberDeclarationclass> getEmptyMemberDeclarationclasss() {
//        List<EmptyMemberDeclarationclass> statements = new ArrayList<>();
//
//        if(cunit != null) {
//            new VoidVisitorAdapter<Object>() {
//                @Override
//                public void visit(EmptyMemberDeclarationclass n, Object arg) {
//                    super.visit(n, arg);
//                    statements.add(n);
//                }
//            }.visit(cunit, null);
//        }
//        return statements;
//    }


    /**
     * This method extracts all Enclosed Exprs from the ast
     * @return
     */

    public List<EnclosedExpr> getEnclosedExprs() {
        List<EnclosedExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(EnclosedExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Enum Constant Declarations from the ast
     * @return
     */

    public List<EnumConstantDeclaration> getEnumConstantDeclarations() {
        List<EnumConstantDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(EnumConstantDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Enum Declarations from the ast
     * @return
     */

    public List<EnumDeclaration> getEnumDeclarations() {
        List<EnumDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(EnumDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Explicit Constructor Invocation Stmts from the ast
     * @return
     */

    public List<ExplicitConstructorInvocationStmt> getExplicitConstructorInvocationStmts() {
        List<ExplicitConstructorInvocationStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Expression Stmts from the ast
     * @return
     */

    public List<ExpressionStmt> getExpressionStmts() {
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
     * This method extracts all Field Access Exprs from the ast
     * @return
     */

    public List<FieldAccessExpr> getFieldAccessExprs() {
        List<FieldAccessExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(FieldAccessExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Field Declarations from the ast
     * @return
     */

    public List<FieldDeclaration> getFieldDeclarations() {
        List<FieldDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(FieldDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Foreach Stmts from the ast
     * @return
     */

    public List<ForeachStmt> getForeachStmts() {
        List<ForeachStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ForeachStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all For Stmts from the ast
     * @return
     */

    public List<ForStmt> getForStmts() {
        List<ForStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ForStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all If Stmts from the ast
     * @return
     */

    public List<IfStmt> getIfStmts() {
        List<IfStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(IfStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Import Declarations from the ast
     * @return
     */

    public List<ImportDeclaration> getImportDeclarations() {
        List<ImportDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ImportDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Initializer Declarations from the ast
     * @return
     */

    public List<InitializerDeclaration> getInitializerDeclarations() {
        List<InitializerDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(InitializerDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Instance Of Exprs from the ast
     * @return
     */

    public List<InstanceOfExpr> getInstanceOfExprs() {
        List<InstanceOfExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(InstanceOfExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Integer Literal Exprs from the ast
     * @return
     */

    public List<IntegerLiteralExpr> getIntegerLiteralExprs() {
        List<IntegerLiteralExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(IntegerLiteralExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Intersection Types from the ast
     * @return
     */

    public List<IntersectionType> getIntersectionTypes() {
        List<IntersectionType> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(IntersectionType n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Javadoc Comments from the ast
     * @return
     */

    public List<JavadocComment> getJavadocComments() {
        List<JavadocComment> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(JavadocComment n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Labeled Stmts from the ast
     * @return
     */

    public List<LabeledStmt> getLabeledStmts() {
        List<LabeledStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(LabeledStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Lambda Exprs from the ast
     * @return
     */

    public List<LambdaExpr> getLambdaExprs() {
        List<LambdaExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(LambdaExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Line Comments from the ast
     * @return
     */

    public List<LineComment> getLineComments() {
        List<LineComment> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(LineComment n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Local Class Declaration Stmts from the ast
     * @return
     */

    public List<LocalClassDeclarationStmt> getLocalClassDeclarationStmts() {
        List<LocalClassDeclarationStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(LocalClassDeclarationStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Long Literal Exprs from the ast
     * @return
     */

    public List<LongLiteralExpr> getLongLiteralExprs() {
        List<LongLiteralExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(LongLiteralExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Marker Annotation Exprs from the ast
     * @return
     */

    public List<MarkerAnnotationExpr> getMarkerAnnotationExprs() {
        List<MarkerAnnotationExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MarkerAnnotationExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Member Value Pairs from the ast
     * @return
     */

    public List<MemberValuePair> getMemberValuePairs() {
        List<MemberValuePair> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MemberValuePair n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Method Call Exprs from the ast
     * @return
     */

    public List<MethodCallExpr> getMethodCallExprs() {
        List<MethodCallExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MethodCallExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Method Declarations from the ast
     * @return
     */

    public List<MethodDeclaration> getMethodDeclarations() {
        List<MethodDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MethodDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Method Reference Exprs from the ast
     * @return
     */

    public List<MethodReferenceExpr> getMethodReferenceExprs() {
        List<MethodReferenceExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MethodReferenceExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Names from the ast
     * @return
     */

    public List<Name> getNames() {
        List<Name> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(Name n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Name Exprs from the ast
     * @return
     */

    public List<NameExpr> getNameExprs() {
        List<NameExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(NameExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Node Lists from the ast
     * @return
     */

    public List<NodeList> getNodeLists() {
        List<NodeList> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(NodeList n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Normal Annotation Exprs from the ast
     * @return
     */

    public List<NormalAnnotationExpr> getNormalAnnotationExprs() {
        List<NormalAnnotationExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(NormalAnnotationExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Null Literal Exprs from the ast
     * @return
     */

    public List<NullLiteralExpr> getNullLiteralExprs() {
        List<NullLiteralExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(NullLiteralExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Object Creation Exprs from the ast
     * @return
     */

    public List<ObjectCreationExpr> getObjectCreationExprs() {
        List<ObjectCreationExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ObjectCreationExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Package Declarations from the ast
     * @return
     */

    public List<PackageDeclaration> getPackageDeclarations() {
        List<PackageDeclaration> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(PackageDeclaration n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Parameters from the ast
     * @return
     */

    public List<Parameter> getParameters() {
        List<Parameter> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(Parameter n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Primitive Types from the ast
     * @return
     */

    public List<PrimitiveType> getPrimitiveTypes() {
        List<PrimitiveType> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(PrimitiveType n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Return Stmts from the ast
     * @return
     */

    public List<ReturnStmt> getReturnStmts() {
        List<ReturnStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ReturnStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Simple Names from the ast
     * @return
     */

    public List<SimpleName> getSimpleNames() {
        List<SimpleName> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(SimpleName n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Single Member Annotation Exprs from the ast
     * @return
     */

    public List<SingleMemberAnnotationExpr> getSingleMemberAnnotationExprs() {
        List<SingleMemberAnnotationExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(SingleMemberAnnotationExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all String Literal Exprs from the ast
     * @return
     */

    public List<StringLiteralExpr> getStringLiteralExprs() {
        List<StringLiteralExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(StringLiteralExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Super Exprs from the ast
     * @return
     */

    public List<SuperExpr> getSuperExprs() {
        List<SuperExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(SuperExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Switch Entry Stmts from the ast
     * @return
     */

    public List<SwitchEntryStmt> getSwitchEntryStmts() {
        List<SwitchEntryStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(SwitchEntryStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Switch Stmts from the ast
     * @return
     */

    public List<SwitchStmt> getSwitchStmts() {
        List<SwitchStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(SwitchStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Synchronized Stmts from the ast
     * @return
     */

    public List<SynchronizedStmt> getSynchronizedStmts() {
        List<SynchronizedStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(SynchronizedStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all This Exprs from the ast
     * @return
     */

    public List<ThisExpr> getThisExprs() {
        List<ThisExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ThisExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Throw Stmts from the ast
     * @return
     */

    public List<ThrowStmt> getThrowStmts() {
        List<ThrowStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ThrowStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Try Stmts from the ast
     * @return
     */

    public List<TryStmt> getTryStmts() {
        List<TryStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(TryStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Type Exprs from the ast
     * @return
     */

    public List<TypeExpr> getTypeExprs() {
        List<TypeExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(TypeExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Type Parameters from the ast
     * @return
     */

    public List<TypeParameter> getTypeParameters() {
        List<TypeParameter> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(TypeParameter n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Unary Exprs from the ast
     * @return
     */

    public List<UnaryExpr> getUnaryExprs() {
        List<UnaryExpr> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(UnaryExpr n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Union Types from the ast
     * @return
     */

    public List<UnionType> getUnionTypes() {
        List<UnionType> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(UnionType n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Unknown Types from the ast
     * @return
     */

    public List<UnknownType> getUnknownTypes() {
        List<UnknownType> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(UnknownType n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Variable Declaration Exprs from the ast
     * @return
     */

    public List<VariableDeclarationExpr> getVariableDeclarationExprs() {
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
     * This method extracts all Variable Declarators from the ast
     * @return
     */

    public List<VariableDeclarator> getVariableDeclarators() {
        List<VariableDeclarator> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(VariableDeclarator n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Void Types from the ast
     * @return
     */

    public List<VoidType> getVoidTypes() {
        List<VoidType> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(VoidType n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all While Stmts from the ast
     * @return
     */

    public List<WhileStmt> getWhileStmts() {
        List<WhileStmt> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(WhileStmt n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


    /**
     * This method extracts all Wildcard Types from the ast
     * @return
     */

    public List<WildcardType> getWildcardTypes() {
        List<WildcardType> statements = new ArrayList<>();

        if(cunit != null) {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(WildcardType n, Object arg) {
                    super.visit(n, arg);
                    statements.add(n);
                }
            }.visit(cunit, null);
        }
        return statements;
    }


}
