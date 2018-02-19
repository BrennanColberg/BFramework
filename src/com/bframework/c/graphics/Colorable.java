package com.bframework.c.graphics;

import java.awt.Color;

public interface Colorable {
	public void render();

	public void render(Color color);

	public Rect rectBounds();

	public Poly polyBounds();
}
