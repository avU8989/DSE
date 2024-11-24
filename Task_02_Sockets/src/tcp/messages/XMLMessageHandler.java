package tcp.messages;

import tcp.marshaller.MessageMarshaller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.logging.Logger;

public class XMLMessageHandler implements MessageMarshaller<Message> {
    private static final Logger logger = Logger.getLogger(XMLMessageHandler.class.getName());

    @Override
    public String marshalToXML(Message message) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter writer = new StringWriter();
        marshaller.marshal(message, writer);


        return writer.toString();
    }

    @Override
    public Message unmarshalFromXML(String xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(xml);

        // Unmarshal the XML into a Message object
        Message message = (Message) jaxbUnmarshaller.unmarshal(reader);

        return message;
    }



}
