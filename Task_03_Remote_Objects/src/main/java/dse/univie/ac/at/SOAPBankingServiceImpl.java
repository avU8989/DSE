package dse.univie.ac.at;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import dse.univie.ac.at.*;
import dse.univie.ac.at.server.BankingHandler;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;


@WebService(name = "BankingService", targetNamespace = "dse.univie.ac.at")
@XmlSeeAlso({
        ObjectFactory.class
})
public class SOAPBankingServiceImpl implements BankingService {
    private static final Logger logger = Logger.getLogger(SOAPBankingServiceImpl.class.getName());
	private BankingHandler handler;

	public SOAPBankingServiceImpl(BankingHandler handler){
		this.handler = handler;
	}

	@Override
	@WebMethod(operationName = "TransferFunds", action = "TransferFunds")
    @WebResult(name = "BankAccount", targetNamespace = "dse.univie.ac.at")
    @RequestWrapper(localName = "TransferFunds", targetNamespace = "dse.univie.ac.at", className = "dse.univie.ac.at.TransferFunds")
    @ResponseWrapper(localName = "TransferFundsResponse", targetNamespace = "dse.univie.ac.at", className = "dse.univie.ac.at.TransferFundsResponse")
	public BankAccount transferFunds(@WebParam(name = "requestId", targetNamespace = "")
    String requestId,
    @WebParam(name = "transactionType", targetNamespace = "")
    String transactionType,
    @WebParam(name = "fromAccountId", targetNamespace = "")
    String fromAccountId,
    @WebParam(name = "toAccountId", targetNamespace = "")
    String toAccountId,
    @WebParam(name = "amount", targetNamespace = "")
    double amount) {
		UUID fromAccountUUID = UUID.fromString(fromAccountId);
		UUID toAccountUUID = UUID.fromString(toAccountId);
		this.handler.transferFunds(fromAccountUUID, toAccountUUID, amount);
		logger.info("Transfering from " + this.handler.getBankAccount(fromAccountUUID).getName() + " to " + this.handler.getBankAccount(toAccountUUID).getName() + " the amount " + String.valueOf(amount));
		BankAccount response = new BankAccount();
		response.setAccountId(fromAccountId);
		response.setName(this.handler.getBankAccount(fromAccountUUID).getName());
		response.setBalance(this.handler.getBankAccount(fromAccountUUID).getBalance());

		return response;
	}

	@Override
	@WebMethod(operationName = "Audit", action = "Audit")
    @WebResult(name = "totalBalance", targetNamespace = "")
    @RequestWrapper(localName = "Audit", targetNamespace = "dse.univie.ac.at", className = "dse.univie.ac.at.Audit")
    @ResponseWrapper(localName = "AuditResponse", targetNamespace = "dse.univie.ac.at", className = "dse.univie.ac.at.AuditResponse")
	public double audit() {
		var auditResponse = new AuditResponse();
		double auditCall = this.handler.audit();
		logger.info("Calling audit function from SOAP...total balance is: " + String.valueOf(auditCall));
		auditResponse.setTotalBalance(auditCall);
		return auditResponse.totalBalance;
	}
   
 

}
