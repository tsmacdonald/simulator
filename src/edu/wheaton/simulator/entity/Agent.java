/**
 * Agent.java
 *
 * Agents model actors in the simulation's Grid.
 * 
 * @author Agent Team
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.entity;

import java.awt.Color;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.simulation.SimulationPauseException;

public class Agent extends GridEntity {

	/**
	 * A pointer to the environment so new Agents can be added or removed.
	 */
	private Grid grid;

	/**
	 * Prototype of the agent
	 */
	private Prototype prototype;

	/**
	 * Unique ID
	 */
	private final AgentID id;

	/**
	 * Current priority (used by priorityUpdate)
	 */
	private int currentTriggerIndex = 0;

	/**
	 * Constructor.
	 * 
	 * Makes an agent with the default gridEntity color
	 * 
	 * @param g
	 *            The grid
	 */
	public Agent(Grid g, Prototype prototype) {
		grid = g;
		id = new AgentID();
		init(prototype);
	}

	/**
	 * Constructor. Makes an agent with a solid color
	 * 
	 * @param g
	 *            The grid
	 * @param c
	 *            The color of this agent (passed to super constructor)
	 */
	public Agent(Grid g, Prototype prototype, Color c) {
		super(c);
		grid = g;
		id = new AgentID();
		init(prototype);
	}

	/**
	 * Constructor. Makes an agent with custom color and color map
	 * 
	 * @param g
	 *            The grid
	 * @param c
	 *            The color of this agent (passed to super constructor)
	 * @param d
	 *            The design for this agent (passed to super constructor)
	 */
	public Agent(Grid g, Prototype prototype, Color c, byte[] d) {
		super(c, d);
		grid = g;
		id = new AgentID();
		init(prototype);
	}

	/**
	 * Constructor. Makes an agent with custom color and color map. Sets the id
	 * to something specific.
	 * 
	 * @param g
	 *            The grid (passed to super constructor)
	 * @param c
	 *            The color of this agent (passed to super constructor)
	 * @param d
	 *            The design for this agent (passed to super constructor)
	 * @param id
	 *            Identifier for this agent
	 */
	public Agent(Grid g, Prototype prototype, Color c, byte[] d, AgentID id) {
		super(c, d);
		grid = g;
		this.id = id;
		init(prototype);
	}

	/**
	 * Helper method for constructors
	 * 
	 * @param p
	 */
	private void init(Prototype p) {
		triggers = p.getTriggers();
		prototype = p;
	}

	/**
	 * Causes this Agent to perform 1 action. All triggers with valid conditions
	 * will fire.
	 * 
	 * @throws Exception
	 */
	public void act() throws SimulationPauseException {
		for (Trigger t : triggers)
			try {
				t.evaluate(this, grid.getStep());
			} catch (EvaluationException e) {
				System.err.println(e.getMessage());
				String errorMessage = "Error in Agent: "
						+ this.getPrototypeName() + "\n ID: " + this.getID()
						+ "\n Trigger: " + t.getName() + "\n MSG: "
						+ e.getMessage() + "\n condition: "
						+ t.getConditions().toString();
				throw new SimulationPauseException(errorMessage);
			}
	}

	/**
	 * Causes this Agent to perform only the triggers of the input priority
	 * 
	 * @param priority
	 *            : the priority of the triggers evaluated
	 * @throws SimulationPauseException
	 */
	public void priorityAct(int priority) throws SimulationPauseException {
		if (triggers.size() < currentTriggerIndex
				|| triggers.get(currentTriggerIndex).getPriority() > priority) {
			currentTriggerIndex = 0;
		}
		for (int i = currentTriggerIndex; i < triggers.size(); i++) {
			Trigger t = triggers.get(i);
			if (t.getPriority() == priority) {
				try {
					t.evaluate(this, grid.getStep());
				} catch (EvaluationException e) {
					System.err.println(e.getMessage());
					String errorMessage = "Error in Agent: "
							+ this.getPrototypeName() + "\n ID: "
							+ this.getID() + "\n Trigger: " + t.getName()
							+ "\n MSG: " + e.getMessage() + "\n condition: "
							+ t.getConditions().toString();
					throw new SimulationPauseException(errorMessage);
				}
			} else if (t.getPriority() > priority) {
				currentTriggerIndex = i - 1;
				if (currentTriggerIndex < 0)
					currentTriggerIndex = 0;
				return;
			}
		}
	}

	/**
	 * Evaluates all the triggers' conditionals but does not fire any of them
	 * It's one half of AtomicUpdate. It is the preparation for atomicFire().
	 * 
	 * @throws SimulationPauseException
	 */
	public void atomicCondEval() throws SimulationPauseException {
		for (Trigger t : triggers)
			try {
				t.evaluateCond(this);
			} catch (EvaluationException e) {
				System.err.println(e.getMessage());
				String errorMessage = "Error in Agent: "
						+ this.getPrototypeName() + "\n ID: " + this.getID()
						+ "\n Trigger: " + t.getName() + "\n MSG: "
						+ e.getMessage() + "\n condition: "
						+ t.getConditions().toString();
				throw new SimulationPauseException(errorMessage);
			}
	}

	/**
	 * fires each trigger based on whether or not their conditionals evaluated
	 * to true when atomicCondEval() was last run. atomicCondEval() must be run
	 * before this method. One half of AtomicUpdate.
	 * 
	 * @throws SimulationPauseException
	 */
	public void atomicFire() throws SimulationPauseException {
		for (Trigger t : triggers)
			try {
				t.atomicFire(this, grid.getStep());
			} catch (EvaluationException e) {
				System.err.println(e.getMessage());
				String errorMessage = "Error in Agent: "
						+ this.getPrototypeName() + "\n ID: " + this.getID()
						+ "\n Trigger: " + t.getName() + "\n MSG: "
						+ e.getMessage() + "\n behavior: "
						+ t.getConditions().toString();
				throw new SimulationPauseException(errorMessage);
			}
	}

	/**
	 * Removes this Agent from the environment's list.
	 */
	public void die() {
		grid.removeAgent(getPosX(), getPosY());
	}

	/**
	 * Gets the current x position of this agent
	 * 
	 * @return x
	 */
	public int getPosX() {
		return getField("x").getIntValue();
	}

	/**
	 * Gets the current y position of this agent
	 * 
	 * @return y
	 */
	public int getPosY() {
		return getField("y").getIntValue();
	}

	/**
	 * Sets the Agent's new position
	 * 
	 * @param x
	 * @param y
	 */
	public void setPos(int x, int y) {
		updateField("x", x + "");
		updateField("y", y + "");
	}

	/**
	 * Provides a deep clone of this Agent
	 */
	@Override
	public Agent clone() {
		Agent clone = new Agent(grid, getPrototype(), getColor(),
				getDesign(), getID());

		// set fields
		for (String current : getFieldMap().keySet()) {
			try {
				clone.addField(current, getFieldValue(current));
			} catch (ElementAlreadyContainedException e) {
			}
		}

		// set Triggers
		clone.triggers = this.triggers;

		return clone;
	}

	public Prototype getPrototype() {
		return prototype;
	}

	/**
	 * Provides the name of this agent's prototype
	 * 
	 * @return
	 */
	public String getPrototypeName() {
		return getPrototype().getName();
	}

	public AgentID getID() {
		return id;
	}

	public void addTrigger(Trigger trigger) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Provides the grid that this agent is in.
	 * 
	 * @return the Grid object
	 */
	public Grid getGrid() {
		return grid;
	}
	
}
