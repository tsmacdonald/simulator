package sampleAgents;

import java.awt.Color;
import java.util.Random;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

/**
 * Fills in all the information for scissors in Rock Paper Scissors demo
 * @author David Emmanuel Pederson
 *
 */
public class Scissors extends SampleAgent{
	
	/**
	 * Makes scissors with the id field of 2 which is important for rock-paper-scissors interaction
	 * @param rock Empty prototype
	 * @return Scissors prototype
	 */
	@Override
	public Prototype initSampleAgent() {
		Prototype scissors = new Prototype("scissors");
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
		//TODO fix this warning
		Expression setHeadToHeadConflictFlag = new Expression(
				"setField('conflictAhead',"
						+ " getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'typeID') == (this.typeID + 2)%3"
						+ " && getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'xNextDirection') == - this.xNextDirection"
						+ " && getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'yNextDirection') == - this.yNextDirection)");
		
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
				scissors.addTrigger(new Trigger("conflictAhead", 1,
						checkAgentAheadFlag, setEnemyAheadConflictFlag));
				scissors.addTrigger(new Trigger("engageConflict", 1,
						checkConflictAheadFlag, engageInConflict));
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

			Prototype.addPrototype(scissors);
			return scissors;
	}


}
