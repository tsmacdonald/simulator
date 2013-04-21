/**
 * TriggerObserver.java
 * 
 * Interface for trigger observers
 */

package edu.wheaton.simulator.entity;

public interface TriggerObserver {

	public void update(AgentID caller, Trigger trigger, int step);

}