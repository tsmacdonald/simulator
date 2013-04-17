package edu.wheaton.simulator.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import edu.wheaton.simulator.behavior.AbstractBehavior;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.datastructure.GridObserver;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.entity.TriggerObserver;

/**
 * @author Daniel Gill, Nico Lasta
 * 
 */
public class GridRecorder implements GridObserver, TriggerObserver {

	// private static int ii = 0;

	private StatisticsManager statManager;

	private Prototype gridPrototype;
	
	private static HashMap<AgentID, ArrayList<TriggerSnapshot>> triggers;

	/**
	 * Constructor.
	 */
	public GridRecorder(StatisticsManager statManager) {
		this.statManager = statManager;
		gridPrototype = null;
		GridRecorder.triggers = new HashMap<AgentID, ArrayList<TriggerSnapshot>>();
	}

	/**
	 * Record a step of the simulation.
	 * 
	 * @param grid
	 *            The grid at the point in time corresponding to step.
	 * @param step
	 *            The point in the simulation being recorded.
	 * @param prototypes
	 */
	@Override
	public void update(Grid grid, int step) {
		Collection<Prototype> prototypes = Prototype.getPrototypes();
		
		for (Prototype prototype : prototypes) {
			statManager.addPrototypeSnapshot(SnapshotFactory
					.makePrototypeSnapshot(prototype, step));
		}
		
		for (Agent agent : grid) {
			if (agent != null) {
				statManager.addGridEntity(SnapshotFactory.makeAgentSnapshot(
						agent, triggers.get(agent.getID()), step));
				
			}
		}
		
		if(gridPrototype == null) {
			gridPrototype = new Prototype(grid, "GRID");
			statManager.addGridEntity(SnapshotFactory.makeGlobalVarSnapshot(grid, gridPrototype, step));
		}
		else
			statManager.addGridEntity(SnapshotFactory.makeGlobalVarSnapshot(grid, gridPrototype, step));
		
		triggers.clear();
	}

	/**
	 * TODO Fix documentation if necessary
	 * 
	 * Called by behaviors whenever a trigger is activated.
	 * 
	 * @param actor
	 *            The agent acting
	 * @param behavior
	 *            The behavior of the agent
	 * @param recipient
	 *            The agent that is being acted upon
	 * @param step
	 *            The step that this method is called in
	 */
	@Override
	public void update(Agent caller, Trigger trigger) {
		TriggerSnapshot triggerSnap = SnapshotFactory.makeTriggerSnapshot(
				caller.getID(), trigger.getName(), trigger.getPriority(), null, null, statManager.grid.getStep());
		
		if (triggers.containsKey(caller)) {
			triggers.get(caller).add(triggerSnap);
		} else {
			ArrayList<TriggerSnapshot> triggerList = new ArrayList<TriggerSnapshot>();
			triggerList.add(triggerSnap);
			triggers.put(caller.getID(), triggerList);
		}
	}

}
