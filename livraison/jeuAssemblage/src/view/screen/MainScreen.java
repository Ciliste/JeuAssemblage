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

        JButton btnProfile = new JButton("PROFIL");

        JButton btnQuitter = new JButton("QUITTER");

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
        // Center the buttons
        btnSolo.setAlignmentX(CENTER_ALIGNMENT);
        btnMulti.setAlignmentX(CENTER_ALIGNMENT);

        btnProfile.setAlignmentX(CENTER_ALIGNMENT);

        btnQuitter.setAlignmentX(CENTER_ALIGNMENT);

        setLayout(layout);

        add(Box.createVerticalGlue());

        add(btnSolo);

        add(Box.createVerticalStrut(50));

        add(btnMulti);

        add(Box.createVerticalStrut(50));

        add(btnProfile);

        add(Box.createVerticalStrut(50));

        add(btnQuitter);

        add(Box.createVerticalGlue());

        // LISTENERS
        //

        btnSolo.addActionListener(e -> {

            MainScreen.this.mainFrame.setContentPane(new SoloGameCreation(mainFrame));
        });

		btnMulti.addActionListener(e -> {

			MainScreen.this.mainFrame.setContentPane(new MultiplayerSelectScreen(mainFrame));
		});

		btnQuitter.addActionListener(e -> {

			System.exit(0);
		});
    }
}
