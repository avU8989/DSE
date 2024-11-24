package com.vu;

import java.util.concurrent.BlockingQueue;

public class MultByFive extends MultByN{
    private static final int MULTIPLIER = 5;

    public MultByFive(CustomBlockingQueue<Integer> input, CustomBlockingQueue<Integer> output){
        super(input, output);
    }

    @Override
    public int multiply(int inputNumber) {
        return inputNumber * MULTIPLIER;
    }


}
