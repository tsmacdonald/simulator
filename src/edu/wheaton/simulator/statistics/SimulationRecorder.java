package edu.wheaton.simulator.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Prototype;

/**
 * @author Daniel Gill, Nico Lasta
 * 
 */
public class SimulationRecorder {

	// private static int ii = 0;

	private StatisticsManager statManager;

	private static HashMap<AgentID, ArrayList<BehaviorSnapshot>> behaviors;

	/**
	 * Constructor.
	 */
	public SimulationRecorder(StatisticsManager statManager) {
		this.statManager = statManager;
		this.behaviors = new HashMap<AgentID, ArrayList<BehaviorSnapshot>>();
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
}
