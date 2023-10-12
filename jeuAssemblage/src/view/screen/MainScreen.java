package view.screen;

import java.util.concurrent.Flow;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import view.MainFrame;

import java.awt.FlowLayout;

public class MainScreen extends JPanel {
    
    private final MainFrame mainFrame;

    public MainScreen(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        JButton btnSolo    = new JButton("SOLO");
        JButton btnMulti   = new JButton("MULTIJOUEUR");
        JButton btnQuitter = new JButton("Quitter");

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
        // Center the buttons
        btnSolo.setAlignmentX(CENTER_ALIGNMENT);
        btnMulti.setAlignmentX(CENTER_ALIGNMENT);
        btnQuitter.setAlignmentX(CENTER_ALIGNMENT);

        setLayout(layout);

        add(Box.createVerticalGlue());

        add(btnSolo);

        add(Box.createVerticalStrut(50));

        add(btnMulti);

        add(Box.createVerticalStrut(50));

        add(btnQuitter);

        add(Box.createVerticalGlue());

        // LISTENERS
        //

        btnSolo.addActionListener(e -> {

            MainScreen.this.mainFrame.setContentPane(new SoloGameCreation(MainScreen.this.mainFrame));
        });
    }
}
