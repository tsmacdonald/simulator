package edu.wheaton.simulator.statistics;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.PrototypeID;
import edu.wheaton.simulator.entity.Slot;
import edu.wheaton.simulator.simulation.Grid;

public class SnapshotFactoryTest {

	Grid g; 
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		g = new Grid(10, 10);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testMakeSlotSnapshot() {
		Slot s = new Slot(g); 
		try {
			s.addField("foo", "'bar'");
			s.addField("number", "1");
		} catch (ElementAlreadyContainedException e) {
		}
		
		EntitySnapshot ss = SnapshotFactory.makeSlotSnapshot(s, 10); 
		
		Assert.assertNotNull(ss.entityID);
		Assert.assertNotNull(ss.fields);
		Assert.assertNotNull(ss.step);
		Assert.assertNotNull(ss.fields.get("foo"));
		Assert.assertNotNull(ss.fields.get("number"));
		Assert.assertEquals(ss.fields.get("foo").value, "'bar'");
		Assert.assertEquals(ss.fields.get("number").value, "1");
		Assert.assertTrue(ss.fields.get("number").isNumber); 
		Assert.assertFalse(ss.fields.get("foo").isNumber); 
		Assert.assertEquals(ss.fields.get("number").getNumericalValue(), 1.0);
		ss.fields.get("foo").getNumericalValue();
		
	}

	@Test
	public void testMakeAgentSnapshot() {
		Prototype p = new Prototype(g, "bear"); 
		Agent a = new Agent(g, p);
		PrototypeID pID = p.getPrototypeID();
		EntityID aID = a.getEntityID(); 
		Integer step = 15; 
		
		AgentSnapshot ass = SnapshotFactory.makeAgentSnapshot(a, step);
		
		Assert.assertNotNull(ass.entityID);
		Assert.assertNotNull(ass.fields);
		Assert.assertNotNull(ass.prototype);
		Assert.assertNotNull(ass.step);
		Assert.assertEquals(ass.entityID, aID);
		Assert.assertEquals(ass.prototype, pID);
		Assert.assertEquals(ass.step, step);
	}

	@Test (expected = UnsupportedOperationException.class)
	public void testMakeFieldSnapshot() {
		FieldSnapshot fss1 = SnapshotFactory.makeFieldSnapshot("foo", "'bar'");
		FieldSnapshot fss2 = SnapshotFactory.makeFieldSnapshot("int", "1");
		FieldSnapshot fss3 = SnapshotFactory.makeFieldSnapshot("double", "2.0");
				
		Assert.assertNotNull(fss1.value);
		Assert.assertNotNull(fss2.value);
		Assert.assertNotNull(fss3.value);
		Assert.assertEquals(fss1.name, "foo");
		Assert.assertEquals(fss2.name, "int");
		Assert.assertEquals(fss3.name, "double");
		Assert.assertNotNull(fss1.name);
		Assert.assertNotNull(fss2.name);
		Assert.assertNotNull(fss3.name);
		Assert.assertFalse(fss1.isNumber);
		Assert.assertTrue(fss2.isNumber);
		Assert.assertTrue(fss3.isNumber);
		Assert.assertEquals(fss1.value, "'bar'");
		Assert.assertEquals(fss2.value, "1");
		Assert.assertEquals(fss3.value, "2.0");
		Assert.assertEquals(fss2.getNumericalValue(), 1.0);
		Assert.assertEquals(fss3.getNumericalValue(), 2.0);
		//Raise exception.
		fss1.getNumericalValue();
	}

	@Test
	public void testMakeFieldSnapshots() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakePrototypeSnapshot() {
		fail("Not yet implemented");
	}

}
