package edu.wheaton.simulator.statistics;

import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.entity.AgentID;


/**
 * Represents a category of agent at a particular moment in time.
 * 
 * @author Daniel Gill, Akon Ngoh
 */
public class PrototypeSnapshot {

	/**
	 * The name of this category of agent.
	 */
	public final String name;

	/**
	 * The default field values for agents of this category.
	 */
	public final ImmutableMap<String, FieldSnapshot> defaultFields;
	
	/**
	 * The children of this Prototype at this point in time. 
	 */
	public final ImmutableSet<AgentID> children;
	
	/**
	 * The present population of this category of Agent. 
	 */
	public final Integer population;
	
	/**
	 * The point in the simulation at which this snapshot was taken. 
	 */
	public final Integer step; 

	/**
	 * Constructor. 
	 * @param name
	 * @param fields
	 * @param population
	 * @param step
	 */
	public PrototypeSnapshot(String name,
			ImmutableMap<String, FieldSnapshot> fields,
			ImmutableSet<AgentID> children, Integer step) {
		this.name = name; 
		this.defaultFields = fields; 
		this.children = children;
		this.population = children.size(); 
		this.step = step; 
	}

}
