package udp.messages;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class Message implements Serializable {
    private final UUID uuid;
    private final int number;
    private final String senderName;
    private final long timestamp;
    private int sequenceNumber = 0;
    private int totalFragments = 0;
    private byte[] dataBlob;

    public Message() {
        this.number = 0;
        this.senderName = "";
        this.timestamp = 0;
        this.uuid = UUID.randomUUID();
        this.sequenceNumber = 0;
        this.dataBlob = new byte[0];
    }

    public Message(int number, String senderName, int dataSizeInKB) {
        super();
        this.uuid = UUID.randomUUID();
        this.number = number;
        this.senderName = senderName;
        this.timestamp = System.currentTimeMillis();
        initializeDataBlob(dataSizeInKB);
    }

    public Message(UUID uuid, int number, String senderName) {
        super();
        this.uuid = uuid;
        this.number = number;
        this.senderName = senderName;
        this.timestamp = System.currentTimeMillis();
        this.dataBlob = null;
    }

    private void initializeDataBlob(int sizeInKB){

        if(sizeInKB == 128){
            int dataSize = sizeInKB * 1024;
            this.dataBlob = new byte[dataSize];

            //fill first 64 with 'A'
            int first64KB = Math.min(dataSize, 64 * 1024);
            Arrays.fill(this.dataBlob, 0, first64KB, (byte) 'A');

            int last64KB = Math.min(dataSize - 64 * 1024,0);
            Arrays.fill(this.dataBlob, 0, last64KB, (byte) 'B');
        }else{
            int dataSize = sizeInKB * 1024;
            this.dataBlob = new byte[dataSize];
            Arrays.fill(this.dataBlob, (byte) 0);
        }
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setTotalFragments(int totalFragments){
        this.totalFragments = totalFragments;
    }

    public void setDataBlob(byte[] dataBlob){
        this.dataBlob = dataBlob;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getTotalFragments(){
        return this.totalFragments;
    }

    public int getNumber(){
        return this.number;
    }

    public int getSequenceNumber(){return this.sequenceNumber;}

    public String getSenderName(){
        return this.senderName;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public byte[] getDataBlob(){ return this.dataBlob;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return uuid.equals(message.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Message{" +
                "uuid=" + uuid +
                ", number=" + number +
                ", senderName='" + senderName + '\'' +
                ", timestamp=" + timestamp +
                ", sequenceNumber=" + sequenceNumber +
                ", totalFragments=" + totalFragments +
                ", dataBlob=" + dataBlob.length +
                '}';
    }
}

