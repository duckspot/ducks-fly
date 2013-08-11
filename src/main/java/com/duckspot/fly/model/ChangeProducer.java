package com.duckspot.fly.model;

import java.util.ArrayList;
import java.util.Collection;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Sends ChangeEvent.
 * 
 * @author Peter Dobson
 */
public abstract class ChangeProducer {

    private Collection<ChangeListener> listeners;

    public ChangeProducer() {
        this.listeners = new ArrayList<>();
    }
    
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }
    
    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }
    
    protected void fireStateChanged(ChangeEvent event) {        
        for (ChangeListener l: listeners) {
            l.stateChanged(event);
        }
    }
    
    protected void fireStateChanged() {
        fireStateChanged(new ChangeEvent(this));        
    }
}
