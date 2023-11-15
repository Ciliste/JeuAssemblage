package view.screen;

import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.MainFrame;
import view.utils.SwingUtils;

public class MultiplayerSelectScreen extends JPanel {
	
	private JButton btnVsAI = new JButton("VS AI");
	private JButton btnLocal = new JButton("Multijoueur local");
	private JButton btnHosted = new JButton("Multijoueur hébergé");

	public MultiplayerSelectScreen(MainFrame mainFrame) {
		
		super();

		LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);

		btnVsAI.setAlignmentX(CENTER_ALIGNMENT);
		btnLocal.setAlignmentX(CENTER_ALIGNMENT);
		btnHosted.setAlignmentX(CENTER_ALIGNMENT);

		setLayout(layout);

		add(Box.createVerticalGlue());

		add(btnVsAI);

		add(Box.createVerticalStrut(50));

		add(btnLocal);

		add(Box.createVerticalStrut(50));

		add(btnHosted);

		add(Box.createVerticalGlue());

		btnVsAI.addActionListener(e -> {

			mainFrame.setContentPane(new AIGameCreation(mainFrame));
		});
	}

	@Override
	public void paintComponent(java.awt.Graphics g) {
		
		super.paintComponent(g);

		SwingUtils.drawDebugBounds(this, g);
	}
}
