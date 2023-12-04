package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.PlayBoard;

public class ModelTest {

    public PlayBoard playBoard;

    public int width;
    public int height;
    public Long seed;
    public int nbPiece;

    @Before
    public void setup() {
        this.width = (int) (Math.random() * 13) + 4;
        this.height = (int) (Math.random() * 13) + 4;

        this.seed = (long) (Math.random() * 1000000) + 13;
        this.nbPiece = (int) (Math.random() * 3) + 1;

        this.playBoard = PlayBoard.constructPlayBoard(this.seed, this.width, this.height, this.nbPiece);
    }

    @Test
    public void testPlayboardCreation() {

        assertTrue(this.playBoard.getSeed() == this.seed);
        assertTrue(this.playBoard.getBoardWidth() == this.width);
        assertTrue(this.playBoard.getBoardHeight() == this.height);
        assertTrue(this.playBoard.getPiecesCount() == this.nbPiece);

    }
    
    @Test
    public void testPlayboardCopy() {

        PlayBoard copy = PlayBoard.constructCopyPlayBoard(this.playBoard);

        assertTrue(this.playBoard.equals(copy));
    }
    
    @Test
    public void testPlayboardCreationWithSeed() {

        PlayBoard copy = PlayBoard.constructPlayBoard(this.seed, this.width, this.height, this.nbPiece);

        assertTrue(this.playBoard.equals(copy));
    }

    /*
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡇⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣾⠟⠀⣀⣠⠄⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⢠⣶⣿⠟⠁⢠⣾⠋⠁⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠹⣿⡇⠀⠀⠸⣿⡄⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠙⠷⡀⠀⠀⢹⠗⠀⠀⠀⠀⠀⠀
        ⠀⠀⢀⣤⣴⡖⠒⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠒⢶⣄
        ⠀⠀⠈⠙⢛⣻⠿⠿⠿⠟⠛⠛⠛⠋⠉⠀⠀⠀⣸⡿
        ⠀⠀⠀⠀⠛⠿⣷⣶⣶⣶⣶⣾⠿⠗⠂⠀⢀⠴⠛⠁
        ⠀⠀⠀⠀⠀⢰⣿⣦⣤⣤⣤⣴⣶⣶⠄⠀⠀⠀⠀⠀
        ⣀⣤⡤⠄⠀⠀⠈⠉⠉⠉⠉⠉⠀⠀⠀⠀⢀⡀⠀⠀
        ⠻⣿⣦⣄⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣠⣴⠾⠃⠀⢀
        ⠀⠀⠈⠉⠛⠛⠛⠛⠛⠛⠛⠛⠋⠉⠁⠀⣀⣤⡶⠋
        ⠀⠀⠀⠀⠐⠒⠀⠠⠤⠤⠤⠶⠶⠚⠛⠛⠉⠀⠀⠀ 
    */

}
