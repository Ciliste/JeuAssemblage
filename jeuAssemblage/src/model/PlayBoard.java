package model;

import java.awt.Image;
import java.awt.Point;
import java.util.*;

import factory.HFactory;
import factory.IFactory;
import factory.LFactory;
import factory.OFactory;
import factory.PieceFactory;
import factory.TFactory;
import factory.ZFactory;
import observer.AbstractListenableHM;
import observer.interfaces.Listener;
import piece.Piece;
import utils.EDifficulty;
import utils.ETypeListen;
import view.utils.PieceRenderUtils;

public class PlayBoard extends AbstractListenableHM implements Listener {

	private static final int EMPTY = 0;

	private final int[][] board;

	private final Map<Integer, Piece> piecesMap;
	private final Map<Integer, Image> piecesImagesMap;

	private final int sizeX;
	private final int sizeY;

	private final long seed;

	public PlayBoard(long seed, int sizeX, int sizeY) {

		this.board = new int[sizeY][sizeX];

		this.sizeX = sizeX;
		this.sizeY = sizeY;

		this.seed = seed;

		this.piecesMap = new HashMap<>();
		this.piecesImagesMap = new HashMap<>();

		for (int i = 0; i < this.board.length; i++) {

			for (int j = 0; j < this.board[i].length; j++) {

				this.board[i][j] = EMPTY;
			}
		}
	}

	public int[][] getBoardArray() {

		return this.board.clone();
	}

	void randomlyPlacePiece(Piece piece) {

		Random random = new Random(this.seed + piecesMap.size() + 1);
	
		// Define constants for readability
		final int MAX_ROTATIONS = 4;
	
		int nbRotations = random.nextInt(MAX_ROTATIONS);
		
		// Rotate the piece as many times as specified by nbRotations
		for (int i = 0; i < nbRotations; i++) {

			piece.rotateLeft(); 
		}

		// TODO : Place the piece randomly on the board
	
		for (int i = 0; i < board.length; i++) {
	
			for (int j = 0; j < board[i].length; j++) {

				if (placePiece(j, i, piece)) {
	
					return;
				}
			}
		}
	}

	public int getLowerPieceXById(int pieceId) {

		for (int x = 0; x < this.board[0].length; x++) {

			for (int y = this.board.length - 1; y >= 0; y--) {

				if (this.board[y][x] == pieceId) {

					return x;
				}
			}
		}

		throw new IllegalStateException("Piece not found");
	}

	public int getLowerPieceYById(int pieceId) {

		for (int y = 0; y < this.board.length; y++) {

			for (int x = 0; x < this.board[0].length; x++) {

				if (this.board[y][x] == pieceId) {

					return y;
				}
			}
		}

		throw new IllegalStateException("Piece not found");
	}

	public Point getUpperLeftPieceCornerById(int pieceId) {

		return new Point(getLowerPieceXById(pieceId), getLowerPieceYById(pieceId));
	}
	
	public int getWidth() {

		return this.sizeX;
	}

	public int getHeight() {

		return this.sizeY;
	}

	public int getPieceIdAt(int x, int y) {

		return this.board[y][x];
	}

	private boolean placePiece(int x, int y, Piece piece) {

		if (this.canBePlaced(x, y, piece)) {

			int pieceId = piecesMap.size() + 1;
			boolean[][] pieceMatrix = piece.getPiece();

			for (int i = 0; i < piece.getHeight(); i++) {

				for (int j = 0; j < piece.getWidth(); j++) {

					if (pieceMatrix[i][j]) {

						this.board[y + i][x + j] = pieceId;
					}
				}
			}

			this.registerPiece(pieceId, piece);

			return true;
		}
		else {

			return false;
		}
	}

	void removePieceFromBoard(Piece piece) {

		int pieceId = this.getPieceId(piece);

		for (int i = 0; i < this.board.length; i++) {

			for (int j = 0; j < this.board[i].length; j++) {

				if (this.board[i][j] == pieceId) {

					this.board[i][j] = EMPTY;
				}
			}
		}

		this.unregisterPiece(pieceId);
	}

	boolean movePiece(Piece piece, int x, int y) {

		if (canBePlaced(x, y, piece)) {

			int pieceId = this.getPieceId(piece);

			Image cell = this.piecesImagesMap.get(pieceId);

			this.removePieceFromBoard(piece);
			this.placePiece(x, y, piece);

			this.piecesImagesMap.put(pieceId, cell);

			this.fireAllEvents();

			return true;
		}
		else {

			return false;
		}
	}

