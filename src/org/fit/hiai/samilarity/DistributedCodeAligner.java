package org.fit.hiai.samilarity;

import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Distributed Code Aligner is a set of code aligners that
 * accepts a query program code and performs the Needleman-Waunch algorithm
 * on a set of files.
 * Each aligner calls the call method in the CodeAligner that implements
 * the Java Callable.
 * It accepts a queue of links and sends a sub-queue to each aligner
 * for processing.
 * Created by FitzRoi on 5/16/19
 */

public class DistributedCodeAligner {

    private Queue<File> filesList;
    private int taskCount;
    private MethodDeclaration queryMethod;
    private LinkedHashMap<String, String> encodedNodes;

    /**
     *
     * @param filesList a queue of java method files
     * @param taskCount the number of threads/tasks
     * @param queryMethod the query method
     * @param encodedNodes  a binary encoding of AST nodes to use to represent each method file.
     */
    public DistributedCodeAligner(Queue<File> filesList, int taskCount, MethodDeclaration queryMethod, LinkedHashMap<String, String> encodedNodes){
        this.filesList = filesList;
        this.taskCount = taskCount;
        this.queryMethod = queryMethod;
        this.encodedNodes = encodedNodes;
    }

    /**
     * This method splits up the queue into sub-queues based on the number of threads and spawns the tasks
     * @return
     */
    public ArrayList<AlignmentResult> spawnCodeAligners() {

        int queueSize = filesList.size();
        int subQueueSize = queueSize / taskCount;
        ArrayList<Queue<File>> subQueues = new ArrayList<>();
        ArrayList<AlignmentResult> alignments = new ArrayList<>();
        String alignerName;

        for (int i = 0; i < taskCount; i++) {
            Queue<File> subQueue = new LinkedList<>();
            int size = subQueueSize;

            if (i == taskCount - 1)
                size = filesList.size();

            for (int j = 0; j < size; j++) {
                subQueue.add(filesList.remove());
            }

            subQueues.add(subQueue);
        }

        ExecutorService executor = Executors.newFixedThreadPool(taskCount); //create a thread pool

        ArrayList<Future<ArrayList<AlignmentResult>>> futureResults = new ArrayList<>();

        System.out.println("Now spawning " + taskCount +" code aligners with approx. " + subQueueSize +" program files per thread ....");

        for (Queue<File> subQueue : subQueues) {
            alignerName =  "CODE ALIGNER (" + subQueues.indexOf(subQueue) +")";

            CodeAligner task = new CodeAligner(subQueue, alignerName, queryMethod, encodedNodes);
            Future result = executor.submit(task);
            futureResults.add(result);
        }


        for (Future<ArrayList<AlignmentResult>> future : futureResults) {
            try {
                alignments.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        return alignments;
    }

    public int getFileListSize(){
        return filesList.size();
    }
}
