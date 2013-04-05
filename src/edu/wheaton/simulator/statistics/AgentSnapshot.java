package edu.wheaton.simulator.statistics;

import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;
import edu.wheaton.simulator.entity.id;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.PrototypeID;


/**
 * A class representing all the information to track from agents in the game.
 * 
 * @author Akon, Daniel Gill
 * 
 */
public class AgentSnapshot {

	/*
	 * The unique id of the agent for this snapshot
	 */
	public final AgentID id;
	
	/**
	 * The present prototype for the category of this Entity.
	 */
	public final PrototypeID prototype;
	
	/**
	 * The saved fields of this entity.
	 */
	public final ImmutableMap<String, FieldSnapshot> fields;

	/**
	 * The point in the simulation at which this snapshot was taken.
	 */
	public final Integer step;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The ID of the Agent associated with this snapshot.
	 * @param fields
	 *            The current values of the fields of the Entity
	 * @param step
	 *            The step in the simulation associated with this snapshot.
	 * @param prototype
	 *            The prototype for this category of Agent.
	 */
	public AgentSnapshot(AgentID id, ImmutableMap<String, FieldSnapshot> fields,
			Integer step, PrototypeID prototype) {
		this.id = id;
		this.step = step;
		this.fields = fields;
		this.prototype = prototype;
	}

	/**
	 * Produce a string serializing this object
	 * @return a String containing all of the data in this snapshot
	 * 
	 * Format: (Stuff in parentheses is just notes - not actually there)
	 * -----------------------------------------------------------------
	 * AgentSnapshot
	 * 145 (id - just an int)
	 * Fields: FieldSnapshot Name Value
	 * Fields: FieldSnapshot Name Value
	 * 3 (step - an int)
	 * 12 (PrototypeID - an int)
	 */
	public String serialize(){
		String s = "AgentSnapshot";
		s += "\n" + id.getInt();
		
		for (Entry<String, FieldSnapshot> entry : fields.entrySet()) {
			s += "\nFields: " + entry.getValue().serialize();
		}
		
		s += "\n" + step; 
		s += "\n" + prototype.getInt();
		return s; 
	}
}
