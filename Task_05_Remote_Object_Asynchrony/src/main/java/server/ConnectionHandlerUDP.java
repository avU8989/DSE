package server;

import kickstart.RemoteObject;
import network.ConnectionRole;
import network.NetworkRequestHandler;
import network.UDPNetworkHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/*
listens for incoming client connections and manages lifecycle of client sessions
accepts client connections and delegates handling each client session
 */
public class ConnectionHandlerUDP implements Runnable {
    private static final int UDP_PORT = 1255;
    private boolean running;
    private static final Logger logger = Logger.getLogger(ConnectionHandlerUDP.class.getName());
    private final ExecutorService executorService;
    private final RemoteObject remoteObject;

    public ConnectionHandlerUDP(RemoteObject remoteObject) {
        this.executorService = Executors.newCachedThreadPool();
        this.remoteObject = remoteObject;
        this.running = true;
    }

    @Override
    public void run() {
        if (!this.running) {
            logger.severe("ConnectionHandler not running");
            return;
        }

        logger.info("ConnectionHandler started. Ready to accept client connection");
        NetworkRequestHandler udpNetworkRequestHandler = new UDPNetworkHandler(ConnectionRole.SERVER, UDP_PORT, "localhost", remoteObject);
        ServerInvoker serverInvoker = new ServerInvoker(remoteObject);
        executorService.submit(new ServerRequestHandler(udpNetworkRequestHandler, serverInvoker));
    }
}
