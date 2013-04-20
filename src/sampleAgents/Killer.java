package sampleAgents;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

public class Killer extends SampleAgent{

	@Override
	public Prototype initSampleAgent(Prototype killer) {
		return initKiller(killer);
	}
	
	private static Prototype initKiller(Prototype killer){
		// Add fields
		try {
			killer.addField("xNextDirection", 0 + "");
			killer.addField("yNextDirection", 1 + "");
			killer.addField("temp", 0+"");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}
		
		// openSpot conditionals
		Expression freeSpot = new Expression("isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");
		Expression notFreeSpot = new Expression("!isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");

		// Move behavior
		Expression move = new Expression("move('this', this.x + this.xNextDirection, this.y + this.yNextDirection)");
		
		// kill the agent (change their direction)
		Expression kill1 = new Expression("kill(this.x + 1, this.y +1)");
		Expression kill2 = new Expression("kill(this.x + 1, this.y)");
		Expression kill3 = new Expression("kill(this.x + 1, this.y -1)");
		Expression kill4 = new Expression("kill(this.x, this.y +1)");
		Expression kill5 = new Expression("kill(this.x, this.y -1)");
		Expression kill6 = new Expression("kill(this.x - 1, this.y +1)");
		Expression kill7 = new Expression("kill(this.x - 1, this.y)");
		Expression kill8 = new Expression("kill(this.x - 1, this.y -1)");
		
		// turn clockwise
		Expression bounce = new Expression("setField('this', 'xNextDirection', -1 * this.xNextDirection) ||" +
				"setField('this', 'yNextDirection', -1 * this.yNextDirection)");
		
		killer.addTrigger(new Trigger("kill", 1, new Expression("TRUE"), kill1));
		killer.addTrigger(new Trigger("kill", 1, new Expression("TRUE"), kill2));
		killer.addTrigger(new Trigger("kill", 1, new Expression("TRUE"), kill3));
		killer.addTrigger(new Trigger("kill", 1, new Expression("TRUE"), kill4));
		killer.addTrigger(new Trigger("kill", 1, new Expression("TRUE"), kill5));
		killer.addTrigger(new Trigger("kill", 1, new Expression("TRUE"), kill6));
		killer.addTrigger(new Trigger("kill", 1, new Expression("TRUE"), kill7));
		killer.addTrigger(new Trigger("kill", 1, new Expression("TRUE"), kill8));

		killer.addTrigger(new Trigger("bounce", 1, notFreeSpot, bounce));
		killer.addTrigger(new Trigger("move", 1, freeSpot, move));
		
		Prototype.addPrototype(killer);

		return killer;
	}

}
