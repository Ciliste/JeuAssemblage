package view.component.timer;

import java.util.LinkedList;
import java.util.List;

public class Timer {
    
    public static final Timer NO_TIMER = new Timer(-1, -1);

    private final int nbMinutes;
    private final int nbSeconds;

    private int currentNbMinutes;
    private int currentNbSeconds;

    private Thread thread;

    public Timer(int nbMinutes, int nbSeconds) {

        this.nbMinutes = nbMinutes;
        this.nbSeconds = nbSeconds;

        this.currentNbMinutes = nbMinutes;
        this.currentNbSeconds = nbSeconds;
    }

    public int getCurrentNbMinutes() {

        return currentNbMinutes;
    }

    public int getCurrentNbSeconds() {

        return currentNbSeconds;
    }

    public int getNbMinutes() {

        return nbMinutes;
    }

    public int getNbSeconds() {

        return nbSeconds;
    }

    public void startTimer() {

        if (thread != null) {

            thread.interrupt();
        }

        thread = new Thread(() -> {

            while (currentNbMinutes > 0 || currentNbSeconds > 0) {

                try {

                    Thread.sleep(1000);
                } 
                catch (InterruptedException e) {

                    return;
                }

                if (currentNbSeconds > 0) {

                    currentNbSeconds--;
                } 
                else {

                    currentNbSeconds = 59;
                    currentNbMinutes--;
                }

                fireTimerChanged();
            }

            fireTimerFinished();
        });

        thread.start();
    }

    public void stopTimer() {

        if (thread != null) {

            thread.interrupt();
        }
    }

    private void fireTimerChanged() {

        for (TimerListener listener : listeners) {

            listener.timerChanged(this, this);
        }
    }

    private void fireTimerFinished() {

        for (TimerListener listener : listeners) {

            listener.timerFinished(this, this);
        }
    }

    private final List<TimerListener> listeners = new LinkedList<TimerListener>();

    public void addTimerListener(TimerListener listener) {

        listeners.add(listener);
    }

    public void removeTimerListener(TimerListener listener) {

        listeners.remove(listener);
    }
}
