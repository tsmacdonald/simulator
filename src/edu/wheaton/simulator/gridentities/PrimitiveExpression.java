package edu.wheaton.simulator.gridentities;

public class PrimitiveExpression {
	/**
	 * Enum for variable type.
	 * @author Simon Swenson
	 *
	 */
	protected enum Type { 
		INT,
		DOUBLE,
		CHAR,
		STRING;

		public static Type parseString(String typeString) {
			// TODO Should final else throw an exception rather than return null?
			
			if (typeString.toUpperCase().equals("INT"))
				return INT;
			else if (typeString.toUpperCase().equals("DOUBLE"))
				return DOUBLE;
			else if (typeString.toUpperCase().equals("CHAR"))
				return CHAR;
			else if (typeString.toUpperCase().equals("STRING"))
				return STRING;
			else
				return null;
		}
	}
	
	/**
	 * The type of this field (int, double, char, String, AgentID).
	 */
	protected Type type;
	
	/**
	 * The value of this field.
	 */
	protected String value;
	
	/**
	 * Default constructor
	 */
	public PrimitiveExpression(){
		type = null; 
		value = ""; 
	}
	
	/**
	 * Constructor.
	 * @param type Type of variable. Should be int, double, char, or String or Agent ID (index in the array of all agents held by the grid)
	 * @param value Value of variable. Should match the type; an exception will be thrown if not.
	 * @throws Exception 
	 */
	public PrimitiveExpression(Type type, String value) throws Exception {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Clone constructor
	 * @param parent Parent field
	 */
	public PrimitiveExpression(PrimitiveExpression parent) {
		type = parent.type;
		value = parent.value;
	}
	
	public PrimitiveExpression evaluate(Agent me, Agent other) throws Exception{
		// TODO Method stub
		return this; 
	}
 	
	/**
	 * @return True if int, false otherwise.
	 */
	public boolean isInt() {
		if (type == Type.INT) 
			return true;
		return false;
	}
	
	/**
	 * @return True if double, false otherwise.
	 */
	public boolean isDouble() {
		if(type == Type.DOUBLE) 
			return true;
		return false;
	}
	
	/**
	 * @return True if char, false otherwise.
	 */
	public boolean isChar() {
		if(type == Type.DOUBLE) 
			return true;
		return false;
	}
	
	/**
	 * @return True if String, false otherwise.
	 */
	public boolean isString() {
		if(type == Type.DOUBLE) 
			return true;
		return false;
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
}
