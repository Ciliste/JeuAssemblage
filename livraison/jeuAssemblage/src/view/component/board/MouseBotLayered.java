package view.component.board;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JLayeredPane;

public class MouseBotLayered extends JLayeredPane {

    Point mousePosition;

    public MouseBotLayered() {
        super();

        this.mousePosition = new Point(0, 0);

        this.addMouseListener(new MouseFollow());
    }

    @Override
    public void paintComponent(Graphics g) {
        
		Ellipse2D.Double circle = new Ellipse2D.Double(mousePosition.x, mousePosition.y, 10, 10);
		((Graphics2D) g).fill(circle);
    }
    
    private class MouseFollow extends MouseAdapter {
        
        @Override
        public void mouseMoved(MouseEvent e) {
            mousePosition = e.getPoint();
            repaint();
        }
    }
}
