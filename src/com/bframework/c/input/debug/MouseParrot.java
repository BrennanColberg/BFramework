package com.bframework.c.input.debug;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.bframework.c.math.Vector;

public final class MouseParrot implements MouseListener {

	public void mouseClicked(MouseEvent e) {
		System.out.println(String.format("$ MouseParrot: Click at %s", Vector.from(e.getPoint())));
	}

	public void mousePressed(MouseEvent e) {
		System.out.println(String.format("$ MouseParrot: Pressed at %s", Vector.from(e.getPoint())));
	}

	public void mouseReleased(MouseEvent e) {
		System.out.println(String.format("$ MouseParrot: Released at %s", Vector.from(e.getPoint())));
	}

	public void mouseEntered(MouseEvent e) {
		System.out.println(String.format("$ MouseParrot: Entered at %s", Vector.from(e.getPoint())));
	}

	public void mouseExited(MouseEvent e) {
		System.out.println(String.format("$ MouseParrot: Exited at %s", Vector.from(e.getPoint())));
	}

}
