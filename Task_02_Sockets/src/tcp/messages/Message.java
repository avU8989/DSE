package tcp.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Arrays;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.NONE)
public class Message {
    @XmlElement(name = "number", required = true)
    private final int number;
    @XmlElement(name = "senderName", required = true)
    private final String senderName;
    @XmlElement(name = "timeStamp", required = true)
    private final long timestamp;
    @XmlElement(name ="dataBlob", required = true)
    private byte[] dataBlob;

    public Message() {
        this.number = 0;
        this.senderName = "";
        this.timestamp = 0;
        this.dataBlob = new byte[0];
    }

    public Message(int number, String senderName, int dataSizeInKB) {
        super();
        this.number = number;
        this.senderName = senderName;
        this.timestamp = System.currentTimeMillis();
        initializeDataBlob(dataSizeInKB);
    }

    public Message(int number, String senderName, byte[] dataBlob) {
        super();
        this.number = number;
        this.senderName = senderName;
        this.timestamp = System.currentTimeMillis();
        this.dataBlob = dataBlob;
    }


    private void initializeDataBlob(int sizeInKB){
        int dataSize = sizeInKB * 1024;
        this.dataBlob = new byte[dataSize];

        //fill the dataBlob with dummy data
        Arrays.fill(this.dataBlob, (byte) 0);
    }

    public int getNumber(){
        return this.number;
    }

    public String getSenderName(){
        return this.senderName;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public byte[] getDataBlob(){ return this.dataBlob;}


    @Override
    public String toString() {
        return "Message{" +
                "number=" + number +
                ", senderName='" + senderName + '\'' +
                ", timestamp=" + timestamp +
                ", dataBlob=" + dataBlob.length +
                '}';
    }
}
