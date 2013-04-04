/**
 * ConwaysGameOfLifeTest.java
 * 
 * See wiki for an overview
 */

package edu.wheaton.simulator.test.simulation;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

public class ConwaysGameOfLifeTest {

	private Grid grid;

	@Before
	public void setUp() {

		// NOTE: SEE THE WIKI FOR AN OVERVIEW OF THIS

		this.grid = new Grid(20, 30);

		// grid = new Grid(grid.getWidth(), grid.getHeight());

				Prototype deadBeing = new Prototype(grid, Color.RED, "deadBeing");

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
				Expression neigh1 = new Expression("getFieldOfAgentAt(this.x-1, this.y-1, 'alive') == 1");
				Expression neigh2 = new Expression("getFieldOfAgentAt(this.x, this.y-1, 'alive') == 1");
				Expression neigh3 = new Expression("getFieldOfAgentAt(this.x+1, this.y-1, 'alive') == 1");
				Expression neigh4 = new Expression("getFieldOfAgentAt(this.x-1, this.y, 'alive') == 1");
				Expression neigh5 = new Expression("getFieldOfAgentAt(this.x+1, this.y, 'alive') == 1");
				Expression neigh6 = new Expression("getFieldOfAgentAt(this.x-1, this.y+1, 'alive') == 1");
				Expression neigh7 = new Expression("getFieldOfAgentAt(this.x, this.y+1, 'alive') == 1");
				Expression neigh8 = new Expression("getFieldOfAgentAt(this.x+1, this.y+1, 'alive') == 1");
				Expression dieCond = new Expression("(this.alive == 1) && (this.neighbors < 2 || this.neighbors > 3)");
				Expression reviveCond = new Expression("(this.alive == 0) && (this.neighbors == 3)");

				// Set up behaviors
				Expression incrementAge = new Expression("setField('this', 'age', this.age+1)");
				Expression incrementNeighbors = new Expression("setField('this', 'neighbors', this.neighbors+1)");
				Expression die = new Expression("setField('this', 'alive', 0) || setField('this', 'age', 0) || " +
						"setField('this', 'colorRed', 250) || setField('this', 'colorGreen', 0) || setField('this', 'colorBlue', 0)");
				Expression revive = new Expression("setField('this', 'alive', 1) || " +
						"setField('this', 'colorRed', 0) || setField('this', 'colorGreen', 0) || setField('this', 'colorBlue', 250)");
				Expression resetNeighbors = new Expression("setField('this', 'neighbors', 0)");

				// Add triggers
				deadBeing.addTrigger(new Trigger("updateAge", 1, isAlive, incrementAge));
				deadBeing.addTrigger(new Trigger("resetNeighbors", 2, new Expression("true"), resetNeighbors));
				deadBeing.addTrigger(new Trigger("checkNeigh1", 3, neigh1, incrementNeighbors));
				deadBeing.addTrigger(new Trigger("checkNeigh2", 4, neigh2, incrementNeighbors));
				deadBeing.addTrigger(new Trigger("checkNeigh3", 5, neigh3, incrementNeighbors));
				deadBeing.addTrigger(new Trigger("checkNeigh4", 6, neigh4, incrementNeighbors));
				deadBeing.addTrigger(new Trigger("checkNeigh5", 7, neigh5, incrementNeighbors));
				deadBeing.addTrigger(new Trigger("checkNeigh6", 8, neigh6, incrementNeighbors));
				deadBeing.addTrigger(new Trigger("checkNeigh7", 9, neigh7, incrementNeighbors));
				deadBeing.addTrigger(new Trigger("checkNeigh8", 10, neigh8, incrementNeighbors));
				deadBeing.addTrigger(new Trigger("die", 11, dieCond, die));
				deadBeing.addTrigger(new Trigger("revive", 12, reviveCond, revive));

				// Add the prototype to the static list of Prototypes
				Prototype.addPrototype(deadBeing);

				// Make a another prototype that is initially alive
				Prototype aliveBeing = new Prototype(grid, Color.BLUE, "aliveBeing");

				// Add fields
				try {
					aliveBeing.addField("alive", 1 + ""); // 0 for false, 1 for true
					aliveBeing.addField("age", 0 + "");
					aliveBeing.addField("neighbors", 0 + "");
				} catch (ElementAlreadyContainedException e) {
					e.printStackTrace();
				}

				// Add triggers
				aliveBeing.addTrigger(new Trigger("updateAge", 1, isAlive, incrementAge));
				aliveBeing.addTrigger(new Trigger("resetNeighbors", 2, new Expression("true"), resetNeighbors));
				aliveBeing.addTrigger(new Trigger("checkNeigh1", 3, neigh1, incrementNeighbors));
				aliveBeing.addTrigger(new Trigger("checkNeigh2", 4, neigh2, incrementNeighbors));
				aliveBeing.addTrigger(new Trigger("checkNeigh3", 5, neigh3, incrementNeighbors));
				aliveBeing.addTrigger(new Trigger("checkNeigh4", 6, neigh4, incrementNeighbors));
				aliveBeing.addTrigger(new Trigger("checkNeigh5", 7, neigh5, incrementNeighbors));
				aliveBeing.addTrigger(new Trigger("checkNeigh6", 8, neigh6, incrementNeighbors));
				aliveBeing.addTrigger(new Trigger("checkNeigh7", 9, neigh7, incrementNeighbors));
				aliveBeing.addTrigger(new Trigger("checkNeigh8", 10, neigh8, incrementNeighbors));
				aliveBeing.addTrigger(new Trigger("die", 11, dieCond, die));
				aliveBeing.addTrigger(new Trigger("revive", 12, reviveCond, revive));

				// Add the prototype to the static list of Prototypes
				Prototype.addPrototype(aliveBeing);

				// Place dead beings in Grid with some that are alive
				for (int x = 0; x < grid.getWidth(); x++) 
					for(int y = 0; y < grid.getHeight(); y++) {
						if (x == 4 || x == 5 || y == 5) {
							grid.spiralSpawn(Prototype.getPrototype("aliveBeing").createAgent(), x, y);
						} else {
							grid.spiralSpawn(Prototype.getPrototype("deadBeing").createAgent(), x, y);
						}
					}
	}

	@Test
	public void test() {
		// Run through multiple "steps"
		int numSteps = 25;
		for (int i = 0; i < numSteps; i++) {
			try {
				grid.updateEntities();
			} catch(Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				Assert.fail("Bad Evaluation");
			}
		}
	}
}
