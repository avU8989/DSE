package com.vu;

import java.util.concurrent.BlockingQueue;

public abstract class MultByN extends AOperation{
    public MultByN(CustomBlockingQueue<Integer> input, CustomBlockingQueue<Integer> output){
        super(input, output);
    }

    @Override
    public void run(){
        do{
            try {
                int number = inputQueue.take();
                int outputNumber = multiply(number);
                outputQueue.put(outputNumber);
            } catch (InterruptedException e) {
            }

        }while(true);
    }

    protected abstract int multiply(int inputNumber);

}