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

			for (int i = 0; i < number; i++) {
				facade.getGrid().spiralSpawn(prototype.createAgent(), x, y);
			}
		}
		else if (pattern.equals("Random")) {
			for (int i = 0; i < number; i++) {
				facade.getGrid().spiralSpawn(prototype.createAgent());
			}
		}
		else if (pattern.equals("Horizontal")) {
			for (int i = 0; i < number; i++) {
				//Horizontal line spawn
			}
		}
		else if (pattern.equals("Vertical")) {
			for (int i = 0; i < number; i++) {
				//vertical line spawn
			}
		}

	}

}
