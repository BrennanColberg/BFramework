package com.bframework.c.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

import com.bframework.c.core.Control;

public class CameraInput extends Input {
	
	public static final int UP = KeyEvent.VK_UP;
	public static final int DOWN = KeyEvent.VK_DOWN;
	public static final int LEFT = KeyEvent.VK_LEFT;
	public static final int RIGHT = KeyEvent.VK_RIGHT;
	public static final int ZOOM_IN = KeyEvent.VK_EQUALS;
	public static final int ZOOM_OUT = KeyEvent.VK_MINUS;
	
	public static final double MAGNIFICATION_INCREMENT = 0.01;
	public static final double SPEED = 3;

	public CameraInput() {
		add(new KeyTrigger(UP, InputState.PRESSED) {
			public void execute() {
				Control.camera().velocity.value[1] = -SPEED;
			}
		});
		add(new KeyTrigger(DOWN, InputState.PRESSED) {
			public void execute() {
				Control.camera().velocity.value[1] = SPEED;
			}
		});
		add(new KeyTrigger(LEFT, InputState.PRESSED) {
			public void execute() {
				Control.camera().velocity.value[0] = -SPEED;
			}
		});
		add(new KeyTrigger(RIGHT, InputState.PRESSED) {
			public void execute() {
				Control.camera().velocity.value[0] = SPEED;
			}
		});
		add(new KeyTrigger(ZOOM_IN, InputState.PRESSED) {
			public void execute() {
				Control.camera().magnificationChange = MAGNIFICATION_INCREMENT;
			}
		});
		add(new KeyTrigger(ZOOM_OUT, InputState.PRESSED) {
			public void execute() {
				Control.camera().magnificationChange = -MAGNIFICATION_INCREMENT;
			}
		});
		
		
		
		add(new KeyTrigger(UP, InputState.RELEASED) {
			public void execute() {
				if (!Input.pressed(DOWN)) Control.camera().velocity.value[1] = 0;
			}
		});
		add(new KeyTrigger(DOWN, InputState.RELEASED) {
			public void execute() {
				if (!Input.pressed(UP)) Control.camera().velocity.value[1] = 0;
			}
		});
		add(new KeyTrigger(LEFT, InputState.RELEASED) {
			public void execute() {
				if (!Input.pressed(RIGHT)) Control.camera().velocity.value[0] = 0;
			}
		});
		add(new KeyTrigger(RIGHT, InputState.RELEASED) {
			public void execute() {
				if (!Input.pressed(LEFT)) Control.camera().velocity.value[0] = 0;
			}
		});
		add(new KeyTrigger(ZOOM_IN, InputState.RELEASED) {
			public void execute() {
				if (!Input.pressed(ZOOM_OUT)) Control.camera().magnificationChange = 0;
			}
		});
		add(new KeyTrigger(ZOOM_OUT, InputState.RELEASED) {
			public void execute() {
				if (!Input.pressed(ZOOM_IN)) Control.camera().magnificationChange = 0;
			}
		});
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		Control.camera().magnificationTarget = Control.camera().magnification + e.getPreciseWheelRotation() / 100d;
	}
}