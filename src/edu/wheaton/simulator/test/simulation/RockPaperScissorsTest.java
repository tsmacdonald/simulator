package edu.wheaton.simulator.test.simulation;

import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.expression.ExpressionEvaluator;
import edu.wheaton.simulator.simulation.Grid;

public class RockPaperScissorsTest {
	Grid testGrid;
	String [] agentType = {"rock", "paper", "scissors"};
	@Before
	public void setUp(){
		testGrid = new Grid(50, 50);
	}
	@Test
	public void test() {
		ExpressionEvaluator xMove = new Expression("move('this', #{this.x} + 1, #{this.y})");
		ExpressionEvaluator yMove = new Expression("move('this', #{this.x}, #{this.y} + 1)");
		ExpressionEvaluator dir0 = new Expression("#{this.direction} = 0 && #{this.getGrid().emptySlot(#{this.x}, #{this.y}+1)}");
		for(int j = 0; j < agentType.length; j ++){
			Prototype testPrototype = new Prototype(testGrid, "testPrototype");
			try {
				testPrototype.addField("type", agentType[j]);
				testPrototype.addField("direction", j);
			} catch (ElementAlreadyContainedException e) {
				e.printStackTrace();
			}
			testPrototype.addTrigger(new Trigger())
			for(int i = 0; i < 10; i ++){
				testGrid.spawnAgent(testPrototype.clonePrototype());
			}
		}
	}
}
