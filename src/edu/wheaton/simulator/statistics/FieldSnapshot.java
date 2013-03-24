package edu.wheaton.simulator.statistics;

/**
 * Represents a snapshot of a particular field of an agent in time.
 * 
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
	public final String value;
	
	/**
	 * Indicates whether or not the stored value is a number of some sort. 
	 */
	public final boolean isNumber; 
	
	/**
	 * The hidden number corresponding to the value. 
	 */
	private Double numberValue; 

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The name of this particular field.
	 * @param value
	 *            A string representation of the field's value.
	 */
	public FieldSnapshot(String name, String value) {
		this.name = name;
		this.value = value;
		try { 
			numberValue = Double.parseDouble(value); 
		} catch (NumberFormatException e) { 
			numberValue = null; 
		}
		isNumber = numberValue != null; 
	}
	
	/**
	 * Get the numerical value associated with this field's value. 
	 * @return A Double corresponding to value. 
	 * @throws UnsupportedOperationException if the field's value is 
	 */
	public Double getNumericalValue() { 
		if (!isNumber)
			throw new UnsupportedOperationException("Field " + name + " has no numerical value.");
		return numberValue; 
	}

}
