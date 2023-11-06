package observer.interfaces;

public interface Listenable {
    public void addListener(Listener l);
    public void removeListener(Listener l);
    public void fireEvents();
}
