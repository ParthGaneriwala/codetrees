# CodeTrees Repository

**by Fitzroy Nembhard, PhD**

This repository stores Java Code, which is part of my research into finding similar code structures. One concept used is known as code clones and is widely discussed in the literature.

We use the javaparser lib to convert Java code to an AST and use the visitor pattern to traverse the nodes in the tree. JavaParser is written by [Federico Tomassetti](https://leanpub.com/u/tomassetti%5D), who has also written a book on how to use the javaparser library. A free copy is here. A copy of the book is in the [repo]()

**Data Processing Summary** In order to find similar code, we used 3 open source repositories. We parse the Java files and separate the methods into files. These sample files can be found in [data](data)

## Quick Navigation

-   [Parse Methods From Java Files](tree/main/src/org/fit/hiai/main/ProgramMethodsProcessor)

-   [Build and Print out Textual AST](src/org/fit/hiai/main/PProgramTreeBuilder)

-   [Convert Java Methods to ARFF File Based on Ordered Node Frequency](src/org/fit/hiai/main/ProgramMethodClassifier)

-   [Encode Java Methods and Align using the LCS Algorithm](src/org/fit/hiai/main/EncodedMethodAligner)

-   [Compare Java Methods using Semi-Isomorphic AST Match](src/org/fit/hiai/main/IsomorphicMethodsMatcher)

-   [Classify Methods using KNN](src/org/fit/hiai/main/ProgramMethodClassifier)

-   Learn more about [WEKA](https://www.cs.waikato.ac.nz/ml/weka/) and the [ARFF file format](https://www.cs.waikato.ac.nz/ml/weka/arff.html) \## JavaParser Nodes

| Nodes                             |                            |
|-----------------------------------|----------------------------|
| AnnotationDeclaration             | IntersectionType           |
| AnnotationMemberDeclaration       | JavadocComment             |
| ArrayAccessExpr                   | LabeledStmt                |
| ArrayCreationExpr                 | LambdaExpr                 |
| ArrayCreationLevel                | LineComment                |
| ArrayInitializerExpr              | LocalClassDeclarationStmt  |
| ArrayType                         | LongLiteralExpr            |
| AssertStmt                        | MarkerAnnotationExpr       |
| AssignExpr                        | MemberValuePair            |
| BinaryExpr                        | MethodCallExpr             |
| BlockComment                      | MethodDeclaration          |
| BlockStmt                         | MethodReferenceExpr        |
| BooleanLiteralExpr                | Name                       |
| BreakStmt                         | NameExpr                   |
| CastExpr                          | NodeList                   |
| CatchClause                       | NormalAnnotationExpr       |
| CharLiteralExpr                   | NullLiteralExpr            |
| ClassExpr                         | ObjectCreationExpr         |
| ClassOrInterfaceDeclaration       | PackageDeclaration         |
| ClassOrInterfaceType              | Parameter                  |
| CompilationUnit                   | PrimitiveType              |
| ConditionalExpr                   | ReturnStmt                 |
| ConstructorDeclaration            | SimpleName                 |
| ContinueStmt                      | SingleMemberAnnotationExpr |
| DoStmt                            | StringLiteralExpr          |
| DoubleLiteralExpr                 | SuperExpr                  |
| EnclosedExpr                      | SwitchEntryStmt            |
| EnumConstantDeclaration           | SwitchStmt                 |
| EnumDeclaration                   | SynchronizedStmt           |
| ExplicitConstructorInvocationStmt | ThisExpr                   |
| ExpressionStmt                    | ThrowStmt                  |
| FieldAccessExpr                   | TryStmt                    |
| FieldDeclaration                  | TypeExpr                   |
| ForeachStmt                       | TypeParameter              |
| ForStmt                           | UnaryExpr                  |
| IfStmt                            | UnionType                  |
| ImportDeclaration                 | UnknownType                |
| InitializerDeclaration            | VariableDeclarationExpr    |
| InstanceOfExpr                    | VariableDeclarator         |
| IntegerLiteralExpr                | VoidType                   |
|                                   | WhileStmt                  |
|                                   | WildcardType               |

## Detailed Code Clone Approaches Implemented

**APPROACH 1: Flattening Tree and Using Sequence Alignment to Find Related Trees**

Method: 1. Encode nodes based on the programming language using 8-bit representation. 2. Construct AST from each Java file 3. Flatten each AST as a sequence of nodes 4. Encode flattened nodes 5. Perform sequence alignment and store results 6. Sort results based on longest common subsequence (LCS) of nodes 7. Secondarily sort results based on alignment penalty scores 8. Report top k alignments based on LCS similarity threshold

**Approach 2: Nearest Neighbor Whole Program Match** Method: 1. Create AST From Java file 2. Compute ordered frequency of node types for selected java files Given a candidate, use KNN to find K nearest neighbors

**Approach 3: Nearest Neighbor Program Method Match Method:** 1. Create AST From Java file 2. Extract all methods from AST and save methods to separate files 3. For each method file, create an AST 4. Compute ordered frequency of node types for selected java files Given a candidate, use KNN to find `K` nearest neighbors

**APPROACH 4: Semi-isomorphic method match** Method: 1. Construct AST `a = {t1, t2, …, tn}` from each Java method file 2. Given a candidate method, construct its AST `c` 3. For each AST `e` in `a`, begin with the root of `e` and `c` and do a preorder traversal, comparing each node type Compute similarity score as numMatches / (numMatches + numMisMatches)

| Run-time Summaries for 20952 files   |        |
|--------------------------------------|--------|
| Sequential Method Aligner            | 14 sec |
| Threaded (20) threads Method Aligner | 8 sec  |
| Semi-isomorphic Matcher              | 11 sec |

**Similar existing approaches:** 1. Build AST then convert to text lines and find LCS 2. Use chunks of source code and find n-neighbors from segments 3. Hash statements and use Smith-Waterman and LCS 4. Hash subtrees from AST into buckets and use a similarity function to find similar code 5. Build an AST and use preorder traversal to build suffix trees to find full sub-tree copies 6. Create AST parse trees and use structural information to create vectors; use nearest neighbors of LSH 7. Parse AST to IRL and use 21 metrics to find similar function blocks 8. Build forest of ASTs; then serializes forest to encoded strings 9. Deep Learn code fragments using RtNN and RvNN. AST\>Binary tree\>Olive tree

Experimental suggestions Find a way to narrow down search. Hashing limits finding changed code

1.  KNN based on node-type frequency; from neighbors; use semi isomorphic match
2.  Other possible binning technique?
