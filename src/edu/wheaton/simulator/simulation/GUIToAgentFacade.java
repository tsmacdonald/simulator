/**
 * GUIToAgentFacade.java
 * 
 * Facade for the GUI team
 *
 * @author Agent Team
 */

package edu.wheaton.simulator.simulation;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sampleAgents.Bouncer;
import sampleAgents.Confuser;
import sampleAgents.ConwaysAliveBeing;
import sampleAgents.ConwaysDeadBeing;
import sampleAgents.Multiplier;
import sampleAgents.Paper;
import sampleAgents.RightTurner;
import sampleAgents.Rock;
import sampleAgents.Scissors;

import net.sourceforge.jeval.EvaluationException;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Trigger;

public class GUIToAgentFacade {

	/**
	 * The Grid to hold all the Agents
	 */
	private Grid grid;

	/**
	 * Constructor.
	 * 
	 * @param gridX
	 * @param gridY
	 */
	public GUIToAgentFacade(int gridX, int gridY) {
		Prototype.clearPrototypes();
		grid = new Grid(gridX, gridY);
	}
	
	/**
	 * Adds the some sample prototypes
	 */
	public void initSamples() {
		new Multiplier().initSampleAgent(new Prototype(grid, Color.BLUE, "Multiplier"));
		new Bouncer().initSampleAgent(new Prototype(grid, Color.RED, "bouncer"));
		new RightTurner().initSampleAgent(new Prototype(grid, Color.BLACK, "rightTurner"));
		new Confuser().initSampleAgent(new Prototype(grid, Color.GREEN, "confuser"));
	}

	/**
	 * Adds a Prototype to the prototype HashMap
	 * 
	 * @param n
	 * @param g
	 * @param c
	 */
	public static void createPrototype(String n, Grid g, Color c) {
		Prototype.addPrototype(new Prototype(g, c, n));
	}

