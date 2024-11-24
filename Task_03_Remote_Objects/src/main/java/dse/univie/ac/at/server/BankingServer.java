package dse.univie.ac.at.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import dse.univie.ac.at.server.rmi.RMIBankingServiceImpl;
import dse.univie.ac.at.server.rmi.RMIServer;
import dse.univie.ac.at.soap.SOAPServer;
import jakarta.xml.ws.Endpoint;


public class BankingServer {
	private RMIServer rmiServer;
	private SOAPServer soapServer;
	private static final int PORT = 1099;
	private static final Logger logger = Logger.getLogger(BankingServer.class.getName());
	private final ExecutorService executorService;

	public BankingServer(RMIServer rmiServer, SOAPServer soapServer) {
		this.rmiServer = rmiServer;
		this.soapServer = soapServer;
		this.executorService = Executors.newCachedThreadPool();
	}

	public void startServer() {
		startRMIServer();
		startSOAPServer();
	}

	private void startSOAPServer() {
		this.soapServer.startSOAP();
	}

	private void startRMIServer() {
		this.rmiServer.startRMIServer();
	}

}
