package model.listener;

import java.util.ArrayList;
import java.util.List;

public interface IPlayBoardListenable {

	public abstract void addPlayBoardListener(IPlayBoardListener listener);
	public abstract void removePlayBoardListener(IPlayBoardListener listener);
}
