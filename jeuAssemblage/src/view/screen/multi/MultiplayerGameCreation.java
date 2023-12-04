package view.screen.multi;

import javax.swing.JComponent;
import javax.swing.JPanel;

import view.MainFrame;
import view.screen.AIGameCreation;

public class MultiplayerGameCreation extends AIGameCreation {
	
	public MultiplayerGameCreation(MainFrame mainFrame, boolean isHost) {
		
		super(mainFrame);

		for (JComponent component : getSettingsComponents()) {

			component.setEnabled(isHost);
		}
	}
	

}
