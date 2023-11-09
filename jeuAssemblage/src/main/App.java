package main;

import java.io.File;

import javax.swing.SwingUtilities;

import bot.strategy.AbstractStrategyAI;
import model.PlayBoard;
import model.SeedUtils;
import view.MainFrame;

public class App {
    public static void main(String[] args) throws Exception {

        //PlayBoard p = PlayBoard.constructPlayBoard(SeedUtils.generateRandomSeed(), 8, 8, 4);
        //new AbstractStrategyAI(p);
        SwingUtilities.invokeLater(() -> {
            
            new MainFrame();
        });
    }
}
