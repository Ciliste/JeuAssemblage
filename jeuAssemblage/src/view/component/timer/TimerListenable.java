package view.component.timer;

public interface TimerListenable {

    public abstract void addTimerListener(TimerListener listener);
    public abstract void removeTimerListener(TimerListener listener);
}
