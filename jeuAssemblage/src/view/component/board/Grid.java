package view.component.board;

import static view.utils.SwingUtils.*;

import main.Controller;
import model.PlayBoard;
import pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
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

		drawDebugBounds(this, g);

        double componentSize = Math.min(
                getHeightTimesPourcent(this, 0.9f)/(this.controller.getHeightBoard()*1d),
                getWidthTimesPourcent(this,0.9f)/(this.controller.getWidthBoard()*1d)
        );


        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        int[][] matrices = this.controller.getPlayBoard();

        ArrayList<Piece> alreadyDraw = new ArrayList<Piece>();
        for ( int i = 0; i < matrices.length; i++) {
            double y = i*componentSize;

            for ( int j = 0; j < matrices[i].length; j++) {
                double x = j*componentSize;
                Rectangle2D shape = new Rectangle2D.Double(x,y, componentSize, componentSize);

                if (matrices[i][j] != 0) {

                    Piece p = this.controller.getPieceById(matrices[i][j]);
                    if (!alreadyDraw.contains(p)) {
                        Image img = this.controller.getImageById(p.getInstanceId());

                        g2d.drawImage(img,
                                (int) x,
                                (int) y,
                                (int) componentSize * p.getWidth(),
                                (int) componentSize * p.getHeight(),
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
