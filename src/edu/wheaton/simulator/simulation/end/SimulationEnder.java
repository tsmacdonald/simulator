package edu.wheaton.simulator.simulation.end;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import com.google.common.collect.ImmutableMap;

import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Slot;
import edu.wheaton.simulator.simulation.Grid;

/**
 * Handles the determination of whether or not the simulation needs to end.
 * 
 * @author Daniel Gill
 */
public class SimulationEnder {

	private static int TIME_CONDITION = 0;
	private static int NO_AGENTS_CONDITION = 1;
	private static int POPULATION_CONDITIONS = 2;

	private EndCondition[] conditions;

	/**
	 * Constructor. 
	 */
	public SimulationEnder() {
		conditions = new EndCondition[3];
		TimeCondition timer = new TimeCondition(0);
		NoAgentsCondition counter = new NoAgentsCondition();
		conditions[TIME_CONDITION] = timer;
		conditions[NO_AGENTS_CONDITION] = counter;
		conditions[POPULATION_CONDITIONS] = new AgentPopulationCondition(); 
	}

	public void setStepLimit(int maxSteps) {
		((TimeCondition) conditions[TIME_CONDITION]).maxSteps = maxSteps;
	}

	/**
	 * Determine whether the simulation should end.
	 * 
	 * @return true if the simulation should end, false otherwise.
	 */
	public boolean evaluate(int step, Grid grid) {
		for (EndCondition condition : conditions)
			if (condition.evaluate(step, grid))
				return true;
		return false;
	}

	/**
	 * Determines if the simulation has run out of time.
	 * 
	 * @author daniel.gill
	 */
	private final class TimeCondition implements EndCondition {

		/**
		 * The total number of steps the Simulation is permitted to run.
		 */
		private int maxSteps;

		/**
		 * @param maxSteps
		 *            The total number of steps the Simulation is permitted to
		 *            run.
		 */
		public TimeCondition(int maxSteps) {
			this.maxSteps = maxSteps;
		}

		@Override
		public boolean evaluate(int step, Grid grid) {
			return step >= maxSteps;
		}

	}

	/**
	 * Determines if there are no more agents in the simulation.
	 * 
	 * @author daniel.gill
	 */
	private final class NoAgentsCondition implements EndCondition {
		@Override
		public boolean evaluate(int step, Grid grid) {
			for (Slot s : grid) {
				if (s.getAgent() != null)
					return false;
			}
			return true;
		}
	}

	/**
	 * Determines if the population of the given type has exceeded the given
	 * max.
	 * 
	 * @author daniel.gill
	 */
	private final class AgentPopulationCondition implements EndCondition {
		//TODO: Change this to Prototype ID's after those exist. 
		/**
		 * The map where the names of prototypes 
		 */
		private TreeMap<String, Integer> popLimits; 

		/**
		 * Constructor. 
		 */
		public AgentPopulationCondition() { 
			popLimits = new TreeMap<String, Integer>();
		}
		
		/**
		 * Add a new population limit to the simulation. 
		 * @param typeName The name of the category of agent. 
		 * @param maxPop The population that category must not exceed. 
		 */
		public void addPopLimit(String typeName, int maxPop) { 
			popLimits.put(typeName, Integer.valueOf(maxPop)); 
		}
		
		/**
		 * Return a immutable map of 
		 * @return
		 */
		public ImmutableMap<String, Integer> getTypeMaxes() {
			return new ImmutableMap.Builder<String, Integer>().putAll(popLimits).build();
		}

		@Override
		public boolean evaluate(int step, Grid grid) {
			if (popLimits.size() < 1)
				return false; 
			
			TreeMap<String, Integer> currentPopulations = 
					new TreeMap<String, Integer>();
			for (String currentCategory : popLimits.keySet())
				currentPopulations.put(currentCategory, Integer.valueOf(0));
			
			for (Slot currentSlot : grid) { 
				Agent currentAgent; 
				if ((currentAgent = (Agent) currentSlot.getAgent()) != null) { 
					String currentCategory = currentAgent.getProtypeName();
					Integer currentLimit; 
					if ((currentLimit = currentPopulations.get(currentCategory)) != null)
						currentPopulations.put(currentCategory, currentLimit + 1); 
				}
			}
			
			for (String currentCategory : currentPopulations.keySet())
				if (currentPopulations.get(currentCategory) >= popLimits.get(currentCategory))
					return true; 
			
			return false; 
		}
	}
}
