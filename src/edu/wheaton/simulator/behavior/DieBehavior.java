/**
 * DieBehavior.java
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.behavior;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.expression.Expression;

public class DieBehavior extends AbstractBehavior {

	@Override
	public String getName() {
		return "die";
	}
	

	@Override
	public Integer numArgs() {
		return 0;
	}

	/**
	 * Makes the target Agent die().
	 */
	@Override
	public String execute(String[] args) throws EvaluationException {
		Agent target = resolveAgent("this");
		target.die();
		return Expression.TRUE;
	}

}