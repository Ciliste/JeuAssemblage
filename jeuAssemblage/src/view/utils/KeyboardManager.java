package view.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import java.awt.KeyboardFocusManager;

public final class KeyboardManager {
	
	public static enum Keys {

		A(65),
		B(66),
		C(67),
		D(68),
		E(69),
		F(70),
		G(71),
		H(72),
		I(73),
		J(74),
		K(75),
		L(76),
		M(77),
		N(78),
		O(79),
		P(80),
		Q(81),
		R(82),
		S(83),
		T(84),
		U(85),
		V(86),
		W(87),
		X(88),
		Y(89),
		Z(90),
		
		NUM_0(48),
		NUM_1(49),
		NUM_2(50),
		NUM_3(51),
		NUM_4(52),
		NUM_5(53),
		NUM_6(54),
		NUM_7(55),
		NUM_8(56),
		NUM_9(57),
		
		SPACE(32),
		ENTER(10),
		ESCAPE(27),
		TAB(9),
		
		SHIFT(16),
		CONTROL(17),
		ALT(18),
		
		LEFT(37),
		UP(38),
		RIGHT(39),
		DOWN(40),
		
		;

		private final int keycode;

		private Keys(int keycode) {

			this.keycode = keycode;
		}

		public int getKeycode() {

			return keycode;
		}

		public static Keys fromKeycode(int keycode) {

			for (Keys key : Keys.values()) {

				if (key.getKeycode() == keycode) {

					return key;
				}
			}

			return null;
		}
	}

	private KeyboardManager() {}
	
	private static final Map<Integer, Boolean> keysMap = new HashMap<>();
	static {

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
			
			synchronized (KeyboardManager.class) {
				
				switch (e.getID()) {
				
					case java.awt.event.KeyEvent.KEY_PRESSED:
						keysMap.put(e.getKeyCode(), true);
						fireKeyPressed(e.getKeyCode());
						System.out.println(e.getKeyCode());
						break;
						
					case java.awt.event.KeyEvent.KEY_RELEASED:
						keysMap.put(e.getKeyCode(), false);
						fireKeyReleased(e.getKeyCode());
						break;
				}
			}

			return false;
		});
	}

	private static final Set<KeyboardListener> listeners = new HashSet<>();

	public static void addKeyboardListener(KeyboardListener listener) {

		synchronized (KeyboardManager.class) {
			
			listeners.add(listener);
		}
	}

	public static void removeKeyboardListener(KeyboardListener listener) {

		synchronized (KeyboardManager.class) {
			
			listeners.remove(listener);
		}
	}

	private static void fireKeyPressed(int keyCode) {

		synchronized (KeyboardManager.class) {
			
			for (KeyboardListener listener : listeners) {
				
				listener.onKeyPressed(Keys.fromKeycode(keyCode));
			}
		}
	}

	private static void fireKeyReleased(int keyCode) {

		synchronized (KeyboardManager.class) {
			
			for (KeyboardListener listener : listeners) {
				
				listener.onKeyReleased(Keys.fromKeycode(keyCode));
			}
		}
	}

	public static boolean isKeyPressed(int keyCode) {
		
		synchronized (KeyboardManager.class) {
			
			return keysMap.getOrDefault(keyCode, false);
		}
	}

	public static boolean isKeyPressed(Keys key) {
		
		return isKeyPressed(key.getKeycode());
	}

	public static interface KeyboardListener {
	
		public abstract void onKeyPressed(Keys key);
		public abstract void onKeyReleased(Keys key);
	}

	public static class KeyboardAdapter implements KeyboardListener {
	
		@Override
		public void onKeyPressed(Keys key) {}

		@Override
		public void onKeyReleased(Keys key) {}
	}

	public static void main(String[] args) {
		
		KeyboardManager.addKeyboardListener(new KeyboardAdapter() {
			
			@Override
			public void onKeyPressed(Keys key) {
				
				System.out.println("Pressed: " + key);
			}
		});

		new JFrame().setVisible(true);
	}
}