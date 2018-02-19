package com.bframework.c.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bframework.c.core.Control;
import com.bframework.c.core.Display;
import com.bframework.c.math.Vector;

public final class Renderer {
	
	
	/* VARIABLES */

	private static Graphics graphics = null;
	
	private static Color backgroundColor = Color.lightGray;
	
	private static Rect adjustedScreen = new Rect(Display.rectBounds());
	
	
	/* GETTERS / SETTERS */
	
	// graphics
	
	public static Graphics graphics() {
		return graphics;
	}
	
	public static void graphics(Graphics value) {
		Renderer.graphics = value;
	}
	
	// drawing color [of graphics]
	
	public static Color drawingColor() {
		return graphics.getColor();
	}

	public static void drawingColor(Color color) {
		if (color == null)
			return;
		else
			graphics.setColor(color);
	}
	
	// backgroundColor
	
	public static Color backgroundColor() {
		return backgroundColor;
	}

	public static void backgroundColor(Color color) {
		if (color == null)
			return;
		else
			Renderer.backgroundColor = color;
	}

	// adjusted screen
	
	public static Rect adjustedScreen() {
		return adjustedScreen;
	}
	
	
	/* ADJUSTING */
	
	public static boolean inBounds(Renderable renderee) {
		Rect rawScreen = new Rect(Display.rectBounds());
		Rect positionedScreen = (Rect) new Rect(rawScreen);
		positionedScreen.position = Control.camera().position;
		Rect scaledScreen = new Rect(positionedScreen);
		scaledScreen.size = rawScreen.size.over(Control.camera().magnification);
		adjustedScreen.size = scaledScreen.size.minus(Vector.all(1));
		adjustedScreen.position = scaledScreen.position;
		return adjustedScreen.intersects(renderee.rectBounds());
	}

	// vector
	
	public static Vector adjust(Vector point) {
		Vector result = new Vector(point);
		result.subtract(Control.camera().position);
		result.multiply(Control.camera().magnification);
		result.add(Display.center());
		return result;
	}
	public static Vector normalize(Vector point) {
		Vector result = new Vector(point);
		result.subtract(Display.center());
		result.divide(Control.camera().magnification);
		result.add(Control.camera().position);
		return result;
	}
	public static Vector adjustRaw(Vector point) {
		Vector result = new Vector(point);
		result.add(Display.center());
		return result;
	}
	public static Vector normalizeRaw(Vector point) {
		Vector result = new Vector(point);
		result.subtract(Display.center());
		return result;
	}
	
	// rect
	
	public static Rect adjust(Rect rect) {
		Rect result = new Rect(rect);
		result.position = adjust(rect.position);
		result.size.multiply(Control.camera().magnification);
		return result;
	}
	public static Rect normalize(Rect rect) {
		Rect result = new Rect(rect);
		result.position = normalize(rect.position);
		result.size.divide(Control.camera().magnification);
		return result;
	}
	public static Rect adjustRaw(Rect rect) {
		Rect result = new Rect(rect);
		result.position = adjustRaw(rect.position);
		return result;
	}
	public static Rect normalizeRaw(Rect rect) {
		Rect result = new Rect(rect);
		result.position = normalizeRaw(rect.position);
		return result;
	}
	
	// poly
	
	public static Poly adjust(Poly poly) {
		Poly result = new Poly(poly);
		result.position = adjust(poly.position);
		result.scale(Control.camera().magnification);
		return result;
	}
	public static Poly normalize(Poly poly) {
		Poly result = new Poly(poly);
		result.position = normalize(poly.position);
		result.scale(1 / Control.camera().magnification);
		return result;
	}
	public static Poly adjustRaw(Poly poly) {
		Poly result = new Poly(poly);
		result.position = adjustRaw(poly.position);
		return result;
	}
	public static Poly normalizeRaw(Poly poly) {
		Poly result = new Poly(poly);
		result.position = normalizeRaw(poly.position);
		return result;
	}
	
	/* DRAWING */
	
	// resets background
	public static void updateBackground() {
		drawingColor(backgroundColor());
		graphics.fillRect(0, 0, Display.canvas.getWidth(), Display.canvas.getHeight());
	}
	
	// draws outline of given shape
	public static void outline(Renderable renderee, Color color) {
		if (inBounds(renderee)) {
			drawingColor(color);
			graphics.drawPolygon(adjust(renderee.polyBounds()).drawablePolygon());
		}
	}
	public static void outlineRaw(Renderable renderee, Color color) {
		if (inBounds(renderee)) {
			drawingColor(color);
			graphics.drawPolygon(adjustRaw(renderee.polyBounds()).drawablePolygon());
		}
	}
	
	
	// fills area of given shape
	public static void fill(Renderable renderee, Color color) {
		if (inBounds(renderee)) {
			drawingColor(color);
			graphics.fillPolygon(adjust(renderee.polyBounds()).drawablePolygon());
		}
	}
	public static void fillRaw(Renderable renderee, Color color) {
		if (inBounds(renderee)) {
			drawingColor(color);
			graphics.fillPolygon(adjustRaw(renderee.polyBounds()).drawablePolygon());
		}
	}

	// draws given image
	public static void draw(Graphic graphic) {
		BufferedImage image = graphic.image();
		Rect bounds = adjust(new Rect(new Vector(image.getWidth()/2,image.getHeight()/2), new Vector(image.getWidth(), image.getHeight())).polyBounds()).rectBounds();
		graphics.drawImage(image, (int) bounds.corner(0).value[0], (int) bounds.corner(0).value[1], (int) bounds.corner(2).value[0], (int) bounds.corner(2).value[1], null);
	}

	// text
	public static void write(String text, Vector position, Color color, double size) {
		double screenSize = size * Control.camera().magnification;
		Font screenFont = new Font("Impact", Font.BOLD, (int) screenSize);
		graphics.setFont(screenFont);
		Vector screenPosition = adjust(position).add(new Vector(0, graphics.getFontMetrics().getHeight()).multiply(0.25)).subtract(new Vector(graphics.getFontMetrics().stringWidth(text), 0).multiply(0.35));
		writeRaw(text, screenPosition, color, screenSize);
	}
	public static void writeRaw(String text, Vector position, Color color, double size) {
		graphics.setFont(new Font("Impact", Font.BOLD, (int) size));
		drawingColor(color);
		graphics.drawString(text, (int) position.value[0], (int) position.value[1]);
	}

}
