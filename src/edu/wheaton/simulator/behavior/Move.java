package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.GridEntity;
import edu.wheaton.simulator.simulation.Grid;
import edu.wheaton.simulator.entity.Agent;

public class Move implements Behavable {

	/**
	 * The Agent that will move
	 */
	private Agent target;
	
	/**
	 * The (x, y) location that the target moves to.
	 */
	private int x, y;
	
	/**
	 * The Grid in which the Agent will move.
	 */
	private Grid global;
	
	public Move(Grid global, Agent target, int x, int y) {
		this.global = global;
		this.target = target;
		this.x = x;
		this.y = y;
	}
	/* act()
	 * moves the target agent to slot (x, y) in the passed grid.
	 * Problems: It currently does nothing if the slot it wishes to move to is full.
	 * It seems like some sort of error should be returned somehow, but this cannot be done
	 * since the Behavable interface's act method does not throw any exceptions and the return
	 * type on act is void.
	 * In addition, it seems as though certain passed parameters are useless; the precise
	 * system we intend to use for behavables/behaviors is confusing and ambiguous.
	 */
	@Override
	public void act() throws Exception {
		if (global.getSlot(x, y).getEntity() == null) {
			global.getSlot(x, y).setEntity(target);
			global.getSlot(target.getPosX(),target.getPosY()).setEntity(null);
			target.setPos(x, y);
		}
		else
			throw new FullSlotException();
		
	}

}
