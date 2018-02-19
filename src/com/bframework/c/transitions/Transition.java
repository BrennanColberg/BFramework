package com.bframework.c.transitions;

import com.bframework.c.core.Control;
import com.bframework.c.core.State;

public abstract class Transition extends State {

	protected State underneath;
	protected double durationTicks;
	protected double ticksPassed;
	protected int alpha;
	
	public Transition(State underneath, double durationSeconds) {
		this.underneath = underneath;
		this.durationTicks = durationSeconds * Control.TPS();
	}
	
	public void tick() {
		if (underneath.transitionedInto) Control.stateStack.pop();
		ticksPassed += 1;
		alpha = (int) ((1 - ticksPassed / durationTicks) * 255);
		if (ticksPassed >= durationTicks) {
			underneath.transitionedInto = true;
			Control.stateStack.pop();
		}
	}
	
	public void render() {
		underneath.render();
		overlayRender();
	}
	
	public abstract void overlayRender();

	public void init() {
		
	}

	public void focus() {
		
	}

	public void unfocus() {
		
	}
}
