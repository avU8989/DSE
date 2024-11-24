package server;

import client.protocol.RemoteService;

import java.io.IOException;

public class RemoteServiceImpl implements RemoteService {

    @Override
    public String hello(String name) throws IOException {
       return "Hello, " + name + "!";
    }

    @Override
    public String goodbye(String name) throws IOException {
        return name + ", I'm afraid this is a farewell.";
    }
}
