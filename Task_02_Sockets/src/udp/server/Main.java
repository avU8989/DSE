package udp.server;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(udp.server.Main.class.getName());

    public static void main(String[] args){
        int numThreads = 1;
        ConnectionHandler connectionHandler = new ConnectionHandler(numThreads);
        Thread connectionHandlerThread = new Thread(connectionHandler);
        connectionHandlerThread.start();

        logger.info("Server starting. Listening to client connections....");

    }

}
