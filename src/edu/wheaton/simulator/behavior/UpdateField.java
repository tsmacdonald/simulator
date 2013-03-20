package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.entity.Agent;

public class UpdateField implements Behavable {
	
	private String newString;
	
	private Agent target;
		
	private String fieldName;
	
	public UpdateField(Agent target, String fieldName, String newString) {
		this.target = target;
		this.fieldName = fieldName;
		this.newString = newString;
	}
	
	@Override
	public void act() {
		target.getField(fieldName).setValue(newString);

	}

}
