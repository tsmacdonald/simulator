package edu.wheaton.simulator.datastructures;



public class Primitive {
	
	/**
	 * Enum for variable type. It's public so other classes can access it.
	 * @author Simon Swenson
	 *
	 */
	public enum Type { 
		INT,
		DOUBLE,
		CHAR,
		STRING;

		public static Type parseString(String typeString) throws Exception {
			if (typeString.toUpperCase().equals("INT"))
				return INT;
			else if (typeString.toUpperCase().equals("DOUBLE"))
				return DOUBLE;
			else if (typeString.toUpperCase().equals("CHAR"))
				return CHAR;
			else if (typeString.toUpperCase().equals("STRING"))
				return STRING;
			else
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * The type of this field (int, double, char, String, AgentID).
	 */
	private Type type;
	
	/**
	 * The value of this field.
	 */
	private String value;
	
	/**
	 * Default constructor
	 */
	public Primitive(){
		type = null; 
		value = ""; 
	}
	
	/**
	 * Constructor.
	 * @param type Type of variable. Should be int, double, char, or String or Agent ID (index in the array of all agents held by the grid)
	 * @param value Value of variable. Should match the type; an exception will be thrown if not.
	 * @throws Exception 
	 */
	public Primitive(Type type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Clone constructor
	 * @param parent Parent field
	 */
	public Primitive(Primitive parent) {
		type = parent.type;
		value = parent.value;
	}
 	
	/**
	 * @return True if int, false otherwise.
	 */
	public Boolean isInt() {
		if (type == Type.INT) 
			return true;
		return false;
	}
	
	/**
	 * @return True if double, false otherwise.
	 */
	public Boolean isDouble() {
		if(type == Type.DOUBLE) 
			return true;
		return false;
	}
	
	/**
	 * @return True if char, false otherwise.
	 */
	public Boolean isChar() {
		if(type == Type.DOUBLE) 
			return true;
		return false;
	}
	
	/**
	 * @return True if String, false otherwise.
	 */
	public Boolean isString() {
		if(type == Type.DOUBLE) 
			return true;
		return false;
	}
	
	/**
	 * @return The int value of this field, if possible.
	 * @throws StringFormatMismatchException 
	 */
	public Integer intValue() throws StringFormatMismatchException {
		Integer toReturn;
		if(type == Type.STRING || type == Type.DOUBLE) 
			throw new StringFormatMismatchException();
		else if(type == Type.CHAR)
			toReturn = (int) value.charAt(0);
		else
			toReturn = Integer.parseInt(value);
		return toReturn;
	}
	
	/**
	 * @return The double value of this field, if possible.
	 * @throws StringFormatMismatchException 
	 */
	public Double doubleValue() throws StringFormatMismatchException {
		Double toReturn;
		if(type == Type.STRING) 
			throw new StringFormatMismatchException();
		else if(type == Type.CHAR)
			toReturn = (double) value.charAt(0);
		else 
			toReturn = Double.parseDouble(value);
		return toReturn;
	}
	
	/**
	 * @return The char value of this field, if possible.
	 * @throws StringFormatMismatchException 
	 */
	public Character charValue() throws StringFormatMismatchException {
		if(type != Type.CHAR) 
			throw new StringFormatMismatchException();
		return value.charAt(0);
	}
	
	/**
	 * @return The String value of this field.
	 */
	public String stringValue() {
		return toString();
	}
	
	public Type type(){
		return type;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
