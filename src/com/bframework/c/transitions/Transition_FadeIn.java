package com.bframework.c.transitions;

import java.awt.Color;

import com.bframework.c.core.Display;
import com.bframework.c.core.State;
import com.bframework.c.graphics.BColor;
import com.bframework.c.graphics.Renderer;

public class Transition_FadeIn extends Transition {

	protected Color color;
	
	public Transition_FadeIn(State underneath, double durationSeconds, Color color) {
		super(underneath, durationSeconds);
		this.color = color;
	}

	public void overlayRender() {
		Renderer.fill(Display.rectBounds(), BColor.alpha(color, alpha));
	}
}
