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

        //FUTURS TESTS QUI FAUT METTRE DANS LES JUNITS
        /*
        Arrangement a = new Arrangement(0, 0, 0, 0, "shesh", 0);
        Arrangement b = new Arrangement(0, 0, 0, 0, "shesh10", 10);
        
        ArrangementList.addArrangement(a);
        
        PlayBoard p = PlayBoard.constructPlayBoard(Long.parseLong("2392006401451300864"), 10, 11, 5);
        new AIStrategy(p);
        
        
        AgStrategy a = new AgStrategy(PlayBoard.constructPlayBoard(new Long("1252177511074775040"), 12, 15, 6));
        while (a.tick()) {
        }
        */

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
