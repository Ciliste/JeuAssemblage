package pieces;

import observer.AbstractListenable;

public abstract class Piece implements Cloneable {
    
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private static int id = 1;

    private int instanceId;
    private int x;
    private int y;
    protected int rotate;
    protected int size;

	private int[][] bounds = getInitialBounds();

    //CONSTRUCTORS
    private Piece(int x, int y, int size, int rotate, int instanceId) {

        this.x = x;
        this.y = y;
        this.rotate = rotate;
        this.size = size;
        this.instanceId = instanceId;
    }

    public Piece(int x, int y, int size, int rotate) {
        
        this(x, y, size, rotate, Piece.id++);
    }

    public Piece(int x, int y, int size) {
        
        this(x,y, size, 0);
    }

    public Piece(int x, int y) {

        this(x,y, 3, 0);
    }


    //METHODS
    public void rotate(int sense) {

		if (sense == Piece.RIGHT) {

			int rows = bounds.length;
			int cols = bounds[0].length;

			int[][] rotatedMatrix = new int[cols][rows];

			for (int i = 0; i < rows; i++) {

				for (int j = 0; j < cols; j++) {

					rotatedMatrix[j][rows - 1 - i] = bounds[i][j];
				}
			}

			bounds = rotatedMatrix;
        }

        if (sense == Piece.LEFT) {

			rotate(Piece.RIGHT);
			rotate(Piece.RIGHT);
			rotate(Piece.RIGHT);
        }
    }

    //SETTERS
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    public void setSize(int size) { this.size = size;}

    //GETTERS
    public int getX() { return this.x; }
    public int getY() { return this.y; }

    public int getInstanceId () { return this.instanceId; }
    public int getSize       () { return this.size; }
    public int getRotate     () { return this.rotate; }
    
    public int     getWidth () { return this.bounds[0].length; }
    public int     getHeight() { return this.bounds.length; }
    public int[][] getBounds() { return this.bounds; }


    //ABSTRACTS
    protected abstract int[][] getInitialBounds();

	public void reverse() {

		int rows = bounds.length;
		int cols = bounds[0].length;

		int[][] reversedMatrix = new int[rows][cols];

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				reversedMatrix[i][j] = bounds[i][cols - 1 - j];
			}
		}

		bounds = reversedMatrix;
	}

    public void destroy() { Piece.id--; }

    @Override
    public Object clone() {

        try {
            Piece p = (Piece) super.clone();
            return p;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        
        return null;
      }

	@Override
	public boolean equals(Object obj) {

		if (obj == null) return false;
		if (!(obj instanceof Piece)) return false;
		
		Piece p = (Piece) obj;
		
		return (this.instanceId == p.instanceId);
	}
    
    // Main
    public static void main(String[] args) {
        System.out.println("bozo");
    }
}
