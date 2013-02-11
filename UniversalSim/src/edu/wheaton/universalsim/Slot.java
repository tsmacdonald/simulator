package edu.wheaton.universalsim;

import edu.wheaton.universalsim.agent.Agent;

/**
 * Slot.java
 * 
 * Class that defines properties for a specific point in the overall grid.
 *
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

public class Slot {

    /**
     * The Agent currently in this slot
     */
    private Agent agent;

    /**
     * This slot's temperature
     */
    private int temperature;

    /**
     * This slot's humidity
     */
    private int humidity;

    /**
     * Constructor
     * @param temp Initial value for temperature.
     * @param temp Initial value for humidity.
     */
    public Slot(int temp, int hum) {
	temperature = temp;
	humidity = hum;
    }
    
    /**
     * Adds a different agent to this slot
     * @param a The new agent
     */
    public void setAgent(Agent a) {
	agent = a;
    }
    
    /**
     * Returns the agent currently in this slot
     * @return The Agent reference
     */
    public Agent getAgent() {
	return agent;
    }
    
    /**
     * Changes the temperature of the slot
     * @param temp The new temperature
     */
    public void setTemperature(int temp) {
	temperature = temp;
    }
    
    /**
     * Returns the current temp
     * @return Current temp
     */
    public int getTemperature() {
	return temperature;
    }
    
    /**
     * Changes the humidity of the slot
     * @param hum The new humidity
     */
    public void setHumidity(int hum) {
	humidity = hum;
    }
    
    /**
     * Gets the humidity
     * @return The current humidity
     */
    public int getHumidity() {
	return humidity;
    }

}