package com.cy4.Cy4Engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keyState = new boolean[66568];

	public void update() {

	}

	public boolean getKey(int id) {
		return keyState[id];
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.keyState[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.keyState[e.getKeyCode()] = false;
	}

}
