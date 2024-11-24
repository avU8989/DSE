package tcp.marshaller;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;

public interface MessageMarshaller<T> {
    String marshalToXML(T message) throws JAXBException;
    T unmarshalFromXML(String xml) throws JAXBException;
}
