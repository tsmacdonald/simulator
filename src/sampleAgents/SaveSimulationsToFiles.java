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
		Simulator.getInstance().load("Rock Paper Scissors", 15, 15, new SimulationEnder());
		Simulator.getInstance().initRockPaperScissors();
		Simulator.getInstance().saveToFile(new File("simulations/RockPaperScissors.txt"), new SimulationEnder());
		
		Simulator.getInstance().load("Conway's Game of Life", 15, 15, new SimulationEnder());
		Simulator.getInstance().initGameOfLife();
		Simulator.getInstance().saveToFile(new File("simulations/ConwaysGameOfLife.txt"), new SimulationEnder());
		
		Simulator.getInstance().load("Samples", 15, 15, new SimulationEnder());
		Simulator.getInstance().initSamples();
		Simulator.getInstance().saveToFile(new File("simulations/Samples.txt"), new SimulationEnder());
	}
}
