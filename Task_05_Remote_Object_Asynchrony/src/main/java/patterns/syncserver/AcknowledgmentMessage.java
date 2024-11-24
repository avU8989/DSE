package patterns.syncserver;

import kickstart.IMarshall;
import kickstart.ResponseMessage;

public class AcknowledgmentMessage<T> extends ResponseMessage<T> implements IMarshall {
    private boolean ack;

    public AcknowledgmentMessage(T responseData, boolean ack) {
        super(responseData);
        this.ack = ack;
    }

    public boolean acknowledged(){
        return this.ack;
    }

    @Override
    public String toString() {
        return "AcknowledgmentMessage{" + "responseData=" + getResponseData() +
                ", ack=" + ack +
                '}';
    }
}
