package view.component.timer;

public interface TimerListener {

    public abstract void timerChanged(Object source, Timer timer);
    public abstract void timerFinished(Object source, Timer timer);
}
