package edu.wheaton.simulator.datastructures;



/**
 * Field class for modeling variables.
 * The name and type of the field will not be changeable throughout the life of the field.
 * The value of the field will be changeable.
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
	 * @param type Type of variable. Should be int, double, char, or String.
	 * @param value Value of variable. Should match the type; an exception will be thrown if not.
	 *
	 */
	public Field(Object name, Object value){
		this.name = name.toString();
		this.value = new Value(value);
	}
	
	public Field(String name, String value){
		this.name = name;
		this.value = new Value(value);
	}
	
	/**
	 * Clone constructor
	 * @param parent Parent field
	 * @throws StringFormatMismatchException 
	 */
	public Field(Field parent) throws Exception {
		this(parent.name, parent.value);
	}
	
	/**
	 * @param s The new String value to set this field to.
	 * @throws StringFormatMismatchException 
	 */
	public void setValue(Value v){
		value = v;
	}
	
	/**
	 * @return The string representation of this field.
	 */
	@Override
	public String toString() {
		String toReturn = "";
		toReturn += "FIELD:\n";
		toReturn += "  NAME=" + name + "\n";
		toReturn += "  VALUE=" + value + "\n";
		return toReturn;
	}

	/**
	 * 
	 * @return This field's name.
	 */
	public String name() {
		return name;
	}
	
	/**
	 * @return This field's value
	 */
	public Value value(){
		return value;
	}

	
}
