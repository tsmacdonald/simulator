package edu.wheaton.simulator.test.statistics;

/**
 * A JUnit test case for testing StatisticsManager.java.
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335
 * Spring 2013
 * 25 Mar 2013
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.entity.Trigger.Builder;
import edu.wheaton.simulator.statistics.AgentSnapshot;
import edu.wheaton.simulator.statistics.PrototypeSnapshot;
import edu.wheaton.simulator.statistics.Recorder;
import edu.wheaton.simulator.statistics.SnapshotFactory;
import edu.wheaton.simulator.statistics.StatisticsManager;
import edu.wheaton.simulator.statistics.TriggerSnapshot;

public class StatisticsManagerTest {

	StatisticsManager sm;
	Grid g;
	String categoryName;
	Prototype prototype;
	HashMap<String, String> fields;
	int population;
	ImmutableSet<AgentID> children;
	Integer step;
	PrototypeSnapshot protoSnap;
	PrototypeSnapshot protoSnap2;
	Recorder recorder;

	/*
	 * Helper method used in tests
	 */
	public Set<TriggerSnapshot> makeTriggerSnapshots() {
		Builder builder = new Trigger.Builder(prototype);
		builder.addBehavioral("behavior");
		builder.addConditional("conditional");
		builder.addName("trigger");
		builder.addPriority(1);

		Trigger trigger = builder.build();

		Set<TriggerSnapshot> tSnaps = new HashSet<TriggerSnapshot>();
		prototype.addTrigger(trigger);
		

		TriggerSnapshot tSnap = SnapshotFactory.makeTriggerSnapshot(trigger.getName(), trigger.getPriority(), trigger
				.getConditions().toString(), trigger.getBehavior().toString());
		tSnaps.add(tSnap);
		return tSnaps;
	}

	@Before
	public void setUp() {
		sm = StatisticsManager.getInstance();
		g = new Grid(10, 10);

		// Add a test PrototypeSnapshot
		categoryName = "testing";
		prototype = new Prototype("tester");
		fields = new HashMap<String, String>();
		population = 50;
		children = prototype.childIDs();
		step = new Integer(1);

		protoSnap = new PrototypeSnapshot(categoryName,
				SnapshotFactory.makeFieldSnapshots(fields), population,
				children, makeTriggerSnapshots(), null, null);

		categoryName = "testing2";
		prototype = new Prototype("tester2");
		population = 40;
		step = new Integer(2);

		// Add another test PrototypeSnapshot
		protoSnap2 = new PrototypeSnapshot(categoryName,
				SnapshotFactory.makeFieldSnapshots(fields), population,
				children, makeTriggerSnapshots(), null, null);
	}

	/**
	 * VERY IMPORTANT TO REMOVE INSTANCE OF STATISTICSMANAGER IN ORDER FOR
	 * TESTS TO PASS
	 */
	@After
	public void tearDown() {
		StatisticsManager.removeInstance();
	}

	@Test
	public void testStatisticsManager() {
		Assert.assertNotNull("Constructor failed", sm);
	}

	@Test
	public void testAddPrototypeSnapshot() {
		Prototype p = new Prototype("TestPrototype");
		Assert.assertNotNull(p);

		PrototypeSnapshot protoSnap = new PrototypeSnapshot("categoryname",
				SnapshotFactory.makeFieldSnapshots(new HashMap<String, String>()),
				100, p.childIDs(), makeTriggerSnapshots(), null, null);
		StatisticsManager.addPrototypeSnapshot(protoSnap);
	}

	@Test
	public void testGetPopVsTime() {
		StatisticsManager.addPrototypeSnapshot(protoSnap);

		// Create data for a test simulation with a random number of steps
		// and random population in each step
		Random R = new Random();
		int numSteps = R.nextInt(10) + 2;
		int[] expected = new int[numSteps];

		// Populate the test "expected" array and add the data to the
		// StatsManager
		for (int i = 0; i < numSteps; i++) {
			expected[i] = R.nextInt(100);

			// Add the appropriate AgentSnapshots to the StatsManager
			for (int pop = 0; pop < expected[i]; pop++) {
				Agent agent = prototype.createAgent(g);
				sm.addGridEntity(new AgentSnapshot(agent.getID(),
						SnapshotFactory.makeFieldSnapshots(agent
								.getCustomFieldMap()), i,
						protoSnap.categoryName, null, null, null, 0, 0));
			}
		}

		int[] result = sm.getPopVsTime(protoSnap.categoryName);
		System.out.println("\ngetPopVsTime()");
		System.out.println("Expected: " + display(expected));
		System.out.println("Result: " + display(result));
		Assert.assertArrayEquals(expected, result);
	}

	/**
	 * Display the contents of an int array
	 * 
	 * @param array
	 *            The array to display
	 * @return The display string
	 */
	private static String display(int[] array) {
		String ret = "[";
		for (int x : array) {
			ret += x + ", ";
		}

		if (array.length > 0)
			ret = ret.substring(0, ret.length() - 2);

		return ret + "]";
	}

	@Test
	public void testGetAvgFieldValue() {
		ArrayList<AgentSnapshot> snaps = new ArrayList<AgentSnapshot>();
		HashSet<AgentID> ids = new HashSet<AgentID>();
		String[] names = new String[] { "bear", "tom", "john", "piglet",
				"reese" };

		/* create snapshots */
		for (int i = 0; i < 5; i++) {
			Agent agent = prototype.createAgent(g);
			try {
				agent.addField("name", names[i]);
				agent.addField("weight", "10");
			} catch (ElementAlreadyContainedException e) {
				e.printStackTrace();
			}
			ids.add(agent.getID());
			for (int s = 1; s < 3; s++) {
				snaps.add(new AgentSnapshot(agent.getID(), SnapshotFactory
						.makeFieldSnapshots(agent.getCustomFieldMap()), s,
						protoSnap.categoryName, null, null, null, 0, 0));
			}
		}

		/* fill table w/ snapshots */
		for (AgentSnapshot snap : snaps) {
			sm.addGridEntity(snap);
		}

		/* test method */
		double[] avg = sm.getAvgFieldValue(protoSnap.categoryName, "weight");

		for (double i : avg)
			System.out.print((int) i + " ");

		for (double i : avg) {
			int a = (int) i;
			org.junit.Assert.assertEquals(10, a);
		}
	}

	@Test
	public void testGetTriggerExecutionsFor() {
		Grid grid = new Grid(10, 10);
		ArrayList<AgentSnapshot> snaps = new ArrayList<AgentSnapshot>();
		HashSet<AgentID> ids = new HashSet<AgentID>();
		HashSet<Agent> agents = new HashSet<Agent>();
		ArrayList<TriggerSnapshot> triggers = new ArrayList<TriggerSnapshot>();
		String[] names = new String[] { "bear", "tom", "john", "piglet",
				"reese" };

		Builder builder = new Trigger.Builder(prototype);
		builder.addBehavioral("true");
		builder.addConditional("true");
		builder.addName("trigger");
		builder.addPriority(1);

		Trigger trigger = builder.build();
		
		triggers.add(SnapshotFactory.makeTriggerSnapshot(trigger.getName(), 1, "true", "true"));
		
		prototype.addTrigger(trigger);
		/* create snapshots */
		for (int i = 0; i < 5; i++) {
//			try {
//				prototype.addField("name", names[i]);
//				prototype.addField("weight", "10");
//			} catch (ElementAlreadyContainedException e) {
//				e.printStackTrace();
//			}
			Agent agent = prototype.createAgent(g);
			grid.addAgent(agent);
			agents.add(agent);
			ids.add(agent.getID());
			snaps.add(new AgentSnapshot(agent.getID(), SnapshotFactory
					.makeFieldSnapshots(agent.getCustomFieldMap()), 0,
						protoSnap.categoryName, null, null,triggers, 0, 0));
		}

//		try {
//			grid.updateEntities();
//		} catch (SimulationPauseException e) {
//			e.printStackTrace();
//		}

		/* create snapshots */
		for (Agent agent : agents) {
				snaps.add(new AgentSnapshot(agent.getID(), SnapshotFactory
						.makeFieldSnapshots(agent.getCustomFieldMap()), 1,
						protoSnap.categoryName, null, null, triggers, 0, 0));
		}
		
		/* fill table w/ snapshots */
		for (AgentSnapshot snap : snaps) {
			sm.addGridEntity(snap);
		}

		/* test method */
		double[] answer = sm.getTriggerExecutionsFor(protoSnap.categoryName, "trigger");

		System.out.println("\nExecutions");
		for (double i : answer)
			System.out.print((int) i + " ");
		System.out.println("");
		
		for (double i : answer) {
			int a = (int) i;
			org.junit.Assert.assertEquals(5, a);
		}
	}
	
	@Test
	public void testGetAvgLifespan() {
		ArrayList<AgentSnapshot> snaps = new ArrayList<AgentSnapshot>();

		// Generate lifespans for each agent that will be inserted.
		Random R = new Random();
		ArrayList<Integer> lifespans = new ArrayList<Integer>();
		int numAgents = R.nextInt(41);
		for (int i = 0; i < numAgents; i++) {
			lifespans.add(R.nextInt(100));
		}

		/* create snapshots */
		for (int i = 0; i < lifespans.size(); i++) {
			Agent agent = prototype.createAgent(g);
			AgentID ID = agent.getID();

			// For each agent, add a snapshot of it at each Step it was alive
			for (int step = 0; step <= lifespans.get(i); step++) {
				snaps.add(new AgentSnapshot(ID, SnapshotFactory
						.makeFieldSnapshots(agent.getCustomFieldMap()), step,
						protoSnap.categoryName, null, null, null, 0, 0));
			}
		}

		/* fill table w/ snapshots */
		for (AgentSnapshot snap : snaps) {
			sm.addGridEntity(snap);
		}

		double actual = sm.getAvgLifespan(protoSnap.categoryName);
		double expected = average(lifespans);
		System.out.println("\nGetAvgLifesppan()");
		System.out.println("Expected: " + expected);
		System.out.println("Actual:   " + actual);
		Assert.assertEquals((int) expected, (int) actual);
	}
	
	@Test
	public void testRecorder(){
		recorder = new Recorder(sm);
		
		recorder.update(g);
	}
	
	/**
	 * Calculate the average of the values in an int array
	 * 
	 * @param array
	 *            The array of integer values
	 * @return The average of the values in the given array
	 */
	private static double average(ArrayList<Integer> list) {
		double avg = 0.0;
		for (int i : list)
			avg += i;
		return avg / list.size();
	}

}
