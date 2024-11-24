package tcp.client;

import tcp.marshaller.MessageMarshaller;
import tcp.messages.Message;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientRequestHandler implements Runnable{
    private static final Logger logger = Logger.getLogger(ClientRequestHandler.class.getName());
    private ClientModel model;
    private ClientView view;
    private int totalCount = 0;
    private final int iterations;
    private final int numberToSend;
    private final int dataSizeInKB;
    private long averageTransmissionTime = 0;
    private static final String SERVER_ADRESS = "localhost";
    private final MessageMarshaller<Message> messageHandler;

    public ClientRequestHandler(MessageMarshaller<Message> messageHandler, ClientModel model, int iterations, int numberToSend, int dataSizeInKB){
        this.numberToSend = numberToSend;
        this.dataSizeInKB = dataSizeInKB;
        this.messageHandler = messageHandler;
        this.iterations = iterations;
        this.model = model;
        this.view = new ClientView(this);
        model.addPropertyChangeListener(view);
    }
    //----------------------------SUBTASK_03------------------------------
    //average transmission time for TCP with 1 KB of dummy data takes 1ms
    //average transmission time for TCP with 2 KB of dummy data takes 2 ms
    //average transmission time for TCP with 4 KB of dummy data takes 2 ms
    //average transmission time for TCP with 8 KB of dummy data takes 2 ms
    //average transmission time for TCP with 16 KB of dummy data takes 2 ms
    //average transmission time for TCP with 128 KB of dummy data takes 31 ms
    //Is it getting slower? Yes its getting slower but only by the 128 KB mark of dummy data, but somehow the increasement of the transmission time
    //of the dummy data until 128 KB doesn't change significantly.

    //----------------------------SUBTASK_04------------------------------
    //RESULTS for 1 THREAD
    //average transmission time for TCP with 1 KB of dummy data takes 1 ms
    //average transmission time for TCP with 2 KB of dummy data takes 1 ms
    //average transmission time for TCP with 4 KB of dummy data takes 1 ms
    //average transmission time for TCP with 8 KB of dummy data takes 1 ms
    //average transmission time for TCP with 16 KB of dummy data takes 2 ms
    //average transmission time for TCP with 128 KB of dummy data takes 20 ms
    //RESULTS for 2 THREADS
    //average transmission time for TCP with 1 KB of dummy data takes 1 ms
    //average transmission time for TCP with 2 KB of dummy data takes 1 ms
    //average transmission time for TCP with 4 KB of dummy data takes 1 ms
    //average transmission time for TCP with 8 KB of dummy data takes 1 ms
    //average transmission time for TCP with 16 KB of dummy data takes 2 ms
    //average transmission time for TCP with 128 KB of dummy data takes 20 ms
    //RESULTS for 4 THREADS
    //average transmission time for TCP with 1 KB of dummy data takes 1 ms
    //average transmission time for TCP with 2 KB of dummy data takes 1 ms
    //average transmission time for TCP with 4 KB of dummy data takes 1 ms
    //average transmission time for TCP with 8 KB of dummy data takes 1 ms
    //average transmission time for TCP with 16 KB of dummy data takes 2 ms
    //average transmission time for TCP with 128 KB of dummy data takes 20 ms

    @Override
    public void run() {
        Socket socket = null;
        try{
            for(int i = 0; i < iterations; i++){
                socket = new Socket(SERVER_ADRESS, 1254);
                long startTime = System.currentTimeMillis();
                Message message = new Message(this.numberToSend, "CLIENT", this.dataSizeInKB);
                ++totalCount;

                String xmlMessage = messageHandler.marshalToXML(message);

                //send number to server
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                sendMessage(writer, xmlMessage);

                //receive response from server
                InputStream is = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String receivedMessage = receiveMessage(reader);
                long endTime = System.currentTimeMillis();
                Message messageReceivedFromServer = messageHandler.unmarshalFromXML(receivedMessage);
                int receivedNumber = messageReceivedFromServer.getNumber();
                logger.info("Receiving message from server: " + messageReceivedFromServer.toString());
                model.setNumber(receivedNumber);

                long transmissionTime = endTime - startTime;
                averageTransmissionTime += transmissionTime;
                logger.info("Transmission time :" + transmissionTime + "ms");

                socket.close();
                reader.close();
                writer.close();
            }
            averageTransmissionTime = averageTransmissionTime/1000;
            logger.info("Average transmission time over 1000 iterations: " + averageTransmissionTime + "ms");
            logger.info("total count is: " + totalCount);
        }catch(IOException e){
            logger.info(String.valueOf(totalCount));
            logger.severe("Error during sending number from client to server: " + e.getMessage());
        }catch (JAXBException e){
            logger.severe("Error during marshalling to XML: " + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.severe("Error closing socket: " + e.getMessage());
                }
            }
        }
    }

    private void sendMessage(BufferedWriter writer, String xmlMessage) throws IOException{
        writer.write(xmlMessage);
        writer.newLine();
        writer.flush();
    }

    private String receiveMessage(BufferedReader reader) throws IOException{
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            if (line.isEmpty()) {
                break;
            }
        }

        return builder.toString();
    }
}

