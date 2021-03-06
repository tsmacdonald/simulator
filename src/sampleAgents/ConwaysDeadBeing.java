package sampleAgents;


import java.awt.Color;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

/**
 * Makes a dead being from Conway's Game of Life
 * 
 * @author Elliot Penson, Emmanuel Pederson
 *
 */
public class ConwaysDeadBeing extends SampleAgent{

	public ConwaysDeadBeing(){
		super.sampleAgents.add(this);
	}
	
	@Override
	public Prototype initSampleAgent() {
		Prototype deadBeing = new Prototype(new Color(219, 219, 219), "deadBeing");
		return initDeadBeing(deadBeing);
	}
	
	private static Prototype initDeadBeing(Prototype deadBeing){
	// Add fields
	try {
		deadBeing.addField("alive", 0 + ""); // 0 for false, 1 for true
		deadBeing.addField("age", 0 + "");
		deadBeing.addField("neighbors", 0 + "");
	} catch (ElementAlreadyContainedException e) {
		e.printStackTrace();
	}

	// Set up conditionals
	Expression isAlive = new Expression("this.alive == 1");
	Expression neigh1 = new Expression(
			"getFieldOfAgentAt(this.x-1, this.y-1, 'alive') == 1");
	Expression neigh2 = new Expression(
			"getFieldOfAgentAt(this.x, this.y-1, 'alive') == 1");
	Expression neigh3 = new Expression(
			"getFieldOfAgentAt(this.x+1, this.y-1, 'alive') == 1");
	Expression neigh4 = new Expression(
			"getFieldOfAgentAt(this.x-1, this.y, 'alive') == 1");
	Expression neigh5 = new Expression(
			"getFieldOfAgentAt(this.x+1, this.y, 'alive') == 1");
	Expression neigh6 = new Expression(
			"getFieldOfAgentAt(this.x-1, this.y+1, 'alive') == 1");
	Expression neigh7 = new Expression(
			"getFieldOfAgentAt(this.x, this.y+1, 'alive') == 1");
	Expression neigh8 = new Expression(
			"getFieldOfAgentAt(this.x+1, this.y+1, 'alive') == 1");
	Expression reviveCond = new Expression(
			"(this.alive == 0) && (this.neighbors == 3)");

	// Set up behaviors
	Expression incrementAge = new Expression(
			"setField('age', this.age+1)");
	Expression incrementNeighbors = new Expression(
			"setField('neighbors', this.neighbors+1)");
	Expression revive = new Expression("" +
			"clonePrototype(this.x, this.y, 'aliveBeing')");
	Expression resetNeighbors = new Expression(
			"setField('neighbors', 0)");

	// Add triggers
	deadBeing
			.addTrigger(new Trigger("updateAge", 1, isAlive, incrementAge));
	deadBeing.addTrigger(new Trigger("resetNeighbors", 2, new Expression(
			"true"), resetNeighbors));
	deadBeing.addTrigger(new Trigger("checkNeigh1", 3, neigh1,
			incrementNeighbors));
	deadBeing.addTrigger(new Trigger("checkNeigh2", 4, neigh2,
			incrementNeighbors));
	deadBeing.addTrigger(new Trigger("checkNeigh3", 5, neigh3,
			incrementNeighbors));
	deadBeing.addTrigger(new Trigger("checkNeigh4", 6, neigh4,
			incrementNeighbors));
	deadBeing.addTrigger(new Trigger("checkNeigh5", 7, neigh5,
			incrementNeighbors));
	deadBeing.addTrigger(new Trigger("checkNeigh6", 8, neigh6,
			incrementNeighbors));
	deadBeing.addTrigger(new Trigger("checkNeigh7", 9, neigh7,
			incrementNeighbors));
	deadBeing.addTrigger(new Trigger("checkNeigh8", 10, neigh8,
			incrementNeighbors));
	deadBeing.addTrigger(new Trigger("revive", 12, reviveCond, revive));
	
	return deadBeing;
	}
}
