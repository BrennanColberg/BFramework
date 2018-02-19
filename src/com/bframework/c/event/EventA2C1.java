package com.bframework.c.event;

public abstract class EventA2C1<C1, A1, A2> extends EventA2C0<A1, A2> {
	
	public C1 condition1;
	
	public EventA2C1(C1 condition1) {
		this.condition1 = condition1;
	}
	
	public void execute(C1 condition1, A1 arg1, A2 arg2) {
		if (this.condition1 == condition1)
			execute(arg1, arg2);
	}
	
	@Deprecated public abstract void execute(A1 arg1, A2 arg2);
	
}
