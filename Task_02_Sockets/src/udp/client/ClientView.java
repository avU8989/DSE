package udp.client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ClientView implements PropertyChangeListener {
    private ClientRequestHandler controller;

    public ClientView(ClientRequestHandler controller){
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();

        System.out.println("Number received from the server: " + newValue);
    }
}
