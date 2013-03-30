package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.expression.Expression;

public class SetFieldBehavior extends AbstractBehavior {

//	public SetFieldBehavior(ExpressionEvaluator eval) {
//		super(eval);
//	}

	@Override
	public String getName() {
		return "setField";
	}

	/**
	 * ex: setField('this','x',20)
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Entity target = resolveEntity(args[0]);
		String fieldName = args[1].replaceAll("'", "");
		String fieldValue = args[2];
		
		target.updateField(fieldName,fieldValue);
		return Expression.TRUE;
	}

}
