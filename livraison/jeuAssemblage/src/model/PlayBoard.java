package model;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

public class PlayBoard extends AbstractListenableHM implements Listener, Comparable<PlayBoard> {

	public static final int EMPTY = 0;

	private int[][] board;

	private final Map<Integer, Piece> piecesMap;

	private final int sizeX;
	private final int sizeY;

	private final long seed;

	public PlayBoard(long seed, int sizeX, int sizeY) {

		this.board = new int[sizeY][sizeX];

		this.sizeX = sizeX;
		this.sizeY = sizeY;

		this.seed = seed;

		this.piecesMap = new HashMap<Integer, Piece>();

		for (int i = 0; i < this.board.length; i++) {

			for (int j = 0; j < this.board[i].length; j++) {

				this.board[i][j] = EMPTY;
			}
		}
	}

	boolean randomlyPlacePiece(Piece piece, long seedPlacement) {

		Random randomRotation = new Random(seedPlacement + piecesMap.size() + 1);
		final int MAX_ROTATIONS = 4;
		int nbRotations = randomRotation.nextInt(MAX_ROTATIONS);
		for (int i = 0; i < nbRotations; i++) {

			piece.rotateLeft();
		}

		final int MAX_FLIPS = 2;
		int nbReverse = randomRotation.nextInt(MAX_FLIPS);
		for (int i = 0; i < nbReverse; i++) {

			piece.reverse();
		}

		final int MAX_ITERATIONS = 100;
		int iteration = 0;
		Random randomPlacement = new Random(seedPlacement + piecesMap.size() + 1);
		do {

			int x = randomPlacement.nextInt(this.board[0].length - piece.getWidth() + 1);
			int y = randomPlacement.nextInt(this.board.length - piece.getHeight() + 1);

			if (placePiece(x, y, piece)) {

				return true;
			}

			iteration++;

		} while (iteration < MAX_ITERATIONS);

		return false;
	}

	boolean randomlyPlacePiece(Piece piece) {

		return randomlyPlacePiece(piece, this.seed);
	}

	private boolean placePiece(int x, int y, Piece piece) {

		int pieceId = piecesMap.size() + 1;

		if (placePieceAsId(x, y, piece, pieceId)) {

			registerPiece(pieceId, piece);

			return true;
		}

		return false;
	}

	public boolean placePieceAsId(int x, int y, Piece piece, int pieceId) {

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

		removePieceFromBoardWithoutRegistration(piece);

		this.unregisterPiece(piece);
	}

