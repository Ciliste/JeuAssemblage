package view.component;

import static view.utils.SwingUtils.*;

import javax.swing.JPanel;

import pieces.Piece;
import view.utils.SwingUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import main.Controller;
import java.util.ArrayList;
import java.awt.Color;


public class GameSummary extends JPanel {

    private final Controller controller = Controller.getInstance(); 

    public GameSummary() {
        super();
        this.setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        SwingUtils.drawDebugBounds(this, g);

        int[] areaInformation = this.controller.areaInfomartion();

        int xBegin = areaInformation[0];
        int yBegin = areaInformation[1];
        int xEnd   = areaInformation[2];
        int yEnd   = areaInformation[3];

        double componentSize = Math.min(
                getHeightTimesPourcent(this, 0.95f) / ((yEnd - yBegin) * 1d),
                getWidthTimesPourcent(this, 0.95f) / ((xEnd - xBegin)  * 1d)
        );

        double paddingWidth  = this.getWidth () - (componentSize * ((xEnd - xBegin)  * 1d));
        double paddingHeight = this.getHeight() - (componentSize * ((yEnd - yBegin) * 1d));

        //TODO METTRE A JOUR LA METHODE
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        int[][] matrices = this.controller.getPlayBoard();

        ArrayList<Piece> alreadyDraw = new ArrayList<Piece>();
        for (int i = yBegin; i < yEnd ; i++) {
            double y = (i - yBegin) * componentSize + paddingHeight/2;

            for (int j = xBegin; j < xEnd; j++) {
                double x = (j - xBegin) * componentSize + paddingWidth/2;
                Rectangle2D shape = new Rectangle2D.Double(x, y, componentSize, componentSize);

                if (matrices[i][j] != 0) {

                    Piece p = this.controller.getPieceById(matrices[i][j]);
                    if (!alreadyDraw.contains(p)) {
                        // TODO rotate the image if needed
                        Image img = this.controller.getImageById(p.getInstanceId());

                        g2d.drawImage(img,
                                (int) Math.ceil(x),
                                (int) Math.ceil(y),
                                (int) Math.floor(componentSize * 3),
                                (int) Math.floor(componentSize * 3),
                                null);

                        alreadyDraw.add(p);
                    }
                } else {
                    g2d.draw(shape);
                }
            }
        }
    }
    
	

}
