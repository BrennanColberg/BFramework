package com.bframework.c.input.debug;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class KeyParrot implements KeyListener {

	public void keyTyped(KeyEvent e) {
		System.out.println(String.format("$ KeyParrot: %s typed", e.getKeyChar()));
	}

	public void keyPressed(KeyEvent e) {
		System.out.println(String.format("$ KeyParrot: %s pressed (code %s)", e.getKeyChar(), e.getKeyCode()));
	}

	public void keyReleased(KeyEvent e) {
		System.out.println(String.format("$ KeyParrot: %s released (code %s)", e.getKeyChar(), e.getKeyCode()));
	}

}
