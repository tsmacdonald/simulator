package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.simulation.Grid;

public interface Behavable {

	public void act(GridEntity me, GridEntity other, GridEntity local, Grid global);
}