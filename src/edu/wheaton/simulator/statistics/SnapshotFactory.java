package edu.wheaton.simulator.statistics;

import java.util.HashMap;
import java.util.List;

import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.entity.GridEntity;

/**
 * This class will create the Snapshots to be put into the Database
 * 
 * @author akonwi and Daniel Gill
 */

public class SnapshotFactory {

	private SnapshotFactory() {
	}

	/**
	 * This method will put the given entity into an editable database
	 * 
	 * @param entity
	 *            the entity to create snapshot from
	 * @param step
	 *            the int representing this turn and will be used as a column
	 *            index in the database(table)
	 */
	public static EntitySnapshot createEntity(GridEntity entity, int step) {
		EntityID id = entity.getID();

		/**
		 * all the fields of the agent
		 */
		List<Field> entityFields = entity.getFields();

		/*
		 * map of the fields to save in entitySnapShot
		 */
		HashMap<String, FieldSnapshot> fieldsForSnap = new HashMap<String, FieldSnapshot>();

		for (Field currentField : entityFields) {
			FieldSnapshot snap = new FieldSnapshot(currentField.getName(),
					currentField.getValue());
			fieldsForSnap.put(currentField.getName(), snap);
		}

		/*
		 * Create protoype Because this requires me having the initial set of
		 * fields, I think the Observer class should record those on when the
		 * user defines them. Then have a getter method that returns the
		 * 'default fields'
		 * 
		 * Observer class could also possibly create a prototype because it
		 * will have first access to the 'categoryName' and with the Fields, it
		 * can make a map<String, FieldSnapshot> getPrototype(String name)
		 * could be a static method that returns a prototype from a Map<String,
		 * EntityPrototype>
		 */
		EntityPrototypeSnapshot prototype = EntityObserver.getPrototype(entity
				.getName()); // TODO: Annoy angent team about making Arbiter.
		EntitySnapshot snap;
		// determine class of Entity and create snapshot accordingly
		if (entity.getClass() == Agent.class)
			snap = new AgentSnapshot(id, fieldsForSnap, step, prototype, null);
		else
			snap = new SlotSnapshot(id, fieldsForSnap, step, prototype, null);

		return snap;
	}

}
