package patterns.resultcallback;

import kickstart.KnownMethods;
import kickstart.RequestMessage;

import java.util.UUID;

public class CallbackRequest<T> extends RequestMessage<T> {
    private ResultCallbackObject callbackObject;

    public CallbackRequest(KnownMethods method, T requestData, UUID requestID, ResultCallbackObject callbackObject) {
        super(method, requestData, requestID);

        this.callbackObject = callbackObject;

    }

    public ResultCallback getCallback(){
        return this.callbackObject;
    }

    @Override
    public String toString() {
        return "CallbackRequest{" +
                "method=" + getMethod() +
                ", requestData=" + getRequestData() +
                ", requestID=" + getRequestID() +
                ", callbackObject=" + callbackObject +
                '}';
    }
}