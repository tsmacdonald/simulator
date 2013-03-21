package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.Grid;

public class Clone implements Behavable {

	/**
	 * The expression for the x-coordinate where the clone will be placed
	 */
	private Expression xExpr;

	/**
	 * The expression for the y-coordinate where the clone will be placed
	 */
	private Expression yExpr;

	/**
	 * The grid into which the Agent is being cloned
	 */
	private Grid global;

	/**
	 * Main (only) constructor for Clone
	 * 
	 * @param xExpr
	 *            The expression for the x-coordinate where the clone will be
	 *            placed
	 * @param yExpr
	 *            The expression for the y-coordinate where the clone will be
	 *            placed
	 */
	public Clone(Grid global, Expression xExpr, Expression yExpr) {
		this.global = global;
		this.xExpr = xExpr;
		this.yExpr = yExpr;
	}

	/**
	 * Attempts to clone the target Agent into the slot at (x, y) in global If
	 * (x, y) is already full, throws an Exception Similarly to Move, it would
	 * be good if we can figure out how to give the user the ability to change
	 * what happens as a result.
	 */
	@Override
	public void act(Agent target) throws Exception {
		int x = (int) (xExpr.evaluateDouble() + 0.5);
		int y = (int) (yExpr.evaluateDouble() + 0.5);
		if (global.getEntity(x, y) == null) {
			Agent clone = new Agent(target, false);
			global.addEntity(target, x, y);
		} else
			throw new FullSlotException();
	}

}
