/**
 * StatisticsFacade.java
 * 
 * Facade that takes different information
 * and returns it to the GUI team. 
 * 
 * @author Nico Lasta
 * Wheaton College
 * CSCI 335
 * Spring 2013
 */

package edu.wheaton.simulator.statistics;

import java.awt.Color;

import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.simulation.Grid;

public class StatisticsFacade {
	
	/**
	 * getAgent returns the AgentID of the desired Agent,
	 * not the Agent itself.
	 * TODO Subject to change
	 * 
	 * @return AgentID of Agent
	 */
	public AgentID getAgent() {
		// TODO properly initialize variables
		Grid grid = null;
		Color color = null;
		byte[] design = null;
		Agent agent = new Agent(grid, color, design);
		AgentID id = agent.getAgentID();
		return id;
	}
	
	/**
	 * getField returns the field object (?) 
	 * of an Entity.
	 * TODO Make a better description?
	 * 
	 * @return Field of Entity
	 */
	public Field getField() {
		Entity entity = new Entity();
		Field field = entity.getField(Object);
		return field;
	}

}
