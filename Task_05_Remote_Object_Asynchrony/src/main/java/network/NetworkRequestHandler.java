package network;

import java.io.IOException;

public interface NetworkRequestHandler {
    void sendMessage(byte[] data) throws IOException;
    byte[] readMessage() throws IOException;
    void close() throws IOException;
    boolean isClosed();
    void startListening();

}
