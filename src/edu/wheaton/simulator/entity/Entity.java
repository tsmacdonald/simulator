package edu.wheaton.simulator.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Field;

public class Entity {

	/**
	 * The list of all fields (variables) associated with this agent.
	 */
	private Map<String, String> fields;

	public Entity() {
		fields = new HashMap<String, String>();
	}

	/**
	 * Note that if a field already exists for this agent with the same name as
	 * the new candidate, it won't be added and will instead throw an
	 * exception.
	 * 
	 * @throws ElementAlreadyContainedException
	 */
	public void addField(String name, String value)
			throws ElementAlreadyContainedException {
		name = formatFieldName(name);
		assertNoSuchField(name);
		putField(name, value);
	}

	/**
	 * if the entity has a field by that name it updates it's value. Otherwise
	 * throws NoSuchElementException()
	 * 
	 * @return returns the old field
	 */
	public Field updateField(String name, String value) {
		name = formatFieldName(name);
		assertHasField(name);
		String oldvalue = putField(name, value);
		return new Field(name, oldvalue);
	}

	private String putField(String name, String value) {
		return fields.put(name.toString(), value.toString());
	}

	/**
	 * Removes a field from this Entity and returns it.
	 */
	public Field removeField(String name) {
		name = formatFieldName(name);
		assertHasField(name);
		String value = fields.remove(name.toString());
		return new Field(name, value);
	}

	public Field getField(String name) {
		name = formatFieldName(name);
		assertHasField(name);
		return new Field(name.toString(), getFieldValue(name));
	}

	public String getFieldValue(String name) {
		name = formatFieldName(name);
		assertHasField(name);
		return fields.get(name.toString());
	}

	private void assertHasField(String name) {
		if (hasField(name) == false)
			throw new NoSuchElementException();
	}

	private void assertNoSuchField(String name)
			throws ElementAlreadyContainedException {
		if (hasField(name) == true)
			throw new ElementAlreadyContainedException();
	}

	/**
	 * Tells if this prototype has a Field corresponding to the given name
	 * 
	 * @param name
	 * @return
	 */
	public boolean hasField(String name) {
		return (fields.containsKey(name.toString()));
	}

	/**
	 * Provides this Entity's field names and values
	 * 
	 * @return String to String map
	 */
	public Map<String, String> getFieldMap() {
		return fields;
	}

	/**
	 * Provides this Entity's custom field names and values. This method
	 * basically removes the default values that are created with Agents.
	 * 
	 * @return String to String map
	 */
	public Map<String, String> getCustomFieldMap() {
		Map<String, String> toReturn = new HashMap<String, String>(fields);
		toReturn.remove("x");
		toReturn.remove("y");
		toReturn.remove("colorRed");
		toReturn.remove("colorBlue");
		toReturn.remove("colorGreen");
		return toReturn;
	}

	private static String formatFieldName(String name) {
		String fieldName = name.toString();

		// to make Expression parsing more lenient
		if (fieldName.charAt(0) == '\''
				&& fieldName.charAt(fieldName.length() - 1) == '\'')
			fieldName = fieldName.substring(1, fieldName.length() - 1);

		return fieldName;
	}
}
