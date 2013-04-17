package edu.wheaton.simulator.test.statistics;

/**
 * A JUnit test case for testing BehaviorSnapshot.java.
 * 
 * @author Nico Lasta
 * Wheaton College, CSCI 335
 * Spring 2013
 * 11 Apr 2013
 */

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.wheaton.simulator.behavior.AbstractBehavior;
import edu.wheaton.simulator.behavior.MoveBehavior;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;

@RunWith(JUnit4.class)
public class BehaviorSnapshotCase {

	Grid grid;
	Prototype prototype;
	Agent actor, recipient;
	AbstractBehavior behavior;
	Integer step;

	@Before
	public void setUp() throws Exception {
		grid = new Grid(1, 1);
		prototype = new Prototype(grid, "BehaviorSnapshotTest");
		actor = prototype.createAgent();
		recipient = prototype.createAgent();
		behavior = new MoveBehavior();
		step = 23; // arbitrary

	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void behaviorSnapshotTest() {
//		BehaviorSnapshot behaviorSnap = new BehaviorSnapshot(actor.getID(),
//				behavior, recipient.getID(), step);
//		Assert.assertNotNull("BehaviorSnapshot not created", behaviorSnap);
//		System.out.println(behaviorSnap.serialize());
//		
//	}

}
