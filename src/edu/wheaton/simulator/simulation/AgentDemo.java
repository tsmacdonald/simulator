/**
 * AgentDemo class
 * 
 * Demo for displaying our Agent implementation
 * Phase 1 Deliverable
 * 
 * @author Grant Hensel
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.simulation;

import java.awt.Color;
import java.util.Scanner;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;

public class AgentDemo {

	public static void main(String[] args) {
		Grid grid = new Grid(100, 100);
		Scanner S = new Scanner(System.in);

		// Create a new Agent
		System.out.println("Creating a new Agent");
		Prototype dog = new Prototype(grid, Color.RED);

		// Add a field to this Agent
		System.out.println("Specify Agent Fields:");
		while (true) {
			System.out.println("Name=");
			String name = S.nextLine().trim();

			if (name.equals("done"))
				break;

			System.out.println("Value=");
			String value = S.nextLine().trim();

			try {
				dog.addField(name, value);
			} catch (ElementAlreadyContainedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Add a trigger condition to this Agent
		System.out.println("Specify Agent Triggers:");
		while (true) {
			System.out.println("Priority=");
			String priority = S.nextLine().trim();

			if (priority.equals("done"))
				break;

			System.out.println("Condition=");
			String condition = S.nextLine().trim();

			System.out.println("Behavior=");
			String behavior = S.nextLine().trim();

			Trigger trigger = new Trigger(Integer.valueOf(priority),
					new Expression(condition),
					Behavior.constructBehavior(behavior));
			dog.addTrigger(trigger);
		}

		S.close();
	}
}
