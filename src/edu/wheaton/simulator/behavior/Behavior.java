package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;

public interface Behavior {

	public void act(Agent target) throws Exception;

}
