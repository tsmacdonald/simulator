package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.GridEntity;
import edu.wheaton.simulator.simulation.Grid;

public interface Behavable {

	public void act(GridEntity me, GridEntity other, GridEntity local, Grid global);
}
