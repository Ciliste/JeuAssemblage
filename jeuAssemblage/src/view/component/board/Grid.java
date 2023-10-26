package view.component.board;

import static view.utils.SwingUtils.*;

import model.PlayBoard;
import piece.Piece;
import view.component.board.listener.IPieceClickedListenable;
import view.component.board.listener.IPieceClickedListener;
import view.utils.PieceRenderUtils;
import view.utils.SwingUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JPanel;

public class Grid extends JPanel implements IPieceClickedListenable {

	private final PlayBoard playBoard;

	private int xGridDeb = 0;
	private int yGridDeb = 0;

	private int xGridFin = 0;
	private int yGridFin = 0;

	private Image selectedPieceSurrondingImage = null;
	private int xSelectedPieceSurrondingImage = 0;
	private int ySelectedPieceSurrondingImage = 0;

    public Grid(PlayBoard playBoard) {
        
		this.playBoard = playBoard;

        this.setLayout(null);

        this.setVisible(true);

		this.addMouseListener(new GridClickListener());
		this.addMouseMotionListener(new GridMouseMotionListener());

		this.addPieceClickedListener((Object source, int pieceId) -> {

			Piece piece = playBoard.getPieceCloneById(pieceId);

			selectedPieceSurrondingImage = PieceRenderUtils.createSurrondingPieceImage(piece.getPiece(), Color.CYAN);

			Point upperLeftPieceCorner = playBoard.getUpperLeftPieceCornerById(pieceId);
			xSelectedPieceSurrondingImage = upperLeftPieceCorner.x;
			ySelectedPieceSurrondingImage = upperLeftPieceCorner.y;

			repaint();

			Logger.getGlobal().info("Piece clicked: " + pieceId);
			Logger.getGlobal().info("Piece clicked x: " + xSelectedPieceSurrondingImage);
			Logger.getGlobal().info("Piece clicked y: " + ySelectedPieceSurrondingImage);
		});
    }

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

        SwingUtils.drawDebugBounds(this, g);

        int componentSize = (int) Math.min(
			getHeightTimesPourcent(this, 0.95f) / (playBoard.getHeight() * 1d),
			getWidthTimesPourcent(this,0.95f) / (playBoard.getWidth() * 1d)
        );

        double paddingWidth  = this.getWidth () - (componentSize * playBoard.getBoardWidth());
        double paddingHeight = this.getHeight() - (componentSize * playBoard.getBoardHeight());

		xGridDeb = (int) (paddingWidth / 2);
		yGridDeb = (int) (paddingHeight / 2);

		xGridFin = (int) (getWidth() - paddingWidth / 2);
		yGridFin = (int) (getHeight() - paddingHeight / 2);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);

		int[][] matrices = playBoard.getBoardArray();
		Image scaledSurroningImage = null;
		if (selectedPieceSurrondingImage != null) {

			scaledSurroningImage = selectedPieceSurrondingImage.getScaledInstance(
				componentSize * (selectedPieceSurrondingImage.getWidth(null) / PieceRenderUtils.CELL_PIXEL_SIZE), 
				componentSize * (selectedPieceSurrondingImage.getHeight(null) / PieceRenderUtils.CELL_PIXEL_SIZE), 
				Image.SCALE_SMOOTH
			);
		}

        for ( int i = 0; i < matrices.length; i++) {

            int y = i * componentSize + (int) paddingHeight / 2;

            for ( int j = 0; j < matrices[i].length; j++) {

                int x = j * componentSize + (int) paddingWidth / 2;
                Rectangle2D shape = new Rectangle2D.Double(x,y, componentSize, componentSize);

                if (matrices[i][j] != 0) {

                    Image cell = playBoard.getCellImageByPieceId(matrices[i][j]);

					g2d.drawImage(
						cell,
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

		if (selectedPieceSurrondingImage != null) {

			g2d.drawImage(
				scaledSurroningImage,
				xSelectedPieceSurrondingImage * componentSize + (int) paddingWidth / 2 + 1,
				ySelectedPieceSurrondingImage * componentSize + (int) paddingHeight / 2 + 1,
				scaledSurroningImage.getWidth(null),
				scaledSurroningImage.getHeight(null),
				null
			);
		}

		// if (controller.getSelectedPiece() != null) {

		// 	// Get the position of the mouse in the grid
		// 	int x = (int) ((mousePosition.getX() - xGridDeb) / ((xGridFin - xGridDeb) / matrices[0].length));
		// 	int y = (int) ((mousePosition.getY() - yGridDeb) / ((yGridFin - yGridDeb) / matrices.length));

		// 	int[][] piece = controller.getSelectedPiece().getBounds();
		// 	Image img = controller.getImageById(controller.getSelectedPiece().getInstanceId());

		// 	for (int i = 0; i < piece.length; i++) {

		// 		for (int j = 0; j < piece[i].length; j++) {

		// 			if (piece[i][j] != 0) {

		// 				int x2 = (x + j - 1);
		// 				int y2 = (y + i - 1);

		// 				if (x2 - j < 0) x2 = j;
		// 				if (y2 - i < 0) y2 = i;

		// 				if (x2 + (piece[i].length - j) > matrices[0].length) x2 = matrices[0].length - piece[i].length + j;
		// 				if (y2 + (piece.length - i) > matrices.length) y2 = matrices.length - piece.length + i;

		// 				g2d.drawImage(
		// 					img,
		// 					x2 * (int) componentSize + (int) paddingWidth / 2 + 1,
		// 					y2 * (int) componentSize + (int) paddingHeight / 2 + 1,
		// 					(int) componentSize,
		// 					(int) componentSize,
		// 					null
		// 				);
		// 			}
		// 		}
		// 	}
		// }
	}

	private final Point mousePosition = new Point(0, 0);

	private class GridClickListener extends MouseAdapter {
	
		@Override
		public void mouseClicked(MouseEvent e) {

			mousePosition.setLocation(e.getX(), e.getY());

			int x = (int) ((mousePosition.getX() - xGridDeb) / ((xGridFin - xGridDeb) / playBoard.getWidth()));
			int y = (int) ((mousePosition.getY() - yGridDeb) / ((yGridFin - yGridDeb) / playBoard.getHeight()));

			int pieceId = playBoard.getPieceIdAt(x, y);

			if (pieceId != 0) {

				Grid.this.firePieceClicked(Grid.this, pieceId);
			}
		}
	}

	private class GridMouseMotionListener extends MouseAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {

			// if (controller.getSelectedPiece() != null) {

			// 	mousePosition.setLocation(e.getX(), e.getY());
			// 	repaint();
			// }
		}
	}

	private List<IPieceClickedListener> pieceClickedListeners = new LinkedList<>();

	public void addPieceClickedListener(IPieceClickedListener listener) {

		this.pieceClickedListeners.add(listener);
	}

	public void removePieceClickedListener(IPieceClickedListener listener) {

		this.pieceClickedListeners.remove(listener);
	}

	private void firePieceClicked(Object source, int pieceId) {

		for (IPieceClickedListener listener : this.pieceClickedListeners) {

			listener.pieceClicked(source, pieceId);
		}
	}

	public void unselectPiece() {

		selectedPieceSurrondingImage = null;
		repaint();
	}
}
