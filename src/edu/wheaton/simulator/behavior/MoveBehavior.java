package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.Grid;

public class MoveBehavior implements Behavior {

	/**
	 * The Grid in which the Agent will move.
	 */
	private Grid global;

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
	 * @param global
	 *            The Grid in which the Agent will move
	 * @param xExpr
	 *            The expression for the new x-coordinate
	 * @param yExpr
	 *            The expression for the new y-coordinate
	 */
	public MoveBehavior(Grid global, Expression xExpr, Expression yExpr) {
		this.global = global;
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
	public void act(Agent target) throws Exception {
		int x = (int) (xExpr.evaluateDouble() + 0.5);
		int y = (int) (yExpr.evaluateDouble() + 0.5);
		if (global.getSlot(x, y).getAgent() == null) {
			global.getSlot(x, y).setAgent(target);
			global.getSlot(target.getPosX(), target.getPosY()).setAgent(null);
			target.setPos(x, y);
		} else
			throw new FullSlotException();

	}

}
