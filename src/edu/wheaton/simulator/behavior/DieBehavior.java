package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.Expression;

public class DieBehavior extends AbstractBehavior {

//	public DieBehavior(ExpressionEvaluator eval) {
//		super(eval);
//	}

	@Override
	public String getName() {
		return "die";
	}
	

	@Override
	public Integer numArgs() {
		return 0;
	}

	@Override
	public String execute(String[] args) throws EvaluationException {
		Agent target = resolveAgent("this");
		target.die();
		return Expression.TRUE;
	}

}
