package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.Grid;

public class MoveBehavior implements Behavior {

	/**
	 * The Grid in which the Agent will move.
	 */
	private Grid grid;

	/**
	 * The expression for the x-position which the Agent will move to
	 */
	private Expression xExpr;

	/**
	 * The expression for the y-position which the Agent will move to
	 */
	private Expression yExpr;

	/**
	 * Main constructor
	 * 
	 * @param grid
	 *            The Grid in which the Agent will move
	 * @param xExpr
	 *            The expression for the new x-coordinate
	 * @param yExpr
	 *            The expression for the new y-coordinate
	 */
	public MoveBehavior(Grid grid, Expression xExpr, Expression yExpr) {
		this.grid = grid;
		this.xExpr = xExpr;
		this.yExpr = yExpr;
	}

	/**
	 * Attempts to move the target Agent to the (x, y) position found by
	 * evaluating the expressions. If the slot it attempts to move to is
	 * already full, it throws an exception. It would be good if we could find
	 * a way to allow the user to say what happens when a FullSlotException is
	 * thrown.
	 */
	@Override
	public void execute(Agent target) throws Exception {
		int x = (int) (xExpr.evaluateDouble() + 0.5);
		int y = (int) (yExpr.evaluateDouble() + 0.5);
		if (grid.isValidCoord(x, y) && grid.getSlot(x, y).getAgent() == null) {
			grid.getSlot(x, y).setAgent(target);
			grid.getSlot(target.getPosX(), target.getPosY()).setAgent(null);
			target.setPos(x, y);
		} else
			throw new FullSlotException();

	}

}
