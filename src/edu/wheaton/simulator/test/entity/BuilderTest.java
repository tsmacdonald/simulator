package edu.wheaton.simulator.test.entity;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;

public class BuilderTest {

	private Grid grid;
	private Prototype prototype;
	private Trigger.Builder builder;
	private Trigger trigger;

	@Before
	public void setUp() {
		try{
			grid = new Grid(1,1);
			prototype = new Prototype(grid, "test");
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
		//TODO ExpressionEvaluationTest.tearDown() is empty
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
		Assert.assertTrue(trigger.getConditions().toString().equals("1==1")); 
	}
	
	@Test
	public void testConditionValues(){
		builder.addConditional("weight > health");
		trigger = builder.build();
		Assert.assertTrue(trigger.getConditions().toString().equals("this.weight>this.health")); 
	}

	@Test
	public void testIsValidMethod(){
		builder.addConditional("this.weight>2"); 
		builder.addBehavioral("move(2,2)");
		Assert.assertTrue(builder.isValid() == java.lang.Boolean.TRUE);
	}
	

	@Test
	public void testForUserEnteredExpression(){
		builder.addConditional("health > 3");
		builder.addBehavioral("move(5,5)");
		Assert.assertTrue(builder.isValid() == java.lang.Boolean.TRUE);
	}
	
	@Test
	public void testIsValidMethod2(){
		builder.addConditional("blah blah blah");
		builder.addBehavioral("gibberish");
		Assert.assertTrue(builder.isValid() == java.lang.Boolean.FALSE);
	}
	
	@Test
	public void testForIncorrectArguments(){
		builder.addConditional("health > 3");
		builder.addBehavioral("move(5,5,5)");
		Assert.assertTrue(builder.isValid() == java.lang.Boolean.FALSE);
	}
}
