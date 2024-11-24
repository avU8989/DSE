package dse.univie.ac.at.server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import dse.univie.ac.at.client.BankAccount;
import dse.univie.ac.at.server.rmi.RMIBankingServiceImpl;
import dse.univie.ac.at.server.rmi.RMIServer;
import dse.univie.ac.at.soap.SOAPServer;
import jakarta.xml.ws.Endpoint;
import util.TransactionRequest;
import util.TransactionType;

public class ServerMain {
	private static final Logger logger = Logger.getLogger(SOAPServer.class.getName());

	public static void main(String[] args) {
		try {
			ConcurrentHashMap<UUID, BankAccount> accounts = new ConcurrentHashMap<>();

			for (int i = 0; i < 10; ++i) {
				BankAccount newAccount = new BankAccount("user " + String.valueOf(i), 10000);
				accounts.put(newAccount.getAccountId(), newAccount);
			}
			BankingHandler handler = new BankingHandler(accounts);
			RMIServer rmi = new RMIServer(handler);
			SOAPServer soap = new SOAPServer(handler);
			BankingServer server = new BankingServer(rmi, soap);
			server.startServer();

		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}

	}
}
