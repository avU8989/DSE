package tcp.server;

import tcp.messages.XMLMessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
listens for incoming client connections and manages lifecycle of client sessions
accepts client connections and delegates handling each client session
 */
public class ConnectionHandler implements Runnable {
    private static final int PORT = 1254;
    private ServerSocket serverSocket;
    private boolean running;
    private static final Logger logger = Logger.getLogger(ConnectionHandler.class.getName());
    private ExecutorService executorService;
    private ScheduledExecutorService scheduler;
    private int numThreads;

    public ConnectionHandler(int numThreads){
        try{
            serverSocket = new ServerSocket(PORT);
        } catch(IOException e){
            logger.severe("Error creating server socket: " + e.getMessage());
        }
        this.numThreads = numThreads;
        this.executorService = Executors.newFixedThreadPool(numThreads);
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
        this.running = true;

        scheduler.schedule(() -> {
            this.running = false;
            executorService.shutdown();
            System.exit(0);
            System.out.println("ConnectionHandler stopped running after 15 seconds.");
        }, 15, TimeUnit.SECONDS);

    }

    @Override
    public void run() {
        if(!this.running){
            logger.severe("ConnectionHandler not running");
            return;
        }

        try {
            logger.info("ConnectionHandler started. Ready to accept client connection");
            do {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ServerRequestHandler(new XMLMessageHandler(), clientSocket));
            } while (this.running);

        }catch(IOException e) {
            logger.severe("Error during accepting client connection: " + e.getMessage());
        }
    }

}
