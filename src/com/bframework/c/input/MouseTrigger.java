package com.bframework.c.input;

import com.bframework.c.core.Control;
import com.bframework.c.core.State;
import com.bframework.c.entities.Entity;
import com.bframework.c.event.EventA0C3;

public abstract class MouseTrigger extends EventA0C3<State, Entity, InputState> {

	public MouseTrigger(Entity condition2, InputState condition3) {
		super(Control.currentState, condition2, condition3);
	}
	public MouseTrigger(State condition1, Entity condition2, InputState condition3) {
		super(condition1, condition2, condition3);
	}
	
	public void execute(State condition1, Entity condition2, InputState condition3) {
		execute(condition1, condition3);
	}
	@SuppressWarnings("deprecation")
	public void execute(State condition1, InputState condition3) {
		if (this.condition1 == condition1 && this.condition3 == condition3 && this.condition2.rectBounds().contains(Input.mousePosition()))
			execute();
	}

}
