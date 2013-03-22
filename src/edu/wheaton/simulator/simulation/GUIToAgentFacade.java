/**
 * GUIToAgentFacade.java
 * 
 * Facade for the GUI team
 *
 * @author Agent Team
 */

package edu.wheaton.simulator.simulation;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.sourceforge.jeval.EvaluationException;

import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.entity.GridEntity;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Slot;

public class GUIToAgentFacade implements Iterable<String>{

	/**
	 * The Grid to hold all the Agents
	 */
	private Grid grid;

	/**
	 * HashMap of Prototypes with associated names
	 */
	private HashMap<String, Prototype> prototypes;

	/**
	 * Constructor.
	 * 
	 * @param gridX
	 * @param gridY
	 */
	public GUIToAgentFacade(int gridX, int gridY) {
		grid = new Grid(gridX, gridY);
		prototypes = new HashMap<String, Prototype>();
	}

	/**
	 * Adds a Prototype to the HashMap
	 * 
	 * @param n
	 * @param g
	 * @param c
	 */
	public void addPrototype(String n, Grid g, Color c) {
		prototypes.put(n, new Prototype(g, c));
	}

	/**
	 * Adds a Prototype (with a design) to the HashMap
	 * 
	 * @param n
	 * @param g
	 * @param c
	 * @param d
	 */
	public void addPrototype(String n, Grid g, Color c, byte[] d) {
		prototypes.put(n, new Prototype(g, c, d));
	}
	
	/**
	 * Returns the Prototype that corresponds to the given string.
	 * 
	 * @param n
	 * @return
	 */
	public Prototype getPrototype(String n) {
		return prototypes.get(n);
	}
	
	/**
	 * Gets a Set of the prototype names
	 * 
	 * @return
	 */
	public Set<String> prototypeNames() {
		return prototypes.keySet();
	}
	
	/**
	 * Causes all entities in the grid to act()
	 */
	public void updateEntities() {
		grid.updateEntities();
	}
	
	/**
	 * Adds the given entity at the closest free spot to the spawn position.
	 * The search for an open spot begins at the given x/y and then spirals
	 * outwards.
	 * 
	 * @param ge
	 *            The Entity to add.
	 * @param spawnX
	 *            Central x location for spawn
	 * @param spawnY
	 *            Central y location for spawn
	 * @return true if successful (entity added), false otherwise
	 */
	public boolean spawnEntity(String prototypeName, int spawnX, int spawnY) {
		Agent toAdd = getPrototype(prototypeName).clonePrototype();
		return grid.spawnEntity(toAdd, spawnX, spawnY);
	}
	
	/**
	 * Adds the given entity to a random (but free) position.
	 * 
	 * @param ge
	 *            The Entity to add.
	 */
	public boolean spawnEntity(String prototypeName) {
		Agent toAdd = getPrototype(prototypeName).clonePrototype();
		return grid.spawnEntity(toAdd);
	}
	
	/**
	 * Returns the Entity in the slot at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public GridEntity getEntity(int x, int y) {
		return grid.getEntity(x, y);
	}
	
	/**
	 * Removes a Entity from the slot at the given coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public void removeEntity(int x, int y) {
		grid.removeEntity(x, y);
	}
	
	/**
	 * Removes the given entity from the grid.
	 * 
	 * @param ge
	 *            The Entity to remove.
	 */
	public void removeEntity(GridEntity ge) {
		grid.removeEntity(ge);
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
	 * Loops through the grid and set's the Layer's min/max values. This must be done
	 * before a Layer is shown.
	 * PRECONDITION: The newLayer method has been called to setup a layer
	 * 
	 * @throws EvaluationException
	 */
	public void setLayerExtremes() throws EvaluationException {
		grid.setLayerExtremes();
	}
	
	/**
	 * TODO GUI Team: feel free to add method stubs and we will implement them.
	 */
}
