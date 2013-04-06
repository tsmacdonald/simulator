package edu.wheaton.simulator.statistics;

import java.util.Map.Entry;

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
	 * @param id The PrototypeID of this prototype. 
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
	
	/**
	 * Produce a string serializing this object
	 * @return a String containing all of the data in this snapshot
	 * 
	 * Format: (Stuff in parentheses is just notes - not actually there)
	 * -----------------------------------------------------------------
	 * PrototypeSnapshot
	 * Dog (categoryName)
	 * DefaultFields: FieldSnapshot Name Value
	 * DefaultFields: FieldSnapshot Name Value
	 * Children: 3345
	 * Children: 1237
	 * Children: 9457
	 * 12 (population)
	 * 35 (step) 
	 */
	public String serialize(){
		String s = "PrototypeSnapshot";
		s += "\n" + categoryName; 
		
		//Serialize the defaultFields map
		for (Entry<String, FieldSnapshot> entry : defaultFields.entrySet()) {
			s += "\nDefaultFields: " + entry.getValue().serialize();
		}
		
		//Serialize the Children set
		for (AgentID a : children) {
			s += "\nChildren: " + a.getInt(); 
		}
		
		s += "\n" + population; 
		s += "\n" + step; 
		
		return s; 
	}

}
