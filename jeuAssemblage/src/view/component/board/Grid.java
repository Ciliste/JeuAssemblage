package view.component.board;

import static view.utils.SwingUtils.*;

import model.PlayBoard;
import observer.interfaces.Listener;
import piece.Piece;
import utils.ETypeListen;
import view.utils.KeyboardManager;
import view.utils.PieceRenderUtils;
import view.utils.PiecesColor;
import view.utils.SwingUtils;
import view.utils.KeyboardManager.KeyboardAdapter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import bot.view.interfaces.IMovableView;

public class Grid extends JPanel implements Listener, IMovableView{

	private final PlayBoard playBoard;

	private int xGridDeb = 0;
	private int yGridDeb = 0;

	private int xGridFin = 0;
	private int yGridFin = 0;

	private Piece selectedPiece = null;
	private Piece selectedPieceClone = null;
	private int selectedPieceId = -1;
	private int xClickOriginSelectedPiece = -1;
	private int yClickOriginSelectedPiece = -1;

	private Image selectedPieceSurrondingImage = null;
	private int xSelectedPieceSurrondingImage = 0;
	private int ySelectedPieceSurrondingImage = 0;

	private PiecesColor piecesColor;

	private final boolean isPreview;

	public Grid(PlayBoard playBoard, PiecesColor piecesColor) {

		this(playBoard, false, piecesColor);
	}

	public Grid(PlayBoard playBoard, boolean isPreview, PiecesColor piecesColor) {

		this.playBoard = playBoard;
		this.playBoard.addListener(ETypeListen.PLAYVIEW.typeListen, this);

		this.piecesColor = piecesColor;

		this.setLayout(null);

		this.setVisible(true);

		this.isPreview = isPreview;
		if (isPreview)
			return;

		this.addMouseListener(new GridClickListener());
		this.addMouseMotionListener(new GridMouseMotionListener());

		this.addMouseMotionListener(new GridMouseMotionListener());

		// Gestion des contrôles de la manipulation des pièces
		KeyboardManager.addKeyboardListener(new PieceKeyboardListener());

		// Gestion des contrôles de la sélection des pièces
		KeyboardManager.addKeyboardListener(new SelectPieceKeyboardListener());

		// Gestion des contrôles du déplacement des pièces
		KeyboardManager.addKeyboardListener(new ControlMoveKeyboardListener());
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

                    Image cell = piecesColor.getImageById(matrices[i][j]);

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

		if (selectedPieceClone != null) {

			Image cellImage = piecesColor.getImageById(selectedPieceId);
			boolean[][] pieceMatrix = selectedPieceClone.getPiece();

			int x = mousePosition.x;
			int y = mousePosition.y;

			for (int i = 0; i < selectedPieceClone.getHeight(); i++) {

				for (int j = 0; j < selectedPieceClone.getWidth(); j++) {

					if (pieceMatrix[i][j]) {

						g2d.drawImage(
							cellImage,
							(x + j - xClickOriginSelectedPiece) * componentSize + (int) paddingWidth / 2 + 1,
							(y + i - yClickOriginSelectedPiece) * componentSize + (int) paddingHeight / 2 + 1,
							componentSize,
							componentSize,
							null
						);
					}
				}
			}
		}

		Point upperLeftCorner = playBoard.getUpperLeftPieceCorner();
		Point lowerRightCorner = playBoard.getLowerRightPieceCorner();
		
		int width = lowerRightCorner.x - upperLeftCorner.x + 1;
		int height = lowerRightCorner.y - upperLeftCorner.y + 1;

		Image surrondingRectangleImage = PieceRenderUtils.createSurrondingRectangleImage(width, height);

		if (null != surrondingRectangleImage && false == isPreview) {

			g2d.drawImage(
				surrondingRectangleImage,
				upperLeftCorner.x * componentSize + (int) paddingWidth / 2 + 1,
				upperLeftCorner.y * componentSize + (int) paddingHeight / 2 + 1,
				width * componentSize,
				height * componentSize,
				null
			);
		}
		
		if (this.isPreview) {
			g.setColor(Color.RED);
			Ellipse2D.Double circle = new Ellipse2D.Double(
				(mousePosition.x * componentSize) + paddingWidth/2,
				(mousePosition.y * componentSize) + paddingHeight/2,
				componentSize,
				componentSize
			);

			((Graphics2D) g).fill(circle);
			g.setColor(Color.BLACK);
			((Graphics2D) g).draw(circle);
		}
	}

