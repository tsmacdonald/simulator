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
	//private final AgentID id;

	//private static Map<AgentID, Entity> database = new HashMap<AgentID, Entity>();

	public Entity() {
		//id = new AgentID();
		fields = new HashMap<String, String>();
		//database.put(id, this);
	}

//	public static synchronized Entity getEntity(AgentID agentID) {
//		return database.get(agentID);
//	}

//	public static synchronized Entity removeEntity(AgentID agentID) {
//		return database.remove(agentID);
//	}

	/**
	 * Note that if a field already exists for this agent with the same name as
	 * the new candidate, it won't be added and will instead throw an
	 * exception.
	 * 
	 * @throws ElementAlreadyContainedException
	 */
	public void addField(Object name, Object value)
			throws ElementAlreadyContainedException {
		if (fields.containsKey(name.toString()))
			throw new ElementAlreadyContainedException();
		fields.put(name.toString(), value.toString());
	}

	/*
	 * if the entity has a field by that name it updates it's value. Otherwise
	 * throws NoSuchElementException()
	 * 
	 * @return returns the old field
	 */
	public Field updateField(Object name, Object value) {
		if (fields.containsKey(name.toString()) == false)
			throw new NoSuchElementException();
		String oldvalue = fields.put(name.toString(), value.toString());
		return new Field(name, oldvalue);
	}

	/**
	 * Removes a field from this Entity and returns it.
	 */
	public Field removeField(Object name) {
		if (fields.containsKey(name.toString()) == false)
			throw new NoSuchElementException();
		String value = fields.remove(name.toString());
		return new Field(name, value);
	}

	public Field getField(Object name) {
		if (fields.containsKey(name.toString()) == false)
			throw new NoSuchElementException();
		return new Field(name.toString(), fields.get(name.toString()));
	}

	public String getFieldValue(Object name) {
		if (fields.containsKey(name.toString()) == false)
			throw new NoSuchElementException();
		return fields.get(name.toString());
	}

	public Map<String, String> getFieldMap() {
		return fields;
	}

//	public AgentID getID() {
//		return id;
//	}
}
