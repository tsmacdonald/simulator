package edu.wheaton.simulator.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.wheaton.simulator.behavior.AbstractBehavior;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Prototype;

/**
 * @author Daniel Gill, Nico Lasta
 * 
 */
public class GridRecorder {

	// private static int ii = 0;

	private StatisticsManager statManager;

	private Prototype gridPrototype;
	
	private static HashMap<AgentID, ArrayList> behaviors;

	/**
	 * Constructor.
	 */
	public GridRecorder(StatisticsManager statManager) {
		this.statManager = statManager;
		gridPrototype = null;
		this.behaviors = new HashMap<AgentID, ArrayList>();
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
	public void recordSimulationStep(Grid grid, Integer step,
			Collection<Prototype> prototypes) {
		for (Prototype prototype : prototypes) {
			statManager.addPrototypeSnapshot(SnapshotFactory
					.makePrototypeSnapshot(prototype, step));
		}
		for (Agent agent : grid) {
			if (agent != null) {
				statManager.addGridEntity(SnapshotFactory.makeAgentSnapshot(
						agent, behaviors.get(agent.getID()), step));

			}
		}
		if(gridPrototype == null) {
			gridPrototype = new Prototype(grid, "GRID");
			statManager.addGridEntity(SnapshotFactory.makeGlobalVarSnapshot(grid, gridPrototype, step));
		}
		else
			statManager.addGridEntity(SnapshotFactory.makeGlobalVarSnapshot(grid, gridPrototype, step));
		behaviors.clear();
	}

	/**
	 * Record the time of the most recent iteration.
	 * 
	 * @param timeOfRecentIteration
	 *            the time (in ms) of the most recent turn of the simulation.
	 */
	public void updateTime(long timeOfRecentIteration, long addedDuration) {
		statManager.updateRecentTime(timeOfRecentIteration);
		statManager.updateTotalTime(statManager.getSimulationDuration()
				+ addedDuration);
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
	public static void notify(AgentID actor, AbstractBehavior behavior,
			AgentID recipient, Integer step) {
		// do things
		BehaviorSnapshot behaveSnap = SnapshotFactory.makeBehaviorSnapshot(
				actor, behavior, recipient, step);

		if (behaviors.containsKey(actor)) {
			behaviors.get(actor).add(behaveSnap);
		} else {
			ArrayList<BehaviorSnapshot> behaviorList = new ArrayList<BehaviorSnapshot>();
			behaviorList.add(behaveSnap);
			behaviors.put(actor, behaviorList);
		}
	}
}
