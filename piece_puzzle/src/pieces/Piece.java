package pieces;

public abstract class Piece implements Cloneable {
    
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private static int id = 0;

    private int instanceId;
    private int x;
    private int y;
    protected int rotate;
    protected int size;

    //CONSTRUCTORS
    private Piece(int x, int y, int size, int rotate, int instanceId) {
        this.x = x;
        this.y = y;
        this.rotate = rotate;
        this.size = size;
        this.instanceId = instanceId;
    }

    public Piece( int x, int y, int size, int rotate) {
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
        if (sense == Piece.LEFT) {
            this.rotate = (this.rotate - 1) % 4;
        }

        if (sense == Piece.RIGHT) {
            this.rotate = (this.rotate + 1) % 4;
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

    //ABSTRACTS
    protected abstract int[][] getInitialBounds();

    //BOUNDS
    private int[][] getRotateBounds () {
        int[][] initBounds = getInitialBounds();
        int[][] retBounds  = new int[initBounds.length][initBounds[0].length];

        if ( this.rotate == 0) return initBounds;

        if ( this.rotate == 2) {
            for (int i = 0; i < initBounds.length; i++) {
                for ( int j = 0 ; j < initBounds[i].length; j++) {
                    retBounds[i][j] = initBounds[initBounds.length - 1 -j][i];
                }
            }
        }

        if ( this.rotate == 1) {
            for (int i = 0; i < initBounds.length; i++) {
                for ( int j = 0 ; j < initBounds[i].length; j++) {
                    retBounds[i][j] = initBounds[j][initBounds.length - 1 -i];
                }
            }
        }

        if ( this.rotate == 3 ) {
            for (int i = 0; i < initBounds.length; i++) {
                for ( int j = 0 ; j < initBounds[i].length; j++) {
                    retBounds[i][j] = initBounds[initBounds.length - 1 -j][initBounds.length - 1 -i];
                }
            }
        }

        return retBounds;
    }

    public int[][] getBounds() {
        return this.getRotateBounds();
    }

    public int getWidth () { return 3; }
    public int getHeight() { return 3; }

    public void destroy() {
        Piece.id--;
    }

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

    // Main
    public static void main(String[] args) {
        System.out.println("bozo");
    }

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) return false;
		if (!(obj instanceof Piece)) return false;
		
		Piece p = (Piece) obj;
		
		return (this.instanceId == p.instanceId);
	}
}
