package edu.wheaton.simulator.test.statistics;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.statistics.FieldSnapshot;
import edu.wheaton.simulator.statistics.PrototypeSnapshot;
import edu.wheaton.simulator.statistics.SnapshotFactory;

public class PrototypeSnapshotCase {
	
	String categoryName;
	Prototype prototype;
	HashMap<String, String> fields;
	int population;
	ImmutableSet<AgentID> children;
	Integer step;

	@Before
	public void setUp() throws Exception {
		children = prototype.childIDs();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	public void prototypeSnapshotTest() {
		PrototypeSnapshot protoSnap = new PrototypeSnapshot(categoryName, prototype.getPrototypeID(),
				SnapshotFactory.makeFieldSnapshots(fields), population,
				children, step);
		
	}

}
