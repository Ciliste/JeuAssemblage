package view;

import java.awt.Container;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

import view.screen.MainScreen;
import view.screen.SoloGameCreation;
import view.screen.SoloGameScreen;

public class MainFrame extends JFrame {
    
    private final MainScreen mainScreen;

    public MainFrame() {

		FlatDarkLaf.setup();

		mainScreen = new MainScreen(this);

        setContentPane(mainScreen);

        setSize(1200, 800);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
		
			InputStream stream = MainFrame.class.getResourceAsStream("/assets/icon.png");

			// Scale down the image
			Image image = ImageIO.read(stream).getScaledInstance(25, 25, Image.SCALE_SMOOTH);

			setIconImage(image);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		setVisible(true);
    }

	@Override
	public void setContentPane(Container contentPane) {
		
		super.setContentPane(contentPane);
		
		revalidate();
		repaint();
	}
}
