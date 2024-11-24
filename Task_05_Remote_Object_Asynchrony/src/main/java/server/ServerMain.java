package server;

import kickstart.RemoteObject;

import java.rmi.Remote;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    public static void main(String[] args) {
        int numThreads = 10; // Set the desired number of threads
        RemoteObject remoteObject = new RemoteObject(); // Shared RemoteObject instance
        ConnectionHandlerUDP connectionHandlerUDP = new ConnectionHandlerUDP(remoteObject);
        ConnectionHandlerTCP connectionHandlerTCP = new ConnectionHandlerTCP(6, remoteObject);

        new Thread(connectionHandlerUDP).start();
        new Thread(connectionHandlerTCP).start();

        logger.info("Server starting. Listening to client connections....");
    }
}
