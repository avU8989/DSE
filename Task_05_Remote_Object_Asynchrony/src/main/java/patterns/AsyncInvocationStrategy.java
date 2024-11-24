package patterns;

import kickstart.RequestMessage;
import network.NetworkRequestHandler;

public interface AsyncInvocationStrategy<T> {
    T invokeAsync(byte[] requestMessage) throws Exception;
}
