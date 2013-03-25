package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.ExpressionEvaluator;

public class DieBehavior extends AbstractBehavior {

//	public DieBehavior(ExpressionEvaluator eval) {
//		super(eval);
//	}

	@Override
	public String getName() {
		return "die";
	}

	@Override
	public String execute(String[] args) throws EvaluationException {
		Agent target = (Agent) this.resolveEntity(args[0].replaceAll("'", ""));
		target.die();
		return "true";
	}

}
