package edu.wheaton.simulator.gridentities;

public interface BooleanExpression {

	public boolean evaluate(GridEntity me, GridEntity other);
}