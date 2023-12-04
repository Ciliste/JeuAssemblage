package bot.utils;

public class Move {
    
    public int pieceId;
    public int numberOfLeftRotation;
    public int numberOfReverse;
    public int x;
    public int y;

    public Move(int pieceId, int x, int y, int numberOfLeftRotation, int numberOfReverse) {
        this.pieceId = pieceId;
        this.x = x;
        this.y = y;
        this.numberOfReverse = numberOfReverse;
        this.numberOfLeftRotation = numberOfLeftRotation;
    }

    @Override
    public String toString() {
        return "Piece : " + this.pieceId + " x : " + this.x + " y : " + this.y + " numberOfLeftRotation : "
                + this.numberOfLeftRotation + " numberOfReverse : " + this.numberOfReverse + "\n";
    }
}
