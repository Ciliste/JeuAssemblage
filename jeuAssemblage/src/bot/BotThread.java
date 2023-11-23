package bot;

import java.util.ArrayList;

import bot.interfaces.IBot;
import bot.view.interfaces.IMovesView;

public class BotThread implements Runnable {
    
    private final static int DEFAULT_SLEEP = 1000;

    private int sleepTime;

    private ArrayList<IBot> bots;
    private ArrayList<IMovesView> movesViews;

    private boolean stop;

    public BotThread(ArrayList<IBot> bots, ArrayList<IMovesView> movesViews) {

        this(bots, DEFAULT_SLEEP, movesViews);
    }

    public BotThread(ArrayList<IBot> bots, int sleepTime, ArrayList<IMovesView> movesViews) {

        this.bots = bots;
        this.sleepTime = sleepTime;
        this.movesViews = movesViews;
        this.stop = false;
    }

    public ArrayList<IBot> getBots() {
        return this.bots;
    }

    public void stop() {
        this.stop = true;
    }

    public void start() {
        this.stop = false;
    }

    @Override
    public void run() {

        while (!this.stop) {
            boolean stopable = true;

            for ( int i = 0; i < this.bots.size(); i++) {
                IBot bot = this.bots.get(i);
                IMovesView movesView = this.movesViews.get(i);
                
                if ( bot.tick() ) {
                    stopable = false;
                } else {
                    movesView.start();
                }
            }

            if ( stopable ) this.stop();

            try { Thread.sleep(sleepTime); }
            catch(Exception e ) { e.printStackTrace(); }        
        }
        
        this.stop();
    }

}
