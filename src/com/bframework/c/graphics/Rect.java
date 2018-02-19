package com.bframework.c.graphics;

import java.awt.Color;
import java.awt.Point;

import com.bframework.c.entities.Entity;
import com.bframework.c.math.Vector;

public final class Rect extends Entity implements Renderable {

	
	/* VARIABLES */
	
	public Vector size; // size in each dimension

	
	/* GETTERS & SETTERS */
	
	// corner generator
	
	public Vector corner(int index) { // 0,1,2,3 ... each corner clockwise from top left
		switch (index) {
		case 0:
			return position.plus(size.times(0.5).reflectNew(0, 1));
		case 1:
			return position.plus(size.times(0.5).reflectNew(1));
		case 2:
			return position.plus(size.times(0.5).reflectNew());
		case 3:
			return position.plus(size.times(0.5).reflectNew(0));
		default:
			return null;
		}
	}
	
	
	/* CONSTRUCTORS */
	
	public Rect(Vector position, Vector size) {
		super(position);
		this.size = size;
	}

	public Rect(Rect rect) {
		this(rect.position, rect.size);
	}

	
	/* METHODS */
	
	public void render() {
		Renderer.outline(this, Color.blue);
	}

	public String toString() {
		return String.format("px: %s, py: %s, sx: %s, sy: %s", position.value[0], position.value[1], size.value[0], size.value[1]);
	}

	
	/* CALCULATIONS */
	
	public boolean contains(Vector vector) {
		boolean[] within = new boolean[2];
		for (int i = 0; i < within.length; i++)
			within[i] = Math.abs(vector.value[i] - position.value[i]) <= size.value[0] * 0.5;
		return within[0] && within[1];
	}

	public boolean contains(Point point) {
		return contains(Vector.from(point));
	}

	public boolean intersects(Rect rect) {
		if (this.contains(rect.corner(0)))
			return true;
		else if (this.contains(rect.corner(1)))
			return true;
		else if (this.contains(rect.corner(2)))
			return true;
		else if (this.contains(rect.corner(3)))
			return true;
		else if (rect.contains(this.corner(0)))
			return true;
		else if (rect.contains(this.corner(2)))
			return true;
		else return rect.contains(this.position);
	}

	public boolean encapsulates(Rect rect) {
		if (!this.contains(rect.corner(0)))
			return false;
		else if (!this.contains(rect.corner(1)))
			return false;
		else if (!this.contains(rect.corner(2)))
			return false;
		else if (!this.contains(rect.corner(3)))
			return false;
		else
			return true;
	}
	
	public boolean intersects(Poly poly) {
		return polyBounds().intersects(poly);
	}
	
	public boolean encapsulates(Poly poly) {
		return polyBounds().encapsulates(poly);
	}

	
	/* REQUIRED BOUNDS CALC */
	
	public Rect rectBounds() {
		return this;
	}

	public Poly polyBounds() {
		Poly result = new Poly();
		result.position = this.position;
		for (int i = 0; i < 4; i++)
			result.addVertex(corner(i).minus(position));
		return result;
	}
}
