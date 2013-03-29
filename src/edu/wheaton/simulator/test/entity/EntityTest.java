package edu.wheaton.simulator.test.entity;

import org.junit.Assert;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Entity;

public class EntityTest {
	String [] exampleNames = {"hare", "rabbit", "fox", "cat", "bear", "lion", "elephant", "pig", "clover", "tree"};
	String [] exampleFields = {"health", "energy", "sight", "speed", "weight"};
	
	/*
	 * Very simple test to see that fields are being assigned values for general Entities
	 */
	@Test
	public void testAddEntityField() {
		Grid testGrid = new Grid(30, 30);
		for(int i = 0; i < 10; i++){
			Entity newEntity = new Entity();
			for(int j = 0; j < 5; j ++){
				newEntity = addExampleFields(newEntity);
			}
			Assert.assertEquals("100", newEntity.getFieldValue("health"));
		}
	}
	private Entity addExampleFields(Entity newAgent){
		for(int i = 0; i < 5; i ++)
			try {
				newAgent.addField(exampleFields[i], 100);
			} catch (ElementAlreadyContainedException e) {
				e.printStackTrace();
			}
		return newAgent;
	}
}
