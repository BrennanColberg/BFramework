package com.bframework.c.core;

import java.util.ArrayList;

import com.bframework.c.graphics.Camera;
import com.bframework.c.graphics.Poly;
import com.bframework.c.graphics.Rect;
import com.bframework.c.graphics.Renderable;

public abstract class State implements Tickable, Renderable {

	public static boolean DEFAULT_RESETTING = false;
	
	public boolean transitionedInto = false;

	/* CONSTRUCTORS */
	
	public State() {
		this(DEFAULT_RESETTING, null);
	}

	public State(Camera camera) {
		this(DEFAULT_RESETTING, camera);
	}

	public State(boolean resetting) {
		this(resetting, null);
	}

	public State(boolean resetting, Camera camera) {
		resetting(resetting);
		if (camera != null)
			camera(camera);
		else
			camera(new Camera());
		
		// easy starting - simply declare a State to run the game
		if (!Control.running) Control.run(this);
	}

	/* VARIABLES */
	private ArrayList<Object> objects = new ArrayList<Object>();

	public ArrayList<Object> objects() {
		return objects;
	}

	public State objects(ArrayList<Object> value) {
		this.objects = value;
		return this;
	}

	public Object object(int index) {
		return objects().get(index);
	}
	
	public State object(int index, Object value) {
		objects().set(index, value);
		return this;
	}
	
	public void add(Object object) {
		objects.add(object);
	}

	public void remove(Object object) {
		try { objects.remove(object); }
		catch (Exception e) { e.printStackTrace(); }
	}

	public boolean contains(Object object) {
		return objects().contains(object);
	}
	
	private Camera camera;

	public Camera camera() {
		return camera;
	}

	public void camera(Camera camera) {
		this.camera = camera;
	}

	// determines if resetting every time it's made
	private boolean resetting;

	public boolean resetting() {
		return resetting;
	}

	public void resetting(boolean bool) {
		this.resetting = bool;
	}

	protected int ticks = 0;
	public void tick() {
		for (int i = objects().size() - 1; i > 0; i--) {
			try { if (objects.get(i) instanceof Tickable) ((Tickable) objects.get(i)).tick(); }
			catch (NullPointerException e) { }
			catch (ClassCastException e) { }
			catch (IndexOutOfBoundsException e) { }
		}
		camera.tick();
		ticks++;
		if (ticks % 10 == 0) Display.canvas.requestFocus();
	}

	public void render() {
		for (int i = objects().size() - 1; i >= 0; i--) {
			try { if (objects.get(i) instanceof Renderable) ((Renderable) objects.get(i)).render(); }
			catch (NullPointerException e) { }
			catch (ClassCastException e) { }
			catch (IndexOutOfBoundsException e) { }
		}
	}

	public abstract void init();

	public abstract void focus();

	public void unfocus() {
		transitionedInto = false;
	}
	
	public void reset() {
		for (int i = 0; i < objects.size(); i++)
			remove(objects.get(i));
		init();
	}

	/* GETTERS */
	public Rect rectBounds() {
		return Display.rectBounds();
	}

	public Poly polyBounds() {
		return rectBounds().polyBounds();
	}
}