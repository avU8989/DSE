package com.vu;


import java.util.concurrent.BlockingQueue;

public class MultByThree extends MultByN{
    private static final int MULTIPLIER = 3;

    public MultByThree(CustomBlockingQueue<Integer> input, CustomBlockingQueue<Integer> output){
        super(input, output);
    }

    @Override
    public int multiply(int inputNumber) {
        return inputNumber * MULTIPLIER;
    }

}
