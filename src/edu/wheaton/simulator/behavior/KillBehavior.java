/**
 * KillBehavior.java
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.expression.Expression;

public class KillBehavior extends AbstractBehavior {

	@Override
	public String getName() {
		return "kill";
	}

	@Override
	public Integer numArgs() {
		return 2;
	}

	/**
	 * Follows the form: kill(x, y). Places null at the given position in the grid.
	 * 
	 * @param args
	 * @return
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Integer x = Double.valueOf(args[0]).intValue();
		Integer y = Double.valueOf(args[1]).intValue();
		Grid grid = resolveAgent("this").getGrid();
		if(grid.isValidCoord(x, y) && grid.getAgent(x,y)!=null){
			grid.getAgent(x, y).die();
			return Expression.TRUE;
		}
		return Expression.FALSE;
	}

}