	private int getPieceId(Piece piece) {

		return this.piecesMap.entrySet().stream()
			.filter(entry -> entry.getValue().equals(piece))
			.map(Map.Entry::getKey)
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("Piece is not on board"));
	}

	public Piece getPieceCloneById(int pieceId) {

		Piece p = Piece.clone(this.piecesMap.get(pieceId));
		return p;
	}

	private boolean canBePlaced(int x, int y, Piece piece) {

		boolean[][] pieceMatrix = piece.getPiece();

		boolean canBePlaced = true;
		for (int i = 0; i < piece.getHeight(); i++) {

			for (int j = 0; j < piece.getWidth(); j++) {

				if (pieceMatrix[i][j] == false) {

					continue;
				}

				if ((y + i < this.board.length && x + j < this.board[0].length && this.board[y + i][x + j] == EMPTY) == false) {

					canBePlaced = false;
					break;
				}
			}

			if (canBePlaced == false) {

				break;
			}
		}

		return canBePlaced;
	}

	private void registerPiece(int pieceId, Piece piece) {

		this.piecesMap.put(pieceId, piece);
		this.piecesImagesMap.put(pieceId, PieceRenderUtils.createCellImage(this.seed + pieceId));
	}

	private void unregisterPiece(int pieceId) {

		this.piecesMap.get(pieceId).removeListener(this);

		this.piecesMap.remove(pieceId);
		this.piecesImagesMap.remove(pieceId);
	}

	public Image getCellImageByPieceId(int pieceId) {

		return this.piecesImagesMap.get(pieceId);
	}

	public int getBoardWidth() {

		return this.board[0].length;
	}

	public int getBoardHeight() {

		return this.board.length;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < this.board.length; i++) {

			for (int j = 0; j < this.board[i].length; j++) {

				sb.append((this.board[i][j] == EMPTY) ? "  " : this.board[i][j] + " ");
				sb.append(" ");
			}

			sb.append("\n");
		}

		return sb.toString();
	}

	@Override
	public void update() {
		this.fireEvents(ETypeListen.PIECEVIEW.typeListen);
	}

	public static int getSizeXBySeedAndDifficulty(long seed, EDifficulty difficulty) {

		return new Random(seed).nextInt(difficulty.getMaxSizeX() - difficulty.getMinSizeX() + 1) + difficulty.getMinSizeX();
	}

	public static int getSizeYBySeedAndDifficulty(long seed, EDifficulty difficulty) {

		return new Random(seed + 1).nextInt(difficulty.getMaxSizeY() - difficulty.getMinSizeY() + 1) + difficulty.getMinSizeY();
	}

	public static int getPiecesCountBySeedAndDifficulty(long seed, EDifficulty difficulty) {

		return new Random(seed + 2).nextInt(difficulty.getMaxNbPieces() - difficulty.getMinNbPieces() + 1) + difficulty.getMinNbPieces();
	}

	public static PlayBoard constructPlayBoard(long seed, int sizeX, int sizeY, int nbPieces, EDifficulty difficulty) {

		PlayBoard playBoard = constructEmptyPlayBoard(seed, sizeX, sizeY);
		List<PieceFactory> pieceFactorys = getPossiblePieceFactorys();

		Random random = new Random(seed + 3);

		for (int i = 0; i < nbPieces; i++) {

			PieceFactory pieceFactory = pieceFactorys.get(random.nextInt(pieceFactorys.size()));
			Piece piece = pieceFactory.createPiece(0);
			
			piece.addListener(playBoard);

			playBoard.randomlyPlacePiece(piece);
		}

		return playBoard;
	}

	public static PlayBoard constructEmptyPlayBoard(long seed, int sizeX, int sizeY) {

		return new PlayBoard(seed, sizeX, sizeY);
	}

	private static List<PieceFactory> getPossiblePieceFactorys() {

		List<PieceFactory> pieceFactorys = new ArrayList<>();

		pieceFactorys.add(new HFactory());
		pieceFactorys.add(new IFactory());
		pieceFactorys.add(new LFactory());
		pieceFactorys.add(new OFactory());
		pieceFactorys.add(new TFactory());
		pieceFactorys.add(new ZFactory());

		return pieceFactorys;
	}
}
