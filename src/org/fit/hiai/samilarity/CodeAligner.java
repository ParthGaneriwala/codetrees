package org.fit.hiai.samilarity;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.fit.hiai.constants.ProjectConstants;
import org.fit.hiai.javaparser.MethodNodesExtractor;
import org.fit.hiai.util.CodeAlignmentComparator;
import org.fit.hiai.util.CodeAlignmentLCSComparator;
import org.fit.hiai.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;

public class CodeAligner implements Callable {
    private Queue<File> fileList;
    private String alignerName;
    private MethodDeclaration queryMethod;
    private LinkedHashMap<String, String> encodedNodes;

    public CodeAligner(Queue<File> fileList, String alignerName, MethodDeclaration queryMethod, LinkedHashMap<String, String> encodedNodes) {
        this.fileList = fileList;
        this.alignerName = alignerName;
        this.queryMethod = queryMethod;
        this.encodedNodes = encodedNodes;
    }


    public ArrayList<AlignmentResult> call() {
        ArrayList<AlignmentResult> alignments = new ArrayList<>();
        ArrayList<AlignmentResult> topAlignments;

        MethodNodesExtractor methodNodesExtractor = new MethodNodesExtractor();
        List<String> methodNodes1, methodNodes2;
        String[] nodeStructSeq1, nodeStructSeq2;

        methodNodes1 = methodNodesExtractor.getOrderedNodes(queryMethod);
        int totalCodeSequences = fileList.size();

        while (!fileList.isEmpty()) {

            File method2FilePath = fileList.remove();
            String methodSample2 = Utils.getDataFromFile(method2FilePath); //pick a file from the queue
            MethodDeclaration method2 = (MethodDeclaration) JavaParser.parseBodyDeclaration(methodSample2); // cast BodyDeclaration as subclass MethodDeclaration
            methodNodes2 = methodNodesExtractor.getOrderedNodes(method2);

            nodeStructSeq1 = Utils.getNodeStructureSeq(encodedNodes, methodNodes1);
            nodeStructSeq2 = Utils.getNodeStructureSeq(encodedNodes, methodNodes2);
            CodeAlignment codeAlignment = new CodeAlignment();
            AlignmentResult alignmentResult = codeAlignment.calcOptimalAlignment(nodeStructSeq1, nodeStructSeq2, false);
            alignmentResult.setSeq2SrcCode(method2); //add the src code to the alignment result for ref
            alignmentResult.setSeq2SrcCodePath(method2FilePath.getPath());

            alignments.add(alignmentResult);
        }

        System.out.println(alignerName + " finished aligning " + totalCodeSequences + " sequences");

        //return only the top k values
        alignments.sort(new CodeAlignmentComparator().thenComparing(new CodeAlignmentLCSComparator())); // sort by penalty then by LCS
        topAlignments = new ArrayList<>(alignments.subList(0, 10));
        alignments.clear();

        return topAlignments;
    }
}
