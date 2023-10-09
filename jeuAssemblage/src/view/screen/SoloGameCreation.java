package view.screen;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SoloGameCreation extends JPanel {
    
    public SoloGameCreation() {

        JButton btnCancel = new JButton("<--");

        JLabel lblSeed = new JLabel("Seed :");
        JTextField txtSeed = new JTextField();

        JLabel lblSizeX = new JLabel("Size X :");
        JTextField txtSizeX = new JTextField();

        JLabel lblSizeY = new JLabel("Size Y :");
        JTextField txtSizeY = new JTextField();

        JLabel lblNbPieces = new JLabel("Nb pièces :");
        JTextField txtNbPieces = new JTextField();

        JButton btnPlay = new JButton("Jouer");

        
    }
}
