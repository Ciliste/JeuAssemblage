package view.component.board;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.component.timer.Timer;
import view.component.timer.interfaces.TimerListener;

public class TimerPanel extends JPanel implements TimerListener {

    private final JLabel lblMinutes = new JLabel("00");
    private final JLabel lblColon   = new JLabel(" : ");
    private final JLabel lblSeconds = new JLabel("00");

    private Runnable onTimerFinished;

    public TimerPanel(JFrame frame, Timer timer, Runnable onTimerFinished) {
        
        super();

        this.onTimerFinished = onTimerFinished;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(lblMinutes);
        add(lblColon);
        add(lblSeconds);

        if (false == (Timer.NO_TIMER == timer)) {

            lblMinutes.setText(String.format("%02d", timer.getNbMinutes()));
            lblSeconds.setText(String.format("%02d", timer.getNbSeconds()));

            timer.addTimerListener(this);

            frame.addWindowListener(new WindowAdapter() {
                
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    
                    timer.stopTimer();
                }
            });

            timer.startTimer();
        }
    }

    @Override
    public void timerChanged(Object source, Timer timer) {

        lblMinutes.setText(String.format("%02d", timer.getCurrentNbMinutes()));
        lblSeconds.setText(String.format("%02d", timer.getCurrentNbSeconds()));

        repaint();
    }

    @Override
    public void timerFinished(Object source, Timer timer) {

        lblMinutes.setText("00");
        lblSeconds.setText("00");

        this.onTimerFinished.run();

        repaint();
    }

}