package view.component;

import static view.utils.SwingUtils.*;

import javax.swing.JPanel;

import model.PlayBoard;
import view.MainFrame;
import view.component.board.Finish;
import view.component.board.Grid;
import view.component.board.TimerPanel;
import view.screen.SoloGameFinishScreen;
import view.utils.SwingUtils;
import view.component.timer.Timer;

import java.awt.Graphics;

public class GamePanel extends JPanel {
	
	private final Grid grid;
	private final TimerPanel timerPanel;
	private final Finish finish;

	public GamePanel(MainFrame mainFrame, PlayBoard playBoard, Timer timer) {

		super();

		this.setLayout(null);


		this.grid = new Grid(playBoard);
		this.finish = new Finish(mainFrame, playBoard);

		this.timerPanel = new TimerPanel(mainFrame, timer, new Runnable() {
			
			@Override
			public void run() {
				
				mainFrame.setContentPane(new SoloGameFinishScreen(mainFrame, playBoard));
			}
		});

		this.add(this.grid);
		this.add(this.timerPanel);
		this.add(this.finish);
	}

	@Override
	public void doLayout() {

		super.doLayout();

		this.grid.setBounds(
			0,
		 	0,
		 	getWidthTimesPourcent(this, .8f),
		 	this.getHeight()
		);

		this.timerPanel.setBounds(
			this.grid.getWidth(),
		 	0,
		 	getWidthTimesPourcent (this, .2f),
		 	getHeightTimesPourcent(this, .4f)
		);

		this.finish.setBounds(
			this.grid.getWidth(),
		 	this.timerPanel.getHeight(),
		 	getWidthTimesPourcent (this, .2f),
		 	getHeightTimesPourcent(this, .6f)
		);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		SwingUtils.drawDebugBounds(this, g);
	}
}
