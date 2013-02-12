package edu.wheaton.universalsim.agent;


/**
 * Field class for modeling variables.
 * The name and type of the field will not be changeable throughout the life of the field.
 * The value of the field will be changeable.
 * @author Simon Swenson
 *
 */
public class Field {

	/**
	 * Enum for variable type.
	 * @author Simon Swenson
	 *
	 */
	private enum Type { INT, DOUBLE, CHAR, STRING;
		
	}
	
	/**
	 * The name for this field.
	 */
	private String name;
	
	/**
	 * The type of this field (int, double, char, String).
	 */
	private Type type;
	
	/**
	 * The value of this field.
	 */
	private String value;
	
	/**
	 * Constructor.
	 * @param type Type of variable. Should be int, double, char, or String.
	 * @param value Value of variable. Should match the type; an exception will be thrown if not.
	 * @throws StringFormatMismatchException 
	 */
	public Field(String name, String type, String value) throws StringFormatMismatchException {
		this.name = name;
		switch(type.toUpperCase()) {
			case "INT":
				this.type = Type.INT;
				setValue(Integer.parseInt(value));
				break;
			case "DOUBLE":
				this.type = Type.DOUBLE;
				setValue(Double.parseDouble(value));
				break;
			case "CHAR":
				this.type = Type.CHAR;
				setValue(value.charAt(0));
				break;
			case "STRING":
				this.type = Type.STRING;
				this.value = value;
				break;
			default:
				throw new StringFormatMismatchException();
		}
		
		this.value = value;
	}
	
	/**
	 * Clone constructor
	 * @param parent Parent field
	 */
	public Field(Field parent) {
		name = parent.name;
		type = parent.type;
		value = parent.value;
		
	}
 	
	/**
	 * @return True if int, false otherwise.
	 */
	public boolean isInt() {
		if (type == Type.INT) return true;
		else return false;
	}
	
	/**
	 * @return True if double, false otherwise.
	 */
	public boolean isDouble() {
		if(type == Type.DOUBLE) return true;
		else return false;
	}
	
	/**
	 * @return True if char, false otherwise.
	 */
	public boolean isChar() {
		if(type == Type.DOUBLE) return true;
		else return false;
	}
	
	/**
	 * @return True if String, false otherwise.
	 */
	public boolean isString() {
		if(type == Type.DOUBLE) return true;
		else return false;
	}
	
	/**
	 * @return The int value of this field, if possible.
	 * @throws StringFormatMismatchException 
	 */
	public int intValue() throws StringFormatMismatchException {
		int toReturn;
		if(type == Type.STRING || type == Type.DOUBLE) throw new StringFormatMismatchException();
		else if(type == Type.CHAR) {
			toReturn = value.charAt(0);
		}
		else {
			toReturn = Integer.parseInt(value);
		}
		return toReturn;
	}
	
	/**
	 * @return The double value of this field, if possible.
	 * @throws StringFormatMismatchException 
	 */
	public double doubleValue() throws StringFormatMismatchException {
		double toReturn;
		if(type == Type.STRING) throw new StringFormatMismatchException();
		else if(type == Type.CHAR) {
			toReturn = value.charAt(0);
		}
		else {
			toReturn = Double.parseDouble(value);
		}
		return toReturn;
	}
	
	/**
	 * @return The char value of this field, if possible.
	 * @throws StringFormatMismatchException 
	 */
	public char charValue() throws StringFormatMismatchException {
		if(type != Type.CHAR) throw new StringFormatMismatchException();
		return value.charAt(0);
	}
	
	/**
	 * @return The String value of this field.
	 */
	public String stringValue() {
		return value;
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
	public String toString() {
		String toReturn = "";
		toReturn += "FIELD:\n";
		toReturn += "  NAME=" + name + "\n";
		toReturn += "  TYPE=" + type.toString() + "\n";
		toReturn += "  VALUE=" + value + "\n";
		return toReturn;
	}

	/**
	 * 
	 * @return This field's name.
	 */
	public String getName() {
		return name;
	}
}
