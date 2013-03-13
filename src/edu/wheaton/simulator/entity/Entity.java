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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.datastructure.StringFormatMismatchException;
import edu.wheaton.simulator.simulation.Grid;
import edu.wheaton.simulator.simulation.Layer;
import edu.wheaton.simulator.statistics.EntityID;

public abstract class Entity {

	/**
	 * A pointer to the environment so new Agents can be added or removed.
	 */
	protected Grid grid;

	/**
	 * The list of all fields (variables) associated with this agent.
	 */
	protected List<Field> fields;

	/**
	 * Bitmask for storing an entity's customized appearance, initially set
	 */
	protected byte[] design;

	/**
	 * Constructor
	 * 
	 * @param g
	 *            The grid object
	 * @param c
	 *            This entity's defaut color
	 */
	public Entity(Grid g, Color c) {
		grid = g;
		fields = new ArrayList<Field>();
		fields.add(new Field("colorRed", c.getRed()));
		fields.add(new Field("colorBlue", c.getBlue()));
		fields.add(new Field("colorGreen", c.getGreen()));
		fields.add(new Field("x", 0));
		fields.add(new Field("y", 0));

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
	public Entity(Grid g, Color c, byte[] d) {
		grid = g;
		fields = new ArrayList<Field>();
		fields.add(new Field("colorRed", c.getRed()));
		fields.add(new Field("colorBlue", c.getBlue()));
		fields.add(new Field("colorGreen", c.getGreen()));

		design = d;
	}

	public abstract void act();

	/**
	 * Parses the input string for a field, then adds that field. The input
	 * string should be in the format: "Name=...\nType=...\nValue=..." There
	 * should be no spaces present in the input string. Note that if a field
	 * already exists for this agent with the same name as the new candidate,
	 * it won't be added and will instead throw an exception.
	 * 
	 * @param s
	 *            The text representation of this field.
	 * @throws ElementAlreadyContainedException
	 * @throws StringFormatMismatchException
	 */
	public void addField(String name, String value)
			throws ElementAlreadyContainedException, Exception {

		// Check to see if it's contained.
		Field contained = null;
		for (Field f : fields) {
			if (f.name().equals(name)) {
				contained = f;
				break;
			}
		}
		if (contained != null)
			throw new ElementAlreadyContainedException();
		fields.add(new Field(name, value));
	}

	/**
	 * Removes a field from this Agent.
	 * 
	 * @param name
	 */
	public void removeField(String name) {
		for (Field f : fields) {
			if (f.name().equals(name)) {
				fields.remove(f);
				break;
			}
		}
	}

	/**
	 * Returns the object's default color
	 * 
	 */
	public Color getColor() {
		return new Color(getField("colorRed").value().toInt(), getField(
				"colorGreen").value().toInt(), getField("colorBlue").value()
				.toInt());
	}

	/**
	 * Gets a color for the entity based on the Field being examined by the
	 * Layer object. Returns null if the entity does not contain the Field.
	 * 
	 * @return The specific Color to represent the value of this entity's Field
	 * @throws EvaluationException
	 */
	public Color getLayerColor() throws EvaluationException {
		for (Field current : fields) {
			if (current.name().equals(Layer.getInstance().getFieldName()))
				return Layer.getInstance().newShade(current);
		}
		return null;
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

	public Field getField(String name) {
		for (Field f : fields) {
			if (f.name().equals(name)) {
				return f;
			}
		}
		throw new NoSuchElementException();
	}

	public List<Field> getFields() {
		return fields;
	}

	public String getName() {
		throw new UnsupportedOperationException();
	}

	public EntityID getID() {
		throw new UnsupportedOperationException();
	}
}
