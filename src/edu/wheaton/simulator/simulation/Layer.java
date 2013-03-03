/**
 * Layer.java
 *
 * A singleton class to give a colored representation of a specific
 * field.
 *
 * @author Elliot Penson
 */

package edu.wheaton.simulator.simulation;

import edu.wheaton.simulator.gridentities.Field;
import edu.wheaton.simulator.gridentities.StringFormatMismatchException;
import java.awt.Color;

public class Layer {

	/**
	 * The single instance of this Layer
	 */
	private static Layer layer;

	/**
	 * The name of the field being represented by this layer
	 */
	private String fieldName;

	/**
	 * The user defined color to represent the field
	 */
	private HSBColor fieldColor;

	/**
	 * Maximum value the represented field can have
	 */
	private Field max; 

	/**
	 * Minimum value the represented field can have
	 */
	private Field min;

	/**
	 * Constructor
	 * @param fieldName The name of this layer's field
	 * @param c
	 */
	private Layer(String fieldName, Color c) {
		this.fieldName = fieldName;
		fieldColor = new HSBColor(c);
	}

	private static Layer getInstance() {
		return layer;
	}

	/**
	 * Returns the name of the field being represented
	 */
	private String getFieldName() {
		return fieldName;
	}

	/**
	 * Changes the value of max
	 */
	private void setMax(Field max) {
		this.max = max;
	}

	/**
	 * Changes the value of min
	 */
	private void setMin(Field min) {
		this.min = min;
	}

	/**
	 * Translates the given generic value into a brightness degree from 0.0 to 1.0.
	 * @param Value of an agent's field
	 * @return A new Color with a different brightness value
	 * @throws StringFormatMismatchException
	 */
	private Color newShade(Field f) throws StringFormatMismatchException {

		float degree = 0.0f;
		if(f.isInt()) 
			degree = (f.intValue() - min.intValue()) / (max.intValue() - min.intValue());
		else if(f.isDouble())
			degree = (float)((f.doubleValue() - min.doubleValue()) / (max.doubleValue() - min.doubleValue()));
		else if(f.isChar())
			degree = (f.charValue() - min.charValue()) / (max.charValue() - min.charValue());

		return fieldColor.newBrightness(degree);
	}

	/**
	 * When called for each agent in a string, this sets the min and max values for the field
	 * @param Value of an agent's field
	 * @throws StringFormatMismatchException
	 */
	private void setExtremes(Field f) throws StringFormatMismatchException {
		if(f.isInt()){
			if(f.intValue() < this.min.intValue())
				this.min = f;
			else if(f.intValue() > this.max.intValue())
				this.max = f;
		}else if(f.isDouble()){
			if(f.doubleValue() < this.min.doubleValue())
				this.min = f;
			else if(f.doubleValue() > this.max.doubleValue())
				this.max = f;
		}else if(f.isChar()){
			if(f.charValue() < this.min.charValue())
				this.min = f;
			else if(f.charValue() > this.max.charValue())
				this.max = f;
		}
	}

}