	public boolean movePiece(int x, int y, Piece piece) {

		if (canBePlaced(x, y, piece)) {

			int pieceId = this.getPieceId(piece);

			this.removePieceFromBoardWithoutRegistration(piece);
			this.placePieceAsId(x, y, piece, pieceId);

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

		int[][] rollback = new int[this.board.length][this.board[0].length];
		for (int i = 0; i < this.board.length; i++)
			rollback[i] = this.board[i].clone();

		if (canBeSwapped(pointP2.x, pointP2.y, p1, id2) && canBeSwapped(pointP1.x, pointP1.y, p2, id1)) {

			removePieceFromBoardWithoutRegistration(p1);
			removePieceFromBoardWithoutRegistration(p2);

			if (placePieceAsId(pointP1.x, pointP1.y, p2, id2) && placePieceAsId(pointP2.x, pointP2.y, p1, id1)) {
				return true;
			}
		}

		this.board = rollback;
		piecesMap.put(id1, p1);
		piecesMap.put(id2, p2);

		return false;
	}

	public void removePieceFromBoardWithoutRegistration(Piece piece) {

		int pieceId = getPieceId(piece);

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
		int pieceId;
		try {
			pieceId = this.getPieceId(piece);
		} catch (Exception e) {
			pieceId = EMPTY;
		}

		boolean canBePlaced = true;
		for (int i = 0; i < piece.getHeight() && canBePlaced; i++) {

			for (int j = 0; j < piece.getWidth() && canBePlaced; j++) {

				if (pieceMatrix[i][j] == true) {
					canBePlaced = (y + i < this.board.length &&
							x + j < this.board[0].length &&
							(this.board[y + i][x + j] == EMPTY ||
									this.board[y + i][x + j] == ignoreId ||
									this.board[y + i][x + j] == pieceId));
				}
			}
		}

		return canBePlaced;
	}

	private void registerPiece(int pieceId, Piece piece) {

		this.piecesMap.put(pieceId, piece);

		piece.addListener(this);
	}

	private void unregisterPiece(Piece piece) {

		int pieceId = this.getPieceId(piece);

		this.piecesMap.remove(pieceId);
	}

	@Override
	public void update() {
		this.fireAllEvents();
	}

	@Override
	public boolean equals(Object p) {
		if (!(p instanceof PlayBoard) || p == null) {
			return false;
		}

		PlayBoard pBoard = (PlayBoard) p;

		if (this.seed != pBoard.seed)
			return false;
		if (this.sizeX != pBoard.sizeX)
			return false;
		if (this.sizeY != pBoard.sizeY)
			return false;
		if (this.piecesMap.size() != pBoard.piecesMap.size())
			return false;

		for (int i = 0; i < this.board.length; i++) {
			if (!Arrays.deepEquals(this.board, pBoard.board))
				return false;
		}

		return true;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < this.board.length; i++) {

			for (int j = 0; j < this.board[i].length; j++) {

				sb.append((this.board[i][j] == EMPTY) ? " " : this.board[i][j]);
				sb.append(" ");
			}

			sb.append("\n");
		}

		return sb.toString();
	}

	// -----------------
	// GET METHODS
	// -----------------

