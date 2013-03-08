package edu.wheaton.simulator.datastructures.expression;

import edu.wheaton.simulator.gridentities.GridEntity;

public interface BooleanExpression {

	public Boolean evaluate(GridEntity me, GridEntity other);
}