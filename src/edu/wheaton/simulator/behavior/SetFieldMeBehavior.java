package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.simulation.Grid;

public class SetFieldMeBehavior implements Behavable {

	/**
	 * Informal parameter that the user gives when
	 * making this Behavior. Designates the field
	 * name within the agent.
	 */
	private String fieldName;
	
	/**
	 * Informal parameter given when user creates this
	 * behavior.
	 */
	private PrimitiveOperationTree parameters;
	
	@Override
	public void act(GridEntity me, GridEntity other, GridEntity local,
			Grid global) {
		// TODO Auto-generated method stub
		
	}

}
