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
	 * The variable name for this field.
	 */
	private String name;

	/**
	 * The value for this field.
	 */
	private String value;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The variable name for the field.
	 * @param value
	 *            The starting value for the field.
	 */
	public Field(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public Field(String strRepresentation) {
		String[] params = strRepresentation.split(":");
		if (!params[0].equals("Field")) {
			System.out.println("Tried to parse a Field from string, but assert failed.");
			(new Exception()).printStackTrace();
			System.exit(1);
		}
		this.name = params[1];
		this.value = params[2];
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
	 * @return The string representation of this field. Note: easily parsed with
	 *         toString().split(":")
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
	public String getValue() {
		return value;
	}

	/**
	 * @return Integer.valueOf(getValue())
	 */
	public Integer getIntValue() {
		return getDoubleValue().intValue();
	}

	/**
	 * @return Integer.valueOf(getValue())
	 */
	public Double getDoubleValue() {
		return Double.valueOf(value);
	}

	/**
	 * @return Boolean.valueOf(getValue())
	 */
	public Boolean getBoolValue() {
		return Boolean.valueOf(value);
	}
}
