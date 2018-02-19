package com.bframework.c.event;

public abstract class EventA0C1<C1> extends EventA0C0 {
	
	public C1 condition1;
	
	public EventA0C1(C1 condition1) {
		this.condition1 = condition1;
	}
	
	public void execute(C1 condition1) {
		if (this.condition1 == condition1)
			execute();
	}
	
	@Deprecated public abstract void execute();
	
}
