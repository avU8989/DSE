package server;

import client.ClientProxy;
import kickstart.IMarshall;
import kickstart.RequestMessage;
import kickstart.ResponseMessage;
import network.NetworkRequestHandler;
import network.TCPNetworkHandler;
import network.UDPNetworkHandler;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

public class ServerRequestHandler implements Runnable {
    private NetworkRequestHandler networkRequestHandler;
    private ServerInvoker serverInvoker;
    private static final Logger logger = Logger.getLogger(ServerRequestHandler.class.getName());

    public ServerRequestHandler(NetworkRequestHandler networkRequestHandler, ServerInvoker invoker) {
        this.networkRequestHandler = networkRequestHandler;
        this.serverInvoker = invoker;
    }

    @Override
    public void run() {
        if (networkRequestHandler instanceof UDPNetworkHandler) {
            networkRequestHandler.startListening();
        }else{
            handleTCPRequests();
        }
    }

    private void handleTCPRequests() {
        try {
            do {
                byte[] request = networkRequestHandler.readMessage();
                byte[] response = serverInvoker.invoke(request);
                networkRequestHandler.sendMessage(response);
            } while (true);
        } catch (SocketException e) {
            logger.info("Client closed the connection.");
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("Error during handling the RequestMessages from the client: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!networkRequestHandler.isClosed()) {
                try {
                    networkRequestHandler.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public NetworkRequestHandler getNetworkRequestHandler() {
        return networkRequestHandler;
    }
}
