package client.protocol;

import java.io.IOException;

public interface RemoteService {
    String hello(String name) throws IOException;
    String goodbye(String name) throws IOException;

}
