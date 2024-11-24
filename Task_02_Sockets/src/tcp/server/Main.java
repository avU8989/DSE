package tcp.server;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args){
        ConnectionHandler handler = new ConnectionHandler(2);
        Thread connectionHandlerThread = new Thread(handler);
        connectionHandlerThread.start();
        logger.info("Server starting. Listening to client connections....");
    }
}
