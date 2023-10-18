package view.component.board;

import static view.utils.SwingUtils.*;

import main.Controller;
import pieces.Piece;
import view.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Grid extends JPanel {

    private final Controller controller;

    public Grid() {
        this.controller = Controller.getInstance();
        
        this.setLayout(null);

        this.setVisible(true);
    }

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

        SwingUtils.drawDebugBounds(this, g);

        double componentSize = Math.min(
                getHeightTimesPourcent(this, 0.95f)/(this.controller.getHeightBoard()*1d),
                getWidthTimesPourcent(this,0.95f)/(this.controller.getWidthBoard()*1d)
        );

        double paddingWidth  = this.getWidth () - (componentSize * this.controller.getWidthBoard ());
        double paddingHeight = this.getHeight() - (componentSize * this.controller.getHeightBoard());

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        int[][] matrices = this.controller.getPlayBoard();

        ArrayList<Piece> alreadyDraw = new ArrayList<Piece>();
        for ( int i = 0; i < matrices.length; i++) {
            double y = i*componentSize + paddingHeight/2;

            for ( int j = 0; j < matrices[i].length; j++) {
                double x = j*componentSize + paddingWidth/2;
                Rectangle2D shape = new Rectangle2D.Double(x,y, componentSize, componentSize);

                if (matrices[i][j] != 0) {

                    Piece p = this.controller.getPieceById(matrices[i][j]);
                    if (!alreadyDraw.contains(p)) {
                        // TODO rotate the image if needed
                        Image img = this.controller.getImageById(p.getInstanceId());

                        g2d.drawImage(img,
                                (int) Math.ceil(x) + 1,
                                (int) Math.ceil(y) + 1,
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
