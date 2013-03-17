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

import net.sourceforge.jeval.EvaluationException;
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
	 */
	public GridEntity(Grid g, Color c) {
		super();
		grid = g;
		getFields().add(new Field("colorRed", c.getRed()));
		getFields().add(new Field("colorBlue", c.getBlue()));
		getFields().add(new Field("colorGreen", c.getGreen()));
		getFields().add(new Field("x", 0));
		getFields().add(new Field("y", 0));

		design = new byte[8];
		for (int i = 0; i < 8; i++)
			design[i] = 127; // sets design to a solid image
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
		getFields().add(new Field("colorRed", c.getRed()));
		getFields().add(new Field("colorBlue", c.getBlue()));
		getFields().add(new Field("colorGreen", c.getGreen()));

		design = d;
	}

	public abstract void act(GridEntity local, GridEntity global);

	/**
	 * Returns the object's default color
	 * 
	 */
	public Color getColor() {
		return new Color(getField("colorRed").getIntValue(), getField(
				"colorGreen").getIntValue(), getField("colorBlue").getIntValue());
	}

	/**
	 * Gets a color for the entity based on the Field being examined by the
	 * Layer object. Returns null if the entity does not contain the Field.
	 * 
	 * @return The specific Color to represent the value of this entity's Field
	 * @throws Exception
	 * @throws EvaluationException
	 */
	public Color getLayerColor() throws EvaluationException, Exception {
		for (Field current : getFields()) {
			if (current.getName().equals(Layer.getInstance().getFieldName()))
				return Layer.getInstance().newShade(current);
		}
		throw new Exception(
				"Entity.getLayerColor() could not find a valid field for return");
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

	public String getName() {
		throw new UnsupportedOperationException();
	}

	protected Grid getGrid() {
		return grid;
	}
}
