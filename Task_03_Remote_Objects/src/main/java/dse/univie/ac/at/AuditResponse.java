
package dse.univie.ac.at;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
 *         <element name="totalBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "totalBalance"
})
@XmlRootElement(name = "AuditResponse")
public class AuditResponse {

    protected double totalBalance;

    /**
     * Ruft den Wert der totalBalance-Eigenschaft ab.
     * 
     */
    public double getTotalBalance() {
        return totalBalance;
    }

    /**
     * Legt den Wert der totalBalance-Eigenschaft fest.
     * 
     */
    public void setTotalBalance(double value) {
        this.totalBalance = value;
    }

}
