package bot;

import java.util.ArrayList;

import bot.interfaces.IBot;

public class BotThread implements Runnable {
    
    private final static int DEFAULT_SLEEP = 1000;

    private ArrayList<IBot> bots;
    private int sleepTime;

    private boolean stop;

    public BotThread() {

        this(new ArrayList<IBot>());
    }

    public BotThread(ArrayList<IBot> bots) {

        this(bots, DEFAULT_SLEEP);
    }

    public BotThread(ArrayList<IBot> bots, int sleepTime) {

        this.bots = bots;
        this.sleepTime = sleepTime;
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

            for (IBot bot : bots) {
                bot.tick();   
            }

            try { Thread.sleep(sleepTime); }
            catch(Exception e ) { e.printStackTrace(); }        
        }
    }

}
