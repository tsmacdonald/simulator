/**
 * TriggerObserver.java
 * 
 * Interface for trigger observers
 */

package edu.wheaton.simulator.entity;

public interface TriggerObserver {

	public void update(Agent caller, Trigger trigger);

}