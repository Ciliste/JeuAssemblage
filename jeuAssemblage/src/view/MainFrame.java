package view;

import javax.swing.JFrame;

import view.screen.MainScreen;
import view.screen.SoloGameCreation;

public class MainFrame extends JFrame {
    
    private final MainScreen mainScreen = new MainScreen(createSoloGameCreationCallback(this));

    public MainFrame() {

        setContentPane(mainScreen);

        setSize(1200, 800);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static Runnable createSoloGameCreationCallback(MainFrame mainFrame) {

        return () -> {

            mainFrame.setContentPane(new SoloGameCreation());
            mainFrame.revalidate();
        };
    }
}
