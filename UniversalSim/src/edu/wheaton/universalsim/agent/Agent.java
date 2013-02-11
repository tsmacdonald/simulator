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
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import edu.wheaton.universalsim.Grid;

public class Agent {
    
    /**
     * The list of all fields (variables) associated with this agent.
     */
    private HashMap<String, String> fields;
    
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
        fields = new HashMap<>();
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
    	fields = new HashMap<>();
    	triggers = new ArrayList<>();
    	
    	Set<Entry<String, String>> entrySet = parent.fields.entrySet();
    	for(Entry<String, String> e : entrySet) {
    		fields.put(e.getKey(), e.getValue());
    	}
    	
    	for(Trigger t : parent.triggers) {
    		triggers.add(new Trigger(t, this));
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
     */
    public void act() {
        Trigger toDo;
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
    
    /**
     * Clones this agent and puts it in the environment's list of agents.
     */
    private void cloneAgent() {
        grid.addAgent(new Agent(this, false));
    }
    
    /**
     * Clones this agent and prepares it to be a prototype agent.
     */
    private void cloneAgentPrototype() {
    	grid.addAgent(new Agent(this, true));
    }
    
    /**
     * Removes this Agent from the environment's list.
     */
    private void die() {
        grid.removeAgent(this);
    }
    
    /**
     * Parses the input string for a field, then adds that field.
     * Will throw an IOException if the input string is not formatted properly.
     * The input string should be in the format: "Name=...\nType=...\nValue=..." There should be no spaces present in the input string.
     * @param s The text representation of this field.
     */
    public void addField(String s) throws IOException {
        String[] lines = s.split("\n");
        if(lines.length != 3 || !lines[0].substring(0, 5).equals("Name=") || !lines[1].substring(0, 5).equals("Type=") || !lines[2].substring(0, 6).equals("Value=")) {
            throw new IOException();
        }
        String name = lines[0].substring(5, lines[0].length());
        String type = lines[1].substring(5, lines[1].length());
        String value = lines[2].substring(6, lines[2].length());
        try {
            switch(type) {
                case "int":
                    fields.add(new Field<Integer>(name, Integer.parseInt(value)));
                    break;
                case "double":
                    fields.add(new Field<Double>(name, Double.parseDouble(value)));
                    break;
                case "char":
                    fields.add(new Field<Character>(name, value.charAt(0)));
                    break;
                case "String":
                    fields.add(new Field<String>(name, value));
                    break;
            }
        }
        catch(Exception e) {
            throw new IOException();
        }
    }
    
    /**
     * Removes a field from this Agent.
     * @param name 
     */
    public void removeField(String name) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Parses the input string for a trigger, then adds that trigger.
     * Will throw an IOException if the input string is not formatted properly.
     * The input string should be in the format: "Condition=...\nResult=..." There should be no spaces present in the input string.
     * @param s The text representation of this trigger.
     */
    public void addTrigger(String s) throws IOException {
        String[] lines = s.split("\n");
        if(lines.length != 2 || !lines[0].substring(0, 10).equals("Condition=") || !lines[1].substring(0, 7).equals("Result=")) {
            throw new IOException();
        }
        Trigger.BooleanExpression be = Trigger.BooleanExpression.parseExpression(lines[0].substring(10, lines[0].length()));
        Trigger.Result result = Trigger.Result.parseResult(lines[1].substring(7, lines[1].length()));
        triggers.add(new Trigger(be, result));
        Collections.sort(triggers);
    }
    
    /**
     * Removes a trigger with the given name. May not be implemented depending on if we give triggers names or not.
     * @param s The name of the trigger to remove.
     */
    public void removeTrigger(String name) {
        throw new UnsupportedOperationException();
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
