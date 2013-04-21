/**
 * GridObserver.java
 * 
 * Interface for grid observers
 */

package edu.wheaton.simulator.datastructure;

import java.util.Set;

public interface GridObserver {

	public void update(Grid grid);
	
	public void update(Set<AgentAppearance> agents);

}