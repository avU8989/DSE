package network;

import kickstart.RemoteObject;
import server.ServerInvoker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class UDPNetworkHandler implements NetworkRequestHandler {
    private DatagramSocket socket;
    private final String aHost;
    private static final int BUFFERSIZE = 5000;
    private final int port;
    private final ConnectionRole role;
    private final ExecutorService executor;
    private RemoteObject remoteObject;

    private static final Logger logger = Logger.getLogger(UDPNetworkHandler.class.getName());

    // Modify constructor to accept RemoteObject
    //bug which took me almost 2 weeks to figure out, i didnt pass the remoteobject to the udpnetworkhandler so thats why the server
    //each request handling within the UDPNetworkHandler was creating a new instance of RemoteObject instead of using a single shared instance.
    //this led to a situation where changes made to the RemoteObject in one operation
    //were not reflected in subsequent operations because they were operating on different instances of RemoteObject.
    //i thought i had problems with the implementation of the different patterns but the root cause was something completely different (Cause of the Problem ConnectionUDPHandler and UDPNetworkHandler)
    public UDPNetworkHandler(ConnectionRole role, int port, String aHost, RemoteObject remoteObject) {
        this.aHost = aHost;
        this.port = port;
        this.role = role;
        this.executor = Executors.newSingleThreadExecutor();
        this.remoteObject = remoteObject;
        initiateUDPNetworkSocket(role);
    }

    public UDPNetworkHandler(ConnectionRole role, int port, String aHost) {
        this.aHost = aHost;
        this.port = port;
        this.role = role;
        this.executor = Executors.newSingleThreadExecutor();
        initiateUDPNetworkSocket(role);
    }

    private void initiateUDPNetworkSocket(ConnectionRole role) {
        DatagramSocket tempSocket = null;
        if (role.equals(ConnectionRole.CLIENT)) {
            try {
                tempSocket = new DatagramSocket();
            } catch (SocketException e) {
                logger.severe("Error during creating Client Socket for UDP");
            }
        } else if (role.equals(ConnectionRole.SERVER)) {
            try {
                tempSocket = new DatagramSocket(port);
            } catch (SocketException e) {
                e.printStackTrace();
                logger.severe("Error during creating Server Socket for UDP: " + e.getMessage());
            }
        }
        this.socket = tempSocket;
    }

    @Override
    public void sendMessage(byte[] data) throws IOException {
        try {
            InetAddress aHost = InetAddress.getByName(this.aHost);
            DatagramPacket reply = new DatagramPacket(data, data.length, aHost, port);
            socket.send(reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] readMessage() throws IOException {
        byte[] buffer = new byte[BUFFERSIZE];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        socket.receive(request);
        return request.getData();
    }

    public void startListening() {
        new Thread(() -> {
            byte[] buffer = new byte[BUFFERSIZE];
            try {
                while (!socket.isClosed()) {
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    socket.receive(request);
                    logger.info("Message received from " + request.getAddress() + ":" + request.getPort());

                    // Process the request and prepare the response
                    byte[] response = handleRequest(request.getData());
                    DatagramPacket reply = new DatagramPacket(response, response.length, request.getAddress(), request.getPort());
                    socket.send(reply);
                }
            } catch (IOException e) {
                if (!socket.isClosed()) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Use the same RemoteObject instance in handleRequest
    private byte[] handleRequest(byte[] requestData) {
        ServerInvoker serverInvoker = new ServerInvoker(remoteObject);
        try {
            return serverInvoker.invoke(requestData);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    @Override
    public boolean isClosed() {
        return socket.isClosed();
    }
}
