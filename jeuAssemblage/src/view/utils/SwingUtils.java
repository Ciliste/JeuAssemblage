package view.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;

public class SwingUtils {
    
	public static final Color DEBUG_COLOR = Color.RED;

    public static int getWidthTimesPourcent(JComponent component, float pourcent) {
        
        return (int) (component.getWidth() * pourcent);
    }

    public static int getHeightTimesPourcent(JComponent component, float pourcent) {
        
        return (int) (component.getHeight() * pourcent);
    }

	public static void drawDebugBounds(Component component, Graphics g) {

		Color oldColor = g.getColor();

		g.setColor(DEBUG_COLOR);
		g.drawRect(1, 1, component.getWidth() - 2, component.getHeight() - 2);

		g.setColor(oldColor);
	}
}
