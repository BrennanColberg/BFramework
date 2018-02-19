package com.bframework.c.entities;

import com.bframework.c.graphics.Poly;
import com.bframework.c.graphics.Rect;
import com.bframework.c.math.Vector;

// A universal class which ensures the presence of an accessible position

public abstract class Entity implements Cloneable {

	public static final Vector VECTOR_POSITION_DEFAULT = Vector.all(0);

	
	/* VARIABLES */
	public Vector position = Vector.all(0);

	public Rect rectBounds() {
		return null;
	}

	public Poly polyBounds() {
		return null;
	}

	
	/* CONSTRUCTOR */
	public Entity(Vector position) {
		position = position != null ? position : VECTOR_POSITION_DEFAULT;
	}

	public Entity() {
		this(null);
	}
	
	
	/* METHODS */
	
	public boolean intersects(Entity entity) {
		return this.rectBounds().intersects(entity.rectBounds());
	}
	public boolean encapsulates(Entity entity) {
		return this.rectBounds().encapsulates(entity.rectBounds());
	}
}
