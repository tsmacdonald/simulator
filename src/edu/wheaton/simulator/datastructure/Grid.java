/**
 * Grid.java
 * 
 * Models a cartesian-based coordinate plane for the actors to interact within.
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * 
 * Wheaton College, CSCI 335, Spring 2013
 */
package edu.wheaton.simulator.datastructure;

import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.EntityID;
import edu.wheaton.simulator.simulation.Layer;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class Grid implements Iterable<Slot> {

	/**
	 * The minimum and maximum priorities for priorityUpdateEntities()
	 * Should be changed so that the user can define these values
	 * Or that they are defined by checking minimum and maximum priorities
	 * of all triggers of all agents in a simulation
	 */
	private int minPriority = 0;
	private int maxPriority = 20;
	
	/**
	 * The grid of all slots containing all Agent objects Total # slots = Width
	 * x Height
	 */
	private Slot[][] grid;
	private final Integer width;
	private final Integer height;
	private Updater updater = new LinearUpdater();
	
	/**
	 * Constructor. Creates a grid with the given width and height
	 * specifications
	 * 
	 * @param width
	 * @param height
	 */
	public Grid(int width, int height) {
		this.width = width;
		this.height = height;

		grid = new Slot[getHeight()][getWidth()];
		for (int x = 0; x < getWidth(); x++)
			for (int y = 0; y < getHeight(); y++)
				setSlot(new Slot(this), x, y);
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public boolean isValidCoord(int x, int y) {
		return (x >= 0) && (y >= 0) && x < getWidth() && y < getHeight();
	}

	public Slot getSlot(int x, int y) {
		if (isValidCoord(x, y))
			return grid[y][x];
		System.err.println("invalid Coord: " + x + "," + y);
		throw new ArrayIndexOutOfBoundsException();
	}

	public void setSlot(Slot s, int x, int y) {
		if (isValidCoord(x, y))
			grid[y][x] = s;
		else {
			System.err.println("invalid Coord: " + x + "," + y);
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * Causes all the triggers of all the entities in the grid to be fired
	 * 
	 * @throws SimulationPauseException
	 */
	public void updateEntities() throws SimulationPauseException {

		updater.update();
		
	}

	public void setLinearUpdater() {
		updater = new LinearUpdater();
	}
	
	public void setPriorityUpdater() {
		updater = new PriorityUpdater();
	}
	
	public void setAtomicUpdater() {
		updater = new AtomicUpdater();
	}
	
	private static interface Updater {
		
		public void update() throws SimulationPauseException;
		
	}
	
	
	private class LinearUpdater implements Updater {
		
		/**
		 * Causes all entities in the grid to act(). Checks to make sure each Agent
		 * has only acted once this iteration.
		 * 
		 * @throws SimulationPauseException
		 */
		public void update() throws SimulationPauseException {
			HashSet<EntityID> processedIDs = new HashSet<EntityID>();

			for (Slot[] row : grid)
				for (Slot currentSlot : row) {
					Agent current = currentSlot.getAgent();
					if (current != null)
						if (!processedIDs.contains(current.getEntityID())) {
							current.act();
							processedIDs.add(current.getEntityID());
						}
				}
		}
	}
	
	
	private class PriorityUpdater implements Updater {
		
		/**
		 * Makes the entities in the grid perform their triggers in ascending
		 * priority order; that is, priority takes precedence over Agent order
		 * for when triggers are evaluated.
		 * 
		 * @throws SimulationPauseException
		 */
		public void update() throws SimulationPauseException {
			for (int priority = minPriority; priority <= maxPriority; priority++) {
				HashSet<EntityID> processedIDs = new HashSet<EntityID>();

				for (Slot[] row : grid)
					for (Slot currentSlot : row) {
						Agent current = currentSlot.getAgent();
						if (current != null)
							if (!processedIDs.contains(current.getEntityID())) {
								current.priorityAct(priority);
								processedIDs.add(current.getEntityID());
							}
					}
			}
		}
	}
	
	private class AtomicUpdater implements Updater {
		
		public void update() throws SimulationPauseException{
			//TODO: Implement atomic update
		}
	}
	
	/**
	 * Places an Agent to the slot at the given coordinates. This method
	 * replaces (kills) anything that is currently in that position. The
	 * Agent's own position is also updated accordingly.
	 * 
	 * @param a
	 * @param x
	 * @param y
	 */
	public boolean addAgent(Agent a, int x, int y) {
		if (emptySlot(x, y)) {
			getSlot(x, y).setAgent(a);
			a.setPos(x, y);
			return true;
		}
		return false;
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
	public boolean spawnAgent(Agent a, int spawnX, int spawnY) {

		a.setPos(-1, -1);

		for (int distance = 0; distance < height || distance < width; distance++) {
			int x = spawnX - distance;
			int y = spawnY - distance;
			if (spawnAgentHelper(a, x, y))
				return true;
			for (; x < spawnX + distance; x++)
				if (spawnAgentHelper(a, x, y))
					return true;
			for (; y < spawnY + distance; y++)
				if (spawnAgentHelper(a, x, y))
					return true;
			for (; x > spawnX - distance; x--)
				if (spawnAgentHelper(a, x, y))
					return true;
			for (; y > spawnY - distance; y--)
				if (spawnAgentHelper(a, x, y))
					return true;
		}
		return false;
	}

	private boolean spawnAgentHelper(Agent a, int x, int y) {
		if (emptySlot(x, y)) {
			addAgent(a, x, y);
			return true;
		}
		return false;
	}

	/**
	 * Returns true if a slot is empty, false otherwise. Also returns false if
	 * invalid x, y values are given.
	 * 
	 * @param x
	 * @param y
	 * @return Whether or not the particular slot is empty
	 */
	public boolean emptySlot(int x, int y) {
		if (isValidCoord(x, y) && getSlot(x, y).getAgent() == null)
			return true;
		return false;
	}

	/**
	 * Adds the given Agent to a random (but free) position.
	 * 
	 * @param a
	 *            The Agent to add.
	 */
	public boolean spawnAgent(Agent a) {
		int randomX = (int) (Math.random() * (width - 1));
		int randomY = (int) (Math.random() * (height - 1));
		return spawnAgent(a, randomX, randomY);
	}

	/**
	 * Returns the Agent in the slot at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public Agent getAgent(int x, int y) {
		return getSlot(x, y).getAgent();
	}

	/**
	 * Removes an Agent from the slot at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public boolean removeAgent(int x, int y) {
		if (isValidCoord(x, y)) {
			Slot slot = getSlot(x, y);
			if (slot.getAgent() != null)
				return slot.setAgent(null);
		}
		System.err.println("Grid.removeAgent(" + x + "," + y
				+ ") : invalid coord");
		return false;
	}

	/**
	 * Removes the given agent from the grid.
	 * 
	 * @param a
	 *            The Agent to remove.
	 */
	public boolean removeAgent(Agent a) {
		int x = a.getPosX();
		int y = a.getPosY();
		if (isValidCoord(x, y)) {
			Slot slot = getSlot(x, y);
			Agent b = slot.getAgent();
			if (b != null && b.getEntityID().equals(a.getEntityID()))
				return slot.setAgent(null);
		}
		System.err.println("Grid.removeAgent(Agent a) : agent not found");
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
	public void newLayer(String fieldName, Color c) {
		Layer.getInstance().setFieldName(fieldName);
		Layer.getInstance().setColor(c);
		Layer.getInstance().resetMinMax();
	}

	/**
	 * Loops through the grid and set's the Layer's min/max values
	 * PRECONDITION: The newLayer method has been called to setup a layer
	 * 
	 * @throws EvaluationException
	 */
	public void setLayerExtremes() throws EvaluationException {
		Iterator<Slot> it = iterator();
		while (it.hasNext()) {
			Slot current = it.next();
			if (current.getAgent() != null) {
				Field currentField = current.getAgent().getField(
						Layer.getInstance().getFieldName());
				Layer.getInstance().setExtremes(currentField);
			}
		}
	}

	/**
	 * Returns an iterator that goes through the Slots in the Grid
	 * 
	 * @return Iterator<Slot>
	 */
	@Override
	public Iterator<Slot> iterator() {
		return new Iterator<Slot>() {

			int x = 0;
			int y = 0;

			@Override
			public boolean hasNext() {
				return y < getHeight();
			}

			@Override
			public Slot next() {
				Slot toReturn = getSlot(x, y);
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
				// TODO method stub
				throw new UnsupportedOperationException();
			}
		};
	}

}
