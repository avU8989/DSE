package com.vu;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class Copy extends AOperation {
    List<CustomBlockingQueue<Integer>> outputs;
    private static final Logger logger = Logger.getLogger(Copy.class.getName());

    public Copy(CustomBlockingQueue<Integer> input, List<CustomBlockingQueue<Integer>> outputs) {
        super(input, null);
        this.outputs = outputs;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int firstNum = inputQueue.take();
                for (var output : this.outputs) {
                    output.put(firstNum);
                }
            }
        } catch (InterruptedException e) {
            logger.warning("Interrupted in Copy");
        }
    }
}