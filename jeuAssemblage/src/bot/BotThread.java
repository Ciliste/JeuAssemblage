package bot;

import java.util.ArrayList;

import bot.interfaces.IBot;
import bot.view.interfaces.IMovesView;

public class BotThread implements Runnable {
    
    private final static int DEFAULT_SLEEP = 1000;

    private ArrayList<IBot> bot;
    private int sleepTime;

    private IMovesView movesView;

    private boolean stop;

    public BotThread(ArrayList<IBot> bot, IMovesView movesView) {

        this(bot, DEFAULT_SLEEP, movesView);
    }

    public BotThread(ArrayList<IBot> bot, int sleepTime, IMovesView movesView) {

        this.bot = bot;
        this.sleepTime = sleepTime;
        this.movesView = movesView;
        this.stop = false;
    }

    public ArrayList<IBot> getBots() {
        return this.bot;
    }

    public void stop() {
        this.stop = true;
    }

    public void start() {
        this.stop = false;
    }

    @Override
    public void run() {

        // while (!this.stop && bot.tick()) {
        //     try { Thread.sleep(sleepTime); }
        //     catch(Exception e ) { e.printStackTrace(); }        
        // }
        
        // this.movesView.setMoves(this.bot.getMoves());
        // this.movesView.start();
        
        // this.stop();
    }

}
