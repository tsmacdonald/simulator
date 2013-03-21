package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;

public class Die implements Behavable {

	
	public Die() {
	}
	
	@Override
	public void act(Agent target) {
		target.die();
		
	}

}
