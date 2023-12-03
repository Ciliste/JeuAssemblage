package view.component.timer.interfaces;

public interface TimerListenable {

    public abstract void addTimerListener(TimerListener listener);
    public abstract void removeTimerListener(TimerListener listener);
}
