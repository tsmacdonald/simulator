package edu.wheaton.simulator.datastructures;

import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.gridentities.Primitive;
import edu.wheaton.simulator.gridentities.Primitive.Type;
import edu.wheaton.simulator.gridentities.StringFormatMismatchException;
import edu.wheaton.simulator.simulation.Grid;

public class PrimitivePrimitiveOperatorNode implements PrimitiveEvaluatable{

	public enum Sign { ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULUS;
		
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
			try {
				return new Primitive(Type.CHAR, (char)(leftEval.charValue() + rightEval.charValue()) + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
			try {
				return new Primitive(Type.INT, leftEval.intValue() + rightEval.intValue() + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
			try {
				return new Primitive(Type.DOUBLE, leftEval.doubleValue() + rightEval.doubleValue() + "");
			}
			catch(StringFormatMismatchException e) {

			}
			return new Primitive(Type.STRING, leftEval.stringValue() + rightEval.stringValue());
		}
		else if(sign.equals(Sign.SUBTRACT)) {
			try {
				return new Primitive(Type.CHAR, (char)(leftEval.charValue() - rightEval.charValue()) + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
			try {
				return new Primitive(Type.INT, leftEval.intValue() - rightEval.intValue() + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
			try {
				return new Primitive(Type.DOUBLE, leftEval.doubleValue() - rightEval.doubleValue() + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
		}
		else if(sign.equals(Sign.MULTIPLY)) {
			try {
				return new Primitive(Type.CHAR, (char)(leftEval.charValue() * rightEval.charValue()) + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
			try {
				return new Primitive(Type.INT, leftEval.intValue() * rightEval.intValue() + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
			try {
				return new Primitive(Type.DOUBLE, leftEval.doubleValue() * rightEval.doubleValue() + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
		}
		else if(sign.equals(Sign.DIVIDE)) {
			try {
				return new Primitive(Type.CHAR, (char)(leftEval.charValue() / rightEval.charValue()) + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
			try {
				return new Primitive(Type.INT, leftEval.intValue() / rightEval.intValue() + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
			try {
				return new Primitive(Type.DOUBLE, leftEval.doubleValue() / rightEval.doubleValue() + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
		}
		else if(sign.equals(Sign.MODULUS)) {
			try {
				return new Primitive(Type.CHAR, (char)(leftEval.charValue() % rightEval.charValue()) + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
			try {
				return new Primitive(Type.INT, leftEval.intValue() % rightEval.intValue() + "");
			}
			catch(StringFormatMismatchException e) {
				
			}
		}
		return null;
	}
	
	
}
