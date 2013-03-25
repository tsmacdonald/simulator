/**
 * Agent.java
 *
 * Agents model actors in the simulation's Grid.
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.entity;

import java.awt.Color;
import java.util.NoSuchElementException;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.simulation.Grid;
import edu.wheaton.simulator.simulation.Layer;

public abstract class GridEntity extends Entity {

	/**
	 * A pointer to the environment so new Agents can be added or removed.
	 */
	private Grid grid;

	/**
	 * Bitmask for storing an entity's customized appearance, initially set
	 */
	private byte[] design;

	/**
	 * Constructor
	 * 
	 * @param g
	 *            The grid object
	 * @param c
	 *            This entity's defaut color
	 * 
	 */
	public GridEntity(Grid g) {
		super();
		grid = g;
		Color c = Color.black;
		try {
			initFields(c);
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}

		design = makeDesign();
	}

	/**
	 * Constructor
	 * 
	 * @param g
	 *            The grid object
	 * @param c
	 *            This entity's defaut color
	 * 
	 */
	public GridEntity(Grid g, Color c) {
		super();
		grid = g;

		try {
			initFields(c);
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}

		design = makeDesign();
	}

	/**
	 * Constructor
	 * 
	 * @param g
	 *            The grid object
	 * @param c
	 *            This entity's defaut color
	 * @param d
	 *            The bitmask design chosen by the user
	 */
	public GridEntity(Grid g, Color c, byte[] d) {
		super();
		grid = g;

		try {
			initFields(c);
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}

		design = d;
	}
	
	private static byte[] makeDesign(){
		byte[] design = new byte[8];
		for (int i = 0; i < design.length; i++)
			design[i] = 127; // sets design to a solid image
	}
	
	private void initFields(Color c){
		initPosition();
		initColor(c);
	}
	
	private void initPosition(){
		//position fields initialized to invalid coordinates
		//to catch assumptions that this entity is already
		//added to the Grid
		addField("x", -1);
		addField("y", -1);
	}
	
	private void initColor(Color c) {
		addField("colorRed", new Integer(c.getRed()));
		addField("colorBlue", new Integer(c.getBlue()));
		addField("colorGreen", new Integer(c.getGreen()));
	}

	/**
	 * Set the default color of this entity to a new value
	 * 
	 * @param c
	 */
	public void setColor(Color c) {
		updateField("colorRed", new Integer(c.getRed()));
		updateField("colorBlue", new Integer(c.getBlue()));
		updateField("colorGreen", new Integer(c.getGreen()));
	}

	/**
	 * Returns the object's default color
	 * 
	 */
	public Color getColor() {
		return new Color(getField("colorRed").getIntValue(), getField(
				"colorGreen").getIntValue(), getField("colorBlue")
				.getIntValue());
	}

	/**
	 * Gets a color for the entity based on the Field being examined by the
	 * Layer object. Returns null if the entity does not contain the Field.
	 * 
	 * @return The specific Color to represent the value of this entity's Field
	 * @throws EvaluationException
	 */
	public Color getLayerColor() throws EvaluationException {

		Field field = getField(Layer.getInstance().getFieldName());
		if (field == null)
			throw new NoSuchElementException(
					"Entity.getLayerColor() could not find a valid field for return");
		return Layer.getInstance().newShade(field);
	}

	/**
	 * Sets the image bitmask array to a new design
	 */
	public void setDesign(byte[] design) {
		this.design = design;
	}

	/**
	 * Returns the image bitmask array
	 */
	public byte[] getDesign() {
		return design;
	}

	/**
	 * Provides the grid that this entity is in.
	 * 
	 * @return the Grid object
	 */
	public Grid getGrid() {
		return grid;
	}

}
