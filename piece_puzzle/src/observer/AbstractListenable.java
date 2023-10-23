package observer;

import java.util.ArrayList;
import java.util.HashMap;

import observer.interfaces.Listener;

public abstract class AbstractListenable {
    private HashMap<String,ArrayList<Listener>> hmListeners;

    public AbstractListenable() {
        this.hmListeners = new HashMap<String,ArrayList<Listener>>();
    }

    public void addListener(String type, Listener l) {
        if (!this.hmListeners.containsKey(type)) {
            this.hmListeners.put(type, new ArrayList<Listener>());
        }
        this.hmListeners.get(type).add(l);
    }

    public void removeListener(String type, Listener l) {
        if (!this.hmListeners.containsKey(type)) return;
        this.hmListeners.get(type).remove(l);
    }

    protected void fireEvents(String type) {
        if (!this.hmListeners.containsKey(type))
            return;
        for (Listener l : this.hmListeners.get(type)) {
            l.update();
        }
    }
    
    protected void fireAllEvents() {
        for (String s : this.hmListeners.keySet()) {
            fireEvents(s);
        }
    }
}