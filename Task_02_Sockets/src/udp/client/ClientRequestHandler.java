package udp.client;

import udp.messages.Message;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

public class ClientRequestHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientRequestHandler.class.getName());
    private ClientModel model;
    private ClientView view;
    private final int iterations;
    private final static int maxPacketSize = 50000;
    private long averageTransmissionTime = 0;
    private int numberToSend;
    private int dataSizeInKB;
    private Map<UUID, Map<Integer, byte[]>> fragmentedMessages;
    private static final String SERVER_ADDRESS = "localhost";

    public ClientRequestHandler(ClientModel model, int iterations, int numberToSend, int dataSizeInKB) {
        this.model = model;
        this.view = new ClientView(this);
        this.fragmentedMessages = new HashMap<>();
        this.iterations = iterations;
        this.numberToSend = numberToSend;
        this.dataSizeInKB = dataSizeInKB;
        model.addPropertyChangeListener(view);
    }

    //----------------------------SUBTASK_03------------------------------
    //average transmission time for UDP with 1 KB of dummy data takes 1385586ns
    //average transmission time for UDP with 2 KB of dummy data takes 1424892ns
    //average transmission time for UDP with 4 KB of dummy data takes 1303847ns
    //average transmission time for UDP with 8 KB of dummy data takes 1333969ns
    //average transmission time for UDP with 16 KB of dummy data takes 1433717ns
    //average transmission time for UDP with 128 KB of dummy data takes 3309513 ns
    //Is it getting slower? Yes its getting slower but only by the 128 KB mark of dummy data, especially because of my fragmentation algorithm
    //in comparison to the TCP the UDP is way faster, but for exchange we can have package losses and need to fragment the data

    //----------------------------SUBTASK_04------------------------------
    //RESULTS for 1 THREAD
    //average transmission time for UDP with 1 KB of dummy data takes 1370627 ns
    //average transmission time for UDP with 2 KB of dummy data takes 1274439 ns
    //average transmission time for UDP with 4 KB of dummy data takes 1139042 ns
    //average transmission time for UDP with 8 KB of dummy data takes 1205029 ns
    //average transmission time for UDP with 16 KB of dummy data takes 1419184 ns
    //average transmission time for UDP with 128 KB of dummy data takes 3401439 ns
    //RESULTS for 2 THREADS
    //average transmission time for UDP with 1 KB of dummy data takes 923760 ns with
    //average transmission time for UDP with 2 KB of dummy data takes 1178968 ns
    //average transmission time for UDP with 4 KB of dummy data takes 1297701 ns
    //average transmission time for UDP with 8 KB of dummy data takes 1005563 ns
    //average transmission time for UDP with 16 KB of dummy data takes 1130344 ns
    //average transmission time for UDP with 128 KB of dummy data takes 3860212 ns
    //RESULTS for 4 THREADS
    //average transmission time for UDP with 1 KB of dummy data takes 921916 ns
    //average transmission time for UDP with 2 KB of dummy data takes 998282 ns
    //average transmission time for UDP with 4 KB of dummy data takes 970860 ns
    //average transmission time for UDP with 8 KB of dummy data takes 1038599 ns
    //average transmission time for UDP with 16 KB of dummy data takes 1205523 s
    //average transmission time for UDP with 128 KB of dummy data takes 5238693 ns

    //by fragmenting 128KB with a max packet size of 50000 bytes i will have package loss for like 1-6 Messages

    // weird thing is when i have System.currentMillis in my loop
    // i will get stuck in the loop and will result in package loss,
    // my client will endlessly wait for server response,
    // even though server sent a response
    @Override
    public void run() {
        int dataBlobSize = this.dataSizeInKB * 1024;
        long startTime = 0;
        long endTime = 0;
        long transmissionTime = 0;
        DatagramSocket socket = null;
        try {
            for (int i = 0; i < iterations; i++) {
                socket = new DatagramSocket();
                Message message = new Message(numberToSend, "CLIENT", dataSizeInKB);
                if (dataBlobSize >= 64 * 1024) {
                    //handling fragmentation and sending fragmented data
                    logger.info("Sending fragmented packs of data");
                    int numberOfFragments = (int) Math.ceil((double) dataBlobSize / maxPacketSize);
                    message.setTotalFragments(numberOfFragments);
                    startTime = System.nanoTime();
                    //sending fragmented messages to server by the times of the calculated total fragments per message
                    for (int j = 0; j < numberOfFragments; j++) {
                        int fragmentOffset = j * maxPacketSize;
                        int fragmentSize = Math.min(maxPacketSize, dataBlobSize - fragmentOffset);

                        //set sequence number for each fragment
                        message.setSequenceNumber(j);
                        Message fragmentMessage = createFragmentMessage(message, fragmentOffset, fragmentSize);

                        sendFragmentedDataToServer(socket, fragmentMessage);
                    }
                    //receiving fragmented messages by server
                    for (int j = 0; j < numberOfFragments + 1; j++) {
                        logger.info("--------------RECEIVING SERVER RESPONSE-------------");
                        try {
                            byte[] receiveData = new byte[66 * 1024];
                            // receive server response
                            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                            //Setting Timeout to continue with loop when loosing package
                            socket.setSoTimeout(100);
                            socket.receive(receivePacket);
                            //Deserialize the received message
                            logger.info("Successfully received packet from server " + receivePacket.getAddress() + " " + receivePacket.getPort() + "!");
                            Message receivedMessage = deserializeMessage(receivePacket.getData());
                            logger.info("Receiving message from server: " + receivedMessage.toString());

                            //store the UUID and store the related fragmented messages accordingly
                            handleFragmentedMessage(receivedMessage);
                            if (receivedMessage.getSequenceNumber() == receivedMessage.getTotalFragments() - 1) {
                                logger.info("All fragments gathered");
                                Message assembledMessage = assembleMessage(receivedMessage.getUuid(), receivedMessage);
                                logger.info("Assembled message: " + assembledMessage.toString());
                                break;
                            }
                        } catch (SocketTimeoutException e) {
                            logger.severe("Lost package during receiving server response:" + e.getMessage());
                        } catch (IOException e) {
                            logger.severe("Error during receiving server response: " + e.getMessage());
                        } catch (ClassNotFoundException e) {
                            logger.severe("Error during receiving packet from server: " + e.getMessage());
                        }
                    }
                    endTime = System.nanoTime();
                    transmissionTime = endTime - startTime;
                    averageTransmissionTime += transmissionTime;
                } else {
                    //send single pack of data
                    logger.info("Sending single pack of data normally");
                    sendDataToServer(socket, message);
                }
            }
        } catch (IOException e) {
            logger.severe("Error during client operation: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }

        averageTransmissionTime = averageTransmissionTime / 1000;

        int totalCountFullyAssembledMessages = getTotalCountOfFullyAssembledMessages();

        logger.info("Total count of fully assembled messages with datablob size " + dataBlobSize + ": " + totalCountFullyAssembledMessages);
        logger.info("Total count of numbers sent: " + fragmentedMessages.keySet().size());
        logger.info("Average transmission time over 1000 iterations: " + averageTransmissionTime + " ns");
    }

    private void sendDataToServer(DatagramSocket socket, Message message) {
        try {
            long startTime = System.nanoTime();
            logger.info("Starting client socket UDP!");
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            int serverPort = 1254;

            byte[] sendData = serializeMessage(message);

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);
            logger.info("Sending message to server: " + message.toString());

            byte[] receiveData = new byte[(int) ((dataSizeInKB * 1024) * 2)];
            logger.info("--------------RECEIVING SERVER RESPONSE-------------");
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            long endTime;
            socket.receive(receivePacket);
            endTime = System.nanoTime();
            logger.info("Successfully received packet from server " + receivePacket.getAddress() + " " + receivePacket.getPort() + "!");
            Message receivedMessage = deserializeMessage(receivePacket.getData());
            logger.info("Receiving message from server: " + receivedMessage.toString());
            int receivedNumber = receivedMessage.getNumber();
            model.setNumber(receivedNumber);

            long transmissionTime = endTime - startTime;
            averageTransmissionTime += transmissionTime;
            logger.info("Transmission time :" + transmissionTime + "ms");
        } catch (IOException e) {
            logger.severe("Error during sending number to server: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.severe("Error during receiving packet from server: " + e.getMessage());
        }
    }

    private int getTotalCountOfFullyAssembledMessages(){
        Map<UUID, Integer> accumulatedSizes = new HashMap<>();

        for (Map.Entry<UUID, Map<Integer, byte[]>> entry : fragmentedMessages.entrySet()) {
            UUID uuid = entry.getKey();
            Map<Integer, byte[]> fragments = entry.getValue();

            int totalSize = 0;

            for (byte[] fragment : fragments.values()) {
                totalSize += fragment.length;
            }

            if (totalSize == dataSizeInKB * 1024) {
                accumulatedSizes.put(uuid, totalSize);
            }
        }

        return accumulatedSizes.size();
    }

    private byte[] serializeMessage(Message message) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(message);
        }
        return baos.toByteArray();
    }

    private Message deserializeMessage(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            return (Message) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            logger.severe("Error during deserialization not finding class: " + e.getMessage());
            throw e;
        } finally {
            if (ois != null) {
                ois.close();
            }
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

    private void sendFragmentedDataToServer(DatagramSocket socket, Message message) {
        try {
            logger.info("Sending message to server: " + message.toString());
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            int serverPort = 1254;

            byte[] sendData = serializeMessage(message);

            DatagramPacket fragmentedPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(fragmentedPacket);
        } catch (IOException e) {
            logger.severe("Error during sending fragment to server: " + e.getMessage());
        }
    }

    private void handleFragmentedMessage(Message message) {
        logger.info("Storing the fragmented packets belonging to the UUID");
        UUID uuid = message.getUuid();
        int sequenceNumber = message.getSequenceNumber();
        byte[] fragmentData = message.getDataBlob();

        if (!fragmentedMessages.containsKey(uuid)) {
            fragmentedMessages.put(uuid, new HashMap<>());
        }
        if (fragmentData != null) {
            fragmentedMessages.get(uuid).put(sequenceNumber, fragmentData);
        } else {
            logger.warning("Received a null fragment for sequence number: " + sequenceNumber);
        }
    }

    private Message assembleMessage(UUID uuid, Message oldMessage) {
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
            if (fragment != null) {
                System.arraycopy(fragment, 0, assembledData, offset, fragment.length);
                // Update the offset
                offset += fragment.length;
            }
        }

        Message assembledMessage = new Message(oldMessage.getUuid(), oldMessage.getNumber(), oldMessage.getSenderName());
        assembledMessage.setDataBlob(assembledData);
        assembledMessage.setSequenceNumber(oldMessage.getSequenceNumber());
        assembledMessage.setTotalFragments(oldMessage.getTotalFragments());

        return assembledMessage;
    }

}
