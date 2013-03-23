package edu.wheaton.simulator.statistics;

import java.util.Map;

import com.google.common.collect.ImmutableMap;


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
	 * The number of children this prototype has
	 */
	public final int childPopulation; 

	/**
	 * The default field values for agents of this category.
	 */
	public final ImmutableMap<String, FieldSnapshot> defaultFields;

	/**
	 * Constructor.
	 * 
	 * @param categoryName
	 *            The name of the category of agent.
	 * @param defaultFields
	 *            The default fields for this category of agent.
	 */
	public PrototypeSnapshot(String categoryName, int childPopulation, 
			Map<String, FieldSnapshot> defaultFields) {
		this.categoryName = categoryName;
		this.childPopulation = childPopulation; 
		this.defaultFields = new ImmutableMap.Builder<String, FieldSnapshot>()
				.putAll(defaultFields).build();
	}

}
