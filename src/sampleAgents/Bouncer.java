package sampleAgents;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

public class Bouncer extends SampleAgent{
	
	public Bouncer(){
		super.sampleAgents.add(this);
	}
	
	@Override
	public Prototype initSampleAgent(Prototype bouncer) {
		return initBouncer(bouncer);
	}
	
	private static Prototype initBouncer(Prototype bouncer){
		// Add fields
		try {
			bouncer.addField("xNextDirection", 0 + "");
			bouncer.addField("yNextDirection", 1 + "");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}
		
		// openSpot conditionals
		Expression freeSpot = new Expression("isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");
		Expression notFreeSpot = new Expression("!isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");

		// Move behavior
		Expression move = new Expression("move(this.x + this.xNextDirection, this.y + this.yNextDirection)");
		
		// turn clockwise
		Expression bounce = new Expression("setField('xNextDirection', -1 * this.xNextDirection) ||" +
				"setField('yNextDirection', -1 * this.yNextDirection)");
		
		bouncer.addTrigger(new Trigger("bounce", 1, notFreeSpot, bounce));
		bouncer.addTrigger(new Trigger("move", 1, freeSpot, move));
		
		Prototype.addPrototype(bouncer);

		return bouncer;
	}

}
