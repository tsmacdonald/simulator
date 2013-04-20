package edu.wheaton.simulator.test.statistics;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.end.SimulationEnder;
import edu.wheaton.simulator.statistics.Saver;
import edu.wheaton.simulator.statistics.TriggerSnapshot;

public class SaverTest {

	String s;
	Agent agent;
	Agent agentOther;
	Grid grid;
	Prototype prototypeOne;
	Prototype prototypeTwo;
	Integer step;
	Set<TriggerSnapshot> triggers;
	SimulationEnder simEnder; 
	
	@Before
	public void setUp() throws ElementAlreadyContainedException {
		grid = new Grid(10, 10);
		grid.addField("Depth", "100"); 
		
		prototypeOne = new Prototype("Prototype 1");
		agent = prototypeOne.createAgent(grid);
		try {
			agent.addField("Pig", "Tom");
			agent.addField("Monkey", "Olly");
			agent.addField("Cat", "Joomba");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}
		
		prototypeTwo = new Prototype("Prototype 2");
		agentOther = prototypeTwo.createAgent(grid);
		try {
			agentOther.addField("Crayfish", "Paul");
			agentOther.addField("Meerkat", "Timon");
			agentOther.addField("Person", "John Charles");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}
		
		step = new Integer(23);
		
		//Create the ending conditions
		simEnder = new SimulationEnder(); 
		simEnder.setPopLimit(prototypeOne.getName(), 100); 
		simEnder.setPopLimit(prototypeTwo.getName(), 100); 
		simEnder.setStepLimit(20); 
		
		//Create the list of TriggerSnapshots
		triggers = new HashSet<TriggerSnapshot>();
		triggers.add(new TriggerSnapshot("trigger1", 1, "conditionExpression", "behaviorExpression"));
	}

	@After
	public void tearDown() {
		agent = null;
		agentOther = null;
		grid = null;
		prototypeOne = null;
		prototypeTwo = null;
		step = null;
	}

	@Test
	public void testsaveSimulation() {
		//Create a Set of Agents				
		Set<Agent> agents = new HashSet<Agent>(); 
		agents.add(agent); 
		agents.add(agentOther);
		
		// Creating a HashMap of PrototypeSnapshots
		ImmutableSet.Builder<Prototype> builder = new ImmutableSet.Builder<Prototype>();
		builder.add(prototypeOne);
		builder.add(prototypeTwo); 
		ImmutableSet<Prototype> prototypes = builder.build(); 
		
		Saver s = new Saver();
		s.saveSimulation("SimulationState", agents, prototypes, grid.getCustomFieldMap(), 
				grid.getWidth(), grid.getHeight(), simEnder); 
	}
	
	@Test
	public void testSavePrototype(){
		Saver s = new Saver();
		System.out.println(""); 
		s.savePrototype(prototypeOne);
		System.out.println(""); 
		s.savePrototype(prototypeTwo); 
		
		//Check the terminal output
		Assert.assertTrue(true);
	}
}