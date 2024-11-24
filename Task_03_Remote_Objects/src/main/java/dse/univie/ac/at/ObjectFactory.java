
package dse.univie.ac.at;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dse.univie.ac.at package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dse.univie.ac.at
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BankAccount }
     * 
     * @return
     *     the new instance of {@link BankAccount }
     */
    public BankAccount createBankAccount() {
        return new BankAccount();
    }

    /**
     * Create an instance of {@link TransferFunds }
     * 
     * @return
     *     the new instance of {@link TransferFunds }
     */
    public TransferFunds createTransferFunds() {
        return new TransferFunds();
    }

    /**
     * Create an instance of {@link Audit }
     * 
     * @return
     *     the new instance of {@link Audit }
     */
    public Audit createAudit() {
        return new Audit();
    }

    /**
     * Create an instance of {@link TransferFundsResponse }
     * 
     * @return
     *     the new instance of {@link TransferFundsResponse }
     */
    public TransferFundsResponse createTransferFundsResponse() {
        return new TransferFundsResponse();
    }

    /**
     * Create an instance of {@link AuditResponse }
     * 
     * @return
     *     the new instance of {@link AuditResponse }
     */
    public AuditResponse createAuditResponse() {
        return new AuditResponse();
    }

}
