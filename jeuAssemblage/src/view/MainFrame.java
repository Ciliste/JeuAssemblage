package view;

import java.awt.Container;
import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.formdev.flatlaf.FlatDarkLaf;

import main.App;
import view.screen.MainScreen;

public class MainFrame extends JFrame {

	public MainFrame() {

		FlatDarkLaf.setup();

		this.setFrameIcon();

		this.setSize(1200, 800);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setContentPane(new MainScreen(this));
	}
	
	private void setFrameIcon() {

		try {

			InputStream stream = MainFrame.class.getResourceAsStream(App.RESSOURCES_PATH + "icon.png");

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
