package view;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.formdev.flatlaf.FlatDarkLaf;

import view.screen.MainScreen;

public class MainFrame extends JFrame {

	public static final Cursor DEFAULT_BLANK_CURSOR;
	static {

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		try {
			BufferedImage bi;
			bi = ImageIO.read(MainFrame.class.getResourceAsStream("/assets/blank_cursor.png"));
			Image img = bi.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			DEFAULT_BLANK_CURSOR = toolkit.createCustomCursor(img, new Point(6, 3), "sheeeesh");
		} 
		catch (IOException e) {
			
			e.printStackTrace();
			throw new RuntimeException("Cannot load blank cursor");
		}
	}

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
		this.setCursor(DEFAULT_BLANK_CURSOR);

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
		} 
		catch (Exception e) {

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
