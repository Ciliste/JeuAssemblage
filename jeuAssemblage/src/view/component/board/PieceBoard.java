package view.component.board;

import main.Controller;
import pieces.Piece;
import view.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PieceBoard extends JPanel {

	private final Controller controller = Controller.getInstance();

	private final JButton btnTurnLeft  = new JButton("<-");
	private final JButton btnTurnRight = new JButton("->");

	private final JButton btnCancel = new JButton("X");

	public PieceBoard() {

		// controller.getPlayBoard().

		this.setLayout(null);

		this.add(btnTurnLeft);
		this.add(btnTurnRight);

		this.add(btnCancel);

		btnTurnLeft.addActionListener(e -> {

			controller.getSelectedPiece().rotate(Piece.LEFT);
			repaint();
		});

		btnTurnRight.addActionListener(e -> {

			controller.getSelectedPiece().rotate(Piece.RIGHT);
			repaint();
		});

		btnCancel.addActionListener(e -> {

			controller.setPieceSelected(null);
			getParent().repaint();
		});
	}

	@Override
	public void doLayout() {

		final int MARGIN_TOP = SwingUtils.getHeightTimesPourcent(this, 0.5f);

		final int BTN_WIDTH  = SwingUtils.getWidthTimesPourcent(this, 0.33f);
		final int BTN_HEIGHT = SwingUtils.getHeightTimesPourcent(this, 0.2f);

		final int BTN_MARGIN = SwingUtils.getWidthTimesPourcent(this, 0.11f);

		btnTurnLeft.setBounds(
			BTN_MARGIN,
			MARGIN_TOP,
			BTN_WIDTH,
			BTN_HEIGHT
		);

		btnTurnRight.setBounds(
			BTN_MARGIN + BTN_WIDTH + BTN_MARGIN,
			MARGIN_TOP,
			BTN_WIDTH,
			BTN_HEIGHT
		);

		final int BTN_CANCEL_WIDTH  = SwingUtils.getWidthTimesPourcent(this, 0.5f);
		final int BTN_CANCEL_HEIGHT = BTN_HEIGHT;

		final int BTN_CANCEL_GAP = SwingUtils.getWidthTimesPourcent(this, 0.05f);

		final int BTN_CANCEL_MARGIN = SwingUtils.getWidthTimesPourcent(this, 0.25f);

		btnCancel.setBounds(
			BTN_CANCEL_MARGIN,
			MARGIN_TOP + BTN_HEIGHT + BTN_CANCEL_GAP,
			BTN_CANCEL_WIDTH,
			BTN_CANCEL_HEIGHT
		);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

        SwingUtils.drawDebugBounds(this, g);

		if (this.controller.getSelectedPiece() == null) {

			btnTurnLeft.setVisible(false);
			btnTurnRight.setVisible(false);
			btnCancel.setVisible(false);
		}
		else {

			btnTurnLeft.setVisible(true);
			btnTurnRight.setVisible(true);
			btnCancel.setVisible(true);
		}

		final Piece p = this.controller.getSelectedPiece();
		if (p == null) return;

		final int PIECE_SIZE_X = p.getWidth();
		final int PIECE_SIZE_Y = p.getHeight();

		final int PIECE_MARGIN = SwingUtils.getWidthTimesPourcent(this, 0.05f);
		
		final int PIECE_HEIGHT = SwingUtils.getHeightTimesPourcent(this, 0.4f);

		final int PIECE_WIDTH;

		if (PIECE_SIZE_X > PIECE_SIZE_Y) {
			
			PIECE_WIDTH = PIECE_HEIGHT * PIECE_SIZE_X / PIECE_SIZE_Y;
		} 
		else {

			PIECE_WIDTH = PIECE_HEIGHT;
		}

		final int PIECE_MARGIN_LEFT = (getWidth() - PIECE_WIDTH) / 2;

		Image img = controller.getImageById(p.getInstanceId());
		img = img.getScaledInstance(PIECE_WIDTH, PIECE_HEIGHT, Image.SCALE_SMOOTH);

		AffineTransform at = new AffineTransform();
		at.translate(PIECE_MARGIN_LEFT, PIECE_MARGIN);
		// switch (p.getRotate()) {
			
		// 	case 1:
		// 		at.rotate(Math.toRadians(90), img.getWidth(null) / 2, img.getHeight(null) / 2);
		// 		break;
		// 	case 2:
		// 		at.rotate(Math.toRadians(180), img.getWidth(null) / 2, img.getHeight(null) / 2);
		// 		break;
		// 	case 3:
		// 		at.rotate(Math.toRadians(270), img.getWidth(null) / 2, img.getHeight(null) / 2);
		// 		break;
		// }

		if (g instanceof Graphics2D) {

			Graphics2D g2d = (Graphics2D) g;
			int[][] bounds = p.getBounds();

			final int CELL_SIZE;

			if (bounds.length > bounds[0].length) {

				CELL_SIZE = PIECE_HEIGHT / bounds.length;
			}
			else {

				CELL_SIZE = PIECE_WIDTH / bounds[0].length;
			}

			for (int i = 0; i < bounds.length; i++) {

				for (int j = 0; j < bounds[i].length; j++) {

					if (bounds[i][j] != 0) {

						g2d.drawImage(
							img,
							i * CELL_SIZE,
							j * CELL_SIZE,
							CELL_SIZE,
							CELL_SIZE,
							null
						);
					}
				}
			}
		}
	}
}
