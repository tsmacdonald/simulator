package edu.wheaton.simulator.statistics;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.expression.Expression;
import edu.wheaton.simulator.simulation.end.SimulationEnder;

public class Loader {

	/**
	 * The grid generated by parsing the save file
	 */
	private Grid grid; 

	/**
	 * Map of all PrototypeSnapshots for the simulation
	 * Since PrototypeSnapshots are immutable, this collection is the same for each step
	 */
	private Set<Prototype> prototypes;

	/**
	 * The name of the simulation you are loading
	 */
	private String name; 

	/**
	 * Handles ending the simulation
	 */
	private SimulationEnder simEnder; 

	/**
	 * Indicates if a simulation has been successfully loaded
	 */
	private boolean simulationLoaded; 

	/**
	 * Constructor
	 */
	public Loader(){
		simulationLoaded = false; 
	}

	/**
	 * Get the loaded Grid
	 * @return Populated Grid
	 * @throws Exception If no Simulation has been loaded yet
	 */
	public Grid getGrid() throws Exception{
		if(simulationLoaded)
			return grid;
		throw new Exception("No simulation has been loaded"); 
	}

	/**
	 * Get the loaded Set of Prototypes
	 * @return Populated set of Prototypes
	 * @throws Exception If no Simulation has been loaded yet
	 */
	public Set<Prototype> getPrototypes() throws Exception{
		if(simulationLoaded)
			return prototypes; 
		throw new Exception("No simulation has been loaded"); 
	}

	/**
	 * Get the name of the loaded simulation
	 * @return Simulation name
	 * @throws Exception If no Simulation has been loaded yet
	 */
	public String getName() throws Exception{
		if(simulationLoaded)
			return name; 
		throw new Exception("No simulation has been loaded"); 
	}

	/**
	 * Get the loaded SimulationEnder
	 * @return A SimulationEnder object
	 * @throws Exception If no Simulation has been loaded yet
	 */
	public SimulationEnder getSimEnder() throws Exception{
		if(simulationLoaded)
			return simEnder; 
		throw new Exception("No simulation has been loaded"); 
	}

	/**
	 * Load the contents of a file. After this is done call getGrid(), getPrototypes(), getSimEnder() and 
	 * getName() to retrieve the loaded information
	 * Code based on http://stackoverflow.com/questions/15906640/
	 * @param fileName The name of the file to load
	 */
	public void loadSimulation(File f){
		File file = f; 
		BufferedReader reader = null;
		name = f.getName();
		this.prototypes = new HashSet<Prototype>(); 
		
		//System.out.println(f.getAbsolutePath()); // TODO DEBUG

		try {
			reader = new BufferedReader(new FileReader(file));

			//Instantiate the Grid
			int width = Integer.parseInt(reader.readLine()); 
			int height = Integer.parseInt(reader.readLine()); 
			grid = new Grid(width, height);  
			
			//Set the updater
			String updater = reader.readLine(); 
			if(updater.equals("Linear")) 
				grid.setLinearUpdater();
			else if(updater.equals("Priority"))
				grid.setPriorityUpdater(0, 100); 
			else if(updater.equals("Atomic"))
				grid.setAtomicUpdater(); 				

			String readLine = reader.readLine();
			while (readLine != null && !readLine.equals("")) {
				if(readLine.equals("AgentSnapshot")){
					//Find the appropriate prototype
					String prototypeName = reader.readLine();
					Prototype parent = getPrototype(prototypeName);
					
					//Read in the color and design
					String colorString = reader.readLine(); 
					String[] colorToks = colorString.split("~"); 
					
					Color color = new Color(Integer.parseInt(colorToks[0]), 
							Integer.parseInt(colorToks[1]), Integer.parseInt(colorToks[2]));
					byte[] design = createByteArray(reader.readLine());

					//Create the Agent
					Agent agent = new Agent(grid, parent, color, design);
					//System.out.println(agent.getColor()); 

					//Get the Agent's position on the Grid
					int xpos = Integer.parseInt(reader.readLine()); 
					int ypos = Integer.parseInt(reader.readLine()); 

					//Add the agent's default fields
					readLine = reader.readLine(); 
					while(readLine.substring(0,  13).equals("FieldSnapshot")){
						String[] tokens = readLine.split("~");
						try {
							agent.addField(tokens[1], tokens[2]);
						} catch (ElementAlreadyContainedException e) {
							System.out.println("Agent Field already exists"); 
							System.out.println(tokens[1] + " " + tokens[2]); 
							e.printStackTrace();
						}
						readLine = reader.readLine(); 
					}

					//System.out.println("Adding Agent"); 
					grid.addAgent(agent, xpos, ypos); 
				}

				else if(readLine.equals("GlobalVariables")){ 
					readLine = reader.readLine(); 
					while(readLine.substring(0,  7).equals("GLOBAL")){
						String[] tokens = readLine.split("~");
						try {
							grid.addField(tokens[1], tokens[2]);
						} catch (ElementAlreadyContainedException e) {
							System.out.println("Grid Field already exists"); 
							e.printStackTrace();
						}
						readLine = reader.readLine(); 
					}

					//System.out.println("Adding Grid Global"); 
				}
				
				else if(readLine.equals("PrototypeSnapshot")){
					//Parse the required prototype data
					String name = reader.readLine();
					
					//Read in the color and design
					String colorString = reader.readLine(); 
					String[] colorToks = colorString.split("~"); 
					
					Color color = new Color(Integer.parseInt(colorToks[0]), 
							Integer.parseInt(colorToks[1]), Integer.parseInt(colorToks[2]));
					byte[] design = createByteArray(reader.readLine());

					//Create the prototype
					Prototype proto = new Prototype(color, design, name);

					//Add the prototype's default fields
					readLine = reader.readLine(); 
					while(readLine.substring(0,  13).equals("FieldSnapshot")){
						String[] tokens = readLine.split("~");
						try {
							proto.addField(tokens[1], tokens[2]);
						} catch (ElementAlreadyContainedException e) {
							System.out.println("Prototype Field already exists"); 
							e.printStackTrace();
						}
						readLine = reader.readLine(); 
					}

					//Add the prototype's triggers
					while(readLine.substring(0,  7).equals("Trigger")){
						String[] tokens = readLine.split("~");
						proto.addTrigger(new Trigger(tokens[1], Integer.parseInt(tokens[2]), 
								new Expression(tokens[3]), new Expression(tokens[4])));
						readLine = reader.readLine(); 
					}

					//System.out.println("Adding Prototype"); 
					prototypes.add(proto); 
				}
				else if(readLine.equals("EndConditions")){
					simEnder = new SimulationEnder(); 

					readLine = reader.readLine(); 					
					simEnder.setStepLimit(Integer.parseInt(readLine));

					readLine = reader.readLine(); 
					while(readLine != null && readLine.substring(0, 4).equals("POP")){
						String[] tokens = readLine.split("~"); 
						simEnder.setPopLimit(tokens[1], Integer.parseInt(tokens[2])); 						
						readLine = reader.readLine(); 
					}
					//System.out.println("Added SimulationEnder"); 
				}
				else{
					readLine = reader.readLine(); 
				}
			}
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException("Could not find file: " + file.getAbsolutePath(), e);
		}
		catch (IOException e) {
			throw new RuntimeException("Could not read file: " + file.getAbsolutePath(), e);
		} finally {
			try {
				assert(reader!=null);
				reader.close();
			}
			catch (IOException e) {
				throw new RuntimeException("Could not close stream", e);
			}
		}
		//Indicate that we are ready to use the getGrid(), getPrototypes() and getName() methods
		simulationLoaded = true; 
		System.out.println("Load Complete"); 
	}

