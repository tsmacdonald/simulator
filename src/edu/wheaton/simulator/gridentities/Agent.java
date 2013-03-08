/**
 * Agent.java
 *
 * Agents model actors in the simulation's Grid.
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gridentities;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wheaton.simulator.datastructures.Field;
import edu.wheaton.simulator.datastructures.StringFormatMismatchException;
import edu.wheaton.simulator.datastructures.Type;
import edu.wheaton.simulator.datastructures.expression.BoolExpression;
import edu.wheaton.simulator.simulation.Grid;

public class Agent extends GridEntity {
    
    /**
     * The list of all triggers/events associated with this agent.
     */
    private List<Trigger> triggers;
    
    /**
     * The list of all children of this Agent if it's a prototype agent. Will always be null, otherwise.
     */
    private List<Agent> children;
    
    /**
     * The x position of this Agent
     */
    private int x;
    
    /**
     * The y position of this Agent
     */
    private int y;
    
    /**
     * Constructor.
     * @param g The grid (passed to super constructor)
     * @param c The color of this agent (passed to super constructor)
     * @param isPrototype Is this a prototype agent from which all other agents of this type are made?
     * @throws Exception 
     */
    public Agent(Grid g, Color c, boolean isPrototype) throws Exception {
    	super(g, c);
    	
        triggers = new ArrayList<Trigger>();
        
        if(isPrototype) {
            children = new ArrayList<Agent>();
            fields.add(new Field("spawnCondition", Type.STRING, "random"));
        }
        else {
            children = null;
        }
    }
    
    /**
     * @throws Exception 
     * @throws StringFormatMismatchException 
     * Clone constructor. Will create a deep clone with every instance variable copied, not just references.
     * @param parent The parent from which to clone.
     * @throws 
     */
    public Agent(Agent parent, boolean isPrototype) throws StringFormatMismatchException, Exception {
    	super(parent.grid, parent.getColor(), parent.design);

    	triggers = new ArrayList<Trigger>();
    	
    	for(Field f : parent.fields) {
    		fields.add(new Field(f)); //copy all fields
    	}
    	
    	for(Trigger t : parent.triggers) {
    		triggers.add(new Trigger(t, this)); //copy all triggers
    	}
    	
    	if(isPrototype) {
    		children = new ArrayList<Agent>();
    	}
    	else {
    		children = null;
    	}
    }
    
    /**
     * Causes this Agent to perform 1 action.
     * The first trigger with valid conditions will fire.
     * @throws Exception 
     */
    @Override
	public void act() {
    	try {
    		Trigger toDo = null;
    		for(Trigger t : triggers) {
    			if(t.evaluate() != null) {
	                toDo = t;
	                break;
	            }
	        }
	        
	        if(toDo != null) {
	        	toDo.fire();
	        }
    	}
    	catch(Exception e) {
    		System.err.println(e);
    	}
    }
    
    /**
     * Clones this agent and puts it in the Grid's list of agents.
     * @throws StringFormatMismatchException 
     */
    public void cloneAgent() throws Exception {
        grid.addEntity(new Agent(this, false));
    }
    
    /**
     * Clones this agent and puts it in the environment's list of agents,
     * then prepares it to be a prototype agent.
     * @throws StringFormatMismatchException 
     */
    public void cloneAgentPrototype() throws Exception {
    	grid.addEntity(new Agent(this, true));
    }
    
    /**
     * Removes this Agent from the environment's list.
     */
    public void die() {
        grid.removeEntity(this);
    }
    
    /**
     * Parses the input string for a trigger, then adds that trigger.
     * Will throw an IOException if the input string is not formatted properly.
     * The input string should be in the format: "Priority=...\nCondition=...\nBehavior=..." There should be no spaces present in the input string.
     * @param s The text representation of this trigger.
     */
    public void addTrigger(String s) throws IOException {
        String[] lines = s.split("\n");
        if(lines.length != 3 || !lines[0].substring(0, 9).equals("Priority=") || !lines[1].substring(0, 10).equals("Condition=") || !lines[2].substring(0, 9).equals("Behavior=")) {
            throw new IOException();
        }
        int priority = Integer.parseInt(lines[0].substring(9, lines[0].length()));
        BoolExpression be = BoolExpression.parseExpression(lines[0].substring(10, lines[0].length()));
        Behavior result = Behavior.parseBehavior(lines[1].substring(7, lines[1].length()));
        triggers.add(new Trigger(priority, be, result, this));
        Collections.sort(triggers);
    }
    
    /**
     * Removes a trigger with the given priority (index in array list). If this is the prototype Agent,
     * will also remove the trigger from all children.
     * @param priority The priority of the given trigger to remove.
     */
    public void removeTrigger(int priority) {
        triggers.remove(triggers.get(priority));
        Collections.sort(triggers);
        if(children != null) {
            for(Agent a : children) {
                a.removeTrigger(priority);
            }
        }
    }
    
    /**
     * Gets the current x position of this agent
     * @return x
     */
    public int getX() {
    	return x;
    }
    
    /**
     * Gets the current y position of this agent
     * @return y
     */
    public int getY() {
    	return y;
    }
}
