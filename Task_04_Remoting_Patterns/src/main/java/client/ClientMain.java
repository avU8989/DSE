package client;

import marshaller.Marshaller;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        ClientRequestHandler clientRequestHandler = new ClientRequestHandler("localhost", 1254);
        Marshaller marshaller = new Marshaller();
        ClientRequestor requestor = new ClientRequestor(clientRequestHandler, marshaller);
        ClientProxy proxy = new ClientProxy(requestor);
        try {
            System.out.println(proxy.hello("DSE"));
            System.out.println(proxy.goodbye("DSE"));
        }
        catch(IOException e){
            System.err.println("Error during sending Hello");
        }

    }
}
