package edu.wheaton.simulator.datastructures.expression;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructures.Primitive;
import edu.wheaton.simulator.datastructures.StringFormatMismatchException;
import edu.wheaton.simulator.datastructures.Type;
import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.simulation.Grid;

public class PrimitivePrimitiveOperatorNode implements PrimitiveEvaluatable{
	
	private BinaryOperator op;
	private PrimitiveEvaluatable left;
	private PrimitiveEvaluatable right;
	
	public PrimitivePrimitiveOperatorNode(BinaryOperator op, PrimitiveEvaluatable left, PrimitiveEvaluatable right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Evaluates the expression, based on the sign,
	 * the value of the left, and the value of the
	 * right. Type safety is ensured.
	 * @throws EvaluationException 
	 */
	@Override
	public Primitive evaluate(GridEntity me, GridEntity other,
			GridEntity local, Grid global) throws EvaluationException {
		Primitive leftEval = left.evaluate(me, other, local, global);
		Primitive rightEval = right.evaluate(me, other, local, global);
		Expression expr = new Expression(op,leftEval.toString(),rightEval.toString());
		return new Primitive(Type.DOUBLE,expr.getDouble().toString());
		
		
	}
}
