	/**
	 * Behavior class
	 * 
	 * Behavior subclass. Update one of the Actor's fields;  
	 * 
	 * @author Grant Hensel
	 * Wheaton College, CSCI 335, Spring 2013
	 */

package edu.wheaton.universalsim.gridentities;

public class UpdateField extends Behavior {

	private Field field; 
	private String updateVal;
	
	public UpdateField(Agent target, String name, Field field, String updateVal) {
		super(target, name);
		this.field = field;
		this.updateVal = updateVal; 
	}
	
	/**
	 * Update this Actor's Field to updateVal
	 */
	public void execute(){
		//Set target.field to updateVal
	}

}
