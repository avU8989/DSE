package com.vu;

import com.sun.source.tree.Tree;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMerge extends AOperation {
    private CountDownLatch countDownLatch;
    private static final Logger logger = Logger.getLogger(Copy.class.getName());

    public InMerge(CustomBlockingQueue<Integer> input, CustomBlockingQueue<Integer> output, CountDownLatch countDownLatch) {
        super(input, output);
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Set<Integer> sortedNumbers = new TreeSet<>();
                while (sortedNumbers.size() < 3) {
                    Integer value = inputQueue.take();
                    sortedNumbers.add(value);
                }

                for (Integer input : sortedNumbers) {
                    outputQueue.put(input);
                }

                countDownLatch.countDown();

                if (countDownLatch.getCount() == 0) {
                    countDownLatch = new CountDownLatch(3);
                }
            }
        } catch (InterruptedException e) {
            logger.warning("Interrupted in InMerge");
            Thread.currentThread().interrupt();
        }
    }
}

