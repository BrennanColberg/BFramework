package com.bframework.c.input.debug;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.bframework.c.math.Vector;

public final class MouseMotionParrot implements MouseMotionListener {

	public void mouseDragged(MouseEvent e) {
		System.out.println(String.format("$ MouseMotionParrot: Dragged to %s", Vector.from(e.getPoint())));
	}

	public void mouseMoved(MouseEvent e) {
		System.out.println(String.format("$ MouseMotionParrot: Moved to %s", Vector.from(e.getPoint())));
	}

}
