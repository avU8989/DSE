package udp.client;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args){

        int numThreads = 1;
        int totalIterations = 1000;
        int iterationsPerThread = totalIterations / numThreads;
        int numberToSend = 100;
        int dataSizeInKB = 128;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        System.out.println("Sending number to Server : " + numberToSend);
        System.out.println("Sending dataBlob with the size to Server : " + dataSizeInKB);

        System.out.println("Sending number to Server : " + numberToSend);
        for (int i = 0; i < numThreads; i++) {
            executorService.submit(new ClientRequestHandler(new ClientModel(), iterationsPerThread, numberToSend, dataSizeInKB));
        }

        executorService.shutdown();
    }

}
