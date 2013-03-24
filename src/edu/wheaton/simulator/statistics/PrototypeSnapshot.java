package edu.wheaton.simulator.statistics;

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
	public final String categoryName;

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
	public final int population;

	/**
	 * The point in the simulation at which this snapshot was taken. 
	 */
	public final int step; 

	/**
	 * Constructor. 
	 * @param categoryName The name of this prototype. 
	 * @param fields The default fields for this prototype. 
	 * @param population The population of this prototype. 
	 * @param poulation The number of this prototype's children. 
	 * @param step The current moment in time. 
	 */
	public PrototypeSnapshot(String categoryName,
			ImmutableMap<String, FieldSnapshot> fields, int population,
			ImmutableSet<AgentID> children, Integer step) {
		this.categoryName = categoryName; 
		this.defaultFields = fields; 
		this.children = children;
		this.population = children.size(); 
		this.step = step; 
	}

}
