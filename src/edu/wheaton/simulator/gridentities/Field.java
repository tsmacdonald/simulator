package edu.wheaton.simulator.gridentities;

/**
 * Field class for modeling variables.
 * The name and type of the field will not be changeable throughout the life of the field.
 * The value of the field will be changeable.
 * @author Simon Swenson
 *
 */
public class Field extends Primitive{
	
	/**
	 * The name for this field.
	 */
	private String name;
	
	/**
	 * Constructor.
	 * @param type Type of variable. Should be int, double, char, or String.
	 * @param value Value of variable. Should match the type; an exception will be thrown if not.
	 * @throws StringFormatMismatchException 
	 */
	public Field(String name, Type type, String value) throws Exception {
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Clone constructor
	 * @param parent Parent field
	 * @throws StringFormatMismatchException 
	 */
	public Field(Field parent) throws Exception {
		this(parent.name, parent.type, parent.value);
	}
	
	/**
	 * @param s The new String value to set this field to.
	 * @throws StringFormatMismatchException 
	 */
	public void setValue(String s) throws StringFormatMismatchException {
		if(type == Type.STRING) {
			value = s;
		}
		else {
			throw new StringFormatMismatchException();
		}
	}
	
	/**
	 * @param s The new double value to set this field to.
	 * @throws StringFormatMismatchException 
	 */
	public void setValue(double d) throws StringFormatMismatchException {
		if(type == Type.DOUBLE) {
			value = d + "";
		}
		else {
			throw new StringFormatMismatchException();
		}
	}
	
	/**
	 * @param s The new int value to set this field to.
	 * @throws StringFormatMismatchException 
	 */
	public void setValue(int i) throws StringFormatMismatchException {
		if(type == Type.INT) {
			value = i + "";
		}
		else {
			throw new StringFormatMismatchException();
		}
	}
	
	/**
	 * @param s The new char value to set this field to.
	 * @throws StringFormatMismatchException 
	 */
	public void setValue(char c) throws StringFormatMismatchException {
		if(type == Type.CHAR) {
			value = c + "";
		}
		else {
			throw new StringFormatMismatchException();
		}
	}
	
	/**
	 * @return The string representation of this field.
	 */
	@Override
	public String toString() {
		String toReturn = "";
		toReturn += "FIELD:\n";
		toReturn += "  NAME=" + name + "\n";
		toReturn += "  TYPE=" + type.toString() + "\n";
		toReturn += "  VALUE=" + value + "\n";
		return toReturn;
	}
	
	public Type getType(){
		return this.type; 
	}

	/**
	 * 
	 * @return This field's name.
	 */
	public String getName() {
		return name;
	}
}
