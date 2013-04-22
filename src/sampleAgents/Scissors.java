package sampleAgents;

import java.awt.Color;
import java.util.Random;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

/**
 * Fills in all the information for scissors in scissors Paper Scissors demo
 * 
 * Version 1 will resolve head to head conflicts
 * Version 2 will resolve conflicts where one is facing an enemy
 * Version 3 will resolve conflicts where there is an enemy in an adjacent space
 * 
 * @author David Emmanuel Pederson
 */
public class Scissors extends SampleAgent{
	
	static // set the version to either 1 or 2
	int version = 2;
	
	public void setVersion(int version){
		if(version > 0 && version <= 3)
		Scissors.version = version;
	}
	
	/**
	 * Makes scissors with the id field of 2 which is important for scissors-paper-scissors interaction
	 * @param scissors Empty prototype
	 * @return Scissors prototype
	 */
	@Override
	public Prototype initSampleAgent() {
		Prototype scissors = new Prototype("scissorsV" + version);
		return initScissors(scissors);
	}
	
	private static Prototype initScissors(Prototype scissors) {
		
		Color lightBlue = new Color(93, 198, 245);
		byte[] scissorsDesign = {113, 82, 116, 8, 116, 82, 113};
		scissors.setColor(lightBlue);
		scissors.setDesign(scissorsDesign);

		try {
			scissors.addField("typeID", 2 + "");
			scissors.addField("xNextDirection", 0 + "");
			scissors.addField("yNextDirection", -1 + "");
			scissors.addField("temp", 0 + "");
			scissors.addField("agentAhead", "" + 0);
			scissors.addField("conflictAhead", "" + 0);
			scissors.addField("endTurn", "" + 0);
			scissors.addField("age", 0+"");
		} catch (ElementAlreadyContainedException e) {
			e.printStackTrace();
		}

		// openSpot conditionals
		Expression freeSpot = new Expression(
				"this.endTurn != 1"
						+ "&&isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");
		Expression notFreeSpot = new Expression(
				"this.endTurn != 1"
						+ "&&!isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");

		// Move behavior
		Expression move = new Expression(
				"move(this.x + this.xNextDirection, this.y + this.yNextDirection)"
						+ "&& setField('endTurn', 1)");
		
		/*
		 * Turn clockwise (in 8 directions) Uses 'temp' because as JEval
		 * evaluates each function in order and xNextDirection would become
		 * corrupted in the second use
		 */
		Expression rotateClockwise = new Expression(
				" setField('temp', this.xNextDirection) || setField('xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
						+ " || setField('yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");

		// turn counter clockwise
		Expression rotateCounterClockwise = new Expression(
				" setField('temp', this.xNextDirection) || setField('xNextDirection', round(this.xNextDirection * cos(-PI/4) - this.yNextDirection * sin(-PI/4)))"
						+ " || setField('yNextDirection', round(this.temp * sin(-PI/4) + this.yNextDirection * cos(-PI/4)))");

		// Check for agent ahead
		Expression isAgentAhead = new Expression(
				"this.endTurn != 1"
						+ "&& isValidCoord(this.x + this.xNextDirection, this.y + this.yNextDirection) && !isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");

		// setAgentAhead field to true
		Expression setAgentAhead = new Expression(
				"setField('agentAhead', 1)");

		// reads flag that is set when there is an agent ahead
		Expression checkAgentAheadFlag = new Expression("this.endTurn != 1" +
				"&& this.agentAhead == 1.0");

		// collect information about conflict
		Expression setHeadToHeadConflictFlag = new Expression(
				"setField('conflictAhead',"
						+ " getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'typeID') == (this.typeID + 2)%3"
						+ " && getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'xNextDirection') == - this.xNextDirection"
						+ " && getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'yNextDirection') == - this.yNextDirection)");
		
		// check if there is an enemy agent at each position around the agent
		Expression setKill1 = new Expression(
				"getFieldOfAgentAt(this.x + 1, this.y +1, 'typeID') == (this.typeID + 2)%3");
		Expression setKill2 = new Expression(
				"getFieldOfAgentAt(this.x + 1, this.y, 'typeID') == (this.typeID + 2)%3");
		Expression setKill3 = new Expression(
				"getFieldOfAgentAt(this.x + 1, this.y -1, 'typeID') == (this.typeID + 2)%3");
		Expression setKill4 = new Expression(
				"getFieldOfAgentAt(this.x, this.y +1, 'typeID') == (this.typeID + 2)%3");
		Expression setKill5 = new Expression(
				"getFieldOfAgentAt(this.x, this.y -1, 'typeID') == (this.typeID + 2)%3");
		Expression setKill6 = new Expression(
				"getFieldOfAgentAt(this.x - 1, this.y +1, 'typeID') == (this.typeID + 2)%3");
		Expression setKill7 = new Expression(
				"getFieldOfAgentAt(this.x - 1, this.y, 'typeID') == (this.typeID + 2)%3");
		Expression setKill8 = new Expression(
				"getFieldOfAgentAt(this.x - 1, this.y -1, 'typeID') == (this.typeID + 2)%3");
		
		// Kill all of the agents adjacent		
		Expression kill1 = new Expression(
				"kill(this.x + 1, this.y +1)"
						+ "&& clone(this.x + 1, this.y +1)"
						+ "&& setFieldOfAgent(this.x + 1, this.y +1, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent(this.x + 1, this.y +1, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('endTurn', 1)");
		Expression kill2 = new Expression(
				"kill(this.x + 1, this.y)"
						+ "&& clone(this.x + 1, this.y)"
						+ "&& setFieldOfAgent(this.x + 1, this.y, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent(this.x + 1, this.y, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('endTurn', 1)");
		Expression kill3 = new Expression(
				"kill(this.x + 1, this.y -1)"
						+ "&& clone(this.x + 1, this.y -1)"
						+ "&& setFieldOfAgent(this.x + 1, this.y -1, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent(this.x + 1, this.y -1, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('endTurn', 1)");
		Expression kill4 = new Expression(
				"kill(this.x, this.y +1)"
						+ "&& clone(this.x, this.y +1)"
						+ "&& setFieldOfAgent(this.x, this.y +1, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent(this.x, this.y +1, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('endTurn', 1)");
		Expression kill5 = new Expression(
				"kill(this.x, this.y -1)"
						+ "&& clone(this.x, this.y -1)"
						+ "&& setFieldOfAgent(this.x, this.y -1, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent(this.x, this.y -1, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('endTurn', 1)");
		Expression kill6 = new Expression(
				"kill(this.x - 1, this.y +1)"
						+ "&& clone(this.x - 1, this.y +1)"
						+ "&& setFieldOfAgent(this.x - 1, this.y +1, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent(this.x - 1, this.y +1, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('endTurn', 1)");
		Expression kill7 = new Expression(
				"kill(this.x - 1, this.y)"
						+ "&& clone(this.x - 1, this.y)"
						+ "&& setFieldOfAgent(this.x - 1, this.y, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent(this.x - 1, this.y, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('endTurn', 1)");
		Expression kill8 = new Expression(
				"kill(this.x - 1, this.y -1)"
						+ "&& clone(this.x - 1, this.y -1)"
						+ "&& setFieldOfAgent(this.x - 1, this.y -1, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent(this.x - 1, this.y -1, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('endTurn', 1)");
		
		// collect information about one facing another conflict
		Expression setEnemyAheadConflictFlag = new Expression(
				"setField('conflictAhead',"
						+ " getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'typeID') == (this.typeID + 2)%3)");
		
		// check the flag that is set when a conflict is ahead
		Expression checkConflictAheadFlag = new Expression("this.endTurn != 1" +
				" && this.conflictAhead");

		// conflict behavior
		Expression engageInConflict = new Expression(
				"kill(this.x  + this.xNextDirection, this.y + this.yNextDirection)"
						+ "&& clone(this.x  + this.xNextDirection, this.y + this.yNextDirection)"
						+ "&& setFieldOfAgent(this.x + this.xNextDirection, this.y + this.yNextDirection, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent(this.x + this.xNextDirection, this.y + this.yNextDirection, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('endTurn', 1)");

		// increment the age of the unit
		Expression incrAge = new Expression("setField('age', this.age +1)");
		
		// reset all the flags that are used to determine behavior
		Expression resetConflictFlags = new Expression(
				"setField('agentAhead', 0)|| setField('conflictAhead', 0)");
		Expression resetEndTurnFlag = new Expression(
				"setField('endTurn', 0)");

			/*
			 * Unfortunately, our implementation of triggers makes this the
			 * best way to get agents to check all eight directions before
			 * ending their turn. (55 separate triggers are made)
			 */
		int rotateDirection = new Random().nextInt(2);
			for (int i = 0; i < 8; i++) {
				scissors.addTrigger(new Trigger("incrementAge", 1, new Expression("TRUE"), incrAge));
				scissors.addTrigger(new Trigger("agentAhead", 1, isAgentAhead,
						setAgentAhead));
				
				if(version == 1){
					scissors.addTrigger(new Trigger("conflictAhead", 1,
							setHeadToHeadConflictFlag, setEnemyAheadConflictFlag));
					scissors.addTrigger(new Trigger("engageConflict", 1,
							checkConflictAheadFlag, engageInConflict));
				}
				
				if(version == 2){
					scissors.addTrigger(new Trigger("conflictAhead", 1,
						checkAgentAheadFlag, setEnemyAheadConflictFlag));
					scissors.addTrigger(new Trigger("engageConflict", 1,
							checkConflictAheadFlag, engageInConflict));
				}
				if(version == 3){
					scissors.addTrigger(new Trigger("kill", 1, setKill1, kill1));
					scissors.addTrigger(new Trigger("kill", 1, setKill2, kill2));
					scissors.addTrigger(new Trigger("kill", 1, setKill3, kill3));
					scissors.addTrigger(new Trigger("kill", 1, setKill4, kill4));
					scissors.addTrigger(new Trigger("kill", 1, setKill5, kill5));
					scissors.addTrigger(new Trigger("kill", 1, setKill6, kill6));
					scissors.addTrigger(new Trigger("kill", 1, setKill7, kill7));
					scissors.addTrigger(new Trigger("kill", 1, setKill8, kill8));
				}
				if(rotateDirection == 1)
					scissors.addTrigger(new Trigger("rotateCounterClockwise", 1,
							notFreeSpot, rotateCounterClockwise));
				else
					scissors.addTrigger(new Trigger("rotateClockwise", 1,
							notFreeSpot, rotateClockwise));
				scissors.addTrigger(new Trigger("move", 1, freeSpot, move));
				scissors.addTrigger(new Trigger("resetConflictFlags", 1,
						new Expression("true"), resetConflictFlags));
			}
			scissors.addTrigger(new Trigger("resetConflictFlags", 1,
					new Expression("true"), resetEndTurnFlag));

			return scissors;
	}
}
