package sampleAgents;

import java.awt.Color;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

public class RightTurner extends SampleAgent{

	public RightTurner() {
		super.sampleAgents.add(this);
	}

	@Override
	public Prototype initSampleAgent(Prototype rightTurner) {
		return initRightTurner(rightTurner);
	}

	private Prototype initRightTurner(Prototype rightTurner){
		
		// Add fields
		try {
			rightTurner.addField("xNextDirection", 0 + "");
			rightTurner.addField("yNextDirection", 1 + "");
			rightTurner.addField("temp", 0 + "");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}
		
		// openSpot conditionals
		Expression freeSpot = new Expression("isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");
		Expression notFreeSpot = new Expression("!isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");

		// Move behavior
		Expression move = new Expression(
				"move('this', this.x + this.xNextDirection, this.y + this.yNextDirection)");
		
		// turn clockwise
		Expression rotateClockwise = new Expression(
				" setField('this', 'temp', this.xNextDirection) || setField('this', 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
						+ " || setField('this', 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");
		
		rightTurner.addTrigger(new Trigger("turn", 1, notFreeSpot, rotateClockwise));
		rightTurner.addTrigger(new Trigger("move", 1, freeSpot, move));
		
		Prototype.addPrototype(rightTurner);

		return rightTurner;
	}
}