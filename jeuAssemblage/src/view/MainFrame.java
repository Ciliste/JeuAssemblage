package view;

import java.awt.Container;
import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.formdev.flatlaf.FlatDarkLaf;

import view.screen.MainScreen;

public class MainFrame extends JFrame {

	private static MainFrame instance;

	public static MainFrame getInstance() {
		if ( instance != null ) return instance;
		
		instance = new MainFrame();
		MainScreen mainScreen = new MainScreen();
		instance.setContentPane(mainScreen);
		return instance;
	}

	private MainFrame() {

		FlatDarkLaf.setup();

		this.setFrameIcon();

		this.setSize(1200, 800);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setFrameIcon() {
		try {
			InputStream stream = MainFrame.class.getResourceAsStream("/assets/icon.png");

			// Scale down the image
			Image image = ImageIO.read(stream).getScaledInstance(25, 25, Image.SCALE_SMOOTH);

			setIconImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setContentPane(Container contentPane) {
		
		super.setContentPane(contentPane);
		
		revalidate();
		repaint(); 
	}
}
