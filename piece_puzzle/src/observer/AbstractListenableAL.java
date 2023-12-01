package observer;

import java.util.ArrayList;

import observer.interfaces.Listener;

public abstract class AbstractListenableAL {
    protected ArrayList<Listener> alListeners;

    public AbstractListenableAL() {
        this.alListeners = new ArrayList<Listener>();
    }

    public void addListener(Listener l) {
        this.alListeners.add(l);
    }

    public void removeListener(Listener l) {
        this.alListeners.remove(l);
    }

    protected void fireAllEvents() {
        for (Listener l : this.alListeners) {
            l.update();
        }
    }

    @Override
    public String toString() {
        String ret = "Piece \n";
        for (Listener l : this.alListeners) {
            ret += l + "\n";
        }
        return ret;
    }
}