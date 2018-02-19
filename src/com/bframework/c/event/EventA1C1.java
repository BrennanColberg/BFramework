package com.bframework.c.event;

public abstract class EventA1C1<C1, A1> extends EventA1C0<A1> {
	
	public C1 condition1;
	
	public EventA1C1(C1 condition1) {
		this.condition1 = condition1;
	}
	
	public void execute(C1 condition1, A1 arg1) {
		if (this.condition1 == condition1)
			execute(arg1);
	}
	
	@Deprecated public abstract void execute(A1 arg1);
	
}
