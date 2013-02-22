/**
 * Agent.java
 *
 * Agents model actors in the simulation's Grid.
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.universalsim.gridentities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wheaton.universalsim.Grid;

public abstract class GridEntity {

	/**
	 * A pointer to the environment so new Agents can be added or removed.
	 */
	protected Grid grid;

	/**
	 * The list of all fields (variables) associated with this agent.
	 */
	protected List<Field> fields;

	/**
	 * Represents the default color of the object
	 */
	protected Color color;

	/**
	 * Bitmask for storing an entity's customized appearance, initially set 
	 */
	protected byte[] design;

	/**
	 * Constructor
	 * @param g The grid object
	 * @param c This entity's defaut color
	 */
	public GridEntity(Grid g, Color c) {
		grid = g;
		color = c;

		design = new byte[8]; 
		for(int i = 0; i < 8; i++) 
			design[i] = 127; // sets design to a solid image
	}
	
	/**
	 * Constructor
	 * @param g The grid object
	 * @param c This entity's defaut color
	 * @param d The bitmask design chosen by the user
	 */
	public GridEntity(Grid g, Color c, byte[] d) {
		grid = g;
		color = c;
		design = d;
	} 
	
    public abstract void act();

	/**
	 * Parses the input string for a field, then adds that field.
	 * The input string should be in the format: "Name=...\nType=...\nValue=..." There should be no spaces present in the input string.
	 * Note that if a field already exists for this agent with the same name as the new candidate, it won't be added and will instead throw an exception.
	 * @param s The text representation of this field.
	 * @throws ElementAlreadyContainedException 
	 * @throws StringFormatMismatchException 
	 */
	public void addField(String s) throws ElementAlreadyContainedException, StringFormatMismatchException {
		String[] lines = s.split("\n");
		if(lines.length != 3 || !lines[0].substring(0, 5).equals("Name=") || !lines[1].substring(0, 5).equals("Type=") || !lines[2].substring(0, 6).equals("Value=")) {
			throw new StringFormatMismatchException();
		}
		String name = lines[0].substring(5, lines[0].length());
		String type = lines[1].substring(5, lines[1].length());
		String value = lines[2].substring(6, lines[2].length());

		//Check to see if it's contained.
		Field contained = null;
		for(Field f : fields) {
			if (f.getName().equals(name)) {
				contained = f;
				break;
			}
		}
		if(contained != null) throw new ElementAlreadyContainedException();
		fields.add(new Field(name, type, value));
	}

	/**
	 * Removes a field from this Agent.
	 * @param name 
	 */
	public void removeField(String name) {
		for(Field f : fields) {
			if (f.getName().equals(name)) {
				fields.remove(f);
				break;
			}
		}
	}

	/**
	 * Returns the object's default color
	 */
	public Color getColor() { 
		return color; 
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