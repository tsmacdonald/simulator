/**
 * Layer.java
 *
 * A singleton class to give a colored representation of a specific
 * field.
 *
 * @author Chris Anderson and Elliot Penson
 */

package edu.wheaton.simulator.simulation;

import edu.wheaton.simulator.datastructures.Expression;
import edu.wheaton.simulator.datastructures.Field;
import edu.wheaton.simulator.datastructures.StringFormatMismatchException;
import edu.wheaton.simulator.datastructures.Value;

import java.awt.Color;

import net.sourceforge.jeval.EvaluationException;

public class Layer {

	/**
	 * The single instance of this Layer
	 */
	private static Layer layer = null;

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
	 * Get the instance of the layer
	 * @return
	 */
	public static Layer getInstance() {
		if(layer == null)
			layer = new Layer();	
		return layer;
	}

	/**
	 * Returns the name of the field being represented
	 */
	public String getFieldName() {
		return fieldName;
	}
	
	/**
	 * Sets the fieldName for the layer
	 * @param fieldName
	 */
	public void setFieldName(String fieldName){
		this.fieldName = fieldName;
	}

	/**
	 * Changes the value of max
	 */
	public void setMax(Field max) {
		this.max = max;
	}

	/**
	 * Changes the value of min
	 */
	public void setMin(Field min) {
		this.min = min;
	}
	
	/**
	 * Sets the max and min variables to null.
	 */
	public void resetMinMax() {
		max = null;
		min = null;
	}
	
	/**
	 * Set color for the layer
	 * @param c
	 */
	public void setColor(Color c){
		this.fieldColor = new HSBColor(c);
	}

	/**
	 * Translates the given generic value into a brightness degree from 0.0 to 1.0.
	 * @param Value of an agent's field
	 * @return A new Color with a different brightness value
	 * @throws EvaluationException 
	 */
	public Color newShade(Field f) throws EvaluationException {
		Value degree = new Value(0.0);
		degree = Expression.evaluate("("+f.value()+" - "+min.value()+") / ("+max.value()+" - "+min.value()+")");
		return fieldColor.newBrightness(degree.toDouble().floatValue());
	}

	/**
	 * When called for each agent in a string, this sets the min and max values for the field
	 * @param Value of an agent's field
	 * @throws EvaluationException 
	 * @throws StringFormatMismatchException
	 */
	public void setExtremes(Field f) throws EvaluationException{
		if(min == null && max == null) {
			min = f;
			max = f;
			return;
		}

		if(Expression.evaluate(f.value()+ "<" + this.min.value()).toBool())
			this.min = f;
		else if(Expression.evaluate(f.value()+">"+this.max.value()).toBool())
			this.max = f;
	}

}
