package network;

import kickstart.RemoteObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Logger;

public class TCPNetworkHandler implements NetworkRequestHandler {
    private Socket socket;
    private static final Logger logger = Logger.getLogger(TCPNetworkHandler.class.getName());
    private static final int BUFFER_SIZE = 10000;
    private RemoteObject remoteObject;

    //for the client
    public TCPNetworkHandler(String address, int port) {
        try {
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            logger.severe("Error during starting client: " + e.getMessage());
        }

    }

    //for the server
    public TCPNetworkHandler(ServerSocket serverSocket, RemoteObject remoteObject) {
        this.remoteObject = remoteObject;

        try {
            this.socket = serverSocket.accept();
        } catch (IOException e) {
            logger.severe("Error during starting server: " + e.getMessage());
        }
    }

    @Override
    public void sendMessage(byte[] data) throws IOException {
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            logger.severe("Error sending message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public byte[] readMessage() throws IOException {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
            //the buffer should be appropriate
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead != -1) {
                byte[] request = new byte[bytesRead];
                System.arraycopy(buffer, 0, request, 0, bytesRead);
                return request;
            } else {
                return new byte[0];
            }
        } catch (IOException e) {
            throw new IOException("Error handling client request: " + e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        if (!socket.isClosed() && socket != null) {
            socket.close();
        }
    }

    public boolean isClosed(){
        return this.socket.isClosed();
    }

    @Override
    public void startListening() {

    }

}
