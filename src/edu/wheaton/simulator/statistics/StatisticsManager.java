package edu.wheaton.simulator.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.NameNotFoundException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.simulation.end.SimulationEnder;

public class StatisticsManager {

	/**
	 * Single instance of this class.
	 */
	private static StatisticsManager instance = null;

	/**
	 * The table in which all entity snapshots will be stored.
	 */
	private AgentSnapshotTable table;

	/**
	 * The GridOberserver keeps track of changes in the grid.
	 */
	private static Recorder gridObserver;

	/**
	 * The grid being used. Will be used by GridRecorder
	 */
	public Grid grid;

	/**
	 * Initial set of prototypes.
	 */
	private static ImmutableSet<Prototype> prototypes;

	/**
	 * Prototype snapshots in the game.
	 */
	private static HashMap<String, PrototypeSnapshot> protoSnaps;

	/**
	 * Private constructor to prevent wanton instantiation.
	 */
	private StatisticsManager() {
		table = new AgentSnapshotTable();
		gridObserver = new Recorder(this);
		prototypes = null;
		protoSnaps = new HashMap<String, PrototypeSnapshot>();
	}

	/**
	 * Get instance of this singleton
	 */
	public static StatisticsManager getInstance() {
		if(instance != null) 
			return instance;
		return instance = new StatisticsManager();
	}

	/**
	 * THIS IS FOR TESTING PURPOSES ONLY!!
	 */
	public static void removeInstance() {
		instance = null;
	}

	/**
	 * Initialize an observer for the grid and triggers and prepare prototypes for saving.
	 */
	public void initialize(Grid grid) {
		grid.addObserver(gridObserver);
		Trigger.addObserver(gridObserver);
		this.grid = grid;
		StatisticsManager.prototypes = Prototype.getPrototypes();
		for(Prototype p : prototypes)
			addPrototypeSnapshot(SnapshotFactory.makePrototypeSnapshot(p));
	}

	/**
	 * Get the last step(taken from the table of snapshots).
	 */
	private Integer lastStep() {
		return table.getAllSteps().size();
	}

	/**
	 * Add a PrototypeSnapshot to the StatisticsManager. 
	 * @param prototypeSnapshot The new prototype being recorded.
	 */
	public static void addPrototypeSnapshot(PrototypeSnapshot snap) {
		protoSnaps.put(snap.categoryName, snap);
		//TODO: Save this prototype to a file
	}

	/**
	 * Store a snapshot of a gridEntity.
	 * 
	 * @param agentSnapshot
	 *            The Snapshot to be stored.
	 */
	public void addGridEntity(AgentSnapshot agentSnapshot) {
		table.putEntity(agentSnapshot);
	}

	/**
	 * Returns the entire population at a given step of a given category of
	 * Agent.
	 * 
	 * @param typeID
	 *            The ID of the prototype of the desired type of Agent.
	 * @param step
	 *            The relevant moment in time.
	 * @return An ImmutableSet of AgentSnapshots of typeID at step.
	 */
	private ImmutableSet<AgentSnapshot> getPopulationAtStep(
			String prototypeName, Integer step) {
		ImmutableSet.Builder<AgentSnapshot> builder = new ImmutableSet.Builder<AgentSnapshot>();
		ImmutableMap<AgentID, AgentSnapshot> totalPopulation = table
				.getSnapshotsAtStep(step);
		for (AgentID currentID : table.getSnapshotsAtStep(step).keySet()) {
			AgentSnapshot currentEntity;
			if ((currentEntity = totalPopulation.get(currentID)) != null) {
				AgentSnapshot currentAgent = currentEntity;
				if (currentAgent.prototypeName.equals(prototypeName))
					builder.add(currentAgent);
			}
		}
		return builder.build();
	}

	/**
	 * Get data for a graph of the population of a certain GridEntity over time
	 * 
	 * @param id
	 *            The PrototypeID of the GridEntity to be tracked
	 * @return An array where indexes refer to the step in the simulation and
	 *         the value refers to the population of the targeted entity at
	 *         that time
	 */
	public int[] getPopVsTime(String prototypeName) {
		int[] data = new int[lastStep()];	

		//Populate agentsByStep
		for (int i = 0; i <= lastStep() - 1; i++) {
			Set<AgentSnapshot> stepPop = getPopulationAtStep(prototypeName, i);
			data[i] = stepPop.size(); 
		}

		return data;
	}

