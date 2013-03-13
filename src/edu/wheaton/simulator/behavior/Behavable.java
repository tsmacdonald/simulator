package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.simulation.Grid;

public interface Behavable {

	public void act(Entity me, Entity other, Entity local, Grid global);
}
