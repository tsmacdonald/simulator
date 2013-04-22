/**
 * HSBColor.java
 *
 * This class represents colors in the HSB (hue, saturation,
 * brightness) model, with the utility of easily manipulating a color.
 *
 * Note:
 * HUE is a value from 0-360 representing the color.
 * SATURATION is a percentage (from 0.0-1.0) of color purity.
 * BRIGHTNESS is a percentage (from 0.0-1.0) of color luminance.
 *    - a lower percentage is darker
 * 
 * @author Agent Team
 */

package edu.wheaton.simulator.simulation;

import java.awt.Color;

public class HSBColor {

	/**
	 * Holds the HSB values of this color
	 */
	private float[] hsb;

	/**
	 * Constructor
	 * 
	 * @param rgb
	 *            A Color object to be converted to HSB
	 */
	public HSBColor(Color rgb) {
		addColor(rgb);
	}

	/**
	 * Constructor
	 * 
	 * @param hue
	 *            The initial hue
	 * @param sat
	 *            The starting saturation
	 * @param bri
	 *            The beginning brightness
	 */
	public HSBColor(float hue, float sat, float bri) {
		hsb = new float[] { hue, sat, bri };
	}

	/**
	 * Gives this instance a new Color object to be changed into HSB
	 * 
	 * @param rgb
	 *            the Color object
	 */
	public void addColor(Color rgb) {
		hsb = Color
				.RGBtoHSB(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), null);
	}

	/**
	 * Gets a color object to represent the HSB values
	 * 
	 * @return The Color object
	 */
	public Color getColor() {
		return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
	}

	/**
	 * Gives a new hue color to the instance
	 * 
	 * @param hue
	 *            value from 0-360
	 */
	public void setHue(float hue) {
		hsb[0] = hue;
	}

	/**
	 * Changes the satuation
	 * 
	 * @param sat
	 *            percentage from 0.0 to 1.0
	 */
	public void setSaturation(float sat) {
		hsb[1] = sat;
	}

	/**
	 * Adjusts the brightness (or luminance)
	 * 
	 * @param bri
	 *            percentage from 0.0 to 1.0
	 */
	public void setBrightness(float bri) {
		hsb[2] = bri;
	}

	/**
	 * Gives the brightness (or luminance)
	 * 
	 * @return a brightness value from 0.0 to 1.0
	 */
	public float getBrightness() {
		return hsb[2];
	}

	/**
	 * Gets a new Color with a different brightness from this.
	 * 
	 * @param degree
	 *            The new brightness level.
	 * @return a Color with an adjusted degree of brightness.
	 */
	public Color newBrightness(float degree) {
		return new Color(Color.HSBtoRGB(hsb[0], hsb[1], degree));
	}

	@Override
	public String toString() {
		return "HSLColor\n" + "Hue: " + hsb[0] + " Saturation:" + hsb[1]
				+ " Brightness:" + hsb[2];
	}

}
