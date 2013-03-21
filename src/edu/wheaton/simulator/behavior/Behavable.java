package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;



public interface Behavable {

	public void act(Agent target) throws Exception;
	
}
