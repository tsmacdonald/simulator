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

import javax.naming.NameNotFoundException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.Grid;
import edu.wheaton.simulator.statistics.EntitySnapshot;
import edu.wheaton.simulator.statistics.EntitySnapshotTable;
import edu.wheaton.simulator.statistics.PrototypeSnapshot;
import edu.wheaton.simulator.statistics.SnapshotFactory;
import edu.wheaton.simulator.statistics.StatisticsManager;

public class StatisticsManagerTest {

	StatisticsManager sm;
	Grid g; 
	String categoryName;
	Grid grid;
	Prototype prototype;
	HashMap<String, String> fields;
	int population;
	ImmutableSet<AgentID> children;
	Integer step;
	PrototypeSnapshot protoSnap;
	PrototypeSnapshot protoSnap2;
	
	@Before
	public void setUp() throws Exception {
		sm = new StatisticsManager();
		g = new Grid(10,  10); 		
		
		//Add a test PrototypeSnapshot
		categoryName = "testing";
		grid = new Grid(10, 10);
		prototype = new Prototype(grid, "tester");
		fields = new HashMap<String, String>();
		population = 50;
		children = prototype.childIDs();
		step = new Integer(1);
		
		protoSnap = new PrototypeSnapshot(categoryName, prototype.getPrototypeID(),
				SnapshotFactory.makeFieldSnapshots(fields), population,
				children, step);
		
		//Add another test PrototypeSnapshot
		categoryName = "testing2";
		prototype = new Prototype(grid, "tester2");
		population = 40;
		step = new Integer(2);
		
		protoSnap = new PrototypeSnapshot(categoryName, prototype.getPrototypeID(),
				SnapshotFactory.makeFieldSnapshots(fields), population,
				children, step);
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
		fail("Not yet implemented");
	}

	@Test
	public void testGetAvgFieldValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAvgLifespan() {				
		try {
			double result = sm.getAvgLifespan(protoSnap.id);
			System.out.println(result); 
		} 
		catch (NameNotFoundException e) {
			e.printStackTrace();
		} 
	}

}
