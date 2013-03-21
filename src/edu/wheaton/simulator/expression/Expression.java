package edu.wheaton.simulator.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.VariableResolver;
import net.sourceforge.jeval.function.FunctionException;
import edu.wheaton.simulator.entity.Entity;

public class Expression {
	
	private class EntityFieldResolver implements VariableResolver {

		private Map<String, Entity> entityMap;

		/*
		 * default constructor
		 */
		protected EntityFieldResolver() {
			entityMap = new HashMap<String, Entity>();
		}

		/*
		 * copy constructor
		 */
		protected EntityFieldResolver(EntityFieldResolver resolver) {
			entityMap.putAll(resolver.entityMap);
		}

		@Override
		public String resolveVariable(String variableName)
				throws FunctionException {

			String[] args = variableName.split("\\x2e");

			if (args.length != 2) {
				return null;
			}

			String targetName = args[0];
			String fieldName = args[1];

			Entity target = entityMap.get(targetName);
			if (target == null) {
				System.err.println("##Target entity not found##");
				return null;
			}
			try {
				String toReturn = target.getField(fieldName).getValue().toString();
				return toReturn;
			} catch (NoSuchElementException e) {
				System.err.println("##NoSuchElementException thrown##");
				return null;
			}
		}

		protected void setEntity(String name, Entity entity) {
			entityMap.put(name, entity);
		}

		protected Entity getEntity(String name) {
			return entityMap.get(name);
		}
	}

	private Evaluator evaluator;
	private EntityFieldResolver resolver;
	private Object expr;

	/**
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

	/**
	 * Copy constructor
	 */
	private Expression(Expression expr) {
		evaluator = new Evaluator();
		evaluator.setFunctions(expr.evaluator.getFunctions());
		evaluator.setVariables(expr.evaluator.getVariables());
		resolver = new EntityFieldResolver(expr.resolver);
		evaluator.setVariableResolver(resolver);
		setString(expr.expr);
	}

	@Override
	public Expression clone() {
		return new Expression(this);
	}

	public void setString(Object exprStr) {
		this.expr = exprStr;
	}

	/**
	 * @Param name Do not format this String as you must do when creating an
	 *        expression String. Simply pass the desired variable name.
	 * 
	 */
	public void importVariable(String name, String value) {
		evaluator.putVariable(name, value);
	}

	/**
	 * @Param aliasName The name used to refer to the Entity in the expression
	 *        String ("this", "other", etc.)
	 */
	public void importEntity(String aliasName, Entity entity) {
		resolver.setEntity(aliasName, entity);
	}

	public void importFunctions(ExpressionFunction... functions) {
		for (ExpressionFunction f : functions)
			evaluator.putFunction(f.toJEvalFunction());
	}

	protected Entity getEntity(String aliasName) {
		return resolver.getEntity(aliasName);
	}

	protected String getVariableValue(String variableName)
			throws EvaluationException {
		// TODO Auto-generated method stub
		return evaluator.getVariableValue(variableName);
	}

	/**
	 * clear all added variables
	 */
	public void clearVariables() {
		evaluator.clearVariables();
	}

	/**
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
