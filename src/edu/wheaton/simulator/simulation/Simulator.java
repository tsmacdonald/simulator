/**
 * Simulator.java
 * 
 * Runnable simulator that in a way acts as a facade to the Agent code. 
 * The simulator holds simulations.
 *
 * @author Agent Team
 */

package edu.wheaton.simulator.simulation;

import java.awt.Color;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import sampleAgents.Bouncer;
import sampleAgents.Confuser;
import sampleAgents.ConwaysAliveBeing;
import sampleAgents.ConwaysDeadBeing;
import sampleAgents.Killer;
import sampleAgents.Multiplier;
import sampleAgents.Paper;
import sampleAgents.RightTurner;
import sampleAgents.Rock;
import sampleAgents.Scissors;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.datastructure.GridObserver;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.simulation.end.SimulationEnder;
import edu.wheaton.simulator.statistics.Loader;
import edu.wheaton.simulator.statistics.Saver;

public class Simulator {

	/**
	 * Single instance of the simulator
	 */
	private static Simulator simulator = null;

	/**
	 * Houses the properties of a simulation
	 */
	private Simulation simulation;

	/**
	 * Get the instance of the simulator
	 * 
	 * @return
	 */
	public static Simulator getInstance() {
		if (simulator == null)
			simulator = new Simulator();
		return simulator;
	}

	/**
	 * Resumes the simulation
	 */
	public void play() {
		simulation.play();
	}

	/**
	 * Pauses the update of the simulation. This will happen on the next
	 * iteration.
	 */
	public void pause() {
		simulation.pause();
	}

	/**
	 * Changes how long the simulation waits after each step
	 * 
	 * @param sleepPeriod
	 *            Time in milliseconds
	 */
	public void setSleepPeriod(int sleepPeriod) {
		simulation.setSleepPeriod(sleepPeriod);
	}

	/**
	 * Provides the time (in milliseconds) the simulator waits after each step
	 * 
	 * @return
	 */
	public int getSleepPeriod() {
		return simulation.getSleepPeriod();
	}

	/**
	 * Adds a Prototype to the prototype HashMap
	 * 
	 * @param n
	 * @param g
	 * @param c
	 */
	public static void createPrototype(String n, Color c) {
		Prototype.addPrototype(new Prototype(c, n));
	}

	/**
	 * Adds a Prototype (with a design) to the HashMap
	 * 
	 * @param n
	 * @param g
	 * @param c
	 * @param d
	 */
	public static void createPrototype(String n, Color c, byte[] d) {
		Prototype.addPrototype(new Prototype(c, d, n));
	}
	
	/**
	 * Adds the given prototype to the list
	 * 
	 * @param p
	 */
	public static void addPrototype(Prototype p) {
		Prototype.addPrototype(p);
	}

	/**
	 * Returns the Prototype that corresponds to the given string.
	 * 
	 * @param n
	 * @return
	 */
	public static Prototype getPrototype(String n) {
		return Prototype.getPrototype(n);
	}

	/**
	 * Gets a Set of the prototype names
	 * 
	 * @return
	 */
	public static List<String> prototypeNames() {
		return Prototype.prototypeNames();
	}

	/**
	 * Provides the Grid object that the simulation houses
	 * 
	 * @return
	 */
	private Grid simulationGrid() {
		return simulation.getGrid();
	}

	/**
	 * 
	 * @return a String with the name of the current update method
	 */
	public String currentUpdater() {
		return simulationGrid().currentUpdater();
	}

	/**
	 * Sets the update method to use the PriorityUpdate system
	 */
	public void setPriorityUpdate(int minPriority, int maxPriority) {
		simulationGrid().setPriorityUpdater(minPriority, maxPriority);
	}

	/**
	 * Sets the update method to use the AtomicUpdate system
	 */
	public void setAtomicUpdate() {
		simulationGrid().setAtomicUpdater();
	}

	/**
	 * Sets the update method to use the LinearUpdate system LinearUpdate is the
	 * default
	 */
	public void setLinearUpdate() {
		simulationGrid().setLinearUpdater();
	}

	/**
	 * Places an new agent (that follows the given prototype) at the given
	 * coordinates. This method replaces (kills) anything that is currently in
	 * that position. The Agent's own position is also updated accordingly.
	 * 
	 * @param prototypeName
	 * @param x
	 * @param y
	 * @return false if the x/y values are invalid
	 */
	public boolean addAgent(String prototypeName, int x, int y) {
		Agent toAdd = getPrototype(prototypeName).createAgent(simulationGrid());
		boolean toReturn = simulationGrid().addAgent(toAdd, x, y);
		simulation.notifyDisplayObservers();
		return toReturn;
	}

