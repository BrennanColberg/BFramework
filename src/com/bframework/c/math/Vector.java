package com.bframework.c.math;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import com.bframework.c.graphics.Rect;

public final class Vector {
	public static final int DIMENSIONS = 2; // two dimensions for now ... TODO
											// make compatible with more
	public double[] value;

	/* CONSTRUCTORS */
	public Vector(double... values) { // constructor from array
		this.value = values;
	}

	public Vector(Vector vector) { // easy clone for unattached, modifiable copy
		this(new double[] { vector.value[0], vector.value[1] }); // more for
	}

	public Vector(Point point) {
		this(point.x, point.y);
	}
	
	public Vector(Rectangle2D rectangle) {
		this(rectangle.getX(), rectangle.getY());
	}
	
	public Vector() { // blank vector
		this(new double[] { 0d, 0d }); // two zeros -> 2d game for now ... TODO
										// make more if dimensions added
	}

	/* INSTANCE-BASED OPERATIONS */
	/**
	 * This method adds each provided Vector to the targeted Vector
	 * 
	 * @param vectors
	 */
	public Vector add(Vector... addends) {
		for (int i = 0; i < DIMENSIONS; i++)
			for (int v = 0; v < addends.length; v++)
				value[i] += addends[v].value[i];
		return this;
	}

	public Vector plus(Vector... addends) {
		return new Vector(this).add(addends);
	}

	/**
	 * This method subtracts each provided Vector from the targeted Vector
	 * 
	 * @param vectors
	 */
	public Vector subtract(Vector... subtrahends) {
		for (int i = 0; i < DIMENSIONS; i++)
			for (int v = 0; v < subtrahends.length; v++)
				value[i] -= subtrahends[v].value[i];
		return this;
	}

	public Vector minus(Vector... subtrahends) {
		return new Vector(this).subtract(subtrahends);
	}

	/**
	 * This method multiplies the targeted Vector by each provided Vector
	 * 
	 * @param vectors
	 */
	public Vector multiply(Vector... factors) {
		for (int i = 0; i < DIMENSIONS; i++)
			for (int v = 0; v < factors.length; v++)
				value[i] *= factors[v].value[i];
		return this;
	}

	public Vector times(Vector... factors) {
		return new Vector(this).multiply(factors);
	}

	public Vector multiply(double... factors) {
		for (int i = 0; i < DIMENSIONS; i++)
			for (int f = 0; f < factors.length; f++)
				value[i] *= factors[f];
		return this;
	}

	public Vector times(double... factors) {
		return new Vector(this).multiply(factors);
	}

	/**
	 * This method divides the targeted Vector by each provided Vector
	 * 
	 * @param vectors
	 */
	public Vector divide(Vector... divisors) {
		for (int i = 0; i < DIMENSIONS; i++)
			for (int v = 0; v < divisors.length; v++)
				value[i] /= divisors[v].value[i];
		return this;
	}

	public Vector over(Vector... divisors) {
		return new Vector(this).divide(divisors);
	}

	public Vector divide(double... divisors) {
		for (int i = 0; i < DIMENSIONS; i++)
			for (int d = 0; d < divisors.length; d++)
				value[i] /= divisors[d];
		return this;
	}

	public Vector over(double... divisors) {
		return new Vector(this).divide(divisors);
	}

	/**
	 * This method returns the algebraic dot product of the targeted Vector and
	 * the provided Vector
	 * 
	 * @param factor
	 * @return
	 */
	public double dot(Vector factor) {
		return dot(this, factor);
	}

	public Vector reflect(int... axes) {
		for (int i = 0; i < axes.length; i++)
			value[axes[i]] = value[axes[i]] * -1;
		return this;
	}

	public Vector reflectNew(int... axes) {
		return new Vector(this).reflect(axes);
	}

	/* STATIC OPERATIONS */
	/**
	 * This method returns the sum of all provided Vectors
	 * 
	 * @param addends
	 * @return
	 */
	public static Vector sum(Vector... addends) {
		Vector result = new Vector();
		for (int v = 0; v < addends.length; v++)
			result.add(addends[v]);
		return result;
	}

	/**
	 * This method returns the product of all provided Vectors
	 * 
	 * @param vectors
	 * @return
	 */
	public static Vector product(Vector... factors) {
		Vector result = new Vector();
		for (int v = 0; v < factors.length; v++)
			result.multiply(factors[v]);
		return result;
	}

	/**
	 * This method returns the algebraic dot product of the two provided Vectors
	 * 
	 * @param factors
	 */
	public static double dot(Vector... factors) {
		double result = 0;
		for (int i = 0; i < DIMENSIONS; i++)
			result += factors[0].value[i] * factors[1].value[i];
		return result;
	}

	/**
	 * This method returns the algebraic magnitude, or geometric hypotenuse, of
	 * the targeted Vector
	 * 
	 * @return
	 */
	public double magnitude() {
		double result = 0;
		for (int i = 0; i < DIMENSIONS; i++)
			result += Math.pow(value[i], 2);
		return Math.sqrt(result);
	}

	public Vector magnitude(double in) {
		double ratio = in / magnitude();
		for (int i = 0; i < DIMENSIONS; i++)
			value[i] *= ratio;
		return this;
	}

	public Vector restrictMagnitude(double min, double max) {
		return this.magnitude(BMethods.restrict(magnitude(), Math.max(min, 0), max));
	}
	
	public double angle() {
		return 
			value[0] == 0 && value[1] == 0 ? 0 :
			Math.atan(value[1] / value[0]) - (value[0] <= 0 ? Math.PI : 0);
	}

	public Vector angle(double angle) {
		double magnitude = magnitude();
		value[0] = Math.cos(angle) * magnitude;
		value[1] = Math.sin(angle) * magnitude;
		return this;
	}

	public Vector angleNew(double angle) {
		return new Vector(this).angle(angle);
	}
	
	public Vector rotate(double angle) {
		angle(angle() + angle);
		return this;
	}

	public Vector rotateNew(double angle) {
		return new Vector(this).rotate(angle);
	}
	
	/* GENERATORS / CONVERTERS */
	// from
	public static Vector from(Point point) {
		return new Vector(point.x, point.y);
	}

	public static Vector from(Dimension dimension) {
		return new Vector(dimension.width, dimension.height);
	}

	public static Vector all(double value) {
		double[] values = new double[DIMENSIONS];
		for (int i = 0; i < DIMENSIONS; i++) {
			values[i] = value;
		}
		return new Vector(values);
	}

	public static Vector randomWithin(Rect bounds) {
		Random rnd = new Random();
		double x = bounds.position.value[0] + bounds.size.value[0] * (rnd.nextDouble() - 0.5);
		double y = bounds.position.value[1] + bounds.size.value[1] * (rnd.nextDouble() - 0.5);
		return new Vector(x, y);
	}

	// to
	public Point point() {
		return new Point((int) value[0], (int) value[1]);
	}

	public Dimension dimension() {
		return new Dimension((int) value[0], (int) value[1]);
	}

	// string
	public String toString() {
		return String.format("(%s, %s)", value[0], value[1]); // TODO update if
																// dimensions
																// added
	}
}