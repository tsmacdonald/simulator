package edu.wheaton.simulator.behavior;

import java.util.NoSuchElementException;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.expression.Expression;

public class SetFieldBehavior extends AbstractBehavior {

	@Override
	public String getName() {
		return "setField";
	}

	@Override
	public Integer numArgs() {
		return 2;
	}	
	
	/**
	 * ex: setField('this','x',20)
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Entity target = resolveEntity("this");
		String fieldName = args[0].replaceAll("'", "");
		String fieldValue = args[1];
		try {
			target.updateField(fieldName,fieldValue);
			return Expression.TRUE;
		}
		catch (NoSuchElementException e) {
			return Expression.FALSE;
		}
	}

}
