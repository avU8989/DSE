package tcp.server;

import tcp.marshaller.MessageMarshaller;
import tcp.messages.Message;

import javax.xml.bind.*;
import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerRequestHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ServerRequestHandler.class.getName());
    private Socket clientSocket;
    private final int INCREMENT_BY = 1;
    private final MessageMarshaller<Message> messageHandler;

    public ServerRequestHandler(MessageMarshaller<Message> messageHandler, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        try {
            logger.info("Client connected " + clientSocket.getInetAddress().getHostAddress());
                InputStream is = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    if (line.isEmpty()) {
                        break;
                    }
                }
                logger.info("Finished reading from client");
                String xmlMessage = builder.toString();
                logger.info("Received XML message from client: " + xmlMessage);

                // Process the received XML message
                Message message = messageHandler.unmarshalFromXML(xmlMessage);
                int incrementedNumber = message.getNumber() + INCREMENT_BY;
                Message newMessage = new Message(incrementedNumber, "SERVER", message.getDataBlob());

                String responseXML = messageHandler.marshalToXML(newMessage);
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println(responseXML);
                logger.info("Sent response to client");
                clientSocket.close();
        } catch (IOException e) {
            logger.severe("Error handling client: " + e.getMessage());
        } catch (JAXBException e) {
            logger.severe("Error during unmarshalling XML into object");
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.severe("Error during closing client socket: " + e.getMessage());
            }

        }
    }

}
