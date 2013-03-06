package edu.wheaton.simulator.gridentities;

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
