package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;

public class DieBehavior implements Behavior {

	public DieBehavior() {
	}

	@Override
	public void act(Agent target) {
		target.die();

	}

}
