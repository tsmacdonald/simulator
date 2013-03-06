package edu.wheaton.simulator.datastructures;

import edu.wheaton.simulator.gridentities.GridEntity;

public interface BooleanExpression {

	public boolean evaluate(GridEntity me, GridEntity other);
}