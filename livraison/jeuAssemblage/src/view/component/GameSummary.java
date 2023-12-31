package view.component;

import static view.utils.SwingUtils.*;

import javax.swing.JPanel;

import model.PlayBoard;
import piece.Piece;
import view.utils.PiecesColor;
import view.utils.SwingUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.Color;


public class GameSummary extends JPanel {

    private final PlayBoard playBoard;
    private final PiecesColor piecesColor;


    public GameSummary(PlayBoard playBoard, PiecesColor piecesColor) {

        super();

        this.playBoard = playBoard;
        this.piecesColor = piecesColor;

        this.setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Point upperLeftCorner = playBoard.getUpperLeftPieceCorner();
        Point lowerRightCorner = playBoard.getLowerRightPieceCorner();

        int xBegin = upperLeftCorner.x;
        int yBegin = upperLeftCorner.y;
        int xEnd   = lowerRightCorner.x + 1;
        int yEnd   = lowerRightCorner.y + 1;

        int componentSize = (int) Math.min(
                getHeightTimesPourcent(this, 0.95f) / ((yEnd - yBegin) * 1d),
                getWidthTimesPourcent(this, 0.95f) / ((xEnd - xBegin)  * 1d)
        );

        double paddingWidth  = this.getWidth () - (componentSize * ((xEnd - xBegin)  * 1d));
        double paddingHeight = this.getHeight() - (componentSize * ((yEnd - yBegin) * 1d));

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        int[][] matrices = playBoard.getBoardArray();

        for ( int i = yBegin; i < yEnd; i++) {

            int y = (i - yBegin) * componentSize + (int) paddingHeight / 2;

            for ( int j = xBegin; j < xEnd; j++) {

                int x = (j - xBegin) * componentSize + (int) paddingWidth / 2;
                Rectangle2D shape = new Rectangle2D.Double(x,y, componentSize, componentSize);

                if (matrices[i][j] != PlayBoard.EMPTY) {

                    Piece p = playBoard.getPieceById(matrices[i][j]);

					Image img = piecesColor.getImageById(matrices[i][j]);

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
