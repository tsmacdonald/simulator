package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.expression.AbstractExpressionFunction;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.expression.ExpressionEvaluator;
import edu.wheaton.simulator.simulation.Grid;

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
//	public CloneBehavior(ExpressionEvaluator eval) {
//		super(eval);
//	}

	@Override
	public String getName() {
		return "clone";
	}

	/**
	 * Attempts to clone the target Agent into the slot at (x, y) in global If
	 * (x, y) is already full, throws an Exception Similarly to Move, it would
	 * be good if we can figure out how to give the user the ability to change
	 * what happens as a result.
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Agent target = (Agent)resolveEntity(this.getExprEval(), args[0].replaceAll("'", ""));
		
		Integer x = Integer.valueOf(args[1]);
		Integer y = Integer.valueOf(args[2]);
		
		Grid grid = target.getGrid();
		
		if(grid.isValidCoord(x, y) && grid.getAgent(x,y)==null){
			grid.addAgent(target.getPrototype().clonePrototype(), x, y);
			return "true";
		}
		return "false";
	}

}
