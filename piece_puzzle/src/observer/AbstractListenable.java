package observer;

import java.util.ArrayList;

import observer.interfaces.Listener;

public abstract class AbstractListenable {
    private ArrayList<Listener> alListeners;

    public AbstractListenable() {
        this.alListeners = new ArrayList<Listener>();
    }

    public void addListener(Listener l) {
        this.alListeners.add(l);
    }

    public void removeListener(Listener l) {
        this.alListeners.remove(l);
    }

    protected void fireEvents() {
        for (Listener l : this.alListeners) {
            l.update();
        }
    }
}