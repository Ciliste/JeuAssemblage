package main.event;

import java.util.ArrayList;

import main.listener.EventListener;

public class EventManager {

    private ArrayList<EventListener> eventListeners;

    public EventManager() {
        this.eventListeners = new ArrayList<EventListener>();
    }

    public void addListener(EventListener e) {
        this.eventListeners.add(e);
    }

    public void fireEvent() {
        for (EventListener e : this.eventListeners) {
            e.update();
        }
    }
    
}
