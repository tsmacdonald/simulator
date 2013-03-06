package edu.wheaton.simulator.datastructures;

import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.simulation.Grid;

public class PrimitivePrimitiveOperatorNode implements PrimitiveEvaluatable{

	public enum Sign { 
		ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULUS;
	}
	
	private Sign sign;
	private PrimitiveEvaluatable left;
	private PrimitiveEvaluatable right;
	
	public PrimitivePrimitiveOperatorNode(Sign sign, PrimitiveEvaluatable left, PrimitiveEvaluatable right) {
		this.sign = sign;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Evaluates the expression, based on the sign,
	 * the value of the left, and the value of the
	 * right. Type safety is ensured.
	 */
	@Override
	public Primitive evaluate(GridEntity me, GridEntity other,
			GridEntity local, Grid global) {
		Primitive leftEval = left.evaluate(me, other, local, global);
		Primitive rightEval = right.evaluate(me, other, local, global);
		if(sign.equals(Sign.ADD)) {
			return evaluateAddition(leftEval,rightEval);
		}
		else if(sign.equals(Sign.SUBTRACT)) {
			try {
				return evaluateSubtraction(leftEval,rightEval);
			} catch (StringFormatMismatchException e) {
				//TODO replace null return with thrown exception
				return null;
			}
		}
		else if(sign.equals(Sign.MULTIPLY)) {
			try {
				return evaluateMultiplication(leftEval,rightEval);
			} catch (StringFormatMismatchException e) {
				//TODO replace null return with thrown exception
				return null;
			}
		}
		else if(sign.equals(Sign.DIVIDE)) {
			try {
				return evaluateDivision(leftEval,rightEval);
			} catch (StringFormatMismatchException e) {
				//TODO replace null return with thrown exception
				return null;
			}
		}
		else if(sign.equals(Sign.MODULUS)) {
			try {
				return evaluateModulus(leftEval,rightEval);
			} catch (StringFormatMismatchException e) {
				//TODO replace null return with thrown exception
				return null;
			}
		}
		//TODO replace null return with thrown exception
		return null;
	}
	
	private Primitive evaluateAddition(Primitive leftEval, Primitive rightEval){
		try {
			return new Primitive(Type.CHAR, (char)(leftEval.charValue() + rightEval.charValue()) + "");
		}
		catch(StringFormatMismatchException e) {
			try {
				return new Primitive(Type.INT, leftEval.intValue() + rightEval.intValue() + "");
			} catch (StringFormatMismatchException e1) {
				try {
					return new Primitive(Type.DOUBLE, leftEval.doubleValue() + rightEval.doubleValue() + "");
				} catch (StringFormatMismatchException e2) {
					return new Primitive(Type.STRING, leftEval.stringValue() + rightEval.stringValue());
				}
			}
		}
	}
	
	private Primitive evaluateSubtraction(Primitive leftEval, Primitive rightEval) throws StringFormatMismatchException{
		try {
			return new Primitive(Type.CHAR, (char)(leftEval.charValue() - rightEval.charValue()) + "");
		}
		catch(StringFormatMismatchException e) {
			try {
				return new Primitive(Type.INT, leftEval.intValue() - rightEval.intValue() + "");
			} catch (StringFormatMismatchException e1) {
				try {
					return new Primitive(Type.DOUBLE, leftEval.doubleValue() - rightEval.doubleValue() + "");
				} catch (StringFormatMismatchException e2) {
					throw e2;
				}
			}
		}
	}
	
	private Primitive evaluateMultiplication(Primitive leftEval, Primitive rightEval) throws StringFormatMismatchException{
		try {
			return new Primitive(Type.CHAR, (char)(leftEval.charValue() * rightEval.charValue()) + "");
		}
		catch(StringFormatMismatchException e) {
			try {
				return new Primitive(Type.INT, leftEval.intValue() * rightEval.intValue() + "");
			} catch (StringFormatMismatchException e1) {
				try {
					return new Primitive(Type.DOUBLE, leftEval.doubleValue() * rightEval.doubleValue() + "");
				} catch (StringFormatMismatchException e2) {
					throw e2;
				}
			}
		}
	}
	
	private Primitive evaluateDivision(Primitive leftEval, Primitive rightEval) throws StringFormatMismatchException{
		try {
			return new Primitive(Type.CHAR, (char)(leftEval.charValue() / rightEval.charValue()) + "");
		}
		catch(StringFormatMismatchException e) {
			try {
				return new Primitive(Type.INT, leftEval.intValue() / rightEval.intValue() + "");
			} catch (StringFormatMismatchException e1) {
				try {
					return new Primitive(Type.DOUBLE, leftEval.doubleValue() / rightEval.doubleValue() + "");
				} catch (StringFormatMismatchException e2) {
					throw e2;
				}
			}
		}
	}
	
	private Primitive evaluateModulus(Primitive leftEval, Primitive rightEval) throws StringFormatMismatchException{
		try {
			return new Primitive(Type.CHAR, (char)(leftEval.charValue() % rightEval.charValue()) + "");
		}
		catch(StringFormatMismatchException e) {
			try {
				return new Primitive(Type.INT, leftEval.intValue() % rightEval.intValue() + "");
			} catch (StringFormatMismatchException e1) {
				throw e1;
			}
		}
	}
}
