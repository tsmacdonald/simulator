package edu.wheaton.simulator.statistics;

/**
 * Represents a snapshot of a particular field of an agent in time. 
 * @author Daniel Gill
 */
public class FieldSnapshot {
	
	public final String name; 
	
	public final Type type; 
	
	public final String value; 
	
	/**
	 * Constructor. 
	 * @param name The name of this particular field. 
	 * @param type The type of the field. 
	 * @param value A string representation of the field's value.
	 */
	public FieldSnapshot(String name, Type type, String value) {
		this.name = name; 
		this.type = type; 
		this.value = value; 
	}
	
}
