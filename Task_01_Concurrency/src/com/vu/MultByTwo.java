package com.vu;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class MultByTwo extends MultByN{
    private static final int MULTIPLIER = 2;
    public MultByTwo(CustomBlockingQueue<Integer> input, CustomBlockingQueue<Integer> output) {
        super(input, output);
    }

    @Override
    public int multiply(int input) {
        return input * MULTIPLIER;
    }


}
