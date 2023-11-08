package model;

import java.awt.Image;
import java.awt.Point;
import java.awt.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import factory.HFactory;
import factory.IFactory;
import factory.LFactory;
import factory.OFactory;
import factory.PieceFactory;
import factory.TFactory;
import factory.ZFactory;
import model.listener.IPlayBoardListenable;
import model.listener.IPlayBoardListener;
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

		System.out.println("Randomly placing piece: " + piece);

		Random random = new Random(this.seed + piecesMap.size() + 1);
	
		final int MAX_ROTATIONS = 4;
		int nbRotations = random.nextInt(MAX_ROTATIONS);
		for (int i = 0; i < nbRotations; i++) {

			piece.rotateLeft(); 
		}

		final int MAX_FLIPS = 2;
		int nbReverse = random.nextInt(MAX_FLIPS);
		for (int i = 0; i < nbReverse; i++) {

			piece.reverse();
		}
	
		final int MAX_ITERATIONS = 100;
		int iteration = 0;

		do {

			int x = random.nextInt(this.board[0].length - piece.getWidth() + 1);
			int y = random.nextInt(this.board.length - piece.getHeight() + 1);

			System.out.println("Trying to place piece at: " + x + ", " + y);

			if (placePiece(x, y, piece)) {

				return;
			}

			iteration++;

		} while (iteration < MAX_ITERATIONS);

		// for (int i = 0; i < board.length; i++) {
	
		// 	for (int j = 0; j < board[i].length; j++) {
	
		// 		System.out.println("Trying to place piece at: " + j + ", " + i);

		// 		if (placePiece(j, i, piece)) {
	
		// 			return;
		// 		}
		// 	}
		// }
	}

	public int getLowerPieceX() {

		for (int x = 0; x < this.board[0].length; x++) {

			for (int y = this.board.length - 1; y >= 0; y--) {

				if (this.board[y][x] != EMPTY) {

					return x;
				}
			}
		}

		throw new IllegalStateException("No piece found");
	}

	public int getLowerPieceY() {

		for (int y = 0; y < this.board.length; y++) {

			for (int x = 0; x < this.board[0].length; x++) {

				if (this.board[y][x] != EMPTY) {

					return y;
				}
			}
		}

		throw new IllegalStateException("No piece found");
	}

	public Point getUpperLeftPieceCorner() {

		System.out.println("getUpperLeftPieceCorner() " + getLowerPieceX() + ", " + getLowerPieceY());

		return new Point(getLowerPieceX(), getLowerPieceY());
	}

	public int getUpperPieceX() {

		for (int x = this.board[0].length - 1; x >= 0; x--) {

			for (int y = 0; y < this.board.length; y++) {

				if (this.board[y][x] != EMPTY) {

					return x;
				}
			}
		}

		throw new IllegalStateException("No piece found");
	}

	public int getUpperPieceY() {

		for (int y = this.board.length - 1; y >= 0; y--) {

			for (int x = this.board[0].length - 1; x >= 0; x--) {

				if (this.board[y][x] != EMPTY) {

					return y;
				}
			}
		}

		throw new IllegalStateException("No piece found");
	}

	public Point getLowerRightPieceCorner() {

		return new Point(getUpperPieceX(), getUpperPieceY());
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

	public boolean placePiece(int x, int y, Piece piece) {

		int pieceId = piecesMap.size() + 1;

		if (placePieceWithoutRegistration(x, y, piece, pieceId)) {

			registerPiece(pieceId, piece);

			return true;
		}
		
		return false;
	}

	public boolean placePieceWithoutRegistration(int x, int y, Piece piece, int pieceId) {

		if (canBePlaced(x, y, piece)) {

			boolean[][] pieceMatrix = piece.getPiece();

			for (int i = 0; i < piece.getHeight(); i++) {

				for (int j = 0; j < piece.getWidth(); j++) {

					if (pieceMatrix[i][j]) {

						this.board[y + i][x + j] = pieceId;
					}
				}
			}

			piecesMap.put(pieceId, piece);

			this.fireAllEvents();

			return true;
		}

		return false;
	}

	public void removePieceFromBoard(Piece piece) {
		int pieceId = getPieceId(piece);
		
		removePieceFromBoardWithoutRegistration(pieceId);
		
		this.unregisterPiece(getPieceId(piece));
	}

	public boolean movePiece(Piece piece, int x, int y) {

		if (canBePlaced(x, y, piece)) {

			int pieceId = this.getPieceId(piece);

			Image cell = this.piecesImagesMap.get(pieceId);

			this.removePieceFromBoardWithoutRegistration(getPieceId(piece));
			this.placePieceWithoutRegistration(x, y, piece, getPieceId(piece));

			this.piecesImagesMap.put(pieceId, cell);

			this.fireAllEvents();

			return true;
		}
		
		return false;
	}
	
	public boolean swap(Piece p1, Piece p2) {
		int id1 = getPieceId(p1);
		int id2 = getPieceId(p2);

		Point pointP1 = getUpperLeftPieceCornerById(id1);
		Point pointP2 = getUpperLeftPieceCornerById(id2);

		if (canBeSwapped(pointP2.x, pointP2.y, p1, id2) && canBeSwapped(pointP1.x, pointP1.y, p2, id1)) {
			removePieceFromBoardWithoutRegistration(id1);
			placePieceWithoutRegistration(pointP1.x, pointP1.y, p2, id2);
			placePieceWithoutRegistration(pointP2.x, pointP2.y, p1, id1);
			return true;
		}

		return false;
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

	public void removePieceFromBoardWithoutRegistration(int pieceId) {	

		for (int i = 0; i < this.board.length; i++) {

			for (int j = 0; j < this.board[i].length; j++) {

				if (this.board[i][j] == pieceId) {

					this.board[i][j] = EMPTY;
				}
			}
		}

		this.fireAllEvents();
	}

	public boolean canBePlaced(int x, int y, Piece piece) {
		return canBeSwapped(x, y, piece, EMPTY);
	}

	public boolean canBeSwapped(int x, int y, Piece piece, int ignoreId) {

		boolean[][] pieceMatrix = piece.getPiece();

		boolean canBePlaced = true;
		for (int i = 0; i < piece.getHeight() && canBePlaced; i++) {

			for (int j = 0; j < piece.getWidth() && canBePlaced; j++) {

				if (pieceMatrix[i][j] == true) {
					boolean isEmpty = this.board[y + i][x + j] == EMPTY || this.board[y + i][x + j] == ignoreId;
					canBePlaced = !(
						y + i < this.board.length && 
						x + j < this.board[0].length && 
						isEmpty
					);
				}	
			}
		}

		return canBePlaced;
	}

	private void registerPiece(int pieceId, Piece piece) {

		this.piecesMap.put(pieceId, piece);
		this.piecesImagesMap.put(pieceId, PieceRenderUtils.createCellImage(this.seed + pieceId));
	}

	private void unregisterPiece(int pieceId) {

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
		List<Color> colorList = getDefaultColorList();

		Random random = new Random(seed + 3);

		for (int i = 0; i < nbPieces; i++) {

			PieceFactory pieceFactory = pieceFactorys.get(random.nextInt(pieceFactorys.size()));
			Piece piece = pieceFactory.createPiece(0);
			
			Color color = null;
			if (true == colorList.isEmpty()) {

				colorList = getDefaultColorList();
			}

			color = colorList.remove(random.nextInt(colorList.size()));

			playBoard.randomlyPlacePiece(piece);
			playBoard.piecesImagesMap.put(playBoard.getPieceId(piece), PieceRenderUtils.createCellImage(color));
		}

		return playBoard;
	}

	public static PlayBoard constructEmptyPlayBoard(long seed, int sizeX, int sizeY) {

		return new PlayBoard(seed, sizeX, sizeY);
	}

	public static PlayBoard constructEmptyPlayBoardCopy(PlayBoard toClone) {

		return constructEmptyPlayBoard(toClone.seed, toClone.sizeX, toClone.sizeY);
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

	private static List<Color> getDefaultColorList() {

		List<Color> colorList = new ArrayList<>();

		colorList.add(Color.RED);
		colorList.add(Color.BLUE);
		colorList.add(Color.GREEN);
		colorList.add(Color.YELLOW);
		colorList.add(Color.CYAN);
		colorList.add(Color.MAGENTA);

		return colorList;
	}
	
	@Override
	public void update() {
		this.fireEvents(ETypeListen.PIECEVIEW.typeListen);
	}
}
