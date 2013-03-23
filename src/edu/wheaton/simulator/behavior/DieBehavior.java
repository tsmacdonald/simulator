package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;

public class DieBehavior implements Behavior {

	public DieBehavior() {
	}

	@Override
	public void execute(Agent target) {
		target.die();

	}

}
