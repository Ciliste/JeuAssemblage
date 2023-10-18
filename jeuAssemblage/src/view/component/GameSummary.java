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

        int componentSize = (int) Math.min(
                getHeightTimesPourcent(this, 0.95f) / ((yEnd - yBegin) * 1d),
                getWidthTimesPourcent(this, 0.95f) / ((xEnd - xBegin)  * 1d)
        );

        double paddingWidth  = this.getWidth () - (componentSize * ((xEnd - xBegin)  * 1d));
        double paddingHeight = this.getHeight() - (componentSize * ((yEnd - yBegin) * 1d));

        //TODO METTRE A JOUR LA METHODE
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        int[][] matrices = this.controller.getPlayBoard();

        for ( int i = yBegin; i < yEnd; i++) {

            int y = (i - yBegin) * componentSize + (int) paddingHeight / 2;

            for ( int j = xBegin; j < xEnd; j++) {

                int x = (j - xBegin) * componentSize + (int) paddingWidth / 2;
                Rectangle2D shape = new Rectangle2D.Double(x,y, componentSize, componentSize);

                if (matrices[i][j] != 0) {

                    Piece p = this.controller.getPieceById(matrices[i][j]);

					Image img = this.controller.getImageById(p.getInstanceId());

					g2d.drawImage(
						img,
						x + 1,
						y + 1,
						componentSize,
						componentSize,
						null
					);
                } 
				else {

                    g2d.draw(shape);
                }
            }
        }
    }
    
	

}
