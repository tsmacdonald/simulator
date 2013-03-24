package edu.wheaton.simulator.test.simulation;

import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.simulation.Grid;

public class RockPaperScissorsTest {
	Grid testGrid;
	String [] agentType = {"rock", "paper", "scissors"};
	String [] exampleFields = {"sightDistance", "speed"};
	@Before
	public void setUp(){
		testGrid = new Grid(50, 50);
	}
	@Test
	public void test() {
		for(int j = 0; j< agentType.length; j ++){
			for(int i = 0; i < 10; i ++){
				Agent testAgent = new Agent(testGrid);
				testAgent = (Agent) addFields(testAgent);
				try {
					testAgent.addField("type", agentType[j]);
					testAgent.addField("name", agentType[j]);
				} catch (ElementAlreadyContainedException e) {
					e.printStackTrace();
				}
				testGrid.spawnAgent(testAgent);
			}
		}
	}
	
	private Entity addFields(Entity newAgent){
		for(int i = 0; i < exampleFields.length; i ++)
			try {
				newAgent.addField(exampleFields[i], 5);
			} catch (ElementAlreadyContainedException e) {
				e.printStackTrace();
			}
		return newAgent;
	}
}
