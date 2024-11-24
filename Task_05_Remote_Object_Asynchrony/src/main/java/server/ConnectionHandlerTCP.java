package server;

import kickstart.RemoteObject;
import network.NetworkRequestHandler;
import network.TCPNetworkHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/*
listens for incoming client connections and manages lifecycle of client sessions
accepts client connections and delegates handling each client session
 */
public class ConnectionHandlerTCP implements Runnable {
    private static final int TCP_PORT = 1254;
    private ServerSocket serverSocket;
    private boolean running;
    private static final Logger logger = Logger.getLogger(ConnectionHandlerTCP.class.getName());
    private ExecutorService executorService;
    private ScheduledExecutorService scheduler;
    private int numThreads;
    private final RemoteObject remoteObject;

    public ConnectionHandlerTCP(int numThreads, RemoteObject remoteObject){
        try{
            serverSocket = new ServerSocket(TCP_PORT);
        } catch(IOException e){
            logger.severe("Error creating server socket: " + e.getMessage());
        }
        this.numThreads = numThreads;
        this.remoteObject = remoteObject;
        this.executorService = Executors.newFixedThreadPool(numThreads);
        this.scheduler = Executors.newScheduledThreadPool(numThreads);
        this.running = true;
    }

    @Override
    public void run() {
        if(!this.running){
            logger.severe("ConnectionHandler not running");
            return;
        }

        logger.info("ConnectionHandler started. Ready to accept client connection");
        NetworkRequestHandler tcpNetworkRequestHandler = new TCPNetworkHandler(serverSocket, remoteObject);
        ServerInvoker serverInvoker = new ServerInvoker(remoteObject);
        executorService.submit(new ServerRequestHandler(tcpNetworkRequestHandler, serverInvoker));

    }

}
