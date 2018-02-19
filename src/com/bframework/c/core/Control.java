package com.bframework.c.core;

import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import com.bframework.c.graphics.Camera;
import com.bframework.c.graphics.Renderer;
import com.bframework.c.input.ControlInput;
import com.bframework.c.math.Vector;
import com.bframework.c.sprites.core.SpriteIO;

public abstract class Control { // not going to comment this much, SIMPLY DON'T
								// TOUCH THE MAGIC

	public static final Vector DEFAULT_SCREEN_SIZE = new Vector(640, 480);

	public static Stack<State> stateStack = new Stack<State>();
	public static State currentState = null;
	public static State lastState = null;
	public static ArrayList<State> initiatedStates = new ArrayList<State>();

	public static Thread renderer = new Thread() {
		public void run() {
			Control.startRendering();
		}
	};
	public static Thread ticker = new Thread() {
		public void run() {
			Control.startTicking();
		}
	};

	public static boolean running = false;
	public static boolean rendering = true;
	public static boolean ticking = true;

	protected static final double DEFAULT_TPS = 100d, DEFAULT_FPS = 100d;
	protected static double renderFrequency, renderPeriod, renderMessagePeriod = 1d;
	protected static double tickFrequency, tickPeriod, tickMessagePeriod = 1d;

	public static void run(State startingState) {
		run(DEFAULT_TPS, DEFAULT_FPS, startingState);
	}

	public static void run(double tps, double fps, State startingState) {
		
		running = true;
		
		Resources.load();
		SpriteIO.load();
		
		TPS(tps);
		FPS(fps);
		stateStack.add(startingState);
		currentState = startingState;

		// Settings.init(); TODO make settings, link to display
		
		Display.init(DEFAULT_SCREEN_SIZE); // TODO make variable with settings
		
		renderer.start();
		ticker.start();
	}

	public static void startRendering() {

		// setting all to something - starting time because nothing better, will
		// soon be overriden but can't be null
		long long_renderCycleStart = System.currentTimeMillis();
		long long_renderLast = long_renderCycleStart, long_renderMessageLast = long_renderCycleStart;

		// counter used for message broadcast
		int int_renderOccurences = 0;

		while (Control.running && Control.rendering) {

			// standardize time for entire cycle
			long_renderCycleStart = System.currentTimeMillis();

			// if delay since last call of render() is greater than period, call
			// and subtract period
			if (long_renderCycleStart - long_renderLast >= renderPeriod * 1000d) { // converts
																					// period
																					// from
																					// SECOND
																					// to
																					// MILLISECOND
				render();
				int_renderOccurences += 1;
				long_renderLast += renderPeriod * 1000d;
			}

			// if delay since last broadcast of update is greater than one
			// second, broadcast
			if (long_renderCycleStart - long_renderMessageLast >= renderMessagePeriod * 1000d) {
				System.out.println(String.format("! Rendered %s times in %s milliseconds", int_renderOccurences,
						long_renderCycleStart - long_renderMessageLast));
				int_renderOccurences = 0;
				long_renderMessageLast += renderMessagePeriod * 1000d;
			}
		}
	}

	public static void render() {
		BufferStrategy bs = Display.canvas.getBufferStrategy();
		if (bs == null) {
			Display.canvas.createBufferStrategy(2);
			return;
		}
		Renderer.graphics(bs.getDrawGraphics());
		Renderer.updateBackground();
		currentState.render(); // TODO better color management system

		Renderer.graphics().dispose();
		bs.show();
	}

	public static void startTicking() {

		// setting all to something - starting time because nothing better, will
		// soon be overriden but can't be null
		long long_tickCycleStart = System.currentTimeMillis();
		long long_tickLast = long_tickCycleStart, long_tickMessageLast = long_tickCycleStart;

		// counter used for message broadcast
		int int_tickOccurences = 0;

		while (Control.running && Control.ticking) {

			// standardize time for entire cycle
			long_tickCycleStart = System.currentTimeMillis();

			// if delay since last call of tick() is greater than period, call
			// and subtract period
			if (long_tickCycleStart - long_tickLast >= tickPeriod * 1000d) { // converts
																				// period
																				// from
																				// SECOND
																				// to
																				// MILLISECOND
				tick();
				int_tickOccurences += 1;
				long_tickLast += tickPeriod * 1000d;
			}

			// if delay since last broadcast of update is greater than one
			// second, broadcast
			if (long_tickCycleStart - long_tickMessageLast >= tickMessagePeriod * 1000d) {
				System.out.println(String.format("! Ticked %s times in %s milliseconds", int_tickOccurences,
						long_tickCycleStart - long_tickMessageLast));
				int_tickOccurences = 0;
				long_tickMessageLast += tickMessagePeriod * 1000d;
			}
		}
	}

	public static void tick() {

		/* UPDATING CURRENT STATE */
		try {
			currentState = stateStack.peek();
		} catch (EmptyStackException e) {
			System.exit(0);
		}

		if (lastState != currentState) {
			System.out.println(String.format("Switching states from '%s' to '%s", lastState, currentState));
			Resources.stopAll();

			if (lastState != null) lastState.unfocus();
			
			if (!initiatedStates.contains(currentState)) {
				currentState.init();
				currentState.add(new ControlInput());
				initiatedStates.add(currentState);
			} else if (currentState.resetting()) {
//				currentState.reset();
			}

			lastState = currentState;
			
			currentState.focus();
		}
		
		Display.input.tick();
		currentState.tick();
	}

	/* GETTERS */
	public static double TPS() {
		return tickFrequency;
	}

	public static double FPS() {
		return renderFrequency;
	}

	public static Camera camera() {
		return currentState.camera();
	}

	/* SETTERS */
	public static void TPS(double value) {
		tickFrequency = value;
		tickPeriod = 1d / value;
	}

	public static void FPS(double value) {
		renderFrequency = value;
		renderPeriod = 1d / value;
	}
}