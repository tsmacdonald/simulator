package edu.wheaton.simulator.gui;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.GUIToAgentFacade;

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
	
	public void addToGrid(GUIToAgentFacade facade) {
		if (pattern.equals("Clustered")) {
			facade.getGrid().spawnAgent(prototype.clonePrototype(), x, y);
		}
		else facade.getGrid().spawnAgent(prototype.clonePrototype());
	}
	
}
