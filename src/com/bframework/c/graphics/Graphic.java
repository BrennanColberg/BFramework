package com.bframework.c.graphics;

import java.awt.image.BufferedImage;

import com.bframework.c.core.Resources;
import com.bframework.c.entities.Entity;
import com.bframework.c.math.Vector;

public class Graphic extends Entity implements Renderable {
	
	
	/* VARIABLES */
	
	private BufferedImage image;
	
	
	/* GETTERS & SETTERS */
	
	// image
	
	public BufferedImage image() {
		return image;
	}
	
	public Graphic image(BufferedImage value) {
		this.image = value;
		return this;
	}
	
	
	/* CONSTRUCTORS */
	
	public Graphic(Vector position, BufferedImage image) {
		super(position);
		image(image);
	}
	
	public Graphic(Vector position, String name) {
		this(position, Resources.image(name));
	}
	
	
	/* METHODS */
	
	public void render() {
		Renderer.draw(this);
	}
	
	
	/* BOUNDS CALC */
	
	public Rect rectBounds() {
		return null;
	}

}
