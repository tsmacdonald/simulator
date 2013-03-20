package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;

public class Die implements Behavable {

	private Agent target;
	
	public Die(Agent target) {
		this.target = target;
	}
	
	@Override
	public void act() {
		target.die();
		
	}

}
