package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.datastructure.Expression;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.simulation.Grid;

public class SetFieldMeBehavior implements Behavable {

	/**
	 * Informal parameter that the user gives when making this Behavior.
	 * Designates the field name within the agent.
	 */
	private String fieldName;

	/**
	 * Informal parameter given when user creates this behavior.
	 */
	private Expression parameters;

	@Override
	public void act(Entity me, Entity other, Entity local, Grid global) {
		// TODO Auto-generated method stub

	}

}
