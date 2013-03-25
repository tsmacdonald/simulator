package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.simulation.Grid;

public class MoveBehavior extends AbstractBehavior {

	/**
	 * Main constructor
	 * 
	 */
//	public MoveBehavior(ExpressionEvaluator eval) {
//		super(eval);
//	}

	@Override
	public String getName() {
		return "move";
	}

	/**
	 * Attempts to move the target Agent to the (x, y) position found by
	 * evaluating the expressions. If the slot it attempts to move to is
	 * already full, it throws an exception. It would be good if we could find
	 * a way to allow the user to say what happens when a FullSlotException is
	 * thrown.
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Agent target = resolveAgent(args[0]);
		Integer x = Integer.valueOf(args[1]);
		Integer y = Integer.valueOf(args[2]);
		
		Grid grid = target.getGrid();
		if (grid.isValidCoord(x, y) && grid.getSlot(x, y).getAgent() == null) {
			grid.getSlot(x, y).setAgent(target);
			grid.getSlot(target.getPosX(), target.getPosY()).setAgent(null);
			target.setPos(x, y);
			return "true";
		}
		return "false";
	}

}
