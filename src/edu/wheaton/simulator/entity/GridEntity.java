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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.simulation.Layer;

public abstract class GridEntity extends Entity {

	/**
	 * Bitmask for storing an entity's customized appearance, initially set
	 */
	private byte[] design;

	/**
	 * The list of all triggers/events associated with this agent.
	 */
	protected List<Trigger> triggers;

	/**
	 * Constructor. Gives the entity a default color of black and a solid
	 * design.
	 */
	public GridEntity() {
		super();
		init(Color.black, makeDesign());
	}

	/**
	 * Constructor
	 * 
	 * @param c
	 *            This entity's defaut color
	 * 
	 */
	public GridEntity(Color c) {
		super();
		init(c, makeDesign());
	}

	/**
	 * Constructor
	 * 
	 * @param c
	 *            This entity's defaut color
	 * @param d
	 *            The bitmask design chosen by the user
	 */
	public GridEntity(Color c, byte[] d) {
		super();
		init(c, d);
	}

	/**
	 * Helper method initializing a color and design
	 * 
	 * @param c
	 * @param d
	 */
	private void init(Color c, byte[] d) {
		setDesign(d);
		initFields(c);
	}

	/**
	 * Returns a solid bitmask design
	 * 
	 * @return
	 */
	private static byte[] makeDesign() {
		byte[] design = new byte[8];
		for (int i = 0; i < design.length; i++)
			design[i] = 127; // sets design to a solid image
		return design;
	}

	private void initFields(Color c) {
		initPosition();
		initColor(c);
	}

	private void initPosition() {
		// position fields initialized to invalid coordinates
		// to catch assumptions that this entity is already
		// added to the Grid

		try {
			addField("x", -1 + "");
			addField("y", -1 + "");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}

	}

	private void initColor(Color c) {

		try {
			addField("colorRed", getRed(c) + "");
			addField("colorBlue", getBlue(c) + "");
			addField("colorGreen", getGreen(c) + "");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the default color of this entity to a new value
	 * 
	 * @param c
	 */
	public void setColor(Color c) {
		updateField("colorRed", getRed(c) + "");
		updateField("colorBlue", getBlue(c) + "");
		updateField("colorGreen", getGreen(c) + "");
	}

	/**
	 * Returns the object's default color
	 * 
	 */
	public Color getColor() {
		return new Color(getFieldInt("colorRed"), getFieldInt("colorGreen"),
				getFieldInt("colorBlue"));
	}

	/**
	 * Gets a color for the entity based on the Field being examined by the
	 * Layer object. Returns null if the entity does not contain the Field.
	 * 
	 * @return The specific Color to represent the value of this entity's Field
	 * @throws EvaluationException
	 */
	public Color getLayerColor() throws EvaluationException {

		Field field = getField(getLayer().getFieldName());
		if (field == null)
			throw new NoSuchElementException(
					"Entity.getLayerColor() could not find a valid field for return");
		return getLayer().newShade(field);
	}

	/**
	 * Provides this GridEntity's custom field names and values. Removes the
	 * default fields.
	 * 
	 * @return String to String map
	 */
	public Map<String, String> getCustomFieldMap() {
		Map<String, String> toReturn = new HashMap<String, String>(fields);
		toReturn.remove("x");
		toReturn.remove("y");
		toReturn.remove("colorRed");
		toReturn.remove("colorBlue");
		toReturn.remove("colorGreen");
		return toReturn;
	}

	private Integer getFieldInt(String fieldName) {
		return getField(fieldName).getIntValue();
	}

	private static Layer getLayer() {
		return Layer.getInstance();
	}

	private static Integer getRed(Color c) {
		return new Integer(c.getRed());
	}

	private static Integer getGreen(Color c) {
		return new Integer(c.getGreen());
	}

	private static Integer getBlue(Color c) {
		return new Integer(c.getBlue());
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

}
