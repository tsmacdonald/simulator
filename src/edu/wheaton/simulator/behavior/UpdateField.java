package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.Expression;

public class UpdateField implements Behavable {

	private String fieldName;

	private Expression inputExpr;

	public UpdateField(String fieldName, Expression inputExpr) {
		this.fieldName = fieldName;
		this.inputExpr = inputExpr;
	}

	@Override
	public void act(Agent target) throws EvaluationException {
		target.updateField(fieldName,inputExpr.evaluateString());
	}

}
