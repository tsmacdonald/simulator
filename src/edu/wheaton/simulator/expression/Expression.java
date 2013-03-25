package edu.wheaton.simulator.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.VariableResolver;
import net.sourceforge.jeval.function.FunctionException;
import edu.wheaton.simulator.behavior.CloneBehavior;
import edu.wheaton.simulator.behavior.DieBehavior;
import edu.wheaton.simulator.behavior.MoveBehavior;
import edu.wheaton.simulator.behavior.SetFieldBehavior;
import edu.wheaton.simulator.entity.Entity;

public class Expression implements ExpressionEvaluator {

	/**
	 * All variables that JEval evaluates are first passed to an associated instance of
	 * VariableResolver. If the 'VR' returns a null then the JEval 'Evaluator' will look for the
	 * variable name in its internal map. This 'VR' implementation is solely for the purpose of enabling
	 * a shorter Expression syntax: "#{this.x}" rather than something like "getField('this','x')" which would require
	 * implementing a "getField" ExpressionFunction.
	 * 
	 * @author bgarcia
	 *
	 */
	protected class EntityFieldResolver implements VariableResolver {

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
			entityMap = new HashMap<String, Entity>();
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

			Entity target = getEntity(targetName);
			if (target == null) {
				throw new FunctionException("Target entity not found: " + targetName);
			}
			try {
				String toReturn = target.getFieldValue(fieldName);
				return toReturn;
			} catch (NoSuchElementException e) {
				throw new FunctionException("Target field not found: " + fieldName);
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
		
		//make all project-defined ExpressionFunction implementations recognizable by default
		this.importFunction(new CloneBehavior());
		this.importFunction(new DieBehavior());
		this.importFunction(new MoveBehavior());
		this.importFunction(new SetFieldBehavior());
		this.importFunction(new IsSlotOpen());
		this.importFunction(new GetFieldOfAgentAt());
		this.importFunction(new IsValidCoord());
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
	
	protected Expression(Evaluator eval, EntityFieldResolver res){
		this.evaluator = eval;
		this.resolver = res;
	}

	/**
	 * calls the copy constructor
	 */
	@Override
	public ExpressionEvaluator clone() {
		return new Expression(this);
	}

	/**
	 * sets the string that is evaluated by JEval/JEval-wrapper
	 */
	@Override
	public void setString(Object exprStr) {
		this.expr = exprStr;
	}

	/**
	 * Define a variable
	 * 
	 * @Param name Do not format this String as you must do when creating an
	 *        expression String. Simply pass the desired variable name.
	 */
	@Override
	public void importVariable(String name, String value) {
		evaluator.putVariable(name, value);
	}

	/**
	 * Make an entity recognizable by this expression and all functions called within
	 * 
	 * @Param aliasName The name used to refer to the Entity in the expression
	 *        String ("this", "other", etc.)
	 */
	@Override
	public void importEntity(String aliasName, Entity entity) {
		resolver.setEntity(aliasName, entity);
	}

	/**
	 * Make an ExpressionFunction recognizable by this expression and all functions called within
	 */
	@Override
	public void importFunction(AbstractExpressionFunction function) {
		evaluator.putFunction(function.toJEvalFunction());
	}

	/**
	 * get an imported Entity
	 */
	@Override
	public Entity getEntity(String aliasName) {
		return resolver.getEntity(aliasName);
	}

	/**
	 * get the value of an imported variable
	 */
	@Override
	public String getVariableValue(String variableName)
			throws EvaluationException {
		return evaluator.getVariableValue(variableName);
	}

	/**
	 * clear all variables added with 'importVariable'
	 * 
	 */
	@Override
	public void clearVariables() {
		evaluator.clearVariables();
	}
	
	/**
	 * clear all entities added with 'importEntity'
	 */
	@Override
	public void clearEntities(){
		resolver.entityMap.clear();
	}

	/**
	 * clear all functions added with 'importFunction
	 */
	@Override
	public void clearFunctions() {
		evaluator.clearFunctions();
	}

	@Override
	public Boolean evaluateBool() throws EvaluationException {
		try {
			return evaluator.getBooleanResult(expr.toString());
		} catch (EvaluationException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}

	@Override
	public Double evaluateDouble() throws EvaluationException {
		try {
			return evaluator.getNumberResult(expr.toString());
		} catch (EvaluationException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}

	@Override
	public String evaluateString() throws EvaluationException {
		try {
			return evaluator.evaluate(expr.toString());
		} catch (EvaluationException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public String toString(){
		return expr.toString();
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