	/**
	 * Get data for a graph of the average value of a field over time
	 * 
	 * @param prototypeName
	 *            The PrototypeName of the GridEntity to be tracked
	 * @param FieldName
	 *            The name of the field to be tracked
	 * @return An array in which indexes refer to the step in the simulation
	 *         and the value refers to average field value at that time
	 */
	public double[] getAvgFieldValue(String prototypeName, String FieldName) {
		// set of steps in table
		Set<Integer> steps = table.getAllSteps();

		// array of averages
		double[] averages = new double[steps.size()];

		// index for double[]
		int i = 0;

		// arraylist of the values at each step to average up
		ArrayList<Double> stepVals = new ArrayList<Double>();

		for (int step : steps) {
			ImmutableSet<AgentSnapshot> agents = getPopulationAtStep(prototypeName, step);

			for (AgentSnapshot agent : agents) {
				ImmutableMap<String, FieldSnapshot> fields = agent.fields;

				if (fields.containsKey(FieldName))
					if (fields.get(FieldName).isNumber)
						stepVals.add(fields.get(FieldName).getNumericalValue());
			}

			double total = 0;
			for (Double val : stepVals)
				total += val;
			averages[i] = total / (agents.size());
			total = 0;
			i++;
			stepVals.clear();
		}
		return averages;
	}

	/**
	 * Get data for a graph of how many times a trigger was executed at each
	 * step for a specific Prototype
	 * 
	 * @param prototypeName
	 *            Name of the prototype to track
	 * @param behavior
	 *            The name of behavior to track
	 * 
	 * @return An array in which indexes refer to the step in the simulation
	 *         and the value refers to average field value at that time
	 */
	public double[] getTriggerExecutionsFor(String prototypeName,
			String behavior) {
		// array of answers to return. size = all steps of table
		double[] toReturn = new double[lastStep()];

		// set of steps in the table
		Set<Integer> steps = table.getAllSteps();

		// index for toReturn
		int i = 0;

		// list of totals of each step
		double found = 0;

		for (int step : steps) {
			ImmutableSet<AgentSnapshot> agents = getPopulationAtStep(
					prototypeName, step);

			for (AgentSnapshot agent : agents) {
				ArrayList<TriggerSnapshot> triggers = agent.triggers;

				for (TriggerSnapshot trigger : triggers)
					if (trigger.triggerName.equals(behavior))
						found++;
			}

			toReturn[i] = found;
			found = 0;
			i++;
		}
		return toReturn;
	}

	/**
	 * Get the average lifespan of a given GridEntity
	 * 
	 * @param id
	 *            The PrototypeID of the GridEntity to be tracked
	 * @return The average lifespan of the specified GridEntity
	 */
	public double getAvgLifespan(String prototypeName) {
		// List with index = step in the simulation, value = set of all agents
		// alive at that time
		List<Set<AgentSnapshot>> agentsByStep = new ArrayList<Set<AgentSnapshot>>();

		//Set of all Agent IDs
		Set<AgentID> allIDs = new HashSet<AgentID>(); 

		//Populate agentsByStep
		for (int i = 0; i <= lastStep(); i++) {
			Set<AgentSnapshot> stepData = getPopulationAtStep(prototypeName, i);

			//Populate the list of unique Agent IDs for the Agents we're tracking
			for(AgentSnapshot snap : stepData){
				allIDs.add(snap.id); 
			}

			//Populate the list of agent snapshots by step
			agentsByStep.add(i, stepData);
		}

		double avg = 0.0;

		//Get the lifespan of each agent
		for (AgentID ID : allIDs) {
			int birthTime = getBirthStep(agentsByStep, ID);
			int deathTime = getDeathStep(agentsByStep, ID);

			// Build the sum of all lifetimes - we'll divide by the number of
			// agents at the end to get the average
			int lifespan = (deathTime - birthTime); 
			avg += lifespan; 
		}	

		return avg / allIDs.size();
	}

	/**
	 * Get the step number in which the Agent represented by a given
	 * AgentSnapshot was born
	 * 
	 * @param agentsByStep
	 *            A List with index = step in the simulation, value = set of
	 *            all agents born at that time
	 * @param target
	 *            The AgentSnapshot of the agent we're looking for
	 * @return The step number of the target Agent's birth
	 * @throws NameNotFoundException
	 *             the target Agent wasn't found
	 */
	private int getBirthStep(List<Set<AgentSnapshot>> agentsByStep,
			AgentID target) {
		for (int i = 0; i < lastStep(); i++){
			for(AgentSnapshot s : agentsByStep.get(i)){
				if(s.id.equals(target))
					return i;
			}
		}

		throw new IllegalArgumentException(
				"The target AgentID was not found");
	}

	/**
	 * Get the step number in which the Agent represented by a given
	 * AgentSnapshot died
	 * 
	 * @param agentsByStep
	 *            A List with index = step in the simulation, value = set of
	 *            all agents born at that time
	 * @param target
	 *            The AgentSnapshot of the agent we're looking for
	 * @return The step number of the target Agent's death
	 * @throws NameNotFoundException
	 *             the target Agent wasn't found
	 */
	private static int getDeathStep(List<Set<AgentSnapshot>> agentsByStep,
			AgentID target) {
		for (int i = agentsByStep.size()-1; i >= 0; i--){
			if (agentsByStep.get(i) != null){
				for(AgentSnapshot s : agentsByStep.get(i)){
					if(s.id.equals(target))
						return i;
				}
			}	
		}

		throw new IllegalArgumentException(
				"The target AgentID was not found");
	}
}
