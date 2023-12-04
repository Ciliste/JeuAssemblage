package main;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.MainFrame;

public class App {

	public static final String RESSOURCES_PATH = "/assets/";

    private static JFrame frame = null;

    public static void main(String[] args) throws Exception {

        UIManager.put( "ScrollBar.trackInsets", new Insets( 2, 4, 2, 4 ) );
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("Component.arrowType", "triangle");
        UIManager.put( "Button.arc", 10 );
        UIManager.put( "Component.arc", 10 );
        UIManager.put("TextComponent.arc", 2);
        
        
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
