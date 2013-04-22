/**
 * AbstractGUIObserver.java
 * 
 * An observer that receives a set of AgentAppearances
 * 
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.datastructure;

import java.util.Set;

public abstract class AbstractGUIGridObserver implements GridObserver {

	@Override
	public void update(Grid grid) {
	}

	/**
	 * Will need to be implemented by the GUI team. This method is called each
	 * time the Grid loops
	 * 
	 * @param agents
	 */
	@Override
	public abstract void update(Set<AgentAppearance> agents);

}
