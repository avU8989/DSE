
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
 *         <element ref="{dse.univie.ac.at}BankAccount"/>
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
    "bankAccount"
})
@XmlRootElement(name = "TransferFundsResponse")
public class TransferFundsResponse {

    @XmlElement(name = "BankAccount", namespace = "dse.univie.ac.at", required = true)
    protected BankAccount bankAccount;

    /**
     * Ruft den Wert der bankAccount-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BankAccount }
     *     
     */
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /**
     * Legt den Wert der bankAccount-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccount }
     *     
     */
    public void setBankAccount(BankAccount value) {
        this.bankAccount = value;
    }

}
