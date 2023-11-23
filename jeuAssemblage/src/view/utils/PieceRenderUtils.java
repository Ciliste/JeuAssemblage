package view.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import factory.PieceFactory;

public final class PieceRenderUtils {
	
	public static final int CELL_PIXEL_SIZE = 50;
	private static final int CENTER_PART_OFFSET = 9;

	private PieceRenderUtils() {}
	
	public static Image PLACEHOLDER_IMAGE = createCellImage(Color.WHITE);

	public static BufferedImage createCellImage(long seed) {
		
		return createCellImage(getRandomColor(seed));
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

	private static Color getRandomColor(long seed) {

		return new Color((int) (Math.random() * 0x1000000));
	}

	public static BufferedImage createSurrondingPieceImage(boolean[][] piece, Color color) {
		
		// Create a new image
		BufferedImage image = new BufferedImage(CELL_PIXEL_SIZE * piece[0].length, CELL_PIXEL_SIZE * piece.length, BufferedImage.TYPE_INT_ARGB);

		// Get the graphics of the image
		Graphics g = image.getGraphics();

		for (int i = 0; i < piece.length; i++) {

			for (int j = 0; j < piece[i].length; j++) {

				if (piece[i][j]) {

					if (i == 0 || !piece[i - 1][j]) {

						g.setColor(color);
						g.fillRect(j * CELL_PIXEL_SIZE, i * CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, 2);
					}

					if (j == 0 || !piece[i][j - 1]) {

						g.setColor(color);
						g.fillRect(j * CELL_PIXEL_SIZE, i * CELL_PIXEL_SIZE, 2, CELL_PIXEL_SIZE);
					}

					if (i == piece.length - 1 || !piece[i + 1][j]) {

						g.setColor(color);
						g.fillRect(j * CELL_PIXEL_SIZE, (i + 1) * CELL_PIXEL_SIZE - 2, CELL_PIXEL_SIZE, 2);
					}

					if (j == piece[i].length - 1 || !piece[i][j + 1]) {

						g.setColor(color);
						g.fillRect((j + 1) * CELL_PIXEL_SIZE - 2, i * CELL_PIXEL_SIZE, 2, CELL_PIXEL_SIZE);
					}
				}
			}
		}

		// Register image plz
		// fdp ?
		try {
			File output = new File("output.png");
			ImageIO.write(image, "png", output);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}

	public static BufferedImage createSurrondingRectangleImage(int width, int height) {
		
		return createSurrondingRectangleImage(width, height, Color.RED);
	}

	public static BufferedImage createSurrondingRectangleImage(int width, int height, Color color) {

		System.out.println("width: " + width + " height: " + height);

		if (width < 1 || height < 1) {

			return null;
		}

		// Create a new image
		BufferedImage image = new BufferedImage(width * CELL_PIXEL_SIZE, height * CELL_PIXEL_SIZE, BufferedImage.TYPE_INT_ARGB);

		// Get the graphics of the image
		Graphics g = image.getGraphics();

		g.setColor(color);
		g.fillRect(0, 0, width * CELL_PIXEL_SIZE, 2);
		g.fillRect(0, 0, 2, height * CELL_PIXEL_SIZE);
		g.fillRect(0, height * CELL_PIXEL_SIZE - 2, width * CELL_PIXEL_SIZE, 2);
		g.fillRect(width * CELL_PIXEL_SIZE - 2, 0, 2, height * CELL_PIXEL_SIZE);

		try {
			File output = new File("test.png");
			ImageIO.write(image, "png", output);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}

	public static List<Color> getDefaultColors() {

		List<Color> colors = new ArrayList<>();

		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);
		colors.add(Color.MAGENTA);
		colors.add(Color.CYAN);
		colors.add(Color.ORANGE);
		colors.add(Color.PINK);

		return colors;
	}

	// public static void main(String[] args) {
		
	// 	int[][] piece = PieceFactory.createPieceT().getBounds();

	// 	BufferedImage image = createPieceImage(piece, new Color(0xfa8d12));

	// 	try {
    //         File output = new File("output.png");
    //         ImageIO.write(image, "png", output);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
	// }
}
