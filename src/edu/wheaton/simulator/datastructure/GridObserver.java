/**
 * GridObserver.java
 * 
 * Interface for grid observers
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.datastructure;

import java.util.Set;

import edu.wheaton.simulator.simulation.end.SimulationEnder;

public interface GridObserver {

	public void update(Grid grid);
	
	public void update(Set<AgentAppearance> agents);
	
	public void update(SimulationEnder ender);

}