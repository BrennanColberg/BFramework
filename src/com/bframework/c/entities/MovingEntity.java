package com.bframework.c.entities;

import com.bframework.c.core.Tickable;
import com.bframework.c.math.Vector;

// Adds a velocity and acceleration to an Entity, as well as a default way for updating and using these

public class MovingEntity extends Entity implements Tickable {

	
	/* VARIABLES */
	
	public Vector velocity = new Vector(1 / Math.sqrt(Double.MAX_VALUE), 0);

	public Vector acceleration = Vector.all(0);
	
	
	/* GETTERS & SETTERS */
	
	public double heading() {
		return velocity.angle();
	}
	public void heading(double value) {
		velocity.angle(value);
	}
	public void rotate(double value) {
		velocity.rotate(value);
	}
	
	
	/* CONSTRUCTORS */
	
	public MovingEntity(Vector position) {
		super(position);
	}

	
	/* METHODS */
	
	public void tick() {
		physicsUpdate();
	}
	
	protected void physicsUpdate() {
		velocity.add(acceleration);
		position.add(velocity);
	}
	
}