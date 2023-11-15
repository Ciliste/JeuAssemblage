package view.listener;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResizeListener extends ComponentAdapter {
    
    private final Runnable callback;

    public ResizeListener(Runnable callback) {

        this.callback = callback;
    }

    @Override
    public void componentResized(ComponentEvent e) {

        callback.run();
    }
}
