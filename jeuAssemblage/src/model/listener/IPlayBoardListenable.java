package model.listener;

public interface IPlayBoardListenable {
	
	public abstract void addPlayBoardListener(IPlayBoardListener listener);
	public abstract void removePlayBoardListener(IPlayBoardListener listener);
}
