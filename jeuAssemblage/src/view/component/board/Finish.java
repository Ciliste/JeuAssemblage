package view.component.board;

import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Controller;
import main.listener.EventListener;
import view.utils.SwingUtils;

public class Finish extends JPanel implements EventListener{

    private final Controller controller;

    private final JLabel lblPreciseArea = new JLabel("Number of pixel :");
    private final JLabel lblArea = new JLabel("Area Taken :");


    public Finish() {
        this.controller = Controller.getInstance();

        this.setLayout(null);

        this.setVisible(true);

        this.add(this.lblPreciseArea);
        this.add(this.lblArea);
    }

    @Override
    public void update() {
        int[] areaInfo = this.controller.areaInfomartion();
        lblPreciseArea.setText("Number of pixel : " + areaInfo[0]);
        lblArea.setText("Area Taken : " + areaInfo[0]);
    }

    @Override
    public void doLayout() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        SwingUtils.drawDebugBounds(this, g);
    }
    
}
