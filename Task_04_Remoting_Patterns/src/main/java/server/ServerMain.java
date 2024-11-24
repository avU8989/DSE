package server;

import marshaller.Marshaller;

public class ServerMain {
    public static void main(String[] args){
        RemoteServiceImpl remoteService = new RemoteServiceImpl();
        Marshaller marshaller = new Marshaller();
        ServerInvoker invoker = new ServerInvoker(remoteService, marshaller);
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler(invoker);

        serverRequestHandler.start();
    }
}
