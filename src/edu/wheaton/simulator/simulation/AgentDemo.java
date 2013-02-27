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
import java.io.IOException;
import java.util.Scanner;

import edu.wheaton.simulator.gridentities.Agent;
import edu.wheaton.simulator.gridentities.ElementAlreadyContainedException;

public class AgentDemo {

	public static void main(String[] args){
		Grid grid = new Grid(100, 100); 
		Scanner S = new Scanner(System.in); 
		
		//Create a new Agent		
		System.out.println("Creating a new Agent"); 
		Agent dog = new Agent(grid, Color.RED, true);
		
		//Add a field to this Agent
		System.out.println("Specify Agent Fields:"); 
		while(true){
			System.out.println("Name="); 
			String input = S.nextLine(); 
			
			if(input.equals("done")) 
				break;
			
			input = "Name=" + input; 
			
			System.out.println("Type="); 
			input += "\nType=" + S.nextLine();
			
			System.out.println("Value="); 
			input += "\nValue=" + S.nextLine(); 
			
			try {
				dog.addField(input);
			} catch (ElementAlreadyContainedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		//Add a trigger condition to this Agent
		System.out.println("Specify Agent Triggers:"); 
		while(true){
			System.out.println("Priority="); 
			String input = S.nextLine();
			
			if(input.equals("done")) 
				break;
			
			input = "Priority=" + input; 
		
			
			System.out.println("Condition="); 
			input += "\nCondition=" + S.nextLine();
			
			System.out.println("Behavior="); 
			input += "\nBehavior=" + S.nextLine(); 
			
			try {
				dog.addTrigger(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}	
}
