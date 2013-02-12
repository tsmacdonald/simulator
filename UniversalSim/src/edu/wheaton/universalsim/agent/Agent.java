/**
 * Agent.java
 *
 * Agents model actors in the simulation's Grid.
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.universalsim.agent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import edu.wheaton.universalsim.Grid;
import edu.wheaton.universalsim.exceptions.ElementAlreadyContainedException;
import edu.wheaton.universalsim.exceptions.StringFormatMismatchException;

public class Agent {
    
    /**
     * The list of all fields (variables) associated with this agent.
     */
    private ArrayList<Field> fields;
    
    /**
     * The list of all triggers/events associated with this agent.
     */
    private ArrayList<Trigger> triggers;
    
    /**
     * The list of all children of this Agent if it's a prototype agent. Will always be null, otherwise.
     */
    private ArrayList<Agent> children;
    
    /**
     * A pointer to the environment so new Agents can be added or removed.
     */
    private Grid grid;
    
    /**
     * Constructor.
     * @param e The environment this Agent acts within.
     * @param isPrototype Is this a prototype agent from which all other agents of this type are made?
     */
    public Agent(Grid g, boolean isPrototype) {
        fields = new ArrayList<>();
        triggers = new ArrayList<>();
        grid = g;
        if(isPrototype) {
            children = new ArrayList<>();
        }
        else {
            children = null;
        }
    }
    
    /**
     * Clone constructor. Will create a deep clone with every instance variable copied, not just references.
     * @param parent The parent from which to clone.
     */
    public Agent(Agent parent, boolean isPrototype) {
    	fields = new ArrayList<>();
    	triggers = new ArrayList<>();
    	
    	for(Field f : parent.fields) {
    		fields.add(new Field(f)); //copy all fields
    	}
    	
    	for(Trigger t : parent.triggers) {
    		triggers.add(new Trigger(t, this)); //copy all triggers
    	}
    	
    	if(isPrototype) {
    		children = new ArrayList<>();
    	}
    	else {
    		children = null;
    	}
    	grid = parent.grid;
    }
    
    /**
     * Causes this Agent to perform 1 action.
     * The first trigger with valid conditions will fire.
     * @throws Exception 
     */
    public void act() {
    	try {
    		Trigger toDo = null;
    		for(Trigger t : triggers) {
    			if(t.evaluate()) {
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
     * Clones this agent and puts it in the environment's list of agents.
     */
    public void cloneAgent() {
        grid.addAgent(new Agent(this, false));
    }
    
    /**
     * Clones this agent and puts it in the environment's list of agents,
     * then prepares it to be a prototype agent.
     */
    public void cloneAgentPrototype() {
    	grid.addAgent(new Agent(this, true));
    }
    
    /**
     * Removes this Agent from the environment's list.
     */
    public void die() {
        grid.removeAgent(this);
    }
    
    /**
     * Parses the input string for a field, then adds that field.
     * The input string should be in the format: "Name=...\nType=...\nValue=..." There should be no spaces present in the input string.
     * Note that if a field already exists for this agent with the same name as the new candidate, it won't be added and will instead throw an exception.
     * @param s The text representation of this field.
     * @throws ElementAlreadyContainedException 
     * @throws StringFormatMismatchException 
     */
    public void addField(String s) throws ElementAlreadyContainedException, StringFormatMismatchException {
        String[] lines = s.split("\n");
        if(lines.length != 3 || !lines[0].substring(0, 5).equals("Name=") || !lines[1].substring(0, 5).equals("Type=") || !lines[2].substring(0, 6).equals("Value=")) {
            throw new StringFormatMismatchException();
        }
        String name = lines[0].substring(5, lines[0].length());
        String type = lines[1].substring(5, lines[1].length());
        String value = lines[2].substring(6, lines[2].length());
        
        //Check to see if it's contained.
        Field contained = null;
        for(Field f : fields) {
        	if (f.getName().equals(name)) {
        		contained = f;
        		break;
        	}
        }
        if(contained != null) throw new ElementAlreadyContainedException();
        fields.add(new Field(name, type, value));
    }
    
    /**
     * Removes a field from this Agent.
     * @param name 
     */
    public void removeField(String name) {
        for(Field f : fields) {
        	if (f.getName().equals(name)) {
        		fields.remove(f);
        		break;
        	}
        }
    }
    
    /**
     * Parses the input string for a trigger, then adds that trigger.
     * Will throw an IOException if the input string is not formatted properly.
     * The input string should be in the format: "Priority=...\nCondition=...\nResult=..." There should be no spaces present in the input string.
     * @param s The text representation of this trigger.
     */
    public void addTrigger(String s) throws IOException {
        String[] lines = s.split("\n");
        if(lines.length != 3 || !lines[0].substring(0, 9).equals("Priority=") || !lines[1].substring(0, 10).equals("Condition=") || !lines[2].substring(0, 7).equals("Result=")) {
            throw new IOException();
        }
        int priority = Integer.parseInt(lines[0].substring(9, lines[0].length()));
        BoolExpression be = BoolExpression.parseExpression(lines[0].substring(10, lines[0].length()));
        Result result = Result.parseResult(lines[1].substring(7, lines[1].length()));
        triggers.add(new Trigger(priority, be, result));
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
}
