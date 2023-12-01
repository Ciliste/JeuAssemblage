package view.utils;

import java.util.HashMap;
import java.util.Random;

import model.PlayBoard;

import java.awt.Color;
import java.awt.Image;

public class PiecesColor {

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

            Color c = getColor(rand);

            if (rand.nextBoolean()) {
                c = c.darker();
            }

            piecesImageMap.put(i, PieceRenderUtils.createCellImage(c));
        }
    }

    private static Color getColor(Random rand) {

        return new Color(rand.nextInt(20, 220), rand.nextInt(20, 220), rand.nextInt(20, 220));
    }
    
}
