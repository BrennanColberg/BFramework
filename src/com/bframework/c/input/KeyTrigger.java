package com.bframework.c.input;

import com.bframework.c.core.Control;
import com.bframework.c.core.State;
import com.bframework.c.event.EventA0C3;

public abstract class KeyTrigger extends EventA0C3<State, Integer, InputState> {

	public KeyTrigger(Integer key, InputState pressed) {
		this(Control.currentState, key, pressed);
	}
	public KeyTrigger(State state, Integer key, InputState pressed) {
		super(state, key, pressed);
	}

}