	/**
	 * Load a Prototype from a file
	 * @param filename The name of the file with the saved Prototype
	 * @return
	 */
	public Prototype loadPrototype(File f){
		File file = f; 
		BufferedReader reader = null;
		Prototype proto = null; 

		try {
			reader = new BufferedReader(new FileReader(file));

			//Skip the "PrototypeSnapshot" header
			String readLine = reader.readLine(); 

			//Parse the required prototype data
			String name = reader.readLine(); 
			
			//Read in the color and design
			String colorString = reader.readLine(); 
			String[] colorToks = colorString.split("~"); 
			
			Color color = new Color(Integer.parseInt(colorToks[0]), 
					Integer.parseInt(colorToks[1]), Integer.parseInt(colorToks[2]));
			byte[] design = createByteArray(reader.readLine());

			//Create the prototype
			proto = new Prototype(color, design, name);

			//Add the prototype's default fields
			readLine = reader.readLine();
			while(readLine != null && readLine.substring(0,  13).equals("FieldSnapshot")){
				String[] tokens = readLine.split("~");
				try {
					proto.addField(tokens[1], tokens[2]);
				} catch (ElementAlreadyContainedException e) {
					System.out.println("Prototype Field already exists"); 
					e.printStackTrace();
				}
				readLine = reader.readLine(); 
			}

			//Add the prototype's triggers
			while(readLine != null && readLine.substring(0,  7).equals("Trigger")){
				String[] tokens = readLine.split("~");
				proto.addTrigger(new Trigger(tokens[1], Integer.parseInt(tokens[2]), 
						new Expression(tokens[3]), new Expression(tokens[4])));
				readLine = reader.readLine(); 
			}

			System.out.println("Loaded Prototype"); 
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException("Could not find file: " + file.getAbsolutePath(), e);
		}catch (IOException e) {
			throw new RuntimeException("Could not read file: " + file.getAbsolutePath(), e);
		}catch(Exception e){
			throw new RuntimeException("Oh no! The load file was somehow corrupted! What oh what will we do?", e);
		}finally {
			try {
				assert(reader!=null);
				reader.close();
			}
			catch (IOException e) {
				throw new RuntimeException("Could not close stream", e);
			}
		}

		return proto;
	}

	/**
	 * Create a byte array from a string
	 * @param s String representing a byte array in the form "010111000"
	 * @return The create byte array
	 */
	private static byte[] createByteArray(String s){
		String[] tokens = s.split("~");
		
		byte[] ret = new byte[tokens.length]; 

		for(int i = 0; i < tokens.length; i++)
			ret[i] = (byte) Integer.parseInt(tokens[i]); 

		return ret; 
	}

	/**
	 * Get the Prototype in this class's internal list with the supplied name
	 * @param name The name of the prototype to retrieve
	 * @return The prototype with the supplied name
	 */
	private Prototype getPrototype(String name){
		Prototype ret = null; 

		for(Prototype p : prototypes)
			if(p.getName().equals(name))
				ret = p; 

		if(ret == null) 
			System.out.println("Parent Not Found");

		return ret; 
	}
}
