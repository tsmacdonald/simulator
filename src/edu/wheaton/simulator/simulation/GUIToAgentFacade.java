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
	 * Resets the static list of prototypes
	 */
	public void clearPrototypes() {
		Prototype.clearPrototypes();
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
	public void updateEntities() throws SimulationPauseException {
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
	public void setPriorityUpdate() {
		grid.setPriorityUpdater();
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
	public List<Trigger> getPrototypeTriggers(Prototype p) {
		return p.getTriggers();
	}

	public void initGameOfLife() {

		// grid = new Grid(grid.getWidth(), grid.getHeight());

		Prototype deadBeing = new Prototype(grid, new Color(219, 219, 219), "deadBeing");
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
		Expression neigh1 = new Expression(
				"getFieldOfAgentAt(this.x-1, this.y-1, 'alive') == 1");
		Expression neigh2 = new Expression(
				"getFieldOfAgentAt(this.x, this.y-1, 'alive') == 1");
		Expression neigh3 = new Expression(
				"getFieldOfAgentAt(this.x+1, this.y-1, 'alive') == 1");
		Expression neigh4 = new Expression(
				"getFieldOfAgentAt(this.x-1, this.y, 'alive') == 1");
		Expression neigh5 = new Expression(
				"getFieldOfAgentAt(this.x+1, this.y, 'alive') == 1");
		Expression neigh6 = new Expression(
				"getFieldOfAgentAt(this.x-1, this.y+1, 'alive') == 1");
		Expression neigh7 = new Expression(
				"getFieldOfAgentAt(this.x, this.y+1, 'alive') == 1");
		Expression neigh8 = new Expression(
				"getFieldOfAgentAt(this.x+1, this.y+1, 'alive') == 1");
		Expression dieCond = new Expression(
				"(this.alive == 1) && (this.neighbors < 2 || this.neighbors > 3)");
		Expression reviveCond = new Expression(
				"(this.alive == 0) && (this.neighbors == 3)");

		// Set up behaviors
		Expression incrementAge = new Expression(
				"setField('this', 'age', this.age+1)");
		Expression incrementNeighbors = new Expression(
				"setField('this', 'neighbors', this.neighbors+1)");
		Expression die = new Expression(
				"setField('this', 'alive', 0) || setField('this', 'age', 0) || "
						+ "setField('this', 'colorRed', 219) || setField('this', 'colorGreen', 219) || setField('this', 'colorBlue', 219)");
		Expression revive = new Expression(
				"setField('this', 'alive', 1) || "
						+ "setField('this', 'colorRed', 93) || setField('this', 'colorGreen', 198) || setField('this', 'colorBlue', 245)");
		Expression resetNeighbors = new Expression(
				"setField('this', 'neighbors', 0)");

		// Add triggers
		deadBeing
				.addTrigger(new Trigger("updateAge", 1, isAlive, incrementAge));
		deadBeing.addTrigger(new Trigger("resetNeighbors", 2, new Expression(
				"true"), resetNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh1", 3, neigh1,
				incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh2", 4, neigh2,
				incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh3", 5, neigh3,
				incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh4", 6, neigh4,
				incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh5", 7, neigh5,
				incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh6", 8, neigh6,
				incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh7", 9, neigh7,
				incrementNeighbors));
		deadBeing.addTrigger(new Trigger("checkNeigh8", 10, neigh8,
				incrementNeighbors));
		deadBeing.addTrigger(new Trigger("die", 11, dieCond, die));
		deadBeing.addTrigger(new Trigger("revive", 12, reviveCond, revive));

		// Add the prototype to the static list of Prototypes
		Prototype.addPrototype(deadBeing);

		// Make a another prototype that is initially alive
		Prototype aliveBeing = new Prototype(grid, new Color(93, 198, 245), "aliveBeing");

		// Add fields
		try {
			aliveBeing.addField("alive", 1 + ""); // 0 for false, 1 for true
			aliveBeing.addField("age", 0 + "");
			aliveBeing.addField("neighbors", 0 + "");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}

		// Add triggers
		aliveBeing.addTrigger(new Trigger("updateAge", 1, isAlive,
				incrementAge));
		aliveBeing.addTrigger(new Trigger("resetNeighbors", 2, new Expression(
				"true"), resetNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh1", 3, neigh1,
				incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh2", 4, neigh2,
				incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh3", 5, neigh3,
				incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh4", 6, neigh4,
				incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh5", 7, neigh5,
				incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh6", 8, neigh6,
				incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh7", 9, neigh7,
				incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("checkNeigh8", 10, neigh8,
				incrementNeighbors));
		aliveBeing.addTrigger(new Trigger("die", 11, dieCond, die));
		aliveBeing.addTrigger(new Trigger("revive", 12, reviveCond, revive));

		// Add the prototype to the static list of Prototypes
		Prototype.addPrototype(aliveBeing);

		// Place dead beings in Grid with some that are alive
		for (int x = 0; x < grid.getWidth(); x++)
			for (int y = 0; y < grid.getHeight(); y++) {
				if (x == 4) {
					grid.spiralSpawn(Prototype.getPrototype("aliveBeing")
							.createAgent(), x, y);
				} else {
					grid.spiralSpawn(Prototype.getPrototype("deadBeing")
							.createAgent(), x, y);
				}
			}
	}

	/*
	 * For details about what Rock Paper Scissors is supposed to do, visit
	 * 
	 * Prototypes for rock, paper and scissors (black, blue, red) are made and
	 * added to the grid. Uses fields in the agent as flags to indicate what
	 * next action the agent should do. To simulate the iteration needed to
	 * check multiple directions, many static triggers are added to each agent.
	 * 
	 * A call to this function can be added to NewSimScreenFinishListner.java
	 * to apply the method to visualize the effects of this method.
	 * 
	 * Some testing suggests that errors will occur if this method is used and
	 * then agents are modified mid simulation.
	 */
	public void initRockPaperScissors() {
		// names of the agents
		String[] agentType = { "rock", "paper", "scissors" };

		// openSpot conditionals
		Expression freeSpot = new Expression(
				"this.endTurn != 1"
						+ "&&isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");
		Expression notFreeSpot = new Expression(
				"this.endTurn != 1"
						+ "&&!isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");

		// Move behavior
		Expression move = new Expression(
				"move('this', this.x + this.xNextDirection, this.y + this.yNextDirection)"
						+ "&& setField('this', 'endTurn', 1)");
		/*
		 * Turn clockwise (in 8 directions) Uses 'temp' because as JEval
		 * evaluates each function in order and xNextDirection would become
		 * corrupted in the second use
		 */
		Expression rotateClockwise = new Expression(
				" setField('this', 'temp', this.xNextDirection) || setField('this', 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
						+ " || setField('this', 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");

		// turn counter clockwise
		Expression rotateCounterClockwise = new Expression(
				" setField('this', 'temp', this.xNextDirection) || setField('this', 'xNextDirection', round(this.xNextDirection * cos(-PI/4) - this.yNextDirection * sin(-PI/4)))"
						+ " || setField('this', 'yNextDirection', round(this.temp * sin(-PI/4) + this.yNextDirection * cos(-PI/4)))");

		// Check for agent ahead
		Expression isAgentAhead = new Expression(
				"this.endTurn != 1"
						+ "&& isValidCoord(this.x + this.xNextDirection, this.y + this.yNextDirection) && !isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");

		// setAgentAhead field to true
		Expression setAgentAhead = new Expression(
				"setField('this', 'agentAhead', 1)");

		// reads flag that is set when there is an agent ahead
		Expression checkAgentAheadFlag = new Expression("this.endTurn != 1" + // may
																				// not
																				// be
																				// needed
				"&& this.agentAhead == 1.0");

		// collect information about conflict
		Expression setConflictAheadFlag = new Expression(
				"setField('this', 'conflictAhead',"
						+ " getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'typeID') == (this.typeID + 2)%3"
						+ " && getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'xNextDirection') == - this.xNextDirection"
						+ " && getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'yNextDirection') == - this.yNextDirection)");

		// check the flag that is set when a conflict is ahead
		Expression checkConflictAheadFlag = new Expression("this.endTurn != 1"
				+ // may not be needed
				" && this.conflictAhead");

		// conflict behavior
		Expression engageInConflict = new Expression(
				"die('this')"
						+ "&& cloneAgentAtPosition('this',this.x + this.xNextDirection, this.y +this.yNextDirection, this.x, this.y)"
						+ "&& setFieldOfAgent('this', this.x + this.xNextDirection, this.y + this.yNextDirection, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent('this', this.x + this.xNextDirection, this.y + this.yNextDirection, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('this', 'endTurn', 1)");

		// reset all the flags that are used to determine behavior
		Expression resetConflictFlags = new Expression(
				"setField('this', 'agentAhead', 0)|| setField('this', 'conflictAhead', 0)");
		Expression resetEndTurnFlag = new Expression(
				"setField('this', 'endTurn', 0)");

		// add prototypes and fields. The type (RPS) is stored as an ID and
		// string name.
		for (int j = 0; j < agentType.length; j++) {
			Prototype proto = new Prototype(grid, Color.black, agentType[j]);
			if (j == 1)
				proto = new Prototype(grid, Color.blue, agentType[j]);
			if (j == 2)
				proto = new Prototype(grid, Color.red, agentType[j]);
			try {
				proto.addField("typeID", j + "");
				proto.addField("xNextDirection", 0 + "");
				proto.addField("yNextDirection", -1 + "");
				proto.addField("temp", 0 + "");
				proto.addField("agentAhead", "" + 0);
				proto.addField("conflictAhead", "" + 0);
				proto.addField("endTurn", "" + 0);
			} catch (ElementAlreadyContainedException e) {
				e.printStackTrace();
			}

			/*
			 * Unfortunately, our implementation of triggers makes this the
			 * best way to get agents to check all eight directions before
			 * ending their turn. (55 separate triggers are made)
			 */
			for (int i = 0; i < 8; i++) {
				proto.addTrigger(new Trigger("agentAhead", 1, isAgentAhead,
						setAgentAhead));
				proto.addTrigger(new Trigger("conflictAhead", 1,
						checkAgentAheadFlag, setConflictAheadFlag));
				proto.addTrigger(new Trigger("engageConflict", 1,
						checkConflictAheadFlag, engageInConflict));
				proto.addTrigger(new Trigger("rotateClockwise", 1,
						notFreeSpot, rotateClockwise));
				proto.addTrigger(new Trigger("move", 1, freeSpot, move));
				proto.addTrigger(new Trigger("resetConflictFlags", 1,
						new Expression("true"), resetConflictFlags));
			}
			proto.addTrigger(new Trigger("resetConflictFlags", 1,
					new Expression("true"), resetEndTurnFlag));

			Prototype.addPrototype(proto);
		}
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
