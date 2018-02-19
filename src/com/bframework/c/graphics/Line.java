package com.bframework.c.graphics;

import java.awt.Color;

import com.bframework.c.entities.Entity;
import com.bframework.c.math.Vector;

public class Line extends Entity implements Renderable, Colorable {

	public Vector point;

	public Line(Vector position, Vector point) {
		super(position);
		this.point = point;
	}

	public void render() {
		Renderer.outline(this, null);
	}

	public void render(Color color) {
		Renderer.outline(this, color);
	}

	public Rect rectBounds() {
		return polyBounds().rectBounds();
	}

	public Poly polyBounds() {
		Poly result = new Poly(Vector.all(0), point);
		result.position = this.position;
		return result;
	}
}
