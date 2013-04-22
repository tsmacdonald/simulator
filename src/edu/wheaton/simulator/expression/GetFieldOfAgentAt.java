/**
 * GetFieldOfAgentAt.java
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.expression;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import net.sourceforge.jeval.EvaluationException;

public class GetFieldOfAgentAt extends AbstractExpressionFunction {

	@Override
	public String getName() {
		return "getFieldOfAgentAt";
	}

	@Override
	public int getResultType() {
		return AbstractExpressionFunction.RESULT_TYPE_NUMERIC;
	}

	@Override
	public Integer numArgs() {
		return 3;
	}
	
	/**
	 * Takes the form: getFieldOfAgentAt(x, y, fieldName)
	 * 
	 * @param args
	 * @return
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Double x = Double.valueOf(args[0]);
		Double y = Double.valueOf(args[1]);
		
		Agent xThis = resolveAgent("this");
		Grid grid = xThis.getGrid();
		
		Agent target = grid.getAgent(x.intValue(), y.intValue());
		
		if(target==null){
			throw new NullPointerException("GetFieldOfAgentAt.java : grid.getAgent(" + x.intValue() + "," + y.intValue() + ") returned null therefore no field can be retrieved");
		}
		String fieldName = args[2].replaceAll("'", "");
		String fieldValue = target.getFieldValue(fieldName);
		return fieldValue.toString();
	}
}
