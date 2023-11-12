package bot.strategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collections;

import java.awt.Point;

import bot.Move;
import bot.interfaces.IStrategyBot;
import model.PlayBoard;
import model.SeedUtils;
import piece.Piece;

public class AIStrategy implements IStrategyBot {
    public static final int EASY = 100;
    public static final int MEDIUM = 200; 
    public static final int HARD = 500; 
    
    private static final int POP_SIZE = 100;

    protected List<Move> moves;
    protected PlayBoard model;
    protected long seed;
    protected int testSize;
    
    public AIStrategy(PlayBoard model) {
        this(model, SeedUtils.generateRandomSeed(), AIStrategy.EASY);
    }

    public AIStrategy(PlayBoard model, int testSize) {
        this(model, SeedUtils.generateRandomSeed(), testSize);
    }

    protected AIStrategy(PlayBoard model, long seed, int testSize) {
        System.out.println(model);
        System.out.println(model.getArea());
        
        this.model = model;
        this.seed = seed;
        this.testSize = testSize;
        this.moves = getMoves();
    }
    
    private List<Move> getMoves() {
        List<Move> ret = new LinkedList<Move>();
        
        Random rand = new Random(this.seed);

        List<PlayBoard> population = createPopulation(this.model, POP_SIZE, rand);
        population.add(model);

        for (int i = 0; i < this.testSize; i++) {
            population = mutation(population, rand, 1d);
        }

        PlayBoard bestSolution = population.get(0);
        ArrayList<Piece> pieceAl = new ArrayList<Piece>();

        for (int i = 0; i < bestSolution.getBoardHeight(); i++) {
            for (int j = 0; j < bestSolution.getBoardWidth(); j++) {
                int pieceId = bestSolution.getPieceIdAt(j, i);
                if (pieceId != 0) {
                    Piece p = bestSolution.getPieceById(pieceId);
                    if (!pieceAl.contains(p)) {
                        Point upperLeftCorner = bestSolution.getUpperLeftPieceCornerById(pieceId);
                        ret.add(new Move(pieceId, upperLeftCorner.x, upperLeftCorner.y));
                        pieceAl.add(p);
                    }
                }
            }
        }

        System.out.println(bestSolution);
        System.out.println(bestSolution.getArea());

        return ret;
    }

    private List<PlayBoard> mutation(List<PlayBoard> toMutate, Random rand, double mutationRate) {

        List<PlayBoard> postMutation = new ArrayList<PlayBoard>();

        for (PlayBoard parent : toMutate) {

            postMutation.add(parent);
            double nextRand = Math.abs(rand.nextDouble() % 1);
            if (nextRand < mutationRate) {
                PlayBoard son;
                if (nextRand < mutationRate / 3) {
                    son = individualMutationSwapping(parent, rand);
                } else if (nextRand < 2 * (mutationRate / 3)) {
                    son = individualMutationRotation(parent, rand);
                } else {
                    son = individualMutationMouvement(parent, rand);
                }

                if (!parent.equals(son)) postMutation.add(son);
            }
        }

        Collections.sort(postMutation);

        postMutation = postMutation.subList(0, POP_SIZE);

        return postMutation;
    }
    
    private PlayBoard individualMutationSwapping(PlayBoard parent1, Random rand) {

        PlayBoard son = PlayBoard.constructCopyPlayBoard(parent1);

        Map<Integer, Piece> piecesParent1 = parent1.getPieces();

        for (int cpt = 1; cpt < piecesParent1.size(); cpt++) {
            int toSwapId = cpt;
            while (toSwapId == cpt) {
                toSwapId = Math.abs(rand.nextInt() % piecesParent1.size()) + 1;
            }

            son.swap(son.getPieceById(cpt), son.getPieceById(toSwapId));
        }

        return son;
    }
    
    private PlayBoard individualMutationRotation(PlayBoard parent1, Random rand) {

        PlayBoard son = PlayBoard.constructCopyPlayBoard(parent1);

        Map<Integer, Piece> piecesParent1 = parent1.getPieces();

        for (int cpt = 1; cpt < piecesParent1.size() + 1; cpt++) {

            Piece pieceClone = Piece.clone(son.getPieceById(cpt));

            final int MAX_ROTATIONS = 4;
            int nbRotations = rand.nextInt(MAX_ROTATIONS);
            for (int i = 0; i < nbRotations; i++) {

                pieceClone.rotateLeft();
            }

            final int MAX_FLIPS = 2;
            int nbReverse = rand.nextInt(MAX_FLIPS);
            for (int i = 0; i < nbReverse; i++) {

                pieceClone.reverse();
            }

            Point p = son.getUpperLeftPieceCornerById(cpt);
            son.placePieceAsId(p.x, p.y, pieceClone, cpt);
        }

        return son;
    }
    
    private PlayBoard individualMutationMouvement(PlayBoard parent1, Random rand) {
        final int MAX_MOUVEMENT = 4;

        PlayBoard son = PlayBoard.constructCopyPlayBoard(parent1);
        
        Map<Integer, Piece> piecesParent1 = parent1.getPieces();

        Point mid = new Point(son.getBoardWidth() / 2, son.getBoardHeight() / 2 );

        for (int cpt = 1; cpt < piecesParent1.size() + 1; cpt++) {
            int mouvementnbTime = rand.nextInt(MAX_MOUVEMENT);
            for (int j = 0; j < mouvementnbTime; j++) {
                Point p = son.getUpperLeftPieceCornerById(cpt);
                int x = mid.x - p.x; 
                int y = mid.y - p.y;
                
                if (x != 0) x = x / Math.abs(x);
                if (y != 0) y = y / Math.abs(y);

                son.movePiece(p.x + x, p.y, son.getPieceById(cpt));
                
                p = son.getUpperLeftPieceCornerById(cpt);
                son.movePiece(p.x, p.y + y, son.getPieceById(cpt));
            }
        }

        return son;
    }
    
    @Override
    public Move nextMove() {
        return this.moves.remove(0);
    }

    private static List<PlayBoard> createPopulation(PlayBoard model, int size, Random rand) {
        
        List<PlayBoard> population = new ArrayList<PlayBoard>();

        int pieceCount = model.getPieces().size();
        for (int i = 0; i < size; i++) {

            population.add(PlayBoard.constructPlayBoard(rand.nextLong(), model.getBoardWidth(), model.getBoardHeight(),
                    pieceCount));
        }

        return population;
    }

    public static void main(String args) {
        new AIStrategy(PlayBoard.constructPlayBoard(SeedUtils.generateRandomSeed(), 8, 8, 4));
    }
    
}
