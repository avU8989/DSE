package tcp.client;

import tcp.messages.XMLMessageHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args){
        ClientModel model = new ClientModel();
        int numThreads = 1;
        int totalIterations = 1000;
        int iterationsPerThread = totalIterations / numThreads;
        int numberToSend = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        System.out.println("Sending number to Server : " + numberToSend);
        for (int i = 0; i < numThreads; i++) {
            executorService.submit(new ClientRequestHandler(new XMLMessageHandler(), model, iterationsPerThread, numberToSend, 1));
        }

        executorService.shutdown();
    }
}
