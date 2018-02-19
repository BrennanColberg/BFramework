package com.bframework.c.core;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.bframework.c.graphics.Rect;
import com.bframework.c.input.Input;
import com.bframework.c.math.Vector;

public abstract class Display {

	public static JFrame frame = new JFrame();
	public static Canvas canvas = new Canvas();
	public static Input input = new Input();

	public static void init(Vector size) {
		frame = new JFrame();
		setSize(size.dimension());
		frame.add(canvas);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas.addKeyListener(input);
		canvas.addMouseListener(input);
		canvas.addMouseMotionListener(input);
		canvas.addMouseWheelListener(input);
		canvas.setVisible(true);
		canvas.setFocusable(true);
		canvas.requestFocus();
	}

	public static void setSize(Dimension size) { // TODO make size flexible,
													// scalable graphics
		frame.setPreferredSize(size);
		frame.setMaximumSize(size);
		frame.setMinimumSize(size);
	}

	/* GETTERS */
	public static Rect rectBounds() {
		double width = canvas.getWidth(), height = canvas.getHeight();
		return new Rect(Vector.all(0), new Vector(width, height));
	}

	public static Vector center() {
		return new Vector(canvas.getWidth() / 2, canvas.getHeight() / 2);
	}

}