	/**
	 * Adds a Prototype (with a design) to the HashMap
	 * 
	 * @param n
	 * @param g
	 * @param c
	 * @param d
	 */
	public static void createPrototype(String n, Grid g, Color c, byte[] d) {
		Prototype.addPrototype(new Prototype(g, c, d, n));
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
	 * Resets the static list of prototypes
	 */
	public static void clearPrototypes() {
		Prototype.clearPrototypes();
	}

	/**
	 * Gets a Set of the prototype names
	 * 
	 * @return
	 */
	public static Set<String> prototypeNames() {
		return Prototype.prototypeNames();
	}

	/**
	 * Whether or not a given field is contained in a Prototype
	 * 
	 * @param p
	 * @param fieldName
	 * @return
	 */
	public static boolean prototypeHasField(Prototype p, String fieldName) {
		return p.hasField(fieldName);
	}

	/**
	 * Whether or not a given trigger is contained in a Prototype
	 * 
	 * @param p
	 * @param triggerName
	 * @return
	 */
	public static boolean prototypeHasTrigger(Prototype p, String triggerName) {
		return p.hasTrigger(triggerName);
	}

	/**
	 * Causes all entities in the grid to act()
	 */
	public void updateEntities() throws SimulationPauseException {
		grid.updateEntities();
	}

	/**
	 * 
	 * @return a String with the name of the current update method
	 */
	public String currentUpdater() {
		return grid.currentUpdater();
	}
	
	/**
	 * Adds the given Agent at the closest free spot to the spawn position. The
	 * search for an open spot begins at the given x/y and then spirals
	 * outwards.
	 * 
	 * @param prototypeName
	 *            The name of the prototype to build the Agent from.
	 * @param spawnX
	 *            Central x location for spawn
	 * @param spawnY
	 *            Central y location for spawn
	 * @return true if successful (agent added), false otherwise
	 */
	public boolean spiralSpawn(String prototypeName, int spawnX, int spawnY) {
		Agent toAdd = getPrototype(prototypeName).createAgent();
		return grid.spiralSpawn(toAdd, spawnX, spawnY);
	}

	/**
	 * Adds an Agent to a free spot along the given row
	 * 
	 * @param prototypeName
	 *            The name of the prototype to build the Agent from.
	 * @param row
	 *            The y position of the row
	 * @return true if successful (Agent added), false otherwise
	 */
	public boolean horizontalSpawn(String prototypeName, int row) {
		Agent toAdd = getPrototype(prototypeName).createAgent();
		return grid.horizontalSpawn(toAdd, row);
	}

	/**
	 * Adds an Agent to a free spot in the given column
	 * 
	 * @param prototypeName
	 *            The name of the prototype to build the Agent from.
	 * @param column
	 *            The x position of the column
	 * @return true if successful (Agent added), false otherwise
	 */
	public boolean verticalSpawn(String prototypeName, int column) {
		Agent toAdd = getPrototype(prototypeName).createAgent();
		return grid.verticalSpawn(toAdd, column);
	}

	/**
	 * Adds the given Agent to a random (but free) position.
	 * 
	 * @param prototypeName
	 *            The name of the prototype to build the Agent from.
	 */
	public boolean spiralSpawn(String prototypeName) {
		Agent toAdd = getPrototype(prototypeName).createAgent();
		return grid.spiralSpawn(toAdd);
	}

	/**
	 * Returns the Agent at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public Agent getAgent(int x, int y) {
		return grid.getAgent(x, y);
	}

	/**
	 * Removes an Agent at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public void removeAgent(int x, int y) {
		grid.removeAgent(x, y);
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
	public static void newLayer(String fieldName, Color c) {
		Grid.newLayer(fieldName, c);
	}

	/**
	 * Resets the min/max values of the layer and then loops through the grid
	 * to set's a new Layer's min/max values. This must be done before a Layer
	 * is shown. Usually every step if the Layer is being displayed.
	 * PRECONDITION: The newLayer method has been called to setup a layer
	 * 
	 * @throws EvaluationException
	 */
	public void setLayerExtremes() throws EvaluationException {
		grid.setLayerExtremes();
	}

	/**
	 * Provides the Grid the Facade is using
	 * 
	 * @return Grid object
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * Sets the update method to use the PriorityUpdate system
	 */
	public void setPriorityUpdate(int minPriority, int maxPriority) {
		grid.setPriorityUpdater(minPriority, maxPriority);
	}

	/**
	 * Sets the update method to use the AtomicUpdate system
	 */
	public void setAtomicUpdate() {
		grid.setAtomicUpdater();
	}

	/**
	 * Sets the update method to use the LinearUpdate system LinearUpdate is
	 * the default
	 */
	public void setLinearUpdate() {
		grid.setLinearUpdater();
	}

	/**
	 * Returns a List of Triggers for a specific prototype
	 * 
	 * @return
	 */
	public static List<Trigger> getPrototypeTriggers(Prototype p) {
		return p.getTriggers();
	}

	/**
	 * Sample simulation: Conway's Game of Life
	 */
	public void initGameOfLife() {
		clearPrototypes();
		grid.setPriorityUpdater(0, 50);
		
		// add prototypes
		new ConwaysDeadBeing().initSampleAgent(new Prototype(grid, new Color(219, 219, 219), "deadBeing"));
		new ConwaysAliveBeing().initSampleAgent(new Prototype(grid, new Color(93, 198, 245), "aliveBeing"));

		// Place dead beings in Grid with some that are alive
		for (int x = 0; x < grid.getWidth(); x++)
			for (int y = 0; y < grid.getHeight(); y++) {
				if (x == grid.getWidth() / 2) {
					grid.spiralSpawn(Prototype.getPrototype("aliveBeing")
							.createAgent(), x, y);
				} else {
					grid.spiralSpawn(Prototype.getPrototype("deadBeing")
							.createAgent(), x, y);
				}
			}
	}
	
	/**
	 * Sets up the rock paper and scissors sample units
	 */
	public void initRockPaperScissors(){
		setPriorityUpdate(0, 60);
		new Rock().initSampleAgent(new Prototype(grid, "rock"));
		new Paper().initSampleAgent(new Prototype(grid, "paper"));
		new Scissors().initSampleAgent(new Prototype(grid, "scissors"));
	}

	/**
	 * Gets a field with the given string. Simple wrapper function.
	 * 
	 * @param s
	 *            The name of the field.
	 * @return The field to return.
	 */
	public Field getGlobalField(String s) {
		return grid.getField(s);
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
		grid.updateField(name, value);
	}

	/**
	 * Gets a map holding all values for the global fields.
	 * 
	 * @return A map holding all values for the global fields.
	 */
	public Map<String, String> getGlobalFieldMap() {
		return grid.getFieldMap();
	}

	/**
	 * Adds a global field to the simulation.
	 * 
	 * @param name
	 * @param startingValue
	 */
	public void addGlobalField(String name, String startingValue) {
		try {
			grid.addField(name, startingValue);
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
		grid.removeField(name);
	}

	/**
	 * TODO Are we ensuring that each trigger's priority will be unique? Or
	 * should we use names instead to keep track of them in the hashmap? That
	 * might run into fewer issue while editing (possibility of changing
	 * priorities of the same trigger)
	 */
}
