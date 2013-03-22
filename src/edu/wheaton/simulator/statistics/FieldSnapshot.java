package edu.wheaton.simulator.statistics;

/**
 * Represents a snapshot of a particular field of an agent in time.
 * 
 * @author Daniel Gill
 */
class FieldSnapshot {

	/**
	 * The name of this particular field.
	 */
	public final String name;

	/**
	 * A string representation of the field's value.
	 */
	public final String value;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The name of this particular field.
	 * @param type
	 *            The type of the field.
	 * @param value
	 *            A string representation of the field's value.
	 */
	public FieldSnapshot(String name, String value) {
		this.name = name;
		this.value = value;
	}

}
