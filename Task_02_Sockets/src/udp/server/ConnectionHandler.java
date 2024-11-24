package udp.server;

import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ConnectionHandler implements Runnable {
    private static final int PORT2 = 1255;

    private boolean running;
    private static final Logger logger = Logger.getLogger(ConnectionHandler.class.getName());
    private ExecutorService executorService;
    private ScheduledExecutorService scheduler;
    private int numThreads;

    public ConnectionHandler(int numThreads) {
        this.numThreads = numThreads;
        this.executorService = Executors.newFixedThreadPool(numThreads);
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
        this.running = true;

        scheduler.schedule(() -> {
            this.running = false;
            executorService.shutdown();
            System.exit(0);
            System.out.println("ConnectionHandler stopped running after 30 seconds.");
        }, 30, TimeUnit.SECONDS);

    }

    @Override
    public void run() {

        if (!this.running) {
            logger.severe("ConnectionHandler not running");
            return;
        }
        try {
            logger.info("ConnectionHandler started. Ready to accept client connection");
            do {
                DatagramSocket asocket = new DatagramSocket(1254);
                ServerRequestHandler serverRequestHandler = new ServerRequestHandler(asocket);
                executorService.submit(serverRequestHandler);

            } while (this.running);
        } catch (Exception e) {
            logger.severe("Error in ConnectionHandler: " + e.getMessage());
        }
    }

}
