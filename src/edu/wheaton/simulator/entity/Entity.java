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
	private final EntityID id;

	public Entity() {
		id = new EntityID();
		fields = new HashMap<String, String>();
	}

	/**
	 * Note that if a field already exists for this agent with the same name as
	 * the new candidate, it won't be added and will instead throw an
	 * exception.
	 * 
	 * @throws ElementAlreadyContainedException
	 */
	public void addField(Object name, Object value)
			throws ElementAlreadyContainedException {
		assertNoSuchField(name);
		fields.put(name.toString(), value.toString());
	}

	/**
	 * if the entity has a field by that name it updates it's value. Otherwise
	 * throws NoSuchElementException()
	 * 
	 * @return returns the old field
	 */
	public Field updateField(Object name, Object value) {
		assertHasField(name);
		String oldvalue = fields.put(name.toString(), value.toString());
		return new Field(name, oldvalue);
	}

	/**
	 * Removes a field from this Entity and returns it.
	 */
	public Field removeField(Object name) {
		assertHasField(name);
		String value = fields.remove(name.toString());
		return new Field(name, value);
	}

	public Field getField(Object name) {
		assertHasField(name);
		return new Field(name.toString(), getFieldValue(name));
	}

	public String getFieldValue(Object name) {
		assertHasField(name);
		return fields.get(name.toString());
	}
	
	private void assertHasField(Object name){
		if(hasField(name) == false)
			throw new NoSuchElementException();
	}
	
	private void assertNoSuchField(Object name) throws ElementAlreadyContainedException{
		if(hasField(name) == true)
			throw new ElementAlreadyContainedException();
	}

	/**
	 * Tells if this prototype has a Field corresponding to the given name
	 * 
	 * @param name
	 * @return
	 */
	public boolean hasField(Object name) {
		return (fields.containsKey(name.toString()));
	}

	public Map<String, String> getFieldMap() {
		return fields;
	}

	public EntityID getEntityID() {
		return id;
	}
}
