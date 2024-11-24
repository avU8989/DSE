package dse.univie.ac.at.soap;

import java.util.logging.Logger;

import dse.univie.ac.at.SOAPBankingServiceImpl;
import dse.univie.ac.at.server.BankingHandler;
import jakarta.xml.ws.Endpoint;

public class SOAPServer {

	private static final Logger logger = Logger.getLogger(SOAPServer.class.getName());
	private BankingHandler handler;
	public SOAPServer(BankingHandler handler) {
		this.handler = handler;
	}

	public void startSOAP() {
		logger.info("SOAP Server started successfully");
		String address = "http://127.0.0.1:8023/BankingService";
		Endpoint.publish(address, new SOAPBankingServiceImpl(this.handler));
		System.out.println("Listening: " + address);
	}
}
