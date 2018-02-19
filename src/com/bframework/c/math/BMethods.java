package com.bframework.c.math;

public abstract class BMethods {
	public static boolean between(double value, double lower, double upper) {
		return value >= lower && value <= upper;
	}

	public static double map(double value, double min, double max, double newMin, double newMax) {
		return ((value - min) / max) * newMax + newMin;
	}

	public static double restrict(double value, double min, double max) {
		return Math.max(min, Math.min(max, value));
	}
}