	/**
	 * Places an new agent (that follows the given prototype) at a random
	 * position in the grid. This method replaces (kills) anything that is
	 * currently in that position. The Agent's own position is also updated
	 * accordingly.
	 * 
	 * @param prototypeName
	 * @return returns true if successful
	 */
	public boolean addAgent(String prototypeName) {
		Agent toAdd = getPrototype(prototypeName).createAgent(simulationGrid());
		boolean toReturn = simulationGrid().addAgent(toAdd);
		simulation.notifyDisplayObservers();
		return toReturn;
	}

	/**
	 * Fills the entire grid with agents that follow the given prototype name
	 * 
	 * @param prototypeName
	 */
	public void fillAll(String prototypeName) {
		for(int x = 0; x < getWidth(); x++) 
			for(int y = 0; y < getHeight(); y++)
				addAgent(prototypeName, x, y);
		simulation.notifyDisplayObservers();
	}
	
	/**
	 * Clears all the Agents from the grid
	 * 
	 * @param a
	 */
	public void clearAll() {
		for(int x = 0; x < getWidth(); x++) 
			for(int y = 0; y < getHeight(); y++)
				removeAgent(x, y);
		simulation.notifyDisplayObservers();
	}
	
	/**
	 * Returns the Agent at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public Agent getAgent(int x, int y) {
		return simulationGrid().getAgent(x, y);
	}

	/**
	 * Removes an Agent at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public void removeAgent(int x, int y) {
		simulationGrid().removeAgent(x, y);
		simulation.notifyDisplayObservers();
	}

	/**
	 * Provides the simulation's name
	 * 
	 * @return
	 */
	public String getName() {
		return simulation.getName();
	}

	/**
	 * Change the name of the simulation
	 */
	public void setName(String name) {
		simulation.setName(name);
	}

	/**
	 * Makes a new Layer.
	 * 
	 * @param fieldName
	 *            The name of the Field that the Layer will represent
	 * @param c
	 *            The Color that will be shaded differently to represent Field
	 *            values
	 */
	public void displayLayer(String fieldName, Color c) {
		Layer.getInstance().setFieldName(fieldName);
		Layer.getInstance().setColor(c);
		Layer.getInstance().resetMinMax();
		simulation.setLayerExtremes();
		simulation.runLayer();
		simulation.notifyDisplayObservers();
	}

	/**
	 * Stops the layer from displaying
	 */
	public void clearLayer() {
		simulation.stopLayer();
		simulation.notifyDisplayObservers();
	}

	/**
	 * Adds the some sample prototypes
	 */
	public void initSamples() {
		new Multiplier().initSampleAgent();
		new Bouncer().initSampleAgent();
		new RightTurner().initSampleAgent();
		new Confuser().initSampleAgent();
		new Killer().initSampleAgent();
	}

	/**
	 * Sample simulation: Conway's Game of Life
	 */
	public void initGameOfLife() {
		simulationGrid().setPriorityUpdater(0, 50);

		// add prototypes
		new ConwaysDeadBeing().initSampleAgent();
		new ConwaysAliveBeing().initSampleAgent();

		// Place dead beings in Grid with some that are alive
		for (int x = 0; x < simulationGrid().getWidth(); x++)
			for (int y = 0; y < simulationGrid().getHeight(); y++) {
				if (x == simulationGrid().getWidth() / 2) {
					simulationGrid().addAgent(
							Prototype.getPrototype("aliveBeing").createAgent(simulationGrid()),
							x, y);
				} else {
					simulationGrid().addAgent(
							Prototype.getPrototype("deadBeing").createAgent(simulationGrid()),
							x, y);
				}
			}
		simulation.notifyDisplayObservers();
	}

	/**
	 * Sets up the rock paper and scissors sample units
	 */
	public void initRockPaperScissors() {
		setLinearUpdate();
		new Rock().initSampleAgent();
		new Paper().initSampleAgent();
		new Scissors().initSampleAgent();
		simulation.notifyDisplayObservers();
	}
	
	/**
	 * Version 1 will resolve head to head conflicts
	 * Version 2 will resolve conflicts where one is facing an enemy
	 * Version 3 will resolve conflicts where there is an enemy in an adjacent space
	 * 
	 * @param version The version will decide how agents decide if there is a conflict
	 */
	public void initRockPaperScissors(int version){
		setLinearUpdate();
		Rock rock = new Rock();
		rock.setVersion(version);
		rock.initSampleAgent();
		
		Paper paper = new Paper();
		paper.setVersion(version);
		paper.initSampleAgent();
		
		Scissors scissors = new Scissors();
		scissors.setVersion(version);
		scissors.initSampleAgent();
		simulation.notifyDisplayObservers();
	}

