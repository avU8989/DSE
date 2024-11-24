package client;

import kickstart.LogEntry;
import network.ConnectionRole;
import network.NetworkRequestHandler;
import network.TCPNetworkHandler;
import network.UDPNetworkHandler;

import java.util.logging.Logger;

public class ClientMain {
    private static final Logger logger = Logger.getLogger(ClientMain.class.getName());

    public static void main(String[] args) {
        NetworkRequestHandler networkRequestHandlerTCP = new TCPNetworkHandler("localhost", 1254);
        ClientRequestHandler clientRequestHandlerTCP = new ClientRequestHandler(networkRequestHandlerTCP);
        ClientRequestor requestorTCP = new ClientRequestor(clientRequestHandlerTCP);
        ClientProxy proxyTCP = new ClientProxy(requestorTCP);
        Client clientTCP = new Client(proxyTCP);
        LogEntry logEntry = new LogEntry("First log test\"");
        logger.info("Starting client..................");
        clientTCP.logSingleEntry(logEntry);
        clientTCP.removeOldLogs(1);

        NetworkRequestHandler networkRequestHandlerUDP = new UDPNetworkHandler(ConnectionRole.CLIENT, 1255, "localhost");
        ClientRequestHandler clientRequestHandlerUDP = new ClientRequestHandler(networkRequestHandlerUDP);
        ClientRequestor requestorUDP = new ClientRequestor(clientRequestHandlerUDP);
        ClientProxy proxyUDP = new ClientProxy(requestorUDP);
        Client clientUDP = new Client(proxyUDP);
        LogEntry[] logEntries = new LogEntry[3];
        logEntries[0] = new LogEntry("Error: Database connection failed");
        logEntries[1] = new LogEntry("Warning: Disk space running low");
        logEntries[2] = new LogEntry("Info: Application started successfully");
        clientUDP.addLogsInBulk(logEntries);
        clientUDP.searchLogs("Error: Database connection failed");

    }
}
