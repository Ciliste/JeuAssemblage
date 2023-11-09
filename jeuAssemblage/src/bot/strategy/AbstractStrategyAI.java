package bot.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collections;


import bot.interfaces.IStrategyBot;
import bot.Move;
import model.PlayBoard;
import model.SeedUtils;
import piece.Piece;

public class AbstractStrategyAI implements IStrategyBot {
    private static final int POP_SIZE = 100; 

    protected List<Move> moves;
    protected PlayBoard model;

    protected long seed;
    
    public AbstractStrategyAI(PlayBoard model) {
        this(model, SeedUtils.generateRandomSeed());
    }

    protected AbstractStrategyAI(PlayBoard model, long seed) {
        this.model = model;
        System.out.println(model);
        System.out.println(model.getArea());

        this.moves = getMoves();
        this.seed = seed;
    }
    
    private List<Move> getMoves() {
        List<Move> ret = new LinkedList<Move>();
        
        Random rand = new Random(this.seed);
        List<PlayBoard> population = createPopulation(this.model, POP_SIZE, rand);

        for (int i = 0; i < 10; i++) {
            population = mutation(population, rand, 1d);
        }

        System.out.println(population.get(0));
        System.out.println(population.get(0).getArea());


        return ret;
    }

    private List<PlayBoard> mutation(List<PlayBoard> toMutate, Random rand, double mutationRate) {

        List<PlayBoard> postMutation = new ArrayList<PlayBoard>();
        for (PlayBoard parent : toMutate) {
            postMutation.add(parent);

            if (rand.nextDouble(1) < mutationRate) {
                postMutation.add(individualMutation(parent, rand));
            }
        }

        Collections.sort(postMutation);

        postMutation = postMutation.subList(0, POP_SIZE);

        return postMutation;
    }
    
    private PlayBoard individualMutation(PlayBoard parent1, Random rand) {
        
        PlayBoard son = PlayBoard.constructCopyPlayBoard(parent1);
        
        Map<Integer, Piece> piecesParent1 = parent1.getPieces();

        for (int cpt = 0; cpt < piecesParent1.size(); cpt++) {
            int toSwapId = cpt;
            while (toSwapId == cpt) {
                toSwapId = rand.nextInt(piecesParent1.size());
            }

            son.swap(son.getPieceById(cpt+1), son.getPieceById(toSwapId + 1));
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
        new AbstractStrategyAI(PlayBoard.constructPlayBoard(SeedUtils.generateRandomSeed(), 8, 8, 4));
    }
    
}
