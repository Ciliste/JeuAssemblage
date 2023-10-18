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
		this.addMouseMotionListener(new GridMouseMotionListener());
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

		if (controller.getSelectedPiece() != null) {

			// Get the position of the mouse in the grid
			int x = (int) ((mousePosition.getX() - xGridDeb) / ((xGridFin - xGridDeb) / matrices[0].length));
			int y = (int) ((mousePosition.getY() - yGridDeb) / ((yGridFin - yGridDeb) / matrices.length));

			int[][] piece = controller.getSelectedPiece().getBounds();
			Image img = controller.getImageById(controller.getSelectedPiece().getInstanceId());

			for (int i = 0; i < piece.length; i++) {

				for (int j = 0; j < piece[i].length; j++) {

					if (piece[i][j] != 0) {

						int x2 = (x + j - 1);
						int y2 = (y + i - 1);

						if (x2 - j < 0) x2 = j;
						if (y2 - i < 0) y2 = i;

						if (x2 + (piece[i].length - j) > matrices[0].length) x2 = matrices[0].length - piece[i].length + j;
						if (y2 + (piece.length - i) > matrices.length) y2 = matrices.length - piece.length + i;

						g2d.drawImage(
							img,
							x2 * (int) componentSize + (int) paddingWidth / 2 + 1,
							y2 * (int) componentSize + (int) paddingHeight / 2 + 1,
							(int) componentSize,
							(int) componentSize,
							null
						);
					}
				}
			}
		}
	}

	private final Point mousePosition = new Point(0, 0);

	private class GridClickListener extends MouseAdapter {
	
		@Override
		public void mouseClicked(MouseEvent e) {

			mousePosition.setLocation(e.getX(), e.getY());

			int[][] grille = controller.getPlayBoard();

			// Check if the click is in the grid
			if (e.getX() < xGridDeb || e.getX() > xGridFin || e.getY() < yGridDeb || e.getY() > yGridFin) {
				System.out.println("Click out of the grid");
				return;
			}

			// Get the position of the click in the grid
			int x = (int) ((e.getX() - xGridDeb) / ((xGridFin - xGridDeb) / grille[0].length));
			int y = (int) ((e.getY() - yGridDeb) / ((yGridFin - yGridDeb) / grille.length));

			int pieceId = grille[y][x];

			if (pieceId == 0) {

				if (controller.getSelectedPiece() == null) return;

				if (controller.canBeAddedToBoard(x, y)) {

					controller.addPieceOnBoard(x, y);
					controller.setPieceSelected(null);
					getParent().repaint();
				}
				else {

					System.out.println("Piece can't be added to the board");
				}

				return;
			}

			Piece piece = controller.getPieceById(pieceId);

			controller.setPieceSelected(piece);
			getParent().repaint();
		}
	}

	private class GridMouseMotionListener extends MouseAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {

			if (controller.getSelectedPiece() != null) {

				mousePosition.setLocation(e.getX(), e.getY());
				repaint();
			}
		}
	}
}
