package com.bframework.c.input.debug;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public final class MouseWheelParrot implements MouseWheelListener {

	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println(String.format("$ MouseWheelParrot: Wheel moved precisely %s", e.getPreciseWheelRotation()));
	}

}
