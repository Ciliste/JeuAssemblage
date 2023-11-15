package main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.arrangement.Arrangement;
import model.arrangement.ArrangementList;
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
        */

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
