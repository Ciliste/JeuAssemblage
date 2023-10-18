package view.component.board;

import static view.utils.SwingUtils.*;

import main.Controller;
import model.PlayBoard;
import pieces.Piece;
import view.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Grid extends JPanel {

    private final Controller controller;

	private int xGridDeb = 0;
	private int yGridDeb = 0;

	private int xGridFin = 0;
	private int yGridFin = 0;

    public Grid() {

        this.controller = Controller.getInstance();
        
        this.setLayout(null);

        this.setVisible(true);

		this.addMouseListener(new GridClickListener());
    }

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

        SwingUtils.drawDebugBounds(this, g);

        int componentSize = (int) Math.min(
			getHeightTimesPourcent(this, 0.95f)/(this.controller.getHeightBoard()*1d),
			getWidthTimesPourcent(this,0.95f)/(this.controller.getWidthBoard()*1d)
        );

        double paddingWidth  = this.getWidth () - (componentSize * this.controller.getWidthBoard ());
        double paddingHeight = this.getHeight() - (componentSize * this.controller.getHeightBoard());

		xGridDeb = (int) (paddingWidth / 2);
		yGridFin = (int) (paddingHeight / 2);

		xGridFin = (int) (getWidth() - paddingWidth / 2);
		yGridFin = (int) (getHeight() - paddingHeight / 2);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        int[][] matrices = this.controller.getPlayBoard();

        for ( int i = 0; i < matrices.length; i++) {

            int y = i * componentSize + (int) paddingHeight / 2;

            for ( int j = 0; j < matrices[i].length; j++) {

                int x = j * componentSize + (int) paddingWidth / 2;
                Rectangle2D shape = new Rectangle2D.Double(x,y, componentSize, componentSize);

                if (matrices[i][j] != 0) {

                    Piece p = this.controller.getPieceById(matrices[i][j]);

					if (p.equals(controller.getSelectedPiece())) {

						System.out.println("Piece selected");

						g2d.setColor(Color.RED);
						g2d.fill(shape);
						g2d.setColor(Color.WHITE);

						continue;
					}

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

	private class GridClickListener extends MouseAdapter {
	
		@Override
		public void mouseClicked(MouseEvent e) {

			System.out.println("Grid clicked");

			int[][] grille = controller.getPlayBoard();

			// Check if the click is in the grid
			if (e.getX() < xGridDeb || e.getX() > xGridFin || e.getY() < yGridDeb || e.getY() > yGridFin) {
				System.out.println("Click out of the grid");
				return;
			}

			// Get the position of the click in the grid
			int x = (int) ((e.getX() - xGridDeb) / ((xGridFin - xGridDeb) / grille[0].length));
			int y = (int) ((e.getY() - yGridDeb) / ((yGridFin - yGridDeb) / grille.length));

			System.out.println("x: " + x + " y: " + y);

			int pieceId = grille[y][x];

			if (pieceId == 0) {
				System.out.println("No piece here");
				return;
			}

			System.out.println("Piece id: " + pieceId);
			Piece piece = controller.getPieceById(pieceId);

			controller.setPieceSelected(piece);
			getParent().repaint();
		}
	}
}
