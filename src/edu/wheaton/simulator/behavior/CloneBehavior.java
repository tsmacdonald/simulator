package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.Grid;

public class CloneBehavior implements Behavior {

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
	private Grid grid;

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
	public CloneBehavior(Grid grid, Expression xExpr, Expression yExpr) {
		this.grid = grid;
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
	public void execute(Agent target) throws Exception {
		int x = (int) (xExpr.evaluateDouble() + 0.5);
		int y = (int) (yExpr.evaluateDouble() + 0.5);
		if (grid.isValidCoord(x, y) && grid.getAgent(x, y) == null) {
			grid.addAgent(target, x, y);
		} else
			throw new FullSlotException();
	}

}
