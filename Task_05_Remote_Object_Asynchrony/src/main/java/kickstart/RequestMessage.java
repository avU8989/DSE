package kickstart;

import patterns.resultcallback.ResultCallback;
import patterns.resultcallback.ResultCallbackObject;

import javax.xml.transform.Result;
import java.util.UUID;

public class RequestMessage<T> implements IMarshall {
	private final KnownMethods method;
	private final T requestData;
	private final UUID requestID;
	private ResultCallbackObject callbackObject;

	public RequestMessage(KnownMethods method, T requestData, UUID uuid) {
		super();
		this.method = method;
		this.requestID = uuid;
		this.requestData = requestData;
	}

	public RequestMessage(KnownMethods method, T requestData, UUID uuid,  ResultCallbackObject callbackObject) {
		super();
		this.method = method;
		this.requestID = uuid;
		this.requestData = requestData;
		this.callbackObject = callbackObject;
	}

	public T getRequestData() {
		return requestData;
	}

	public KnownMethods getMethod(){
		return this.method;
	}

	public UUID getRequestID() {
		return requestID;
	}

	public ResultCallbackObject  getCallbackObject(){
		return this.callbackObject;
	}

	public void setCallbackObject(ResultCallbackObject callback) {
		this.callbackObject = callback;

	}
	@Override
	public String toString() {
		return "RequestMessage{" +
				"method=" + method +
				", requestData=" + requestData +
				", requestID=" + requestID +
				'}';
	}


}

