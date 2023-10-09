package view.component;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import static view.utils.SwingUtils.*;

public class Separator extends JPanel {
    
    private final Color color;

    public Separator() {

        this(Color.BLACK);
    }

    public Separator(Color color) {

        this.color = color;
        this.setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(color);

        g.drawLine(
            0, 
            getHeightTimesPourcent(this, .5f),
            getWidth(), 
            getHeightTimesPourcent(this, .5f)
        );
    }
}
