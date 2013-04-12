package edu.wheaton.simulator.statistics;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.wheaton.simulator.datastructure.ElementAlreadyContainedException;
import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Prototype;

public class Loader {

	private Grid grid; 

	/**
	 * Map of all PrototypeSnapshots for the simulation
	 * Since PrototypeSnapshots are immutable, this collection is the same for each step
	 */
	private Map<String, PrototypeSnapshot> prototypes; 

	public Loader(){
		this.prototypes = new HashMap<String, PrototypeSnapshot>(); 
	}

	/**
	 * Load the contents of a file
	 * Code based on http://stackoverflow.com/questions/15906640/
	 * @param fileName The name of the file to load
	 */
	public void load(String fileName){
		File file = new File(fileName);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String readLine = reader.readLine();
			
			while (readLine != null) {
				
				
				if(readLine.equals("AgentSnapshot")){
					 
				}
				else if(readLine.equals("PrototypeSnapshot")){
					//Parse the required prototype data
					String name = reader.readLine(); 
					Color color = new Color(Integer.parseInt(reader.readLine()));
					byte[] design = createByteArray(reader.readLine());
					
					//Create the prototype
					Prototype p = new Prototype(grid, color, design, name);
					
					//Add the prototype's default fields
					readLine = reader.readLine(); 
					while(readLine.substring(0,  13).equals("FieldSnapshot")){
						String[] tokens = readLine.split(" ");
						try {
							p.addField(tokens[1], tokens[2]);
						} catch (ElementAlreadyContainedException e) {
							System.out.println("Element Already Contained"); 
							e.printStackTrace();
						}
					}
				}
				else if(readLine.equals("Globals")){
					
				}
				else{
					reader.readLine(); 
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
				reader.close();
			}
			catch (IOException e) {
				throw new RuntimeException("Could not close stream", e);
			}
		}
	}
	
	private byte[] createByteArray(String s){
		byte[] ret = new byte[s.length()]; 
		
		for(int i = 0; i < s.length(); i++)
			ret[i] = (byte) s.charAt(i); 
		
		return ret; 
	}


}
