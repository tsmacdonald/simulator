package sampleAgents;

import java.awt.Color;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

/**
 * RightTurner is a prototype for an agent that will turn clockwise when it reaches
 * any kind of obstruction. 
 * 
 * @author David Emmanuel
 *
 */
public class RightTurner extends SampleAgent{

	public RightTurner() {
		super.sampleAgents.add(this);
	}

	@Override
	public Prototype initSampleAgent() {
		Prototype rightTurner = new Prototype(Color.BLACK, "rightTurner");
		return initRightTurner(rightTurner);
	}

	private static Prototype initRightTurner(Prototype rightTurner){
		
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
				"move(this.x + this.xNextDirection, this.y + this.yNextDirection)");
		
		// turn clockwise
		Expression rotateClockwise = new Expression(
				" setField('temp', this.xNextDirection) || setField('xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
						+ " || setField('yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");
		
		rightTurner.addTrigger(new Trigger("turn", 1, notFreeSpot, rotateClockwise));
		rightTurner.addTrigger(new Trigger("move", 1, freeSpot, move));
		
		Prototype.addPrototype(rightTurner);

		return rightTurner;
	}
}
