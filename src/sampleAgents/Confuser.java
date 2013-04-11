package sampleAgents;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

/**
 * A Confuser will change the direction of any agent with the fields xNextDirection and yNextDirection
 * by rotating that agent in a clockwise direction (8 directions)
 * 
 * @author David Emmanuel
 *
 */
public class Confuser extends SampleAgent {
	public Confuser(){
		super.sampleAgents.add(this);
	}
	
	@Override
	public Prototype initSampleAgent(Prototype confuser) {
		return initConfuser(confuser);
	}
	
	private static Prototype initConfuser(Prototype confuser){
		// Add fields
		try {
			confuser.addField("xNextDirection", 0 + "");
			confuser.addField("yNextDirection", 1 + "");
			confuser.addField("temp", 0+"");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}
		
		// openSpot conditionals
		Expression freeSpot = new Expression("isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");
		Expression notFreeSpot = new Expression("!isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");

		// Move behavior
		Expression move = new Expression("move('this', this.x + this.xNextDirection, this.y + this.yNextDirection)");
		
		// Confuse the agent (change their direction)
		Expression confuse1 = new Expression("setFieldOfAgent('this', this.x + 1, this.y +1, 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
				+ " || setFieldOfAgent('this', this.x + 1, this.y +1, 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");
		Expression confuse2 = new Expression("setFieldOfAgent('this', this.x + 1, this.y, 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
				+ " || setFieldOfAgent('this', this.x + 1, this.y, 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");
		Expression confuse3 = new Expression("setFieldOfAgent('this', this.x + 1, this.y -1, 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
				+ " || setFieldOfAgent('this', this.x + 1, this.y -1, 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");
		Expression confuse4 = new Expression("setFieldOfAgent('this', this.x, this.y +1, 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
				+ " || setFieldOfAgent('this', this.x, this.y +1, 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");
		Expression confuse5 = new Expression("setFieldOfAgent('this', this.x, this.y -1, 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
				+ " || setFieldOfAgent('this', this.x, this.y -1, 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");
		Expression confuse6 = new Expression("setFieldOfAgent('this', this.x - 1, this.y +1, 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
				+ " || setFieldOfAgent('this', this.x - 1, this.y +1, 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");
		Expression confuse7 = new Expression("setFieldOfAgent('this', this.x - 1, this.y, 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
				+ " || setFieldOfAgent('this', this.x -1, this.y, 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");
		Expression confuse8 = new Expression("setFieldOfAgent('this', this.x - 1, this.y -1, 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
				+ " || setFieldOfAgent('this', this.x - 1, this.y -1, 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");
		
		// turn clockwise
		Expression bounce = new Expression("setField('this', 'xNextDirection', -1 * this.xNextDirection) ||" +
				"setField('this', 'yNextDirection', -1 * this.yNextDirection)");
		
		confuser.addTrigger(new Trigger("confuse", 1, new Expression("TRUE"), confuse1));
		confuser.addTrigger(new Trigger("confuse", 1, new Expression("TRUE"), confuse2));
		confuser.addTrigger(new Trigger("confuse", 1, new Expression("TRUE"), confuse3));
		confuser.addTrigger(new Trigger("confuse", 1, new Expression("TRUE"), confuse4));
		confuser.addTrigger(new Trigger("confuse", 1, new Expression("TRUE"), confuse5));
		confuser.addTrigger(new Trigger("confuse", 1, new Expression("TRUE"), confuse6));
		confuser.addTrigger(new Trigger("confuse", 1, new Expression("TRUE"), confuse7));
		confuser.addTrigger(new Trigger("confuse", 1, new Expression("TRUE"), confuse8));

		confuser.addTrigger(new Trigger("bounce", 1, notFreeSpot, bounce));
		confuser.addTrigger(new Trigger("move", 1, freeSpot, move));
		
		Prototype.addPrototype(confuser);

		return confuser;
	}
}
