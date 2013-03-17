package edu.wheaton.simulator.datastructure;

/**
 * Field class for modeling variables. The name and type of the field will not
 * be changeable throughout the life of the field. The value of the field will
 * be changeable.
 * 
 * @author Simon Swenson
 * 
 */
public class Field {

	/**
	 * The name for this field.
	 */
	private String name;
	private Value value;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            Type of variable. Should be int, double, char, or String.
	 * @param value
	 *            Value of variable. Should match the type; an exception will
	 *            be thrown if not.
	 * 
	 */
	public Field(Object name, Object value) {
		this.name = name.toString();
		this.value = new Value(value);
	}

	public Field(String strRepresentation) {
		String[] params = strRepresentation.split(":");
		this.name = params[1];
		this.value = new Value(params[2]);
	}

	/**
	 * Clone constructor
	 * 
	 * @param parent
	 *            Parent field
	 */
	public Field(Field parent) {
		this(parent.name, parent.value);
	}

	/**
	 * @param v
	 *            The new String value to set this field to.
	 * @throws StringFormatMismatchException
	 */
	public void setValue(Value v) {
		value = v;
	}

	/**
	 * @return The string representation of this field. Note: easily parsed
	 *         with toString().split(":")
	 */
	@Override
	public String toString() {
		return "Field:" + getName() + ":" + getValue();
	}

	/**
	 * 
	 * @return This field's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return This field's value
	 */
	public Value getValue() {
		return value;
	}

}
