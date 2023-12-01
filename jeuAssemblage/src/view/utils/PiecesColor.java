package view.utils;

import java.util.HashMap;
import java.util.Random;

import model.PlayBoard;

import java.awt.Color;
import java.awt.Image;

public class PiecesColor {

    private static Color[] colors = getColors();

    private HashMap<Integer, Image> piecesImageMap;

    public PiecesColor(PlayBoard model) {
        piecesImageMap = new HashMap<Integer, Image>();
        createImage(model);
    }

    public Image getImageById(int id) {
        Image ret = piecesImageMap.get(id);
        if (ret != null) return ret;

        throw new IllegalArgumentException("id " + id + " doesn't exist");
    }

    private void createImage(PlayBoard model) {
        long seed = model.getSeed();
        Random rand = new Random(seed);

        int size = model.getPiecesCount();
        for (int i = 1; i <= size; i++) {
            Color c = colors[rand.nextInt(colors.length)];
            piecesImageMap.put(i, PieceRenderUtils.createCellImage(c));
        }
    }
    
    private static Color[] getColors() {
        return new Color[]
        { 
            Color.RED,
            Color.GREEN, 
            Color.GRAY,
            Color.CYAN,
            Color.ORANGE,
            Color.PINK,
            Color.MAGENTA,
            Color.BLUE,
            Color.WHITE,
            Color.YELLOW
        };
    }
    
}
