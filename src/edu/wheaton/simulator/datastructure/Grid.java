/**
 * Grid.java
 * 
 * Models a cartesian-based coordinate plane for the actors to interact within.
 * 
 * @author Agent team
 * 
 * Wheaton College, CSCI 335, Spring 2013
 */
package edu.wheaton.simulator.datastructure;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.AgentID;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.simulation.Layer;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class Grid extends Entity implements Iterable<Agent> {

	/**
	 * The grid of all Agents
	 */
	private Agent[][] grid;

	/**
	 * Current update state
	 */
	private Updater updater = new LinearUpdater(this);

	/**
	 * Observers to watch the grid
	 */
	private Set<GridObserver> observers;

	/**
	 * Number of iterations performed
	 */
	private int step;

	/**
	 * Constructor. Creates a grid with the given width and height
	 * specifications
	 * 
	 * @param width
	 * @param height
	 */
	public Grid(int width, int height) {
		super();
		try {
			addField("width", width + "");
			addField("height", height + "");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		grid = new Agent[getHeight()][getWidth()];
		updater = new LinearUpdater(this);
		observers = new HashSet<GridObserver>();
		step = 0;
	}

	/**
	 * Hackish solution to Akon's gridrecorder. Should never ever be called
	 * other than from the stats people.
	 */
	public static AgentID getID() {
		AgentID id = new AgentID(-1);
		return id;
	}

	/**
	 * Changes the size of the grid
	 * 
	 * @param width
	 * @param height
	 */
	public void resizeGrid(int width, int height) {
		// TODO
	}

	/**
	 * Provides this grid's width
	 * 
	 * @return
	 */
	public Integer getWidth() {
		return getField("width").getIntValue();
	}

	/**
	 * Provides this grid's height
	 * 
	 * @return
	 */
	public Integer getHeight() {
		return getField("height").getIntValue();
	}

	/**
	 * Checks whether the given x/y position is a valid coordinate (both larger
	 * than 0 and smaller than width/height respectively)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isValidCoord(int x, int y) {
		return (x >= 0) && (y >= 0) && x < getWidth() && y < getHeight();
	}

	/**
	 * Causes all the triggers of all the entities in the grid to be fired
	 * 
	 * @throws SimulationPauseException
	 */
	public void updateEntities() throws SimulationPauseException {
		updater.update();
		step++;
	}

	/**
	 * Provides the iteration number
	 * 
	 * @return the grid step
	 */
	public int getStep() {
		return step;
	}

	/**
	 * Makes updater a LinearUpdater
	 */
	public void setLinearUpdater() {
		updater = new LinearUpdater(this);
	}

	/**
	 * Makes updater a PriorityUpdater
	 */
	public void setPriorityUpdater(int minPriority, int maxPriority) {
		updater = new PriorityUpdater(this, minPriority, maxPriority);
	}

	/**
	 * makes updater an AtomicUpdater
	 */
	public void setAtomicUpdater() {
		updater = new AtomicUpdater(this);
	}

	public String currentUpdater() {
		return updater.toString();
	}

	/**
	 * Returns the Agent at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public Agent getAgent(int x, int y) {
		if (isValidCoord(x, y))
			return grid[y][x];
		System.err.println("invalid Coord: " + x + "," + y);
		throw new ArrayIndexOutOfBoundsException();
	}

	/**
	 * Places an Agent at the given coordinates. This method replaces (kills)
	 * anything that is currently in that position. The Agent's own position is
	 * also updated accordingly.
	 * 
	 * @param a
	 * @param x
	 * @param y
	 * @return false if the x/y values were invalid
	 */
	public boolean addAgent(Agent a, int x, int y) {
		if (isValidCoord(x, y)) {
			grid[y][x] = a;
			a.setPos(x, y);
			return true;
		}
		return false;
	}

	/**
	 * Places an Agent at a random position in the grid. This method replaces
	 * (kills) anything that is currently in that position. The Agent's own
	 * position is also updated accordingly.
	 * 
	 * @param a
	 * @return returns true if successful
	 */
	public boolean addAgent(Agent a) {
		int randomX = (int) (Math.random() * (getField("width").getIntValue() - 1));
		int randomY = (int) (Math.random() * (getField("height").getIntValue() - 1));
		grid[randomY][randomX] = a;
		a.setPos(randomX, randomY);
		return true;
	}

	/**
	 * Adds the given Agent at the closest free spot to the spawn position. The
	 * search for an open spot begins at the given x/y and then spirals
	 * outwards.
	 * 
	 * @param a
	 *            The Agent to add.
	 * @param spawnX
	 *            Central x location for spawn
	 * @param spawnY
	 *            Central y location for spawn
	 * @return true if successful (Agent added), false otherwise
	 */
	public boolean spiralSpawn(Agent a, int spawnX, int spawnY) {

		a.setPos(-1, -1);
		int largestDistance = largestDistanceToSide(spawnX, spawnY);
		for (int distance = 0; distance <= largestDistance; distance++) {
			int x = spawnX - distance;
			int y = spawnY - distance;
			if (spawnHelper(a, x, y))
				return true;
			for (; x < spawnX + distance; x++)
				if (spawnHelper(a, x, y))
					return true;
			for (; y < spawnY + distance; y++)
				if (spawnHelper(a, x, y))
					return true;
			for (; x > spawnX - distance; x--)
				if (spawnHelper(a, x, y))
					return true;
			for (; y > spawnY - distance; y--)
				if (spawnHelper(a, x, y))
					return true;
		}
		return false;
	}

	/**
	 * Calculates the biggest distance from this given x/y to a wall.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int largestDistanceToSide(int x, int y) {
		int presentMax = getField("width").getIntValue() - x - 1; // presetMax
		// = (x -->
		// width)
		if (presentMax < x) // presentMax < (0 --> x)
			presentMax = x;
		if (presentMax < (getField("height").getIntValue() - y - 1)) // presentMax
			// < (y
			// -->
			// height)
			presentMax = getField("height").getIntValue() - y - 1;
		if (presentMax < y) // presentMax < (0 --> y)
			presentMax = y;
		return presentMax;
	}

	/**
	 * Adds an Agent to a free spot along the given row
	 * 
	 * @param a
	 *            The Agent to add.
	 * @param row
	 *            The y position of the row
	 * @return true if successful (Agent added), false otherwise
	 */
	public boolean horizontalSpawn(Agent a, int row) {
		for (int x = 0; x < getField("width").getIntValue(); x++)
			if (spawnHelper(a, x, row))
				return true;
		return false;
	}

	/**
	 * Adds an Agent to a free spot in the given column
	 * 
	 * @param a
	 *            The Agent to add.
	 * @param column
	 *            The x position of the column
	 * @return true if successful (Agent added), false otherwise
	 */
	public boolean verticalSpawn(Agent a, int column) {
		for (int y = 0; y < getField("height").getIntValue(); y++)
			if (spawnHelper(a, column, y))
				return true;
		return false;
	}

	/**
	 * Adds an Agent to the specified x/y if that position is empty.
	 * 
	 * @param a
	 * @param x
	 * @param y
	 * @return true when added, false otherwise
	 */
	private boolean spawnHelper(Agent a, int x, int y) {
		if (emptyPos(x, y)) {
			addAgent(a, x, y);
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the given space is empty, false otherwise. Also returns
	 * false if invalid x, y values are given.
	 * 
	 * @param x
	 * @param y
	 * @return Whether or not the particular position is empty
	 */
	public boolean emptyPos(int x, int y) {
		if (isValidCoord(x, y) && getAgent(x, y) == null)
			return true;
		return false;
	}

	/**
	 * Adds the given Agent to a random (but free) position.
	 * 
	 * @param a
	 *            The Agent to add.
	 */
	public boolean spiralSpawn(Agent a) {
		int randomX = (int) (Math.random() * (getField("width").getIntValue() - 1));
		int randomY = (int) (Math.random() * (getField("height").getIntValue() - 1));
		return spiralSpawn(a, randomX, randomY);
	}

	/**
	 * Removes an Agent at the given coordinates
	 * 
	 * @param x
	 * @param y
	 * @return false if invalid coordinates
	 */
	public boolean removeAgent(int x, int y) {
		if (isValidCoord(x, y)) {
			grid[y][x] = null;
			return true;
		}
		return false;
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
		Layer.getInstance().setFieldName(fieldName);
		Layer.getInstance().setColor(c);
		Layer.getInstance().resetMinMax();
	}

	/**
	 * Resets the min/max values of the layer and then loops through the grid to
	 * set's a new Layer's min/max values PRECONDITION: The newLayer method has
	 * been called to setup a layer
	 * 
	 * @throws EvaluationException
	 */
	public void setLayerExtremes() throws EvaluationException {
		Layer.getInstance().resetMinMax();
		for (Iterator<Agent> it = iterator(); it.hasNext();) {
			Agent current = it.next();
			if (current != null) {
				Field currentField = current.getField(Layer.getInstance()
						.getFieldName());
				Layer.getInstance().setExtremes(currentField);
			}
		}
	}

	/**
	 * Adds an observer to the grid's list
	 * 
	 * @param ob
	 */
	public void addObserver(GridObserver ob) {
		observers.add(ob);
	}

	/**
	 * Notifies all of the observers watching this grid
	 */
	public void notifyObservers() {
		for (GridObserver current : observers)
			synchronized(this) {
				current.update(this);
			}
	}

	/**
	 * Returns an iterator that goes through the Agents in the Grid
	 * 
	 * @return Iterator<Agent>
	 */
	@Override
	public Iterator<Agent> iterator() {
		return new Iterator<Agent>() {

			int x = 0;
			int y = 0;

			@Override
			public boolean hasNext() {
				return y < getHeight();
			}

			@Override
			public Agent next() {
				Agent toReturn = getAgent(x, y);
				if (x < getWidth() - 1) {
					x++;
				} else {
					x = 0;
					y++;
				}
				return toReturn;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Provides all of the user created fields - thus doesn't include width and
	 * height
	 */
	public Map<String, String> getCustomFieldMap() {
		Map<String, String> toReturn = new HashMap<String, String>(fields);
		toReturn.remove("width");
		toReturn.remove("height");
		return toReturn;
	}

}
