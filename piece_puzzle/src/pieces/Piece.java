package pieces;

public abstract class Piece {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    
    private int x;
    private int y;
    private int rotate;
    private int size;

    //CONSTRUCTORS
    public Piece( int x, int y, int size, int rotate) {
        this.x = x;
        this.y = y;
        this.rotate = rotate;
        this.size = size;
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
            this.rotate = (this.rotate - 90) % 180; 
        }

        if (sense == Piece.RIGHT) {
            this.rotate = (this.rotate + 90) % 180; 
        }
    }

    //SETTERS
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    public void setSize(int size) { this.size = size;}

    //GETTERS
    public int getX() { return this.x; }
    public int getY() { return this.y; }

    public int getSize  () { return this.size; }
    public int getRotate() { return this.rotate; }

    //ABSTRACTS
    public abstract int[][] getBounds();

    public static void main(String[] args) {
        System.out.println("bozo");
    }
}
