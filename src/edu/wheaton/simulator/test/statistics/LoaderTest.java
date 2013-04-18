package edu.wheaton.simulator.test.statistics;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.statistics.Loader;

public class LoaderTest {

	Grid grid; 
	Set<Prototype> prototypes;
	String name; 

	@Test
	public void testLoadSimulation() throws Exception {
		
//		String saveFile = "10\n10\nPrototypeSnapshot\nPrototype 1\n-16119286" +
//				"\n127127127127127127127127\nFieldSnapshot Cat Joomba\nFieldSnapshot " +
//				"Pig Tom\nFieldSnapshot Monkey Olly\nTrigger~trigger1~1~conditionExpression" +
//				"~behaviorExpression\nPrototypeSnapshot\nPrototype 2\n-16119286\n" +
//				"127127127127127127127127\nFieldSnapshot Meerkat Timon\nFieldSnapshot " +
//				"Crayfish Paul\nFieldSnapshot Person JohnCharles\nTrigger~trigger1~" +
//				"1~conditionExpression~behaviorExpression\nAgentSnapshot\nPrototype 1" +
//				"\n0\n0\nFieldSnapshot Cat Joomba\nFieldSnapshot Pig Tom\nFieldSnapshot " +
//				"Monkey Olly\nAgentSnapshot\nPrototype 2\n0\n0\nFieldSnapshot Meerkat Timon" +
//				"\nFieldSnapshot Crayfish Paul\nFieldSnapshot Person JohnCharles\nEndingConditions" +
//				"\nEndConditions\nTIME 20\nPOP PrototypeID:0100\nPOP PrototypeID:1 100"; 
		
		Loader l = new Loader();
		l.loadSimulation("SimulationState.txt");
		System.out.println("Loaded"); 
		grid = l.getGrid(); 
		prototypes = l.getPrototypes(); 
		name = l.getName(); 	
		
		Assert.assertNotNull(grid); 
		Assert.assertNotNull(prototypes); 
		Assert.assertEquals("saveFile", name); 
	}
}
