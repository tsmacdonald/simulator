package edu.wheaton.simulator.test.datastructure;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.Field;

public class FieldTest {

	private Field field;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testString() {
		field = new Field("name", "rabbit");
		
		Assert.assertEquals(field.getName(), "name");
		Assert.assertEquals(field.getValue(), "rabbit");
//		
////		try {
////			field.getBoolValue();
////			fail("We got a boolean value from a nonsensical string: " + field.getBoolValue());
////		}
////		catch (Exception e) {
////			
////		}
//		
		try {
			field.getDoubleValue();
			fail("We got a double value from a nonsensical string: " + field.getDoubleValue());
		}
		catch (Exception e) {
			
		}
		
		try {
			field.getIntValue();
			fail("We got an int value from a nonsensical string: " + field.getIntValue());
		}
		catch (Exception e) {
			
		}
	}
	
	@Test
	public void testDouble() {
		field = new Field("percentBodyWeight", "0.221");
		
		//no need to test string name and value (see testString)
		
		try {
			field.getBoolValue();
			fail("We got a boolean value from a nonsensical string: " + field.getBoolValue());
		}
		catch (Exception e) {
			
		}
		
		try {
			field.getDoubleValue();
		}
		catch (Exception e) {
			fail("We could not obtain a double representation of the value: " + field.getDoubleValue());
		}
		
		try {
			field.getIntValue();
			fail("We got an int value from a nonsensical string: " + field.getIntValue());
		}
		catch (Exception e) {
			
		}
	}
	
	@Test
	public void testInt() {
		field = new Field("xPos", "13");
		
		try {
			field.getBoolValue();
			fail("We got a boolean value from a nonsensical string: " + field.getBoolValue());
		}
		catch (Exception e) {
			
		}
		
		try {
			field.getDoubleValue();
		}
		catch (Exception e) {
			fail("We could not obtain a double representation of the value: " + field.getDoubleValue());
		}
		
		try {
			field.getIntValue();
		}
		catch (Exception e) {
			fail("We could not obtain an int representation of the value: " + field.getIntValue());
		}
	}

	@Test
	public void testBoolean() {
		field = new Field("isFemale", "true");
		
		try {
			field.getBoolValue();
		}
		catch (Exception e) {
			fail("We could not obtain a boolean representation of the value.");
		}
		
		try {
			field.getDoubleValue();
			fail("We got a double value from a nonsensical string: " + field.getDoubleValue());
		}
		catch (Exception e) {
			
		}
		
		try {
			field.getIntValue();
			fail("We got an int value from a nonsensical string: " + field.getIntValue());
		}
		catch (Exception e) {
			
		}
	}
}
