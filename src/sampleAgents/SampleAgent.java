package sampleAgents;

import java.util.ArrayList;
import java.util.Iterator;

import edu.wheaton.simulator.entity.Prototype;

/**
 * Abstract class for creation and record of sample agents
 * @author David Emmanuel
 *
 */
public abstract class SampleAgent {
	
	/**
	 * A list of the sample agents that are created.
	 */
	protected ArrayList<SampleAgent> sampleAgents;
	
	public SampleAgent() {
		sampleAgents = new ArrayList<SampleAgent>();
	}

	/**
	 * @param prototype A blank prototype
	 * @return The prototype with all of the triggers and fields to make it a functional sample unit
	 */
	public abstract Prototype initSampleAgent(Prototype prototype);
	
	/**
	 * @return An iterator over all of the sample agent creator classes
	 */
	public Iterator<SampleAgent> getSampleAgents(){
		return sampleAgents.iterator();
	}
}
