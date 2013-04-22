/**
 * TriggerObserver.java
 * 
 * Interface for trigger observers
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.entity;

public interface TriggerObserver {

	public void update(AgentID caller, Trigger trigger, int step);

}