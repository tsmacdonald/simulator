package edu.wheaton.simulator.statistics;

import edu.wheaton.simulator.datastructure.*;

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
	 * A string representation of the field's value.
	 */
	public final Value value; 
	
	/**
	 * Constructor. 
	 * @param name The name of this particular field. 
	 * @param type The type of the field. 
	 * @param value A string representation of the field's value.
	 */
	public FieldSnapshot(String name, Value value) {
		this.name = name; 
		this.value = value; 
	}
	
}
