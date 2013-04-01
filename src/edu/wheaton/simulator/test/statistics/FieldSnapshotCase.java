package edu.wheaton.simulator.test.statistics;

/**
 * A JUnit test case for testing FieldSnapshot.java.
 * 
 * @author Akonwi Ngoh
 * Wheaton College, CSCI 335
 * Spring 2013
 */

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.statistics.FieldSnapshot;

public class FieldSnapshotCase {

	/**
	 * Auto-generated method stub.
	 */
	@Before
	public void setUp() {
		//TODO FieldSnapshotCase.setUp() is empty
	}

	/**
	 * Auto-generated method stub.
	 */
	@After
	public void tearDown() {
		//TODO FieldSnapshotCase.setUp() is empty
	}
	
	/**
	 * Tests the following things:
	 * 		1. A FieldSnapshot object was created.
	 * 		2. One of the parameters passed into FieldSnapshot is not an integer.
	 * 		3. One of the parameters passed into FieldSnapshot is an integer.
	 */
	@Test
	public void fieldSnapshotTest() {
		FieldSnapshot fieldSnap = new FieldSnapshot("name", "akon");
		Assert.assertNotNull(fieldSnap);
		Assert.assertFalse(fieldSnap.isNumber);
		FieldSnapshot fieldSnapWithInt = new FieldSnapshot("akon", "12345");
		Assert.assertTrue(fieldSnapWithInt.isNumber);
		Assert.assertNotNull(fieldSnapWithInt);
	}
	
	/**
	 * Tests the serialize() method 
	 */
	@Test
	public void serializeTest(){
		FieldSnapshot fieldSnap = new FieldSnapshot("Age", "1");
		String expected = "FieldSnapshot Age 1"; 
		Assert.assertEquals(expected, fieldSnap.serialize()); 	
	}
}
