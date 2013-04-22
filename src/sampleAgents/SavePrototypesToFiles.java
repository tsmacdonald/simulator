/**
 * Script to serialize sample prototypes
 */
package sampleAgents;

import edu.wheaton.simulator.simulation.Simulator;

public class SavePrototypesToFiles {	
	public static void main(String[] args){
		// Various Sample Agents
		Simulator.getInstance().savePrototypeToFile(new Bouncer().initSampleAgent());
		Simulator.getInstance().savePrototypeToFile(new Confuser().initSampleAgent());
		Simulator.getInstance().savePrototypeToFile(new Killer().initSampleAgent());
		Simulator.getInstance().savePrototypeToFile(new Multiplier().initSampleAgent());
		Simulator.getInstance().savePrototypeToFile(new RightTurner().initSampleAgent());
		
		// Rock Paper Scissors agents, all three versions for conflict conditions
		Rock rock = new Rock();
		Paper paper = new Paper();
		Scissors scissors = new Scissors();
		for(int version = 1; version < 4; version ++){
			rock.setVersion(version);
			paper.setVersion(version);
			scissors.setVersion(version);
			Simulator.getInstance().savePrototypeToFile(rock.initSampleAgent());
			Simulator.getInstance().savePrototypeToFile(paper.initSampleAgent());
			Simulator.getInstance().savePrototypeToFile(scissors.initSampleAgent());
		}
		
		// Conway's Game of Life Agents
		Simulator.getInstance().savePrototypeToFile(new ConwaysAliveBeing().initSampleAgent());
		Simulator.getInstance().savePrototypeToFile(new ConwaysDeadBeing().initSampleAgent());
	}
}
