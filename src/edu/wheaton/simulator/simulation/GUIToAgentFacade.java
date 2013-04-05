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

import net.sourceforge.jeval.EvaluationException;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

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
		grid = new Grid(gridX, gridY);
	}

	/**
	 * Adds a Prototype to the prototype HashMap
	 * 
	 * @param n
	 * @param g
	 * @param c
	 */
	public void createPrototype(String n, Grid g, Color c) {
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
	public void createPrototype(String n, Grid g, Color c, byte[] d) {
		Prototype.addPrototype(new Prototype(g, c, d, n));
	}

	/**
	 * Returns the Prototype that corresponds to the given string.
	 * 
	 * @param n
	 * @return
	 */
	public Prototype getPrototype(String n) {
		return Prototype.getPrototype(n);
	}

	/**
	 * Gets a Set of the prototype names
	 * 
	 * @return
	 */
	public Set<String> prototypeNames() {
		return Prototype.prototypeNames();
	}

	/**
	 * Whether or not a given field is contained in a Prototype
	 * 
	 * @param p
	 * @param fieldName
	 * @return
	 */
	public boolean prototypeHasField(Prototype p, String fieldName) {
		return p.hasField(fieldName);
	}

	/**
	 * Whether or not a given trigger is contained in a Prototype
	 * 
	 * @param p
	 * @param triggerName
	 * @return
	 */
	public boolean prototypeHasTrigger(Prototype p, String triggerName) {
		return p.hasTrigger(triggerName);
	}

	/**
	 * Causes all entities in the grid to act()
	 */
	public void updateEntities() throws SimulationPauseException{
		grid.updateEntities();
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
	public void newLayer(String fieldName, Color c) {
		grid.newLayer(fieldName, c);
	}

	/**
	 * Loops through the grid and set's the Layer's min/max values. This must
	 * be done before a Layer is shown. Usually every step if the Layer is
	 * being displayed. PRECONDITION: The newLayer method has been called to
	 * setup a layer
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

	public void setPriorityUpdate() {
		grid.setPriorityUpdater();
	}
	
	public void setAtomicUpdate() {
		grid.setAtomicUpdater();
	}
	
	public void setLinearUpdate() {
		grid.setLinearUpdater();
	}
	
	/**
	 * Returns a List of Triggers for a specific prototype
	 * 
	 * @return
	 */
	public List<Trigger> getPrototypeTriggers(Prototype p) {
		return p.getTriggers();
	}

	public void initGameOfLife() {

		// grid = new Grid(grid.getWidth(), grid.getHeight());

		Prototype deadBeing = new Prototype(grid, Color.RED, "deadBeing");
		grid.setPriorityUpdater();
		// Add fields
		try {
			deadBeing.addField("alive", 0 + ""); // 0 for false, 1 for true
			deadBeing.addField("age", 0 + "");
			deadBeing.addField("neighbors", 0 + "");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}

		// Set up conditionals
		Expression isAlive = new Expression("this.alive == 1");
		Expression neigh1 = new Expression("getFieldOfAgentAt(this.x-1, this.y-1, 'alive') == 1");
		Expression neigh2 = new Expression("getFieldOfAgentAt(this.x, this.y-1, 'alive') == 1");
		Expression neigh3 = new Expression("getFieldOfAgentAt(this.x+1, this.y-1, 'alive') == 1");
		Expression neigh4 = new Expression("getFieldOfAgentAt(this.x-1, this.y, 'alive') == 1");
		Expression neigh5 = new Expression("getFieldOfAgentAt(this.x+1, this.y, 'alive') == 1");
		Expression neigh6 = new Expression("getFieldOfAgentAt(this.x-1, this.y+1, 'alive') == 1");
		Expression neigh7 = new Expression("getFieldOfAgentAt(this.x, this.y+1, 'alive') == 1");
		Expression neigh8 = new Expression("getFieldOfAgentAt(this.x+1, this.y+1, 'alive') == 1");
		Expression dieCond = new Expression("(this.alive == 1) && (this.neighbors < 2 || this.neighbors > 3)");
		Expression reviveCond = new Expression("(this.alive == 0) && (this.neighbors == 3)");

		// Set up behaviors
		Expression incrementAge = new Expression("setField('this', 'age', this.age+1)");
		Expression incrementNeighbors = new Expression("setField('this', 'neighbors', this.neighbors+1)");
		Expression die = new Expression("setField('this', 'alive', 0) || setField('this', 'age', 0) || " +
				"setField('this', 'colorRed', 250) || setField('this', 'colorGreen', 0) || setField('this', 'colorBlue', 0)");
		Expression revive = new Expression("setField('this', 'alive', 1) || " +
				"setField('this', 'colorRed', 0) || setField('this', 'colorGreen', 0) || setField('this', 'colorBlue', 250)");
		Expression resetNeighbors = new Expression("setField('this', 'neighbors', 0)");

		// Add triggers
		deadBeing.addTrigger(new Trigger("updateAge", 1, isAlive, incrementAge));
		deadBeing.addTrigger(new Trigger("resetNeighbors", 2, new Expression("true"), resetNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh1", 3, neigh1, incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh2", 4, neigh2, incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh3", 5, neigh3, incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh4", 6, neigh4, incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh5", 7, neigh5, incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh6", 8, neigh6, incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh7", 9, neigh7, incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh8", 10, neigh8, incrementNeighbors));
		deadBeing.addTrigger(new Trigger("die", 11, dieCond, die));
		deadBeing.addTrigger(new Trigger("revive", 12, reviveCond, revive));

		// Add the prototype to the static list of Prototypes
		Prototype.addPrototype(deadBeing);

		// Make a another prototype that is initially alive
		Prototype aliveBeing = new Prototype(grid, Color.BLUE, "aliveBeing");

		// Add fields
		try {
			aliveBeing.addField("alive", 1 + ""); // 0 for false, 1 for true
			aliveBeing.addField("age", 0 + "");
			aliveBeing.addField("neighbors", 0 + "");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}

		// Add triggers
		aliveBeing.addTrigger(new Trigger("updateAge", 1, isAlive, incrementAge));
		aliveBeing.addTrigger(new Trigger("resetNeighbors", 2, new Expression("true"), resetNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh1", 3, neigh1, incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh2", 4, neigh2, incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh3", 5, neigh3, incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh4", 6, neigh4, incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh5", 7, neigh5, incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh6", 8, neigh6, incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh7", 9, neigh7, incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh8", 10, neigh8, incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("die", 11, dieCond, die));
		aliveBeing.addTrigger(new Trigger("revive", 12, reviveCond, revive));

		// Add the prototype to the static list of Prototypes
		Prototype.addPrototype(aliveBeing);

		// Place dead beings in Grid with some that are alive
		for (int x = 0; x < grid.getWidth(); x++) 
			for(int y = 0; y < grid.getHeight(); y++) {
				if (x == 4 || x == 5 || y == 5) {
					grid.spiralSpawn(Prototype.getPrototype("aliveBeing").createAgent(), x, y);
				} else {
					grid.spiralSpawn(Prototype.getPrototype("deadBeing").createAgent(), x, y);
				}
			}
	}
	
	/**
	 * Gets a field with the given string. Simple wrapper function.
	 * @param s The name of the field.
	 * @return The field to return.
	 */
	public Field getGlobalField(String s) {
		return grid.getField(s);
	}
	
	/**
	 * Updates the field with the given name to the given value.
	 * @param name The name of the field to update.
	 * @param value The new value of the field.
	 */
	public void updateGlobalField(String name, String value) {
		grid.updateField(name, value);
	}
	
	/**
	 * Gets a map holding all values for the global fields.
	 * @return A map holding all values for the global fields.
	 */
	public Map<String, String> getGlobalFieldMap() {
		return grid.getFieldMap();
	}
	
	/**
	 * Adds a global field to the simulation.
	 * @param name
	 * @param startingValue
	 */
	public void addGlobalField(String name, String startingValue) {
		try {
			grid.addField(name, startingValue);
		} catch (ElementAlreadyContainedException e) {
			System.out.println("Problem adding a global field. Name already in the map. Exiting.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Removes the global field with the given name.
	 * @param name The name of the field to remove.
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
