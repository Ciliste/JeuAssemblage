package view.component.board;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerPanel extends JPanel {

    private final JLabel lblMinutes = new JLabel("00");
    private final JLabel lblColon   = new JLabel(" : ");
    private final JLabel lblSeconds = new JLabel("00");

    public TimerPanel(JFrame frame, Timer timer, Runnable onTimerFinished) {
        
        super();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(lblMinutes);
        add(lblColon);
        add(lblSeconds);

        if (false == (Timer.NO_TIMER == timer)) {

            lblMinutes.setText(String.format("%02d", timer.getNbMinutes()));
            lblSeconds.setText(String.format("%02d", timer.getNbSeconds()));

            timer.addTimerListener(new TimerListener() {
        
                @Override
                public void timerChanged(Object source, Timer timer) {
        
                    System.out.println("timerChanged");

                    lblMinutes.setText(String.format("%02d", timer.getCurrentNbMinutes()));
                    lblSeconds.setText(String.format("%02d", timer.getCurrentNbSeconds()));

                    repaint();
                }
        
                @Override
                public void timerFinished(Object source, Timer timer) {
        
                    lblMinutes.setText("00");
                    lblSeconds.setText("00");

                    onTimerFinished.run();

                    repaint();
                }
            });

            frame.addWindowListener(new WindowAdapter() {
                
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    
                    timer.stopTimer();
                }
            });

            timer.startTimer();
        }
    }

    public static class Timer {
    
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

        private void stopTimer() {

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

        private final List<TimerListener> listeners = new LinkedList<>();

        public void addTimerListener(TimerListener listener) {
    
            listeners.add(listener);
        }

        public void removeTimerListener(TimerListener listener) {
    
            listeners.remove(listener);
        }
    }

    public static interface TimerListenable {
    
        public abstract void addTimerListener(TimerListener listener);
        public abstract void removeTimerListener(TimerListener listener);
    }

    public static interface TimerListener {
    
        public abstract void timerChanged(Object source, Timer timer);
        public abstract void timerFinished(Object source, Timer timer);
    }
}