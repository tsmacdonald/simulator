package edu.wheaton.simulator.datastructures;

import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.gridentities.Primitive;
import edu.wheaton.simulator.simulation.Grid;

public class PrimitiveNode implements PrimitiveEvaluatable {

	private Primitive value;
	
	public PrimitiveNode(Primitive value) {
		this.value = value;
	}


	@Override
	public Primitive evaluate(GridEntity me, GridEntity other,
			GridEntity local, Grid global) {
		return value;
	}
}
