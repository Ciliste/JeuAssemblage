package view.utils;

import javax.swing.JComponent;

public class SwingUtils {
    
    public static int getWidthTimesPourcent(JComponent component, float pourcent) {
        
        return (int) (component.getWidth() * pourcent);
    }

    public static int getHeightTimesPourcent(JComponent component, float pourcent) {
        
        return (int) (component.getHeight() * pourcent);
    }
}
