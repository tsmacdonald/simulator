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
import java.util.Set;

import net.sourceforge.jeval.EvaluationException;

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
		Prototype.addPrototype(n, new Prototype(g, c, n));
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
		Prototype.addPrototype(n, new Prototype(g, c, d, n));
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
	public void updateEntities() {
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
	public boolean spawnAgent(String prototypeName, int spawnX, int spawnY) {
		Agent toAdd = getPrototype(prototypeName).clonePrototype();
		return grid.spawnAgent(toAdd, spawnX, spawnY);
	}

	/**
	 * Adds the given Agent to a random (but free) position.
	 * 
	 * @param prototypeName
	 *            The name of the prototype to build the Agent from.
	 */
	public boolean spawnAgent(String prototypeName) {
		Agent toAdd = getPrototype(prototypeName).clonePrototype();
		return grid.spawnAgent(toAdd);
	}

	/**
	 * Returns the Agent in the slot at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public Agent getAgent(int x, int y) {
		return grid.getAgent(x, y);
	}

	/**
	 * Removes an Agent from the slot at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public void removeAgent(int x, int y) {
		grid.removeAgent(x, y);
	}

	/**
	 * Removes the given Agent from the grid.
	 * 
	 * @param a
	 *            The Agent to remove.
	 */
	public void removeEntity(Agent a) {
		grid.removeAgent(a);
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

	/**
	 * Returns a List of Triggers for a specific prototype
	 * 
	 * @return
	 */
	public List<Trigger> getPrototypeTriggers(Prototype p) {
		return p.getTriggers();
	}

	/**
	 * TODO Are we ensuring that each trigger's priority will be unique? Or
	 * should we use names instead to keep track of them in the hashmap? That
	 * might run into fewer issue while editing (possibility of changing
	 * priorities of the same trigger)
	 */
}
