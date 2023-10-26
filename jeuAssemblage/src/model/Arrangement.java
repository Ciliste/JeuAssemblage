package model;

public class Arrangement {
    private int sizeX;
    private int sizeY;
    private int pieceCount;
    private long seed;
    
    private String username;
    private int score;

    public Arrangement(int sizeX, int sizeY, int pieceCount, long seed, String username, int score) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.pieceCount = pieceCount;
        this.seed = seed; 
        this.username = username;
        this.seed = seed;
    }

    public void setUsername( String username ) { this.username = username; }
    public void setScore( int score ) { this.score = score; }
}