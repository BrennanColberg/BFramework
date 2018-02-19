package com.bframework.c.event;

public abstract class EventA2C2<C1, C2, A1, A2> extends EventA2C0<A1, A2> {
	
	public C1 condition1;
	public C2 condition2;
	
	public EventA2C2(C1 condition1, C2 condition2) {
		this.condition1 = condition1;
		this.condition2 = condition2;
	}
	
	public void execute(C1 condition1, C2 condition2, A1 arg1, A2 arg2) {
		if (this.condition1 == condition1 && this.condition2 == condition2)
			execute(arg1, arg2);
	}
	
	@Deprecated public abstract void execute(A1 arg1, A2 arg2);
	
}
