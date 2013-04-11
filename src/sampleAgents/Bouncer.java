package sampleAgents;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;
/**
 * Bouncer is a prototype for an agent that will begin in one direction and when it reaches
 * any kind of obstacle, it will reveres that direction.
 * 
 * @author David Emmanuel
 *
 */
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
		Expression move = new Expression("move('this', this.x + this.xNextDirection, this.y + this.yNextDirection)");
		
		// bounce
		Expression bounce = new Expression("setField('this', 'xNextDirection', -1 * this.xNextDirection) ||" +
				"setField('this', 'yNextDirection', -1 * this.yNextDirection)");
		
		bouncer.addTrigger(new Trigger("bounce", 1, notFreeSpot, bounce));
		bouncer.addTrigger(new Trigger("move", 1, freeSpot, move));
		
		Prototype.addPrototype(bouncer);

		return bouncer;
	}

}
