/**
 * AgentAppearance.java
 * 
 * Models the display of an Agent
 */

package edu.wheaton.simulator.datastructure;

import java.awt.Color;

public class AgentAppearance {

	/**
	 * The color of the Agent
	 */
	private final Color color;

	/**
	 * The Agent's bitmask
	 */
	private final byte[] design;

	/**
	 * X coordinate
	 */
	private int x;

	/**
	 * Y coordinate
	 */
	private int y;

	/**
	 * Constructor
	 * 
	 * @param color
	 * @param design
	 */
	public AgentAppearance(Color color, byte[] design, int x, int y) {
		this.color = color;
		this.design = design;
		this.x = x;
		this.y = y;
	}

	/**
	 * Provides the color
	 * 
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Gives the design
	 * 
	 * @return
	 */
	public byte[] getDesign() {
		return design;
	}

	/**
	 * Provides the Agent's x pos
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Provides the Agent's y pos
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}
}
