package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;

public interface Behavior {

	public void execute(Agent target) throws Exception;

}
