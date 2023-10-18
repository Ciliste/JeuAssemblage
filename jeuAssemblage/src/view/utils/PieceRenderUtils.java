package view.utils;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;

import javax.imageio.ImageIO;

import pieces.PieceFactory;

public final class PieceRenderUtils {
	
	private static final int CELL_PIXEL_SIZE = 50;
	private static final int CENTER_PART_OFFSET = 9;

	private PieceRenderUtils() {}
	
	public static BufferedImage createCellImage() {
		
		return createCellImage(getRandomColor());
	}

	public static BufferedImage createCellImage(Color color) {
		
		// Create a new image
		BufferedImage image = new BufferedImage(CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, BufferedImage.TYPE_INT_ARGB);

		// Get the graphics of the image
		java.awt.Graphics g = image.getGraphics();

		// Adjust the brightness or saturation to create a brighter color
		Color brighterColor = createDarkerColor(color, .15f);
		Color darkerColor = createDarkerColor(color, .3f);

		// Draw the cell
		g.setColor(brighterColor);

		// Define the triangle's coordinates
        int[] xPoints = {0, CELL_PIXEL_SIZE, 0};
        int[] yPoints = {CELL_PIXEL_SIZE, 0, 0};

        // Draw a filled triangle
        g.fillPolygon(xPoints, yPoints, 3);

		g.setColor(darkerColor);

		// Define the triangle's coordinates
		xPoints = new int[]{CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, 0};
		yPoints = new int[]{CELL_PIXEL_SIZE, 0, CELL_PIXEL_SIZE};

		// Draw a filled triangle
		g.fillPolygon(xPoints, yPoints, 3);

		g.setColor(color);

		g.fillRect(CENTER_PART_OFFSET, CENTER_PART_OFFSET, CELL_PIXEL_SIZE - (CENTER_PART_OFFSET * 2), CELL_PIXEL_SIZE - (CENTER_PART_OFFSET * 2));

		return image;
	}

    private static Color createDarkerColor(Color givenColor, float pourcent) {

        float[] hsb = Color.RGBtoHSB(givenColor.getRed(), givenColor.getGreen(), givenColor.getBlue(), null);

        hsb[2] = Math.max(0.0f, hsb[2] - pourcent);

        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

	private static Color getRandomColor() {

		return new Color((int) (Math.random() * 0x1000000));
	}

	public static BufferedImage createPieceImage(int[][] piece, Color color) {

		// TODO NE PAS DESSINER LES LIGNES OU COLLONNES VIDES

		
		// Create a new image
		BufferedImage image = new BufferedImage(CELL_PIXEL_SIZE * piece.length, CELL_PIXEL_SIZE * piece[0].length, BufferedImage.TYPE_INT_ARGB);

		// Get the graphics of the image
		java.awt.Graphics g = image.getGraphics();

		BufferedImage cellImage = createCellImage(color);

		for (int i = 0; i < piece.length; i++) {

			for (int j = 0; j < piece[i].length; j++) {

				if (piece[i][j] == 1) {
					g.drawImage(cellImage, j * CELL_PIXEL_SIZE, i * CELL_PIXEL_SIZE, null);
				}
			}
		}

		return image;
	}

	public static BufferedImage createPieceImage(int[][] piece) {

		return createPieceImage(piece, getRandomColor());
	}

	public static void main(String[] args) {
		
		int[][] piece = PieceFactory.createPieceT().getBounds();

		BufferedImage image = createPieceImage(piece, new Color(0xfa8d12));

		try {
            File output = new File("output.png");
            ImageIO.write(image, "png", output);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
