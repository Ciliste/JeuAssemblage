package main.event;

import java.util.TreeSet;

import main.listener.EventListener;

public class EventManager {

    private TreeSet<EventListener> eventListeners;

    public EventManager() {
        this.eventListeners = new TreeSet<EventListener>();
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
