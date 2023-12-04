package bot.strategy;

import java.util.Collection;
import java.util.Collections;

import model.PlayBoard;
import utils.SeedUtils;

public class NoobStrategy extends AbstractStrategy {
    

    public NoobStrategy(PlayBoard model) {
        this(model, SeedUtils.generateRandomSeed(), AgStrategy.EASY);
    }

    public NoobStrategy(PlayBoard model, int testSize) {
        this(model, SeedUtils.generateRandomSeed(), testSize);
    }

    public NoobStrategy(PlayBoard model, long seed, int testSize) {
        super(model, seed, testSize);
    }
    
    @Override
    public void tickMethod() {

        pop.add(PlayBoard.constructPlayBoardWithPlacementSeed(model, SeedUtils.generateRandomSeed()));
    }

    @Override
    protected void setBestSolution() {
        Collections.sort(pop);
        this.bestSolution = pop.get(0);
    }
    
}
