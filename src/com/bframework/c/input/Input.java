package com.bframework.c.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import com.bframework.c.core.Control;
import com.bframework.c.core.Tickable;
import com.bframework.c.event.EventA0C0;
import com.bframework.c.graphics.Renderer;
import com.bframework.c.math.Vector;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Tickable {

	/* VARIABLES */
	
	public static ArrayList<Integer> pressed = new ArrayList<Integer>();

	private static ArrayList<KeyTrigger> keyTriggers = new ArrayList<KeyTrigger>();
	
	private static Vector realMousePosition = Vector.all(0);
	
	private static ArrayList<MouseTrigger> mouseTriggers = new ArrayList<MouseTrigger>();

	
	/* GETTERS & SETTERS */
	
	// pressed
	
	public static boolean pressed(Integer key) {
		return pressed.contains(key);
	}
	
	// triggers
	
	public static void add(KeyTrigger trigger) {
		keyTriggers.add(trigger);
	}

	public static void remove(KeyTrigger value) {
		keyTriggers.remove(value);
	}
	
	public static void add(MouseTrigger trigger) {
		mouseTriggers.add(trigger);
	}

	public static void remove(MouseTrigger value) {
		mouseTriggers.remove(value);
	}
	
	
	// mouse
	
	public static Vector realMousePosition() {
		return realMousePosition;
	}
	
	public static Vector mousePosition() {
		return Renderer.normalize(realMousePosition());
	}
	
	public static Vector updateMousePosition(MouseEvent e) {
		return (realMousePosition = new Vector(e.getPoint()));
	}
	
	
	/* METHODS */
	
	public final void keyPressed(KeyEvent e) {
		todo.add(new EventA0C0() {
			public void execute() {
				if (!pressed.contains(e.getKeyCode())) {
					pressed.add((Integer) e.getKeyCode());
					for (int i = 0; i < keyTriggers.size(); i++)
						keyTriggers.get(i).execute(Control.currentState, e.getKeyCode(), InputState.PRESSED);
				}
			}
		});
	}

	public final void keyReleased(KeyEvent e) {
		todo.add(new EventA0C0() {
			public void execute() {
				if (pressed.contains(e.getKeyCode())) {
					pressed.remove((Integer) e.getKeyCode());
					for (int i = 0; i < keyTriggers.size(); i++)
						keyTriggers.get(i).execute(Control.currentState, e.getKeyCode(), InputState.RELEASED);
				}
			}
		});
	}

	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		updateMousePosition(e);
	}

	public void mouseClicked(MouseEvent e) {
		todo.add(new EventA0C0() {
			public void execute() {
				updateMousePosition(e);
				for (int i = 0; i < mouseTriggers.size(); i++)
					mouseTriggers.get(i).execute(Control.currentState, InputState.CLICKED);
			}
		});
	}

	public void mousePressed(MouseEvent e) {
		todo.add(new EventA0C0() {
			public void execute() {
				updateMousePosition(e);
				for (int i = 0; i < mouseTriggers.size(); i++)
					mouseTriggers.get(i).execute(Control.currentState, InputState.PRESSED);
			}
		});
	}

	public void mouseReleased(MouseEvent e) {
		todo.add(new EventA0C0() {
			public void execute() {
				updateMousePosition(e);
				for (int i = 0; i < mouseTriggers.size(); i++)
					mouseTriggers.get(i).execute(Control.currentState, InputState.RELEASED);
			}
		});
	}

	public void mouseEntered(MouseEvent e) {
		updateMousePosition(e);
	}
	
	public void mouseExited(MouseEvent e) {
		updateMousePosition(e);
	}

	public void keyTyped(KeyEvent e) {

	}
	

	ArrayList<EventA0C0> todo = new ArrayList<EventA0C0>();
	public void tick() {
		while (todo.size() > 0) {
			todo.get(0).execute();
			todo.remove(0);
		}
	}
}
