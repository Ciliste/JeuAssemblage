package main;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import bot.strategy.AbstractStrategyAI;
import model.PlayBoard;
import model.SeedUtils;
import view.MainFrame;

public class App {

    private static JFrame frame = null;

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {
            
            frame = new MainFrame();
        });

        JPanel panel = new JPanel();
        JButton btn = new JButton("REBOOT");
        btn.addActionListener(e -> {

            frame.dispose();
            SwingUtilities.invokeLater(() -> {
            
                frame = new MainFrame();
            });
        });

        panel.add(btn);

        JFrame rebootFrame = new JFrame();
        rebootFrame.setContentPane(panel);
        rebootFrame.pack();
        rebootFrame.setVisible(true);
    }
}
