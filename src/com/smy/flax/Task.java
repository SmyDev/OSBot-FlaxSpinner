package com.smy.flax;

import org.osbot.rs07.script.MethodProvider;

public abstract class Task {
	
	protected MethodProvider api;
	
	public Task(MethodProvider api){
		this.api = api;
	}
	
	public abstract boolean canProcess();
	public abstract void process() throws InterruptedException;
	
	public void run(){
		if(canProcess())
			try {
				process();
			} catch (InterruptedException e) {
				api.log("Error: " + e.toString());
			}
	}
	
}
