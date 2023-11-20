package bot;

import java.util.ArrayList;

import bot.interfaces.IBot;

public class BotThread implements Runnable {
    
    private final static int DEFAULT_SLEEP = 1000;

    private ArrayList<IBot> bots;
    private int sleepTime;

    public BotThread() {

        this(new ArrayList<IBot>());
    }

    public BotThread(ArrayList<IBot> bots) {

        this(bots, DEFAULT_SLEEP);
    }

    public BotThread(ArrayList<IBot> bots, int sleepTime) {

        this.bots = bots;
        this.sleepTime = sleepTime;
    }

    public ArrayList<IBot> getBots() {
        return this.bots;
    }

    @Override
    public void run() {

        while (true) {

            for (IBot bot : bots) {
                bot.tick();   
            }

            try { Thread.sleep(sleepTime); }
            catch(Exception e ) { e.printStackTrace(); }        
        }
    }

}
