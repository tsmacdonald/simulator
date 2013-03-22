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
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.simulation.Grid;

public class StatisticsFacade {
	
	public Agent getAgent() {
		// TODO properly initialize variables
		Grid grid = null;
		Color color = null;
		byte[] design = null;
		Agent agent = new Agent(grid, color, design);
		AgentID id = agent.getID();
		return id;
	}
	
	public Field getField() {
		Entity entity = new Entity();
		Field field = entity.getField();
		return field;
	}

}
