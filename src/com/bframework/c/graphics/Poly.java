package com.bframework.c.graphics;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import com.bframework.c.entities.Entity;
import com.bframework.c.graphics.debug.PointOutline;
import com.bframework.c.math.Vector;

public class Poly extends Entity implements Renderable {

	
	/* VARIABLES */
	
	private ArrayList<Vector> vertices = new ArrayList<Vector>();

	
	/* GETTERS & SETTERS */
	
	// vertices
	
	public ArrayList<Vector> vertices() {
		return vertices;
	}
	public void vertices(ArrayList<Vector> list_vector) {
		this.vertices = list_vector;
	}
	
	public Vector vertex(int index) {
		return vertices().get(index);
	}
	public void vertex(int index, Vector vector) {
		vertices().set(index, vector);
	}

	public Poly addVertex(Vector vector) {
		vertices().add(vector);
		return this;
	}
	public Poly removeVertex(int index) {
		vertices().remove(index);
		return this;
	}
	public Poly removeVertex(Vector vector) {
		vertices().remove(vector);
		return this;
	}
	
	// Polygon
	
	public Polygon drawablePolygon() {
		int points = vertices.size();
		int[] xp = new int[points];
		int[] yp = new int[points];

		for (int i = 0; i < points; i++) {
			Vector point = vertex(i).plus(position);
			xp[i] = (int) (point.value[0]);
			yp[i] = (int) (point.value[1]);
		}

		return new Polygon(xp, yp, points);
	}

	// tostring
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < vertices.size(); i++)
			sb.append(String.format("v%s: %s ", i, vertex(i)));
		return sb.toString();
	}
	
	// bounds
	
	public Rect rectBounds() {
		double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
		for (int i = 0; i < vertices().size(); i++) {
			Vector point = vertex(i);
			minX = Math.min(minX, point.value[0]);
			maxX = Math.max(maxX, point.value[0]);
			minY = Math.min(minY, point.value[1]);
			maxY = Math.max(maxY, point.value[1]);
		}
		double avgX = (maxX + minX) / 2, avgY = (maxY + minY) / 2;
		double diffX = Math.abs(maxX - minX), diffY = Math.abs(maxY - minY);
		return new Rect(position.plus(new Vector(avgX, avgY)), new Vector(diffX, diffY));
	}
	public Poly polyBounds() {
		return this;
	}
	
	
	/* CONSTRUCTORS */

	public Poly() {
		this(new ArrayList<Vector>());
	}

	public Poly(ArrayList<Vector> vertices) {
		vertices(vertices);
		position = Vector.all(0);
	}
	public Poly(Vector... vertices) {
		ArrayList<Vector> list = new ArrayList<Vector>();
		for (Vector vertex : vertices)
			list.add(vertex);
		vertices(list);
		position = Vector.all(0);
	}

	public Poly(Poly poly) {
		this(new ArrayList<Vector>());
		position = new Vector(poly.position);
		for (int i = 0; i < poly.vertices().size(); i++) {
			this.addVertex(new Vector(poly.vertex(i)));
		}
	}

	public Poly(int points, double radius) {
		double angleIncrement = Math.PI * 2 / (double) points;
		for (int i = 0; i < points; i++) {
			addVertex(new Vector(radius, 0).angle(i * angleIncrement));
		}
		position = Vector.all(0);
	}

	
	/* TRANSFORMATIONS */

	// scale
	
	public Poly scale(double scale) {
		for (Vector vertex : vertices())
			vertex.multiply(scale);
		return this;
	}
	public Poly scaleNew(double scale) {
		return new Poly(this).scale(scale);
	}
	
	// rotate

	public Poly rotate(double angle) {
		for (Vector vertex : vertices())
			vertex.rotate(angle);
		return this;
	}
	public Poly rotateNew(double angle) {
		return new Poly(this).rotate(angle);
	}

	
	/* CALCULATIONS */
	
	public boolean contains(Point value) {
		return this.drawablePolygon().contains(value);
	}
	public boolean contains(Vector value) {
		return this.drawablePolygon().contains(value.point());
	}
	
	public boolean intersects(Poly poly) {
		Polygon thisBounds = this.drawablePolygon();
		for (int i = 0; i < poly.vertices().size(); i++)
			if (thisBounds.contains(poly.vertex(i).point()))
				return true;
		Polygon polyBounds = poly.drawablePolygon();
		for (int i = 0; i < this.vertices().size(); i++)
			if (polyBounds.contains(this.vertex(i).point()))
				return true;
		return false;
	}
	public boolean intersects(Rect rect) {
		return this.intersects(rect.polyBounds());
	}
	
	public boolean encapsulates(Poly poly) {
		Polygon thisBounds = this.drawablePolygon();
		for (int i = 0; i < poly.vertices().size(); i++)
			if (!thisBounds.contains(poly.vertex(i).point()))
				return false;
		return true;
	}
	public boolean encapsulates(Rect rect) {
		return this.encapsulates(rect.polyBounds());
	}
	
	/* METHODS */

	public void render() {
		Renderer.fill(this, Color.magenta);
		Renderer.outline(rectBounds(), Color.blue);
		new PointOutline(this.position, 1.0).render();
	}
	
}
