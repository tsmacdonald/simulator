package sampleAgents;

import java.awt.Color;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

/**
 * Fills in all the information for a rock in Rock Paper Scissors demo
 * @author David Emmanuel Pederson
 *
 */

public class Rock extends SampleAgent{

	@Override
	public Prototype initSampleAgent(Prototype rock) {
		return initRock(rock);
	}

	/**
	 * Makes a rock with the id field of 0 which is important for rock-paper-scissors interaction
	 * @param rock Empty prototype
	 * @return Rock prototype
	 */
	private Prototype initRock(Prototype rock) {
		// names of the agents
		String[] agentType = { "rock", "paper", "scissors" };
		
		Color brown = new Color(205, 133, 63);
		byte[] rockDesign = {24, 62, 127, 127, 127, 62, 12};
		rock.setColor(brown);
		rock.setDesign(rockDesign);

		try {
			rock.addField("typeID", 0 + "");
			rock.addField("xNextDirection", 0 + "");
			rock.addField("yNextDirection", -1 + "");
			rock.addField("temp", 0 + "");
			rock.addField("agentAhead", "" + 0);
			rock.addField("conflictAhead", "" + 0);
			rock.addField("endTurn", "" + 0);
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
				"move('this', this.x + this.xNextDirection, this.y + this.yNextDirection)"
						+ "&& setField('this', 'endTurn', 1)");
		
		/*
		 * Turn clockwise (in 8 directions) Uses 'temp' because as JEval
		 * evaluates each function in order and xNextDirection would become
		 * corrupted in the second use
		 */
		Expression rotateClockwise = new Expression(
				" setField('this', 'temp', this.xNextDirection) || setField('this', 'xNextDirection', round(this.xNextDirection * cos(PI/4) - this.yNextDirection * sin(PI/4)))"
						+ " || setField('this', 'yNextDirection', round(this.temp * sin(PI/4) + this.yNextDirection * cos(PI/4)))");

		// turn counter clockwise
		Expression rotateCounterClockwise = new Expression(
				" setField('this', 'temp', this.xNextDirection) || setField('this', 'xNextDirection', round(this.xNextDirection * cos(-PI/4) - this.yNextDirection * sin(-PI/4)))"
						+ " || setField('this', 'yNextDirection', round(this.temp * sin(-PI/4) + this.yNextDirection * cos(-PI/4)))");

		// Check for agent ahead
		Expression isAgentAhead = new Expression(
				"this.endTurn != 1"
						+ "&& isValidCoord(this.x + this.xNextDirection, this.y + this.yNextDirection) && !isSlotOpen(this.x + this.xNextDirection, this.y + this.yNextDirection)");

		// setAgentAhead field to true
		Expression setAgentAhead = new Expression(
				"setField('this', 'agentAhead', 1)");

		// reads flag that is set when there is an agent ahead
		Expression checkAgentAheadFlag = new Expression("this.endTurn != 1" +
				"&& this.agentAhead == 1.0");

		// collect information about conflict
		Expression setConflictAheadFlag = new Expression(
				"setField('this', 'conflictAhead',"
						+ " getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'typeID') == (this.typeID + 2)%3"
						+ " && getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'xNextDirection') == - this.xNextDirection"
						+ " && getFieldOfAgentAt(this.x + this.xNextDirection, this.y + this.yNextDirection, 'yNextDirection') == - this.yNextDirection)");

		// check the flag that is set when a conflict is ahead
		Expression checkConflictAheadFlag = new Expression("this.endTurn != 1" +
				" && this.conflictAhead");

		// conflict behavior
		Expression engageInConflict = new Expression(
				"kill(this.x  + this.xNextDirection, this.y + this.yNextDirection)"
						+ "&& clone('this',this.x  + this.xNextDirection, this.y + this.yNextDirection)"
						+ "&& setFieldOfAgent('this', this.x + this.xNextDirection, this.y + this.yNextDirection, 'xNextDirection', this.xNextDirection)"
						+ "&& setFieldOfAgent('this', this.x + this.xNextDirection, this.y + this.yNextDirection, 'xNextDirection', this.xNextDirection)"
						+ "&& setField('this', 'endTurn', 1)");

		// reset all the flags that are used to determine behavior
		Expression resetConflictFlags = new Expression(
				"setField('this', 'agentAhead', 0)|| setField('this', 'conflictAhead', 0)");
		Expression resetEndTurnFlag = new Expression(
				"setField('this', 'endTurn', 0)");

			/*
			 * Unfortunately, our implementation of triggers makes this the
			 * best way to get agents to check all eight directions before
			 * ending their turn. (55 separate triggers are made)
			 */
			for (int i = 0; i < 8; i++) {
				rock.addTrigger(new Trigger("agentAhead", 1, isAgentAhead,
						setAgentAhead));
				rock.addTrigger(new Trigger("conflictAhead", 1,
						checkAgentAheadFlag, setConflictAheadFlag));
				rock.addTrigger(new Trigger("engageConflict", 1,
						checkConflictAheadFlag, engageInConflict));
				rock.addTrigger(new Trigger("rotateClockwise", 1,
						notFreeSpot, rotateClockwise));
				rock.addTrigger(new Trigger("move", 1, freeSpot, move));
				rock.addTrigger(new Trigger("resetConflictFlags", 1,
						new Expression("true"), resetConflictFlags));
			}
			rock.addTrigger(new Trigger("resetConflictFlags", 1,
					new Expression("true"), resetEndTurnFlag));

			Prototype.addPrototype(rock);
			return rock;
	}

}
