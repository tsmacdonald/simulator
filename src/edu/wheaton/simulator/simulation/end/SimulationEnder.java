package edu.wheaton.simulator.simulation.end;

import java.util.HashSet;

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
	private static int MSC_CONDITIONS = 2;

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
		conditions[MSC_CONDITIONS] = new ConditionOrList(
				new HashSet<EndCondition>());
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
				if (s.getEntity() != null)
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
		/**
		 * The population which that category must not exceed.
		 */
		private int maxPop;
		
		/**
		 * The name of the type or category of Agent.
		 */
		private String typeName;

		/**
		 * Constructor.
		 * 
		 * @param typeName
		 *            The name of the type or category of Agent.
		 * @param maxPop
		 *            The population which that category must not exceed.
		 */
		public AgentPopulationCondition(String typeName, int maxPop) {
			this.maxPop = maxPop;
			this.typeName = typeName;
		}

		// TODO: Implement this better so that it checks all categories simultaniously for each slot.
		@Override
		public boolean evaluate(int step, Grid grid) {
			int pop = 0;
			for (Slot s : grid) {
				Agent a;
				if ((a = (Agent) s.getEntity()) != null)
					if (a.getCategoryName() == typeName)
						pop++;
			}
			return pop >= maxPop;
		}
	}

	/**
	 * Abstractly represents a set of EndConditions as a Single set.
	 * 
	 * @author daniel.gill
	 */
	private final class ConditionOrList implements EndCondition {
		private Iterable<EndCondition> conditions;

		/**
		 * Constructor.
		 * 
		 * @param conditions
		 *            An iterable collection of conditions.
		 */
		public ConditionOrList(Iterable<EndCondition> conditions) {
			this.conditions = conditions;
		}

		@Override
		public boolean evaluate(int step, Grid grid) {
			for (EndCondition condition : conditions)
				if (condition.evaluate(step, grid))
					return true;
			return false;
		}

	}
}