	private int getPieceId(Piece piece) {

		return this.piecesMap.entrySet().stream()
				.filter(entry -> entry.getValue().equals(piece))
				.map(Map.Entry::getKey)
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Piece is not on board"));
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

	public int getArea() {
		return ((getUpperPieceX() - getLowerPieceX()) + 1) * ((getUpperPieceY() - getLowerPieceY()) + 1);
	}

	public Map<Integer, Piece> getPieces() {
		return new HashMap<>(piecesMap);
	}

	public int getPiecesCount() {
		return this.piecesMap.size();
	}

	public Point getLowerRightPieceCorner() {
		return new Point(getUpperPieceX(), getUpperPieceY());
	}

	public Point getUpperLeftPieceCorner() {
		return new Point(getLowerPieceX(), getLowerPieceY());
	}

	public Point getUpperLeftPieceCornerById(int pieceId) {
		return new Point(getLowerPieceXById(pieceId), getLowerPieceYById(pieceId));
	}

	public int getPieceIdAt(int x, int y) {
		return this.board[y][x];
	}

	public Piece getPieceById(int pieceId) {
		return this.piecesMap.get(pieceId);
	}

	public int getBoardWidth() {
		return this.board[0].length;
	}

	public int getBoardHeight() {
		return this.board.length;
	}

	public int[][] getBoardArray() {
		return this.board.clone();
	}

	public int getWidth() {
		return this.sizeX;
	}

	public int getHeight() {
		return this.sizeY;
	}

	public long getSeed() {
		return this.seed;
	}

	// Java enjoyer : 
	/*
		⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣤⠶⠶⠶⠶⠶⠶⠶⠶⠶⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠶⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⠲⢦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡴⠟⠁⠀⢀⣠⣤⡄⠀⠀⠀⢠⡤⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠲⢦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⡴⠋⠀⠀⠀⠀⣰⣸⠁⣸⡇⠀⠀⣠⡟⠀⣹⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠲⠤⠤⠤⠤⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⠖⠛⠉⢁⠀⠀⠀⠀⠀⣾⣿⣤⣿⢳⠀⢀⣿⣷⣴⣿⠰⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠲⢦⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⠛⠀⠀⠀⢀⡇⠀⠀⠀⠀⠀⣿⣿⣿⡿⠈⠀⠀⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣇⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡟⠁⠀⠀⠀⢀⡞⠀⠀⢀⣀⣀⠀⡿⣿⣿⡇⠀⠀⠰⢿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠓⢦⡀⠀⠀⠀⠘⣆⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⡾⠀⠀⠀⣀⣀⠀⠀⠀⢸⣿⣥⣩⡿⠃⠹⠯⡀⣀⣀⠀⠨⠽⠋⠀⠀⠀⠀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⢲⣮⣍⣉⣓⣀⣁⠀⠀⠀⠀⠀⢦⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⣼⡃⠀⠠⠤⢵⡏⢸⠆⠀⠈⠉⠉⠉⠀⠀⠀⢻⢿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡀⠀⠉⠉⠉⠉⠁⠀⠀⠀⠀⡿⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⣼⠃⠃⠀⠀⠀⠀⣇⡎⡄⠀⠀⠀⠀⠀⠀⠀⠀⠈⠈⠉⠙⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⢀⣤⠾⢫⢀⠀⠀⠀⠀⠀⣿⣰⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣹⣷⣿⡂⠀⠀⠀⠀⣰⠀⠀⠀⠈⣷⡄⠀⠀⠀
	⠀⠀⠀⢠⡿⠁⠀⠈⣿⢀⠀⢀⣤⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⡟⠈⠻⣿⣦⣄⠀⠀⠀⠀⠀⠀⠈⣷⠀⠀⠀
	⠀⠀⠀⣾⡇⠀⠀⠀⠙⠻⣷⣾⣿⡿⠟⠉⢿⡃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⡿⠁⠀⠀⠈⠙⣿⠃⣤⠀⠀⠀⠀⠀⠀⡿⠀⠀
	⠀⠀⢰⡿⠀⠀⠀⢰⣶⠚⠋⣹⣿⠃⠀⠀⠈⢿⣠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡿⠁⠀⠀⠀⠀⠘⢿⣦⣽⡄⠀⠀⠀⠀⠀⣼⠀⠀
	⠀⠀⣼⠁⠀⠀⢀⡿⣹⢄⣿⡿⠃⠀⠀⠀⠀⠈⠻⣷⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣤⣶⣾⠟⠁⠀⠀⠀⠀⠀⠀⠈⠛⢿⣧⠀⠀⠀⠀⡟⠀⠀⠀
	⠀⢸⠇⠀⠀⢠⣿⣿⠿⠟⠉⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⠷⣦⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠶⠛⠉⠁⠀⠐⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣧⠀⠀⠀⣼⠀⠀⠀⠀
	⠀⣾⠀⠀⠀⠈⢿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⠀⠀⠉⠻⠷⣤⣄⣀⡀⠀⠀⢀⣤⣀⣤⡾⠟⠁⠀⠀⠀⠀⠀⠀⢿⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠞⠀⠀⠀⠀⣷⠀⠀⠀⠀
	⢰⠇⡄⠀⠀⢁⣈⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⢿⠀⠀⠀⠀⠀⠈⠙⠛⠿⣿⣿⣿⣿⡟⠁⠀⠀⣦⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⣸⣷⣄⣀⡀⠀⠀⠀⣻⡄⠀⠀
	⢸⣿⡇⢠⡿⠛⣧⣽⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡿⠸⣇⠀⠀⠀⠀⠀⠀⠀⠀⣾⡟⠀⠈⣷⡆⠀⠀⠸⣦⠀⠀⠀⠀⢠⣼⠀⠀⠀⠀⠀⠀⠀⠀⣏⣟⠉⣹⣿⡆⠀⠀⠀⠸⠀⠀
	⠈⣿⣥⡟⠀⠀⠿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣷⡀⣿⣤⠀⠀⠀⠀⠀⠀⣰⣾⠃⠀⠀⠈⣷⣦⠀⠀⠘⣷⠀⠀⠀⣼⡇⠀⠀⠀⠀⠀⠀⠀⠀⠛⢿⣾⠟⠋⣧⢀⣤⣴⠀⠀⠀
	⠀⠸⣷⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣷⣿⣿⣿⣀⠀⠀⠀⣼⣿⠃⠀⠀⠀⠀⠈⢿⣆⠀⠀⣹⣄⣶⣾⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣷⣼⡾⠏⠀⠀⠀
	⠀⠀⠈⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣮⣿⣝⣦⣄⣠⣿⠇⠀⠀⠀⠀⠀⠀⠘⢿⣿⣿⠃⠉⢛⠛⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠋⠉⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⡎⡌⠙⢿⣿⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠘⢿⣧⣄⡴⠾⢟⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣟⣻⢾⣦⡽⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣶⡾⠟⢸⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⣿⢽⡿⢛⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⣿⠛⠒⠀⠀⣻⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⠸⡆⠀⠀⠀⠀⠀⠀⠀⢠⣿⠀⠸⣇⠀⠀⢀⣼⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣿⠀⠀⠀⠀⠀⣶⣿⠀⠀⠀⠀⠀⠀⠀⠈⢿⣠⠀⢿⡀⠀⠀⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⣴⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣦⣨⠃⠀⢰⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⠀⠀⠀⣴⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⡭⠄⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣤⣄⣀⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⣿⢿⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⣿⣿⣾⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣿⣿⡀⡹⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⣴⣟⣿⣿⣿⣻⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⣟⣿⣽⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ 
	*/

	// -----------------
	// STATIC METHODS
	// -----------------

	@Override
	public int compareTo(PlayBoard o) {
		return this.getArea() - o.getArea();
	}

	// -----------------
	// STATIC METHODS
	// -----------------

	public static int getSizeXBySeedAndDifficulty(long seed, EDifficulty difficulty) {

		return new Random(seed).nextInt(difficulty.getMaxSizeX() - difficulty.getMinSizeX() + 1)
				+ difficulty.getMinSizeX();
	}

	public static int getSizeYBySeedAndDifficulty(long seed, EDifficulty difficulty) {

		return new Random(seed + 1).nextInt(difficulty.getMaxSizeY() - difficulty.getMinSizeY() + 1)
				+ difficulty.getMinSizeY();
	}

	public static int getPiecesCountBySeedAndDifficulty(long seed, EDifficulty difficulty) {

		return new Random(seed + 2).nextInt(difficulty.getMaxNbPieces() - difficulty.getMinNbPieces() + 1)
				+ difficulty.getMinNbPieces();
	}

	public static PlayBoard constructPlayBoardWithPlacementSeed(long seed, long seedPlacement, int sizeX, int sizeY,
			int nbPieces) {

		PlayBoard playBoard = constructEmptyPlayBoard(seed, sizeX, sizeY);
		List<PieceFactory> pieceFactorys = getPossiblePieceFactorys();

		Random random = new Random(seed + 3);

		for (int i = 0; i < nbPieces; i++) {

			PieceFactory pieceFactory = pieceFactorys.get(random.nextInt(pieceFactorys.size()));
			Piece piece = pieceFactory.createPiece(0);

			playBoard.randomlyPlacePiece(piece, seedPlacement);
		}

		return playBoard;
	}

	public static PlayBoard constructPlayBoardWithPlacementSeed(PlayBoard model, long seedPlacement) {

		return constructPlayBoardWithPlacementSeed(model.seed, seedPlacement, model.sizeX, model.sizeY,
				model.getPiecesCount());
	}

	public static PlayBoard constructCopyPlayBoard(PlayBoard parent) {

		PlayBoard copy = constructEmptyPlayBoardCopy(parent);

		for (int i = 1; i <= parent.piecesMap.keySet().size(); i++) {
			Piece p = parent.piecesMap.get(i);
			Point point = parent.getUpperLeftPieceCornerById(i);
			copy.placePiece(point.x, point.y, p);
		}

		return copy;
	}

	public static PlayBoard constructPlayBoard(long seed, int sizeX, int sizeY, int nbPieces) {

		return constructPlayBoardWithPlacementSeed(seed, seed, sizeX, sizeY, nbPieces);
	}

	public static PlayBoard constructPlayBoard(PlayBoard p) {

		return constructPlayBoard(p.seed, p.sizeX, p.sizeY, p.getPiecesCount());
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
}
