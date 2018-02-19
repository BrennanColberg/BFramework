package com.bframework.c.event;

public abstract class EventA0C2<C1, C2> extends EventA0C0 {
	
	public C1 condition1;
	public C2 condition2;
	
	public EventA0C2(C1 condition1, C2 condition2) {
		this.condition1 = condition1;
		this.condition2 = condition2;
	}
	
	public void execute(C1 condition1, C2 condition2) {
		if (this.condition1 == condition1 && this.condition2 == condition2)
			execute();
	}
	
	@Deprecated public abstract void execute();
	
}
