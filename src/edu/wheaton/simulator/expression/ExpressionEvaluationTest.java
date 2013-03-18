/**
 * ExpressionEvaluationTest.java
 * 
 * Class to test some of the features of JEval and the the integration
 * into our simulation.
 * 
 * @autor Emmanuel Pederson
 */
package edu.wheaton.simulator.expression;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionResult;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Entity;

public class ExpressionEvaluationTest {

	/*
	 * When triggers and agents are working properly, then set them up here for
	 * further testing.
	 */
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimpleBooleanEvaluation() {
		Expression testExpression = new Expression("1 < 2");
		try {
			Assert.assertTrue(testExpression.evaluateBool());
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testComplexBooleanEvaluation() {
		Expression testExpression = new Expression(
				"(1+2) > (2*2) && (3/2) < 3");
		try {
			Assert.assertFalse(testExpression.evaluateBool());
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDoubleEvaluation() {
		Expression testExpression = new Expression("3 * (3 + 1)");
		Double result = 1.0;
		try {
			result = testExpression.evaluateDouble();
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(11 < result && result < 13);
	}

	@Test
	public void testStringEvaluation() {
		Expression testExpression = new Expression("'ten' + 'ants'");
		try {
			Assert.assertEquals("'tenants'", testExpression.evaluateString());
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddVariables() {
		Expression testExpression = new Expression("#{three} < #{ten}");
		testExpression.addVariable("three", "3");
		testExpression.addVariable("ten", "10");
		try {
			Assert.assertTrue(testExpression.evaluateBool());
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEntityFieldExpression() {
		Entity entity = new Entity();
		try {
			entity.addField("name", "'mmmhmmmhm'");
		} catch (ElementAlreadyContainedException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Expression testExpression = new Expression("#{entity.name}");
		testExpression.addEntity("entity", entity);

		try {
			Assert.assertEquals("'mmmhmmmhm'", testExpression.evaluateString());
		} catch (EvaluationException e) {
			System.err.println("##testEntityFieldReference() failed##");
		}
	}
	
	@Test
	public void testComplicatedEntityFieldExpression() throws Exception{
		Entity xThis = new Entity();
		Entity xOther = new Entity();
		
		xThis.addField("x","5");
		xThis.addField("y", "0");
		
		xOther.addField("x","0");
		xOther.addField("y", "0");
		
		Expression distanceExpression = new Expression("sqrt(pow(#{this.x}-#{other.x},2) + pow(#{this.y}-#{other.y},2))");
		distanceExpression.addEntity("this", xThis);
		distanceExpression.addEntity("other",xOther);
		
		Assert.assertEquals(new Double(5.0), distanceExpression.evaluateDouble());
	}
	
	@Test
	public void testCustomFunctionExpression() throws Exception{
		Entity xThis = new Entity();
		Entity xOther = new Entity();
		
		xThis.addField("x","5");
		xThis.addField("y", "0");
		
		xOther.addField("x","0");
		xOther.addField("y", "0");
		
		Expression testExpression = new Expression("distance('this','other')");
		testExpression.addEntity("this", xThis);
		testExpression.addEntity("other",xOther);
		
		testExpression.addFunctions(
				new Function(){

					@Override
					public FunctionResult execute(Evaluator evaluator,
							String arguments) throws FunctionException {
						arguments = arguments.replaceAll("'", "");
						String[] args = arguments.split(",");
						EntityFieldResolver resolver = (EntityFieldResolver) evaluator.getVariableResolver();
						Entity arg0 = resolver.getEntity(args[0]);
						Entity arg1 = resolver.getEntity(args[1]);
						
						Expression genericDistanceExpression = new Expression("sqrt(pow(#{arg0.x}-#{arg1.x},2) + pow(#{arg0.y}-#{arg1.y},2))");
						
						genericDistanceExpression.addEntity("arg0", arg0);
						genericDistanceExpression.addEntity("arg1", arg1);
						try {
							return new FunctionResult(genericDistanceExpression.evaluateString(),FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC);
						} catch (EvaluationException e) {
							throw new FunctionException(e);
						}
					}

					@Override
					public String getName() {
						return "distance";
					}
					
				}
				);
		
		Assert.assertEquals(new Double(5.0), testExpression.evaluateDouble());
	}
}
