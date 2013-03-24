package edu.wheaton.simulator.statistics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.entity.PrototypeID;

public class StatisticsManager {

	/**
	 * The table on which all entity snapshots will be stored.
	 */
	private EntitySnapshotTable table;

	/**
	 * The number of steps the simulation has taken. Effectively it is the
	 * largest step it has encountered in a Snapshot given to it.
	 */
	private int numSteps;

	/**
	 * The GridOberserver keeps track of changes in the grid.
	 */
	private GridObserver gridObserver;

	/**
	 * Each index in the List stores the prototype snapshot associated with
	 * that step in the simulation
	 */
	private List<Map<PrototypeID, PrototypeSnapshot>> prototypes;

	// TODO: Some sort of behavior queue mapping AgentID's to behavior
	// representations.

	/**
	 * Private constructor to prevent wanton instantiation.
	 */
	public StatisticsManager() {
		table = new EntitySnapshotTable();
		gridObserver = new GridObserver(this);
		prototypes = new LinkedList<Map<PrototypeID, PrototypeSnapshot>>();
	}

	/**
	 * Get the grid entity Observer.
	 * 
	 * @return The GridEntityObserver associated with this StatisticsManager.
	 */
	public GridObserver getGridObserver() {
		return gridObserver;
	}

	/**
	 * Store a snapshot of a gridEntity.
	 * 
	 * @param gridEntity
	 *            The Snapshot to be stored.
	 */
	public void addGridEntity(EntitySnapshot gridEntity) {
		table.putEntity(gridEntity);
		if (gridEntity.step > numSteps)
			numSteps = gridEntity.step;
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
			PrototypeID typeID, Integer step) {
		ImmutableSet.Builder<AgentSnapshot> builder = new ImmutableSet.Builder<AgentSnapshot>();
		ImmutableMap<EntityID, EntitySnapshot> totalPopulation = table
				.getSnapshotsAtStep(step);
		for (EntityID currentID : table.getSnapshotsAtStep(step).keySet()) {
			EntitySnapshot currentEntity;
			if ((currentEntity = totalPopulation.get(currentID)) instanceof AgentSnapshot) {
				AgentSnapshot currentAgent = (AgentSnapshot) currentEntity;
				if (currentAgent.prototype == typeID)
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
	public int[] getPopVsTime(PrototypeID id) {
		int[] data = new int[prototypes.size()];

		for (int i = 0; i < data.length; i++) {
			PrototypeSnapshot currentSnapshot;
			if ((currentSnapshot = prototypes.get(i).get(id)) != null) {
				data[i] = currentSnapshot.population;
			}
		}
		return data;
	}

	/**
	 * Get data for a graph of the average value of a field over time
	 * 
	 * @param id
	 *            The PrototypeID of the GridEntity to be tracked
	 * @param FieldName
	 *            The name of the field to be tracked
	 * @return An array where indexes refer to the step in the simulation and
	 *         the value refers to average field value at that time
	 */
	public double[] getAvgFieldValue(PrototypeID id, String FieldName) {
		// set of steps in table
		Set<Integer> steps = table.getAllSteps();
		
		// array of averages
		double[] averages = new double[steps.size()];
		
		// marker for double[]
		int i = 0;
		
		// arraylist of the values at each step to average up
		ArrayList<Double> stepVals = new ArrayList<Double>();
		
		for(int step : steps) {
			ImmutableSet<AgentSnapshot> agents = getPopulationAtStep(id, step);
			
			for(AgentSnapshot agent : agents) {
				ImmutableMap<String, FieldSnapshot> fields = agent.fields;
				
				if(fields.containsKey(FieldName))
					if(fields.get(FieldName).isNumber)
						stepVals.add(fields.get(FieldName).getNumericalValue());
			}
			
			double total = 0;
			for(Double val : stepVals)
				total += val;
			averages[i] = total / (steps.size());
			total = 0;
			i++;
			stepVals.clear();
		}
		return averages;
	}

	/**
	 * Get the average lifespan of a given GridEntity
	 * 
	 * @param id
	 *            The PrototypeID of the GridEntity to be tracked
	 * @return The average lifespan of the specified GridEntity
	 */
	public double getAvgLifespan(PrototypeID id) {
		return 0.0;
	}
}
