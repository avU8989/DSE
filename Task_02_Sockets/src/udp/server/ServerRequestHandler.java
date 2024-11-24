package udp.server;

import udp.messages.Message;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class ServerRequestHandler implements Runnable{
    private static final Logger logger = Logger.getLogger(ServerRequestHandler.class.getName());
    private final int INCREMENT_BY = 1;
    private Map<UUID, Map<Integer, byte[]>> fragmentedMessages;
    private static final int maxPacketSize = 50000;
    private DatagramSocket socket;


    public ServerRequestHandler(DatagramSocket socket)   {
        this.fragmentedMessages = new HashMap<>();
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            byte[] buffer = new byte[65*1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

                socket.receive(receivePacket);
                logger.info("Client connected: " + receivePacket.getAddress().getHostAddress());

                Message receivedMessage = deserializeMessage(receivePacket.getData());

                logger.info("Received message from client: " + receivedMessage);
                if (receivedMessage.getDataBlob().length <= maxPacketSize) {
                        handleFragmentedMessage(receivedMessage);
                        if(receivedMessage.getTotalFragments()-1 == receivedMessage.getSequenceNumber()){
                            logger.info("All fragments gathered");
                            Message assembledMessage = assembleMessage(receivedMessage.getUuid(), receivedMessage);
                            sendFragmentedMessage(socket, assembledMessage, receivePacket.getAddress(), receivePacket.getPort());
                        }

                        if(receivedMessage.getTotalFragments() == 0){
                            logger.info("Message with datablob under the maxPacketSize received");
                            int incrementedNumber = receivedMessage.getNumber() + INCREMENT_BY;
                            Message newMessage = new Message(receivedMessage.getUuid(), incrementedNumber, "SERVER");
                            newMessage.setDataBlob(receivedMessage.getDataBlob());
                            newMessage.setTotalFragments(receivedMessage.getTotalFragments());
                            newMessage.setSequenceNumber(receivedMessage.getSequenceNumber());

                            //serialize the new message
                            byte[] response = serializeMessage(newMessage);
                            //send serialized message back to client
                            DatagramPacket reply = new DatagramPacket(response, response.length, receivePacket.getAddress(), receivePacket.getPort());
                            socket.send(reply);

                            logger.info("Sent response to client: " + newMessage);

                        }
                }
            }

        }catch(SocketException e){
            logger.severe("Socket: " + e.getMessage());
        }catch(IOException e){
            logger.severe("Error during handling message from client: " + e.getMessage());
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            logger.severe("Error during reading Object: " + e.getMessage());
        }finally{
            logger.info(String.valueOf(fragmentedMessages.keySet().size()));

            if(socket != null && !socket.isClosed()){
                socket.close();
            }
        }
    }

    private void handleFragmentedMessage(Message message){
        UUID uuid = message.getUuid();
        int sequenceNumber = message.getSequenceNumber();
        byte[] fragmentData = message.getDataBlob();

        if(!fragmentedMessages.containsKey(uuid)){
            fragmentedMessages.put(uuid, new HashMap<>());
        }

        fragmentedMessages.get(uuid).put(sequenceNumber, fragmentData);
        logger.info(fragmentedMessages.get(uuid).toString());
        logger.info(String.valueOf(fragmentedMessages.get(uuid).size()));

    }

    private Message assembleMessage(UUID uuid, Message oldMessage){
        Map<Integer, byte[]> fragments = fragmentedMessages.get(uuid);
        int totalFragments = fragments.size();
        int dataSize = 0;
        for (byte[] fragment : fragments.values()) {
            dataSize += fragment.length;
        }

        byte[] assembledData = new byte[dataSize];

        int offset = 0;
        for (int i = 0; i < totalFragments; i++) {
            byte[] fragment = fragments.get(i);
            if(fragment != null){
                System.arraycopy(fragment, 0, assembledData, offset, fragment.length);
                // Update the offset
                offset += fragment.length;
            }
        }

        int incrementedNumber = incrementNumber(oldMessage.getNumber());
        Message assembledMessage = new Message(oldMessage.getUuid(), incrementedNumber, "SERVER");
        assembledMessage.setDataBlob(assembledData);
        assembledMessage.setSequenceNumber(oldMessage.getSequenceNumber());
        assembledMessage.setTotalFragments((int) Math.ceil((double)assembledData.length / maxPacketSize));

        return assembledMessage;
    }

    private void sendFragmentedMessage(DatagramSocket socket, Message assembledMessage, InetAddress address, int port){
        for(int i=0; i < assembledMessage.getTotalFragments(); i++){
            int fragmentOffset = i * maxPacketSize;
            int fragmentSize = Math.min(maxPacketSize, assembledMessage.getDataBlob().length - fragmentOffset);

            //set sequence number for each fragment
            assembledMessage.setSequenceNumber(i);
            Message fragmentedMessage = createFragmentMessage(assembledMessage, fragmentOffset, fragmentSize);
            sendFragmentedDataToServer(socket, address, port, fragmentOffset, fragmentSize, fragmentedMessage);

        }
    }

    private void sendFragmentedDataToServer(DatagramSocket socket, InetAddress address, int port, int fragmentOffset, int fragmentSize, Message fragmentedMessage){
        try{
            logger.info("---------------SENDING FRAGMENTED MESSAGE-------------");
            byte[] sendData = serializeMessage(fragmentedMessage);
            logger.info(fragmentedMessage.toString());
            DatagramPacket fragmentedPacket = new DatagramPacket(sendData, sendData.length, address, port);
            socket.send(fragmentedPacket);
        }catch(IOException e){
            logger.severe("Error during sending fragment to client: " + e.getMessage());
        }
    }

    private Message createFragmentMessage(Message originalMessage, int fragmentOffset, int fragmentSize) {
        byte[] originalDataBlob = originalMessage.getDataBlob();
        byte[] fragmentData = Arrays.copyOfRange(originalDataBlob, fragmentOffset, fragmentOffset + fragmentSize);
        Message fragmentMessage = new Message(originalMessage.getUuid(), originalMessage.getNumber(), originalMessage.getSenderName());
        fragmentMessage.setDataBlob(fragmentData);
        fragmentMessage.setSequenceNumber(originalMessage.getSequenceNumber());
        fragmentMessage.setTotalFragments(originalMessage.getTotalFragments());
        return fragmentMessage;
    }

    private int incrementNumber(int number){
        return number + INCREMENT_BY;
    }

    private byte[] serializeMessage(Message message) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(message);
        }
        return baos.toByteArray();
    }

    private Message deserializeMessage(byte[] data) throws IOException,ClassNotFoundException{
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            return (Message) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch(ClassNotFoundException e) {
            logger.severe("Error during deserialization not finding class: " + e.getMessage());
            throw e;
        }finally{
            if(ois != null){
                ois.close();
            }
        }
    }
}
