package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class Keyboard implements KeyListener {

	private static HashSet<Integer> pressedKeys = new HashSet<Integer>();

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static boolean isKeyDown(int keyCode) {
		return pressedKeys.contains(keyCode);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
	}
}
