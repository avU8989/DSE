package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ServerRequestHandler {
    private static final Logger logger = Logger.getLogger(ServerRequestHandler.class.getName());
    private ServerSocket serverSocket;
    private final int PORT = 1254;
    private ServerInvoker serverInvoker;

    public ServerRequestHandler(ServerInvoker serverInvoker){
        this.serverInvoker = serverInvoker;
        try{
            this.serverSocket = new ServerSocket(PORT);
            logger.info("Starting server................");

        }catch(IOException e){
            System.err.println("Error during creating server socket");
        }

    }

    public void start(){
        Socket clientSocket = null;
        try{
            clientSocket = this.serverSocket.accept();
        }catch (IOException e) {
            e.printStackTrace();
        }
        do {
           handleRequest(clientSocket);
        } while(true);
    }

    private void handleRequest(Socket clientSocket) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(clientSocket.getInputStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            byte[] buffer = new byte[100];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead != -1) {
                byte[] request = new byte[bytesRead];
                System.arraycopy(buffer, 0, request, 0, bytesRead);

                byte[] responseData = serverInvoker.invoke(request);
                logger.info("Sending response to client");

                outputStream.write(responseData);
                outputStream.flush();
            } else {
                throw new IOException("Empty request received");
            }
        } catch (IOException e) {
            logger.severe("Error handling client request: " + e.getMessage());
        }
    }
}
