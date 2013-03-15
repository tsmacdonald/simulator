package edu.wheaton.simulator.expression;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.VariableResolver;
import net.sourceforge.jeval.function.Function;
import edu.wheaton.simulator.datastructure.Value;
import edu.wheaton.simulator.entity.Entity;

public final class Expression {
	
	public static Value evaluate(String expr, List<Function> functions, Map<String,Entity> entities, Map<String,Value> values) throws EvaluationException{
		Evaluator evaluator = new Evaluator();
		
		VariableResolver varRes = new EntityResolver(entities);
		evaluator.setVariableResolver(varRes);
		
		for(Function f : functions)
			evaluator.putFunction(f);
		
		Set<Map.Entry<String,Value>> valueMapSet = values.entrySet();
		for( Map.Entry<String,Value> entry : valueMapSet ){
			evaluator.putVariable(entry.getKey(), entry.getValue().toString());
		}
	
		return new Value(evaluator.evaluate(expr));
	}
	
}
