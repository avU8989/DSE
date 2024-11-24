package client;

import server.ServerRequestHandler;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

public class ClientRequestHandler {
    private String serverAddress = "localhost";
    private int serverPort = 1254;
    Socket socket;
    private static final Logger logger = Logger.getLogger(ClientRequestHandler.class.getName());

    public ClientRequestHandler(String serverAddress, int serverPort){
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        try {
            this.socket = new Socket(serverAddress, serverPort);
        }catch(IOException e){
            logger.severe("Error during creating client socket: " + e.getMessage());
        }

    }

    public void sendRequest(byte[] request){
        try{
            logger.info("Sending request.........");
            OutputStream out = socket.getOutputStream();
            out.write(request);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Didnt handle the receiving or sending");
        }
    }

    public byte[] receiveResponse() {
        try {
            logger.info("Receiving response from Server.........");
            BufferedInputStream inputStream = new BufferedInputStream(this.socket.getInputStream());
            byte[] buffer = new byte[100];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead != -1) {
                byte[] request = new byte[bytesRead];
                System.arraycopy(buffer, 0, request, 0, bytesRead);
                return request;
            } else {
                throw new IOException("Empty request received");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
