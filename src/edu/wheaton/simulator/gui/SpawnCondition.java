package edu.wheaton.simulator.gui;

import edu.wheaton.simulator.entity.Prototype;

public class SpawnCondition {

	public final Prototype prototype; 
	
	public final int x; 
	
	public final int y; 
	
	public final String pattern; 
	
	public final int number; 
	
	public SpawnCondition(Prototype prototype, int x, int y, int number, String pattern) { 
		this.pattern = pattern; 
		this.prototype = prototype;
		this.x = x; 
		this.y = y;
		this.number = number; 
	}
	
}
