package model.arrangement;

public class Arrangement {

    protected int sizeX;
    protected int sizeY;
    protected int pieceCount;
    protected long seed;
    
    protected String username;
    protected int score;

    public Arrangement(int sizeX, int sizeY, int pieceCount, long seed, String username, int score) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.pieceCount = pieceCount;
        this.seed = seed; 
        this.username = username;
        this.score = score;
    }

    public void setUsername( String username ) { this.username = username; }
    public void setScore( int score ) { this.score = score; }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Arrangement) || o == null) {
            return false;
        }

        Arrangement a = (Arrangement) o;

        if (this.sizeX != a.sizeX || this.sizeY != a.sizeY) {
            return false;
        }
        if (this.pieceCount != a.pieceCount || this.seed != a.seed) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {

        return  this.sizeX       + ";" +
                this.sizeY       + ";" +
                this.pieceCount  + ";" +
                this.seed        + ";" +
                this.username    + ";" +
                this.score;
    }
}