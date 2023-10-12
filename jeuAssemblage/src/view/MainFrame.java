package view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

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

		mainScreen = new MainScreen(createSoloGameCreationCallback(this));

        setContentPane(mainScreen);

        setSize(1200, 800);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
    }

    private static Runnable createSoloGameCreationCallback(MainFrame mainFrame) {

        return () -> {

            mainFrame.setContentPane(new SoloGameCreation(
                    createMainScreenCallback(mainFrame),
                    createSoloGameScreenCallback(mainFrame)
            ));
            mainFrame.revalidate();
        };
    }

    private static Runnable createMainScreenCallback(MainFrame mainFrame) {

        return () -> {

            mainFrame.setContentPane(mainFrame.mainScreen);
            mainFrame.revalidate();
        };
    }

    private static Runnable createSoloGameScreenCallback(MainFrame mainFrame) {

        return () -> {

            mainFrame.setContentPane(new SoloGameScreen(createMainScreenCallback(mainFrame)));
            mainFrame.revalidate();
        };
    }
}
