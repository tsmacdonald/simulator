/**
 * CloneAgentAtPositionBehavior.java
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.AbstractExpressionFunction;
import edu.wheaton.simulator.expression.Expression;

public class CloneAgentAtPositionBehavior extends AbstractBehavior {

	@Override
	public String getName() {
		return "cloneAgentAtPosition";
	}

	@Override
	public int getResultType() {
		return AbstractExpressionFunction.RESULT_TYPE_BOOL;
	}


	@Override
	public Integer numArgs() {
		return 4;
	}
	
	/**
	 * this is basically a method cloneAgentAtPosition(int x, int y, int x2, int y2)
	 * where the first coordinate is the coords of the agent to be clones
	 * and the second one is the coords of where to put the new agent. 
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Agent target = resolveAgent("this");
		
		Integer x1 = Double.valueOf(args[0]).intValue();		
		Integer y1 = Double.valueOf(args[1]).intValue();
		Integer x2 = Double.valueOf(args[2]).intValue();		
		Integer y2 = Double.valueOf(args[3]).intValue();
		
		Grid grid = target.getGrid();
		if(grid.isValidCoord(x2, y2) && grid.getAgent(x2,y2)==null){
				grid.addAgent(grid.getAgent(x1, y1).getPrototype().createAgent(grid), x2, y2);
				return Expression.TRUE;
		}
		return Expression.FALSE;
	}

}
