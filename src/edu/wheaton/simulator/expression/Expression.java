package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import edu.wheaton.simulator.entity.Entity;

public final class Expression {

	private Evaluator evaluator;
	private EntityFieldResolver resolver;
	private Object expr;

	/*
	 * Default constructor
	 * 
	 * The expression string is retrieved by calling expr.toString()
	 */
	public Expression(Object exprStr) {
		setString(exprStr);
		evaluator = new Evaluator();
		resolver = new EntityFieldResolver();
		evaluator.setVariableResolver(resolver);
	}

	/*
	 * Copy constructor
	 */
	public Expression(Expression expr) {
		evaluator = new Evaluator();
		evaluator.setFunctions(expr.evaluator.getFunctions());
		evaluator.setVariables(expr.evaluator.getVariables());
		resolver = new EntityFieldResolver(expr.resolver);
		evaluator.setVariableResolver(resolver);
		setString(expr.expr);
	}

	public void setString(Object exprStr) {
		this.expr = exprStr;
	}

	/*
	 * @Param name Do not format this String as you must do when creating an
	 * expression String. Simply pass the desired variable name.
	 * 
	 * @Param valueIsString Denotes whether the value should be interpreted as
	 * a String rather than of Numeric or Boolean type
	 */
	public void addVariable(String name, String value) {
		evaluator.putVariable(name, value);
	}

	/*
	 * @Param aliasName The name used to refer to the Entity in the expression
	 * String ("this", "other", etc.)
	 */
	public void addEntity(String aliasName, Entity entity) {
		resolver.addEntity(aliasName, entity);
	}

	public void addFunctions(Function... functions) {
		for (Function f : functions)
			evaluator.putFunction(f);
	}

	/*
	 * Formats the passed variable name into the format required by the parser.
	 * 
	 * Variables contained in an expression string must be properly formatted
	 * in order to be correctly evaluated by the parser. Do not use this
	 * function on the 'name' parameter passed to the method 'addVariable(...)'
	 * 
	 * @Param name The variable's name
	 * 
	 * @Return The formatted string
	 */
	public static String formatName(String name) {
		return "#{" + name + "}";
	}

	/*
	 * Formats the passed String value into the format required by the parser.
	 * 
	 * Values that are intended to be parsed as type String require a special
	 * format
	 * 
	 * @Param value
	 * 
	 * @Return The formatted string
	 */
	public static String formatString(String value) {
		return "'" + value + "'";
	}

	/*
	 * clear all added variables
	 */
	public void clearVariables() {
		evaluator.clearVariables();
	}

	/*
	 * clear all added functions
	 */
	public void clearFunctions() {
		evaluator.clearFunctions();
	}

	public Boolean evaluateBool() throws EvaluationException {
		return evaluator.getBooleanResult(expr.toString());
	}

	public Double evaluateDouble() throws EvaluationException {
		return evaluator.getNumberResult(expr.toString());
	}

	public String evaluateString() throws EvaluationException {
		return evaluator.evaluate(expr.toString());
	}

	public static Boolean evaluateBool(Object exprStr)
			throws EvaluationException {
		Evaluator evaluator = new Evaluator();
		return evaluator.getBooleanResult(exprStr.toString());
	}

	public static Double evaluateDouble(Object exprStr)
			throws EvaluationException {
		Evaluator evaluator = new Evaluator();
		return evaluator.getNumberResult(exprStr.toString());
	}

	public static String evaluateString(Object exprStr)
			throws EvaluationException {
		Evaluator evaluator = new Evaluator();
		return evaluator.evaluate(exprStr.toString());
	}
}
