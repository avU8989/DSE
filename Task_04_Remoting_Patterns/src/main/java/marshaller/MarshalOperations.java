package marshaller;

import client.protocol.RPCRequest;

public interface MarshalOperations {
    byte[] marshal(String method, String message);
    RPCRequest unmarshal(byte[] data);
}
