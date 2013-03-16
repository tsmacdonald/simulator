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
		Expression testExpression = new Expression("'car' + 'ing'");
		try {
			Assert.assertEquals("'caring'", testExpression.evaluateString());
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
}
