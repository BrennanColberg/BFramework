package com.bframework.c.input;

import java.awt.event.KeyEvent;
import java.util.EmptyStackException;

import com.bframework.c.core.Control;

public class ControlInput extends Input {

	public ControlInput() {
		Input.add(new KeyTrigger(KeyEvent.VK_BACK_SPACE, InputState.PRESSED) {
			public void execute() {
				try { Control.stateStack.pop(); }
				catch (EmptyStackException e) { System.exit(0); }
			}
		});
	}

}