package com.vu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int NTHREDS = 6;
        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
        int maxPrintSize = 60; // Maximum number of elements to print

        // Initialize input and output queues
        CustomBlockingQueue<Integer> inMergeQueue = new CustomBlockingQueue<>();
        CustomBlockingQueue<Integer> copyQueue = new CustomBlockingQueue<>();
        CustomBlockingQueue<Integer> printQueue = new CustomBlockingQueue<>();
        CustomBlockingQueue<Integer> queue2 = new CustomBlockingQueue<>();
        CustomBlockingQueue<Integer> queue3 = new CustomBlockingQueue<>();
        CustomBlockingQueue<Integer> queue5 = new CustomBlockingQueue<>();
        CountDownLatch latch = new CountDownLatch(3);

        copyQueue.put(1);
        Copy copy = new Copy(copyQueue, List.of(printQueue, queue2, queue3, queue5));
        MultByTwo multByTwo = new MultByTwo(queue2, inMergeQueue);
        MultByThree multByThree = new MultByThree(queue3, inMergeQueue);
        MultByFive multByFive = new MultByFive(queue5, inMergeQueue);
        InMerge inMerge = new InMerge(inMergeQueue, copyQueue, latch);
        Print print = new Print(printQueue, maxPrintSize);

        List<Runnable> runnables = List.of(copy, multByTwo, multByThree, multByFive, inMerge, print);

        for(Runnable runnable : runnables) {
            executor.execute(runnable);
            System.out.println("Running " + runnable.getClass().getName());
        }

        executor.shutdown();

        // Wait until all threads are finish
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}

