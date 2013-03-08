package edu.wheaton.simulator.datastructures.expression;

import edu.wheaton.simulator.datastructures.Primitive;
import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.simulation.Grid;

public interface PrimitiveEvaluatable {
	
	public Primitive evaluate(GridEntity me, GridEntity other, GridEntity local, Grid global);
}
