
package dse.univie.ac.at;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.1
 * Generated source version: 3.0
 * 
 */
@WebService(name = "BankingService", targetNamespace = "dse.univie.ac.at")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BankingService {


    /**
     * 
     * @param amount
     * @param fromAccountId
     * @param requestId
     * @param toAccountId
     * @param transactionType
     * @return
     *     returns dse.univie.ac.at.BankAccount
     */
    @WebMethod(operationName = "TransferFunds", action = "TransferFunds")
    @WebResult(name = "BankAccount", targetNamespace = "dse.univie.ac.at")
    @RequestWrapper(localName = "TransferFunds", targetNamespace = "dse.univie.ac.at", className = "dse.univie.ac.at.TransferFunds")
    @ResponseWrapper(localName = "TransferFundsResponse", targetNamespace = "dse.univie.ac.at", className = "dse.univie.ac.at.TransferFundsResponse")
    public BankAccount transferFunds(
        @WebParam(name = "requestId", targetNamespace = "")
        String requestId,
        @WebParam(name = "transactionType", targetNamespace = "")
        String transactionType,
        @WebParam(name = "fromAccountId", targetNamespace = "")
        String fromAccountId,
        @WebParam(name = "toAccountId", targetNamespace = "")
        String toAccountId,
        @WebParam(name = "amount", targetNamespace = "")
        double amount);

    /**
     * 
     * @return
     *     returns double
     */
    @WebMethod(operationName = "Audit", action = "Audit")
    @WebResult(name = "totalBalance", targetNamespace = "")
    @RequestWrapper(localName = "Audit", targetNamespace = "dse.univie.ac.at", className = "dse.univie.ac.at.Audit")
    @ResponseWrapper(localName = "AuditResponse", targetNamespace = "dse.univie.ac.at", className = "dse.univie.ac.at.AuditResponse")
    public double audit();

}
