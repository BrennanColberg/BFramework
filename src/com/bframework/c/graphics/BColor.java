package com.bframework.c.graphics;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import com.bframework.c.math.BMethods;

public abstract class BColor {
	
	public static Color random() {
		Random rnd = new Random();
		return new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
	}
	
	public static Color all(float value) {
		return new Color(value,value,value);
	}
	public static Color all(int value) {
		return new Color(value,value,value);
	}
	
	public static Color alpha(Color color, int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) BMethods.restrict(alpha, 0, 255));
	}
	
	public static Color average(Color...colors) {
		int r=0, g=0, b=0, a=0, c = colors.length;
		for (Color color:colors) {
			r += color.getRed();
			g += color.getGreen();
			b += color.getBlue();
			a += color.getAlpha();
		}
		return new Color(r/c, g/c, b/c, a/c);
	}
	
	public static boolean printShadeUpdates = false;
	private static HashMap<String,Color> shadeLibrary = new HashMap<String,Color>();
	public static Color shade(Color color, double shade) {
		String key = String.format("r%sg%sb%s@%s", color.getRed(), color.getGreen(), color.getBlue(), shade);
		if (shadeLibrary.containsKey(key)) return BColor.alpha(shadeLibrary.get(key), color.getAlpha());
		else {
			double meanShade = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
			double ratio = shade / meanShade;
			shadeLibrary.put(key, new Color(
					(int) BMethods.restrict(color.getRed() * ratio, 0, 255), 
					(int) BMethods.restrict(color.getGreen() * ratio, 0, 255), 
					(int) BMethods.restrict(color.getBlue() * ratio, 0, 255)));
			if (printShadeUpdates) System.out.println("calculated " + key);
			return shade(color, shade);
		}
	}
	
	public static Color setValue(Color color, int index, double value) {
		float[] values = color.getComponents(null);
		values[index] = (float) BMethods.restrict(value / 255f, 0, 1);
		return new Color(values[0],values[1],values[2],values[3]);
	}
	public static Color incrementValue(Color color, int index, double value) {
		float[] values = color.getComponents(null);
		values[index] = (float) BMethods.restrict(values[index] + value / 255f, 0, 1);
		return new Color(values[0],values[1],values[2],values[3]);
	}
}
