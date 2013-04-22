package edu.wheaton.simulator.test.entity;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

public class BuilderTest {

	private Prototype prototype;
	private Trigger.Builder builder;
	private Trigger trigger;

	@Before
	public void setUp() {
		try{
			prototype = new Prototype("test");
			prototype.addField("weight", "1");
			prototype.addField("health", "10");
			builder = new Trigger.Builder(prototype);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		//ExpressionEvaluationTest.tearDown() is empty
	}
	
	@Test
	public void testNameAndPriority(){
		builder.addName("test");
		builder.addPriority(2);
		trigger = builder.build();
		Assert.assertTrue(trigger.getName().equals("test") && trigger.getPriority() == 2);
	}
	
	@Test
	public void testConditionOperation(){
		builder.addConditional("1 EQUALS 1");
		trigger = builder.build();
		System.out.println(trigger.getConditions() + "");
		Assert.assertTrue(trigger.getConditions().toString().equals("1 == 1")); 
	}
	
	@Test
	public void testConditionValues(){
		builder.addConditional("weight > health");
		trigger = builder.build();
		Assert.assertTrue(trigger.getConditions().toString().equals("this.weight > this.health")); 
	}
	
	@Test
	public void testTrueValues(){
		builder.addConditional("true");
		builder.addBehavioral("true");
		try {
			Assert.assertTrue(builder.isValid() == java.lang.Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIsValidMethod() throws Exception{
		builder.addConditional("this.weight>2"); 
		builder.addBehavioral("move(2,2)");
		Assert.assertTrue(builder.isValid() == java.lang.Boolean.TRUE);
	}
	

	@Test
	public void testForUserEnteredExpression() {
		builder.addConditional("health > 3");
		builder.addBehavioral("1+move(5,5)");
		try {
			Assert.assertTrue(builder.isValid() == java.lang.Boolean.TRUE);
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testIsValidBlank(){
		try {
			Assert.assertTrue(builder.isValid() == java.lang.Boolean.FALSE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	
	@Test
	public void testIsValidMethod2(){
		builder.addConditional("blah blah blah");
		builder.addBehavioral("gibberish");
		try {
			Assert.assertTrue(builder.isValid() == java.lang.Boolean.FALSE);
		} catch (Exception e) {
			Assert.assertTrue(java.lang.Boolean.TRUE);
		}
	}
	
	@Test
	public void testForIncorrectArguments(){
		builder.addConditional("TRUE");
		builder.addBehavioral("move(5,5,5)");
		try {
			Assert.assertTrue(builder.isValid() == java.lang.Boolean.FALSE);
		} catch (Exception e) {
			Assert.assertTrue(java.lang.Boolean.TRUE);
		}
	}
	
	@Test
	public void testParserWithoutFunctions(){
		Trigger.Builder b = new Trigger.Builder(new Trigger("name", 1,
				new Expression("this.health>this.weight"), new Expression("TRUE")), prototype);
		System.out.println(b.getConditionString() + " | " + b.getBehaviorString());
		Assert.assertTrue(b.getBehaviorString().equals("TRUE") && b.getConditionString().equals("health > weight"));
	}
}
