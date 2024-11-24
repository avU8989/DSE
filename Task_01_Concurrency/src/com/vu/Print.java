package com.vu;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Print extends AOperation{
    private int maxSize;
    private static final Logger logger = Logger.getLogger(Copy.class.getName());


    public Print(CustomBlockingQueue<Integer> inputQueue, int maxSize){
        super(inputQueue, null);
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        try {
            Set<Integer> numbers = new TreeSet<>();
            while (numbers.size() < maxSize) {
                Integer num = inputQueue.take();
                numbers.add(num);
                if (numbers.size() == maxSize) {
                    break;
                }
            }

            for (Integer num : numbers) {
                System.out.print(num + ", ");
            }
        } catch (InterruptedException e) {
            logger.warning("Print operation interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
