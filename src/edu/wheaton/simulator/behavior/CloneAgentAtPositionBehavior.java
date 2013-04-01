package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.AbstractExpressionFunction;
import edu.wheaton.simulator.expression.Expression;

public class CloneAgentAtPositionBehavior extends AbstractExpressionFunction{

	@Override
	public String getName() {
		return "cloneAgentAtPosition";
	}

	@Override
	public int getResultType() {
		return AbstractExpressionFunction.RESULT_TYPE_BOOL;
	}

	@Override
	public String execute(String[] args) throws EvaluationException {
		Agent target = resolveAgent(args[0]);
	
		Integer x1 = Double.valueOf(args[1]).intValue();		// the coordinates of the agent to be cloned
		Integer y1 = Double.valueOf(args[2]).intValue();
		Integer x2 = Double.valueOf(args[3]).intValue();		// coordinates for the new agent to be placed at
		Integer y2 = Double.valueOf(args[4]).intValue();
		
		Grid grid = target.getGrid();
		if(grid.isValidCoord(x2, y2) && grid.getAgent(x2,y2)==null){
				grid.addAgent(grid.getAgent(x1, y1).getPrototype().createAgent(), x2, y2);
				return Expression.TRUE;
		}
		return Expression.FALSE;
	}

}
