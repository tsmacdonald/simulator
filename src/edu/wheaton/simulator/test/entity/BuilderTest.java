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
	private Prototype test;
	private Trigger.Builder builder;
	private Trigger trigger;

	@Before
	public void setUp() {
		try{
			grid = new Grid(1,1);
			Prototype prototype = new Prototype(grid, "test");
			prototype.addField("weight", "1");
			prototype.addField("health", "10");
			builder = new Trigger.Builder(prototype);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail("didn't setup right");
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
		System.out.println(trigger.getConditions());
		Assert.assertTrue(trigger.getConditions().toString().equals("1==1")); // not sure why 1==1 isnt the same as 1==1.
	}
	
	@Test
	public void testConditionValues(){
		builder.addConditional("weight > health");
		trigger = builder.build();
		System.out.println(trigger.getConditions());
		Assert.assertTrue(trigger.getConditions().toString().equals("this.weight>this.health")); 
	}

	@Test
	public void tesetIsValidMethod(){
		builder.addConditional("TRUE");
		builder.addBehavioral("DieBehavior(this)");
		Assert.assertTrue(builder.isValid() == java.lang.Boolean.TRUE);
	}
}
