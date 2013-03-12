package edu.wheaton.simulator.statistics;

import edu.wheaton.simulator.datastructures.*;

/**
 * Represents a snapshot of a particular field of an agent in time. 
 * @author Daniel Gill
 */
public class FieldSnapshot {
	
	/**
	 * The name of this particular field. 
	 */
	public final String name; 
	
	/**
	 * The type of this field. 
	 */
	public final Primitive.Type type; 
	
	/**
	 * A string representation of the field's value.
	 */
	public final String value; 
	
	/**
	 * Constructor. 
	 * @param name The name of this particular field. 
	 * @param type The type of the field. 
	 * @param value A string representation of the field's value.
	 */
	public FieldSnapshot(String name, Primitive.Type type, String value) {
		this.name = name; 
		this.type = type; 
		this.value = value; 
	}
	
}
