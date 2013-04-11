package edu.wheaton.simulator.statistics;

import edu.wheaton.simulator.behavior.AbstractBehavior;
import edu.wheaton.simulator.entity.AgentID;

public class BehaviorSnapshot {

	// TODO Add documentation

	public final AgentID actor;

	public final AbstractBehavior behavior;

	public final AgentID recipient;

	public final Integer step;

	public BehaviorSnapshot(AgentID actor, AbstractBehavior behavior,
			AgentID recipient, Integer step) {
		this.actor = actor;
		this.behavior = behavior;
		this.recipient = recipient;
		this.step = step;
	}

	public String serialize() {
		return "BehaviorSnapshot " + actor + " " + behavior + " " + recipient
				+ " " + step;
	}
}
