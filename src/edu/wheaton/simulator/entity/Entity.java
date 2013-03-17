package edu.wheaton.simulator.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.datastructure.StringFormatMismatchException;

public class Entity {

	/**
	 * The list of all fields (variables) associated with this agent.
	 */
	private List<Field> fields;
	private EntityID id;
	private static Map<EntityID,Entity> database = new HashMap<EntityID,Entity>();

	public Entity() {
		id = EntityID.genID(this);
		fields = new ArrayList<Field>();
		database.put(id, this);
	}
	
	public static synchronized Entity getEntity(EntityID id){
		return database.get(id);
	}
	
	public static synchronized Entity removeEntity(EntityID id){
		return database.remove(id);
	}

	/**
	 * Note that if a field already exists for this agent with the same name as
	 * the new candidate, it won't be added and will instead throw an
	 * exception.
	 * 
	 * @param s
	 *            The text representation of this field.
	 * @throws ElementAlreadyContainedException
	 * @throws StringFormatMismatchException
	 */
	public void addField(String name, String value)
			throws ElementAlreadyContainedException, Exception {

		// Check to see if it's contained.
		Field contained = null;
		for (Field f : fields) {
			if (f.getName().equals(name)) {
				contained = f;
				break;
			}
		}
		if (contained != null)
			throw new ElementAlreadyContainedException();
		fields.add(new Field(name, value));
	}

	/**
	 * Removes a field from this Entity.
	 * 
	 * @param name
	 */
	public void removeField(String name) {
		for (Field f : fields) {
			if (f.getName().equals(name)) {
				fields.remove(f);
				break;
			}
		}
	}

	public Field getField(String name) {
		for (Field f : fields) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		throw new NoSuchElementException();
	}

	public List<Field> getFields() {
		return fields;
	}

	public EntityID getID() {
		return id;
	}
}
