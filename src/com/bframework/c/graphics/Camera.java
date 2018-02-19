package com.bframework.c.graphics;

import com.bframework.c.entities.Entity;
import com.bframework.c.entities.MovingEntity;
import com.bframework.c.math.BMethods;
import com.bframework.c.math.Vector;

public final class Camera extends MovingEntity {

	public static final double DEFAULT_MAGNIFICATION = 1d;
	public static final double MIN_MAGNIFICATION = 1 / 5d;
	public static final double MAX_MAGNIFICATION = 5d;

	/* VARIABLES */
	
	public double magnificationTarget = 1;
	
	public double magnificationChange = 0;
	
	public double magnification = 1;
	
	public double zoomRate = 0.05;

	private boolean focused;
	
	public Entity focus;

	/* CONSTRUCTOR */
	
	public Camera() {
		this(null, DEFAULT_MAGNIFICATION);
	}

	public Camera(double magnification) {
		this(null, magnification);
	}

	public Camera(Vector position) {
		this(position, DEFAULT_MAGNIFICATION);
	}

	public Camera(Vector position, double magnification) {
		super(position);
		this.magnification = magnification;
	}

	/* METHODS */
	
	public void tick() {
		
		magnificationTarget = BMethods.restrict(magnificationTarget, MIN_MAGNIFICATION, MAX_MAGNIFICATION);
		
		super.tick();
		
		if (focus != null) {
			position = focus.position;
			focused = true;
		} else if (focused) {
			position = Vector.all(0);
			focused = false;
		}
		
		magnificationChange = (magnificationTarget - magnification) * zoomRate;
		magnification += magnificationChange;
	}

	public Rect rectBounds() {
		return null;
	}
}
