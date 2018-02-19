package com.bframework.c.event;

public abstract class EventA1C2<C1, C2, A1> extends EventA1C0<A1> {
	
	public C1 condition1;
	public C2 condition2;
	
	public EventA1C2(C1 condition1, C2 condition2) {
		this.condition1 = condition1;
		this.condition2 = condition2;
	}
	
	public void execute(C1 condition1, C2 condition2, A1 arg1) {
		if (this.condition1 == condition1 && this.condition2 == condition2)
			execute(arg1);
	}
	
	@Deprecated public abstract void execute(A1 arg1);
	
}
