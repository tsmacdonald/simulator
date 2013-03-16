/**
 * Behavior class
 * 
 * Behavior subclass. Update one of the Actor's fields;  
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.entity;

import edu.wheaton.simulator.datastructure.Field;

public class UpdateField extends Behavior {

	// TODO Unused field: UpdateField.field
	private Field field;

	// TODO Unused field: UpdateField.updateVal
	private String updateVal;

	public UpdateField(String name, String target, Field field,
			String updateVal) {
		super(name, target);
		this.field = field;
		this.updateVal = updateVal;
	}

	/**
	 * Update this Actor's Field to updateVal
	 * 
	 * TODO Method stub
	 */
	@Override
	public void execute(GridEntity xThis, GridEntity xOther,
			GridEntity xLocal, GridEntity xGlobal) {
		// Set target.field to updateVal
	}

}
