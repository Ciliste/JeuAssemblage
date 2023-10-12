package view.screen.board.utils;

import static view.utils.SwingUtils.*;

import main.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class GrilleBoard extends JPanel{

    private Controller controller;

    public GrilleBoard()
    {
        this.controller = Controller.getInstance();

        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        double componentSize = Math.min(
                getHeightTimesPourcent(this, 0.9f)/(this.controller.getHeightBoard()*1d),
                getWidthTimesPourcent(this,0.9f)/(this.controller.getWidthBoard()*1d)
        );


        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        int[][] matrices = this.controller.getPlayBoard();

        for ( int i = 0; i < matrices.length; i++) {
            double y = i*componentSize;

            for ( int j = 0; j < matrices[i].length; j++) {
                double x = j*componentSize;
                Rectangle2D shape = new Rectangle2D.Double(x,y, componentSize, componentSize);

                if (matrices[i][j] != 0) {
                    g2d.fill(shape);
                } else {
                    g2d.draw(shape);
                }
            }
        }

    }
}
