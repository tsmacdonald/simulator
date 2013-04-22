package edu.wheaton.simulator.gui;

import java.util.Set;

import edu.wheaton.simulator.datastructure.AbstractGUIGridObserver;
import edu.wheaton.simulator.datastructure.AgentAppearance;
import edu.wheaton.simulator.simulation.end.SimulationEnder;

public class GridPanelObserver extends AbstractGUIGridObserver{

	private GridPanel gp;
	
	public GridPanelObserver(GridPanel gp){
		this.gp = gp;
	}

	@Override
	public void update(Set<AgentAppearance> agents) {
		gp.setAgents(agents);
		gp.repaint();
	}

	@Override
	public void update(SimulationEnder ender) {
		// TODO GUI TEAM! This method will now be called as soon as
		// the simulation ends due to an ending condition!
	}
}
