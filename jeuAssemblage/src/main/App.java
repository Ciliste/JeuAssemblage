package main;

import javax.swing.SwingUtilities;

import view.MainFrame;

public class App {
    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {
            
            new MainFrame();
        });
    }
}
