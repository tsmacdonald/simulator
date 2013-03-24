package edu.wheaton.simulator.test.statistics;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.statistics.FieldSnapshot;

public class FieldSnapshotCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

	@Test
	public void fieldSnapshotTest() {
		FieldSnapshot snap = new FieldSnapshot("name", "akon");
		Assert.assertNotNull(snap);
	}
}
