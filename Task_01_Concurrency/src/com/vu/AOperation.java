package com.vu;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AOperation implements Runnable {
    protected final CustomBlockingQueue<Integer> inputQueue;
    protected final CustomBlockingQueue<Integer> outputQueue;

    public AOperation(CustomBlockingQueue<Integer> inputQueue, CustomBlockingQueue<Integer> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

}
