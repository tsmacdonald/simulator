/**
 * Layer.java
 *
 * A singleton class to give a colored representation of a specific
 * field.
 *
 * @author Elliot Penson
 */

package edu.wheaton.simulator.simulation;

import java.awt.Color;

public class Layer <E> {

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
    private E max; 

    /**
     * Minimum value the represented field can have
     */
    private E min;

    /**
     * Constructor
     * @param fieldName The name of this layer's field
     * @param c
     */
    public Layer(String fieldName, Color c) {

        this.fieldName = fieldName;
        fieldColor = new HSBColor(c);
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
    private void setMax(E max) {
        this.max = max;
    }

    /**
     * Changes the value of min
     */
    private void setMin(E min) {
        this.min = min;
    }

    /**
     * Translates the given generic value into a brightness degree from 0.0 to 1.0.
     * @param Value of an agent's field
     * @return A new Color with a different brightness value
     */
    private Color newShade(E fieldValue) {
    	
    	// TODO cannot subtract generic values - need subtract class.
        float degree = (fieldValue - min) / (max - min);

        return fieldColor.newBrightness(degree);
    }

}
