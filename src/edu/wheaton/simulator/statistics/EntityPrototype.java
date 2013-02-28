package edu.wheaton.simulator.statistics;

import java.util.HashMap;

/**
 * 
 * @author Daniel Gill, Akon Ngoh
 */
public class EntityPrototype {

	//TODO: Annoy the Agent team about this. 
	private String categoryName; 
	
	//TODO: Document this. 
	private HashMap<String, FieldType> fields;
	
	/**
	 * Determine the type of the field associated with this String.
	 * @param fieldname The name of the field. 
	 * @return A FieldType Enum representing the type of that field. 
	 * @throws NoSuchFieldException If the field does not exist.
	 */
	FieldType getFieldType(String fieldname) throws NoSuchFieldException {
		if (!fields.containsKey(fieldname))
			throw new NoSuchFieldException();
		return fields.get(fieldname);
	}
	
	String getName() { 
		return categoryName;
	}
}
