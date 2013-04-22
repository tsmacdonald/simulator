/**
 * SaveSampleSimulationsToFiles.java
 * 
 * Saves simulations Rock Paper Scissors, Conway's Game of Life and a blank simulation with several loaded sample agents.
 * 
 * @author Agent team
 */
package sampleAgents;

import java.io.File;

import edu.wheaton.simulator.simulation.Simulator;
import edu.wheaton.simulator.simulation.end.SimulationEnder;

public class SaveSimulationsToFiles {
	
	public static void main(String[] args){
		for(int version = 1; version <= 3; version ++){
			Simulator.getInstance().load("Rock Paper Scissors", 15, 15, new SimulationEnder());
			Simulator.getInstance().initRockPaperScissors(version);
			Simulator.getInstance().saveToFile(new File("simulations/RockPaperScissors"+version+".sim"), 
					new SimulationEnder());
		}
		
		Simulator.getInstance().load("Conway's Game of Life", 15, 15, new SimulationEnder());
		Simulator.getInstance().initGameOfLife();
		Simulator.getInstance().saveToFile(new File("simulations/ConwaysGameOfLifeEmpty.sim"), new SimulationEnder());
		
		Simulator.getInstance().load("Samples", 15, 15, new SimulationEnder());
		Simulator.getInstance().initSamples();
		Simulator.getInstance().saveToFile(new File("simulations/Samples.sim"), new SimulationEnder());
	}
}
