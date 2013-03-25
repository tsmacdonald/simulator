package edu.wheaton.simulator.test.statistics;

/**
 * A JUnit test case for testing StatisticsManager.java.
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335
 * Spring 2013
 * 25 Mar 2013
 */

import static org.junit.Assert.fail;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.Grid;
import edu.wheaton.simulator.statistics.PrototypeSnapshot;
import edu.wheaton.simulator.statistics.SnapshotFactory;
import edu.wheaton.simulator.statistics.StatisticsManager;

public class StatisticsManagerTest {

	StatisticsManager sm;
	Grid g; 
	
	@Before
	public void setUp() throws Exception {
		sm = new StatisticsManager();
		g = new Grid(10,  10); 
		
//		//Data for the a test "prototype" list within the StatisticsManager
//		String categoryName = "testing";
//		Grid grid = new Grid(10, 10);
//		Prototype prototype = new Prototype(grid, "tester");
//		HashMap<String, String> fields1 = new HashMap<String, String>();
//		int population fail("Not yet implemented");= 50;
//		ImmutableSet<AgentID> children = prototype.childIDs();
//		Integer step1 = new Integer(1);
//		
//		PrototypeSnapshot protoSnap = new PrototypeSnapshot(categoryName, prototype.getPrototypeID(),
//				SnapshotFactory.makeFieldSnapshots(fields1), population, children, step1);
//		
//		sm.addPrototypeSnapshot(protoSnap); 
//		
//		//Test data for an EntitySnapshot to insert
//		Entity entity = new Entity();
//		Integer step2 = new Integer(1);
//		HashMap<String, String> fields2 = new HashMap<String, String>();
//		fields2.put("Weight", "2.0");
//		fields2.put("Name", "Ted");
//		EntitySnapshot entSnap = new EntitySnapshot(entity.getEntityID(),
//				SnapshotFactory.makeFieldSnapshots(fields2), step2);
//		
//		sm.addGridEntity(entSnap); 
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStatisticsManager() { 
		Assert.assertNotNull("Constructor failed", sm);
	}

	@Test
	public void testGetGridObserver() {
		Assert.assertNotNull("Failed to get GridObserver", sm.getGridObserver()); 
	}

	@Test
	public void testAddPrototypeSnapshot() {
		Prototype p = new Prototype(g, "TestPrototype"); 
		Assert.assertNotNull(p); 
		
		PrototypeSnapshot protoSnap = new PrototypeSnapshot("categoryname", p.getPrototypeID(),
				SnapshotFactory.makeFieldSnapshots(new HashMap<String, String>()), 100, p.childIDs(), new Integer(2)); 
		
		sm.addPrototypeSnapshot(protoSnap);
	}

	@Test
	public void testAddGridEntity() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProtypeIDs() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPrototypeIDs() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPopVsTime() {
		new Prototype(g, "Cat"); 
		new PrototypeSnapshot("Cat", , fields, population, children, step)
		fail("Not yet implemented");
	}

	@Test
	public void testGetAvgFieldValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAvgLifespan() {
		fail("Not yet implemented");
	}

}
