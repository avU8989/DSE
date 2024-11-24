
package dse.univie.ac.at;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="requestId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="transactionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="fromAccountId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="toAccountId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "requestId",
    "transactionType",
    "fromAccountId",
    "toAccountId",
    "amount"
})
@XmlRootElement(name = "TransferFunds")
public class TransferFunds {

    @XmlElement(required = true)
    protected String requestId;
    @XmlElement(required = true)
    protected String transactionType;
    @XmlElement(required = true)
    protected String fromAccountId;
    @XmlElement(required = true)
    protected String toAccountId;
    protected double amount;

    /**
     * Ruft den Wert der requestId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Legt den Wert der requestId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Ruft den Wert der transactionType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Legt den Wert der transactionType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionType(String value) {
        this.transactionType = value;
    }

    /**
     * Ruft den Wert der fromAccountId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromAccountId() {
        return fromAccountId;
    }

    /**
     * Legt den Wert der fromAccountId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromAccountId(String value) {
        this.fromAccountId = value;
    }

    /**
     * Ruft den Wert der toAccountId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToAccountId() {
        return toAccountId;
    }

    /**
     * Legt den Wert der toAccountId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToAccountId(String value) {
        this.toAccountId = value;
    }

    /**
     * Ruft den Wert der amount-Eigenschaft ab.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Legt den Wert der amount-Eigenschaft fest.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

}