	/**
	 * Gets a field with the given string. Simple wrapper function.
	 * 
	 * @param s
	 *            The name of the field.
	 * @return The field to return.
	 */
	public Field getGlobalField(String s) {
		return simulationGrid().getField(s);
	}

	/**
	 * Gets a map holding all values for the global fields.
	 * 
	 * @return A map holding all values for the global fields.
	 */
	public Map<String, String> getGlobalFieldMap() {
		return simulationGrid().getFieldMap();
	}

	/**
	 * Updates the field with the given name to the given value.
	 * 
	 * @param name
	 *            The name of the field to update.
	 * @param value
	 *            The new value of the field.
	 */
	public void updateGlobalField(String name, String value) {
		try {
			simulationGrid().updateField(name, value);
		} catch (NoSuchElementException e) {
			System.out
			.println("Attempting to update a nonexistent global field.");
			System.err.print(e);
		}
	}

	/**
	 * Adds a global field to the simulation.
	 * 
	 * @param name
	 * @param startingValue
	 */
	public void addGlobalField(String name, String startingValue) {
		try {
			simulationGrid().addField(name, startingValue);
		} catch (ElementAlreadyContainedException e) {
			System.out
			.println("Problem adding a global field. Name already in the map. Exiting.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Removes the global field with the given name.
	 * 
	 * @param name
	 *            The name of the field to remove.
	 */
	public void removeGlobalField(String name) {
		try {
			simulationGrid().removeField(name);
		} catch (NoSuchElementException e) {
			System.out.println("Attempting to remove a nonexistant field.");
			System.err.print(e);
		}
	}

	/**
	 * Provides the width of the grid
	 */
	public int getWidth() {
		return simulationGrid().getWidth();
	}

	/**
	 * Provides the height of the grid
	 */
	public int getHeight() {
		return simulationGrid().getHeight();
	}

	/**
	 * Changes the size of the grid
	 * 
	 * @param width
	 * @param height
	 */
	public void resizeGrid(int width, int height) {
		simulationGrid().resizeGrid(width, height);
		simulation.notifyDisplayObservers();
	}

	/**
	 * Adds the given observer to the grid
	 */
	public void addGridObserver(GridObserver ob) {
		simulationGrid().addObserver(ob);
	}

	/**
	 * Creates a new simulation with a blank grid
	 * 
	 * @param name
	 * @param width
	 * @param height
	 * @param se
	 */
	public void load(String name, int width, int height, SimulationEnder se) {
		simulation = new Simulation(name, width, height, se);
	}
	
	/**
	 * Loads a simulation from a grid and prototypes
	 * 
	 * @param name
	 * @param grid
	 * @param prototypes
	 * @param se
	 */
	private void load(String name, Grid grid, Set<Prototype> prototypes,
			SimulationEnder se) {
		simulation = new Simulation(name, grid, se);
		for (Prototype current : prototypes)
			Prototype.addPrototype(current);
	}
	
	/**
	 * Replicates the simulation from the given file
	 * 
	 * @param file
	 */
	public void loadFromFile(File file, GridObserver obs) throws Exception {
		Loader l = new Loader();
		try {
			l.loadSimulation(file);
		} catch(Exception e) {
			throw new Exception("Oh no! The load file was somehow corrupted! What oh what will we do?");
		}
		try {
			load(l.getName(), l.getGrid(), l.getPrototypes(), l.getSimEnder());
		} catch(Exception e){
			System.out.println("No grid has been loaded yet!"); 
			e.printStackTrace();
		}
		addGridObserver(obs);
		simulation.notifyDisplayObservers();
	}
	
	/**
	 * Load a prototype from a file and add it to the simulation
	 *
	 * @param file
	 */
	public void loadPrototypeFromFile(File file) throws Exception {
		Loader l = new Loader();
		try {
			Prototype.addPrototype(l.loadPrototype(file));
		} catch(Exception e){
			throw new Exception("Oh no! The load file was somehow corrupted! What oh what will we do?");
		}
	}

	/**
	 * Saves a simulation to a given file.
	 *
	 * @param filename
	 */
	public void saveToFile(File file, SimulationEnder ender) {
		Saver s = new Saver();
		Set<Agent> agents = new HashSet<Agent>();
		Grid grid = simulationGrid();
		for (Agent agent : grid)
			if (agent != null)
				agents.add(agent);

		s.saveSimulation(file, agents, Prototype.getPrototypes(),
				getGlobalFieldMap(),
				grid.getWidth(), grid.getHeight(), ender);
	}
	
	/**
	 * Saves a given prototype to a string representation and put into file
	 *
	 * @param proto
	 */
	public void savePrototypeToFile(Prototype proto){
		Saver s = new Saver();
		s.savePrototype(proto);
	}

}
