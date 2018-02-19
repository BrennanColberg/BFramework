package com.bframework.c.graphics.debug;

import java.awt.Color;

import com.bframework.c.core.Display;
import com.bframework.c.graphics.Poly;
import com.bframework.c.graphics.Rect;
import com.bframework.c.graphics.Renderable;
import com.bframework.c.graphics.Renderer;
import com.bframework.c.math.Vector;

public final class DisplayOutline implements Renderable {

	public void render() {

		Rect boundary = rectBounds();
		boundary.size.subtract(Vector.all(1));
		Renderer.outline(boundary, Color.RED);
	}

	public Rect rectBounds() {
		return Display.rectBounds();
	}

	public Poly polyBounds() {
		return rectBounds().polyBounds();
	}

}
