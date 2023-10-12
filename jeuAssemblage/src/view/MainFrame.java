package view;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

import view.screen.MainScreen;
import view.screen.SoloGameCreation;

public class MainFrame extends JFrame {
    
    private final MainScreen mainScreen;

    public MainFrame() {

		FlatDarkLaf.setup();

		mainScreen = new MainScreen(this);

        setContentPane(mainScreen);

        setSize(1200, 800);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
		
			InputStream stream = MainFrame.class.getResourceAsStream("/assets/test.txt");

			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
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
