package edu.wheaton.simulator.statistics;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Represents a category of agent at a particular moment in time. 
 * @author Daniel Gill, Akon Ngoh
 */
public class EntityPrototype {

	/**
	 * The name of this category of agent. 
	 */
	public final String categoryName;

	/**
	 * The default field values for agents of this category. 
	 */
	public final ImmutableMap<String, FieldSnapshot> defaultFields;
	
	/**
	 * Constructor. 
	 * @param categoryName The name of the category of agent. 
	 * @param defaultFields The default fields for this category of agent. 
	 */
	public EntityPrototype(String categoryName, Map<String, FieldSnapshot> defaultFields) { 
		this.categoryName = categoryName; 
		this.defaultFields = new ImmutableMap.Builder<String, FieldSnapshot>().putAll(defaultFields).build();
	}
	
}
