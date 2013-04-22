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
	protected Map<String, String> fields;

	/**
	 * Instantiates an empty map of fields
	 */
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
	public Field updateField(String name, String value)
			throws NoSuchElementException {
		name = formatFieldName(name);
		assertHasField(name);
		String oldvalue = putField(name, value);
		return new Field(name, oldvalue);
	}

	/**
	 * Adds the given field to this entity
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	private String putField(String name, String value) {
		return fields.put(name.toString(), value.toString());
	}

	/**
	 * Removes a field from this Entity and returns it.
	 */
	public Field removeField(String name) throws NoSuchElementException {
		name = formatFieldName(name);
		assertHasField(name);
		String value = fields.remove(name.toString());
		return new Field(name, value);
	}

	/**
	 * Provides a field object that represents the field with the given name.
	 * 
	 * @param name
	 * @return
	 */
	public Field getField(String name) {
		name = formatFieldName(name);
		assertHasField(name);
		return new Field(name.toString(), getFieldValue(name));
	}

	/**
	 * Provides the actual value associated with the field that has the given
	 * name.
	 * 
	 * @param name
	 * @return
	 */
	public String getFieldValue(String name) {
		name = formatFieldName(name);
		assertHasField(name);
		return fields.get(name.toString());
	}

	/**
	 * Throws an NoSuchElementException if the Entity does not contain a field
	 * with the given name.
	 * 
	 * @param name
	 * @throws NoSuchElementException
	 */
	private void assertHasField(String name) throws NoSuchElementException {
		if (!hasField(name)) {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Throws an ElementAlreadyContainedException if the Entity contains a
	 * field with the given name.
	 * 
	 * @param name
	 * @throws ElementAlreadyContainedException
	 */
	private void assertNoSuchField(String name)
			throws ElementAlreadyContainedException {
		if (hasField(name))
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
	 * Provides this Entity's custom field names and values. This method will
	 * remove the default values that are created.
	 * 
	 * @return String to String map
	 */
	private static String formatFieldName(String name) {
		String fieldName = name.toString();

		if (fieldName.charAt(0) == '\''
				&& fieldName.charAt(fieldName.length() - 1) == '\'')
			fieldName = fieldName.substring(1, fieldName.length() - 1);

		return fieldName;
	}
}