	private final Point mousePosition = new Point(0, 0);

	private class GridClickListener extends MouseAdapter {
	
		@Override
		public void mouseClicked(MouseEvent e) {

			mousePosition.setLocation(
				(int) ((e.getX() - xGridDeb) / ((xGridFin - xGridDeb) / playBoard.getWidth())),
				(int) ((e.getY() - yGridDeb) / ((yGridFin - yGridDeb) / playBoard.getHeight()))
			);

			int x = mousePosition.x;
			int y = mousePosition.y;

			if (selectedPiece  == null) {
				clickOnPiece(x, y);
			} else {
				placePiece(x, y);
			} 
		}
	}

	private class GridMouseMotionListener extends MouseAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {

			mousePosition.setLocation(
				(int) ((e.getX() - xGridDeb) / ((xGridFin - xGridDeb) / playBoard.getWidth())),
				(int) ((e.getY() - yGridDeb) / ((yGridFin - yGridDeb) / playBoard.getHeight()))
			);

			if (selectedPiece != null) {

				repaint();
			}
		}
	}

	private class PieceKeyboardListener extends KeyboardAdapter {
			
		@Override
		public void onKeyPressed(KeyboardManager.Keys key) {

			if (null != selectedPieceClone) {

				if (KeyboardManager.Keys.R == key) {

					if (true == KeyboardManager.isKeyPressed(KeyboardManager.Keys.SHIFT)) {

						selectedPieceClone.rotateLeft();
					}
					else if (true == KeyboardManager.isKeyPressed(KeyboardManager.Keys.CONTROL)) {

						selectedPieceClone.reverse();
					}
					else {

						selectedPieceClone.rotateRight();
					}
					
				}
			}
		}
	}

	private class SelectPieceKeyboardListener extends KeyboardAdapter {

		@Override
		public void onKeyPressed(KeyboardManager.Keys key) {

			if (KeyboardManager.Keys.TAB == key) {

				int pieceId = (-1 == selectedPieceId) ? 1 : selectedPieceId + 1;

				try {
					
					selectedPiece = playBoard.getPieceById(pieceId);

					if (null == selectedPiece) {

						pieceId = 1;
						selectedPiece = playBoard.getPieceById(pieceId);
					}

					selectedPieceClone = Piece.clone(selectedPiece);
				} 
				catch (Exception e) {

					e.printStackTrace();
					pieceId = 1;
					selectedPiece = playBoard.getPieceById(pieceId);
					selectedPieceClone = Piece.clone(selectedPiece);
				}

				selectedPieceSurrondingImage = PieceRenderUtils.createSurrondingPieceImage(selectedPieceClone.getPiece(), Color.CYAN);

				Point upperLeftPieceCorner = playBoard.getUpperLeftPieceCornerById(pieceId);
				xSelectedPieceSurrondingImage = upperLeftPieceCorner.x;
				ySelectedPieceSurrondingImage = upperLeftPieceCorner.y;

				selectedPieceId = pieceId;

				xClickOriginSelectedPiece = 0;
				yClickOriginSelectedPiece = 0;

				repaint();
			}
			else if (KeyboardManager.Keys.ESCAPE == key) {

				selectedPieceId = -1;
				xClickOriginSelectedPiece = -1;
				yClickOriginSelectedPiece = -1;

				selectedPieceSurrondingImage = null;
				xSelectedPieceSurrondingImage = -1;
				ySelectedPieceSurrondingImage = -1;

				selectedPiece = null;
				selectedPieceClone = null;

				repaint();
			}
		}
	}

	private class ControlMoveKeyboardListener extends KeyboardAdapter {

		@Override
		public void onKeyPressed(KeyboardManager.Keys key) {

			if (null != selectedPieceClone) {

				switch (key) {
					
					case UP : {

						mousePosition.setLocation(
							mousePosition.getX(),
							mousePosition.getY() - 1
						);

						repaint();

						break;
					}

					case DOWN : {

						mousePosition.setLocation(
							mousePosition.getX(),
							mousePosition.getY() + 1
						);

						repaint();

						break;
					}

					case LEFT : {

						mousePosition.setLocation(
							mousePosition.getX() - 1,
							mousePosition.getY()
						);

						repaint();

						break;
					}

					case RIGHT : {

						mousePosition.setLocation(
							mousePosition.getX() + 1,
							mousePosition.getY()
						);

						repaint();

						break;
					}

					default : {}
				}
			}
		}
	}

	@Override
	public void update() {

		this.repaint();

		if (this.selectedPieceClone != null) {

			Point middle = this.selectedPieceClone.getPieceMiddle();

			xClickOriginSelectedPiece = middle.x;
			yClickOriginSelectedPiece = middle.y;
		}
	}

	@Override
	public void moveMouseOn(int pieceId) {

		Point leftCorner = this.playBoard.getUpperLeftPieceCornerById(pieceId);
		this.moveMouseOn(leftCorner.x, leftCorner.y);
	}

	@Override
	public void moveMouseOn(int x, int y) {

		int deltaX = x - mousePosition.x;
		int deltaY = y - mousePosition.y; 

		int sizeX = Math.abs(deltaX);
		int sizeY = Math.abs(deltaY);

		int uniqueMoveX = (deltaX == 0 ) ? 0 : (deltaX / sizeX);
		int uniqueMoveY = (deltaY == 0 ) ? 0 : (deltaY / sizeY);

		for (int i = 0; i < Math.max(sizeX, sizeY); i++) {

			int xPlus = (i > sizeX) ? 0 : uniqueMoveX;
			int yPlus = (i > sizeY) ? 0 : uniqueMoveY;

			this.mousePosition.setLocation(
				mousePosition.x + xPlus,
				mousePosition.y + yPlus
			);

			repaint();
			
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean clickOnPiece(int pieceId) {

		Point p = playBoard.getPieceById(pieceId).getPieceMiddle();
		Point upperLeftPieceCorner = playBoard.getUpperLeftPieceCornerById(pieceId);
		return clickOnPiece(upperLeftPieceCorner.x + p.x, upperLeftPieceCorner.y + p.y);
	}

	@Override
	public boolean clickOnPiece(int x, int y) {

		int pieceId = playBoard.getPieceIdAt(x, y);

		if (pieceId != 0) {

			selectedPiece = playBoard.getPieceById(pieceId);
			selectedPieceClone = Piece.clone(selectedPiece);

			selectedPieceId = pieceId;

			selectedPieceSurrondingImage = PieceRenderUtils.createSurrondingPieceImage(selectedPieceClone.getPiece(),
					Color.CYAN);

			Point upperLeftPieceCorner = playBoard.getUpperLeftPieceCornerById(pieceId);
			xSelectedPieceSurrondingImage = upperLeftPieceCorner.x;
			ySelectedPieceSurrondingImage = upperLeftPieceCorner.y;

			selectedPieceId = pieceId;

			update();
		}

		return pieceId != 0;
	}
	

	@Override
	public boolean placePiece(int x, int y) {

		return placePieceWithoutTranslate(x - xClickOriginSelectedPiece, y - yClickOriginSelectedPiece);
	}
	
	@Override
	public boolean placePieceWithoutTranslate(int x, int y) {

		if (selectedPieceClone == null) {
			return false;
		}

		Point upperLeftPieceCorner = playBoard.getUpperLeftPieceCornerById(selectedPieceId);
		Piece tempPiece = playBoard.getPieceById(selectedPieceId);
		playBoard.removePieceFromBoardWithoutRegistration(selectedPiece);

		if (playBoard.canBePlaced(x, y, selectedPieceClone)) {

			playBoard.placePieceAsId(x, y, selectedPieceClone, selectedPieceId);

			clearSelectedPiece();

			return true;
		} else {

			playBoard.placePieceAsId(upperLeftPieceCorner.x, upperLeftPieceCorner.y, tempPiece, selectedPieceId);
			return false;
		}
	}
	
	@Override
	public void rotateLeftPiece() {

		if (this.selectedPieceClone != null) {
			this.selectedPieceClone.rotateLeft();
		}
	}
	
	@Override
	public void reversePiece() {

		if (this.selectedPieceClone != null) {
			this.selectedPieceClone.reverse();
		}
	}

	@Override
	public void clearSelectedPiece() {
		selectedPieceId = -1;
		xClickOriginSelectedPiece = -1;
		yClickOriginSelectedPiece = -1;

		selectedPieceSurrondingImage = null;
		xSelectedPieceSurrondingImage = -1;
		ySelectedPieceSurrondingImage = -1;

		selectedPiece = null;
		selectedPieceClone = null;
	}

	@Override
	public Piece getSelectedPiece() {
		
		return this.selectedPieceClone;
	}
}
