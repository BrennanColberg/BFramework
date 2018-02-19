package com.bframework.c.graphics.debug;

import java.awt.Color;

import com.bframework.c.entities.Entity;
import com.bframework.c.graphics.Poly;
import com.bframework.c.graphics.Rect;
import com.bframework.c.graphics.Renderable;
import com.bframework.c.graphics.Renderer;
import com.bframework.c.math.Vector;

public final class PointOutline extends Entity implements Renderable {

	/* VARIABLES */
	private double diameter;

	public double diameter() {
		return diameter;
	}

	public void diameter(double value) {
		this.diameter = value;
	}

	/* CONSTRUCTOR */
	public PointOutline(Vector position, double diameter) {
		super(position);
		diameter(diameter);
	}

	/* ACTIONS */
	public void render() {
		Renderer.outline(rectBounds(), Color.GREEN);
	}

	public Rect rectBounds() {
		return new Rect(position, Vector.all(diameter()));
	}

	public Poly polyBounds() {
		return rectBounds().polyBounds();
	}
}
