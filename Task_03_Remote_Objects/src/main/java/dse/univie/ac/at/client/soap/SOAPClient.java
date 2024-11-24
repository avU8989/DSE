package dse.univie.ac.at.client.soap;

import dse.univie.ac.at.BankAccount;
import dse.univie.ac.at.BankingService;
import dse.univie.ac.at.BankingService_Service;
import dse.univie.ac.at.soap.SOAPServer;
import jakarta.xml.ws.WebServiceException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Logger;

/*
 * We init our service for client
 * get its proxy implementation
 * call it to load programming task info
 */
public class SOAPClient {
    private BankingService_Service service;
    private BankingService implementation;
    private static final Logger logger = Logger.getLogger(SOAPServer.class.getName());


    public BankAccount requestTransferFunds(UUID fromAccount, UUID toAccount, double amount) {
        try {
            logger.info("Requesting SOAP to transfer funds..........");
            String requestId = UUID.randomUUID().toString();
            String fromAccountId = fromAccount.toString();
            String toAccountId = toAccount.toString();
            return this.implementation.transferFunds(requestId, "TRANSFER", fromAccountId, toAccountId, amount);
        } catch (WebServiceException e) {
            e.printStackTrace();
        }

        return null;
    }

    public double audit() {
        return this.implementation.audit();
    }


    public void startSOAP() {
        this.service = new BankingService_Service();
        this.implementation = service.getBankingService();
    }
}
