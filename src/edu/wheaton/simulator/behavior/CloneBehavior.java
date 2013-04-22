/**
 * CloneBehavior.java
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.Expression;

public class CloneBehavior extends AbstractBehavior {

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
	@Override
	public String getName() {
		return "clone";
	}

	@Override
	public Integer numArgs() {
		return 2;
	}

	/**
	 * Attempts to clone the target Agent into the slot at (x, y) in global If
	 * (x, y) is already full, returns false instead of true.
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Agent target = resolveAgent("this");

		Integer x = Double.valueOf(args[0]).intValue();
		Integer y = Double.valueOf(args[1]).intValue();

		Grid grid = target.getGrid();

		if (grid.isValidCoord(x, y) && grid.getAgent(x, y) == null) {
			grid.addAgent(target.getPrototype().createAgent(grid), x, y);
			return Expression.TRUE;
		}
		return Expression.FALSE;
	}

}
