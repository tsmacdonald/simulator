/**
 * SimulationEnder.java
 *
 * A class that handles the conclusion of a simulation
 *
 * @author Grant Hensel
 */

package edu.wheaton.simulator.simulation;

public class SimulationEnder {

	/**
	 * A BoolExpression object containing the conditions under which the
	 * simulation ends
	 */
	private EndCondition endCondition;

	/**
	 * Constructor
	 * 
	 * @param conditionDescription
	 *            The conditions under which the simulation is to finish.
	 *            Format: Type Arg1 Arg2 Arg3 Arg4 Type - "time", "pop" or
	 *            "popstate". Specifies the type of ending condition
	 * 
	 *            For the "time" type: Arg1 = Number representing the time the
	 *            # of steps to simulate before terminating the simulation
	 *            Example: "time 100" -> End the simulation when 100 steps are
	 *            completed
	 * 
	 *            For the "pop" type: Arg1 = Name of the agent who's population
	 *            we will track Arg2 = Number representing the population level
	 *            at which the simulation will end Example: "pop Fox 100" ->
	 *            End the simulation when the fox population reaches 100
	 * 
	 *            For the "popstate" type Arg1 = Name of the agent who's
	 *            population we will track Arg2 = Number representing the
	 *            population level at which the simulation will end Arg3 = Name
	 *            of the field within the agent specified in Arg1 to track Arg4
	 *            = Value we care if the field specified in Arg3 is set to
	 *            Example: "popstate Fox 100 FurColor black" -> End the
	 *            simulation when there are 100 foxes with the FurColor field
	 *            set to "black"
	 */
	public SimulationEnder(Grid g, String conditionDescription)
			throws Exception {
		String[] args = conditionDescription.split(" ");

		if (args[0].equals("time")) {
			endCondition = new TimeElapsedCondition(g,
					Integer.parseInt(args[1]));
		} else if (args[0].equals("pop")) {
			endCondition = new AgentPopulationCondition(g, args[1],
					Integer.parseInt(args[2]));
		} else if (args[0].equals("popstate")) {
			endCondition = new AgentStateCondition(g, args[1],
					Integer.parseInt(args[2]), args[3], args[4]);
		}

		if (endCondition == null)
			throw new Exception(); // the conditionDescription was invalid
	}

	/**
	 * Check if the ending conditions for the simulation have been met
	 * 
	 * @return true or false
	 */
	public boolean simulationFinished() {
		return endCondition.evaluate();
	}

}
