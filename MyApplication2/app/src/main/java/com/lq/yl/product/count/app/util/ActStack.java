package com.lq.yl.product.count.app.util;

import android.app.Activity;

import com.lq.yl.product.count.app.BaseAct;

import java.util.Stack;

public class ActStack {
	private Stack<BaseAct> mStack;
	private static ActStack INSTANCE;

	private ActStack() {
		this.mStack = new Stack<BaseAct>();
	}

	public static ActStack getInstance() {
		if (INSTANCE == null) {
			synchronized (ActStack.class) {
				if (INSTANCE == null) {
					INSTANCE = new ActStack();
				}
			}
		}
		return INSTANCE;
	}

	public void pop(Activity activity) {
		if (activity != null) {
			this.mStack.remove(activity);
		}
	}

	public void push(BaseAct activity) {
		this.mStack.add(activity);
	}

	public boolean has() {
		return this.mStack.size() > 0;
	}

	public void exit() {
		for (int i = 0; i < this.mStack.size(); i++) {
			Activity act = this.mStack.get(i);
			this.mStack.remove(i);
			i--;
			if (act != null) {
				act.finish();
				act = null;
			}
		}
	}
}
