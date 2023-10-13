package view;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

import main.Controller;
import view.screen.MainScreen;
import view.screen.SoloGameCreation;
import view.screen.SoloGameScreen;

public class MainFrame extends JFrame {
    
    private final MainScreen mainScreen;

    public MainFrame() {

		super("Puzzle GAMING");

		FlatDarkLaf.setup();

		mainScreen = new MainScreen(this);

        setContentPane(mainScreen);

        setSize(1200, 800);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
		
			InputStream stream = MainFrame.class.getResourceAsStream("/assets/icon.png");

			setIconImage(javax.imageio.ImageIO.read(stream));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Controller.getInstance().cleaningRunSpace();
			}
		});
    }

	@Override
	public void setContentPane(Container contentPane) {
		
		super.setContentPane(contentPane);
		
		revalidate();
		repaint();
	}
}
