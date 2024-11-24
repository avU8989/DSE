package udp.client;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ClientModel {
    private int number;
    private PropertyChangeSupport support;

    public ClientModel(){
        this.number = 0;
        this.support = new PropertyChangeSupport(this);
    }

    public void setNumber(int newOutput){
        int oldValue = this.number;
        this.number = newOutput;
        this.support.firePropertyChange("Number", oldValue, newOutput);
    }

    public int getNumber(){
        return this.number;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }
}
