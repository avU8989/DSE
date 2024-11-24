package udp.marshaller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface MessageSerializer<T> {
    byte[] serialize(T message) throws IOException;
    T deserialize(byte[] data) throws IOException;
}
