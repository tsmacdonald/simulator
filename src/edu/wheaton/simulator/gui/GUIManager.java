<<<<<<< HEAD
package edu.wheaton.simulator.gui;

public class GUIManager {

	private static String nameOfSimulation;
	private static int gridHeight;
	private static int gridWidth;
	private static int guiId;
	
	private GUIManager(){
		guiId++;
	}
	
	public static GUIManager getInstance(){
		if(guiId == 0)
			return new GUIManager();
		else 
			return null;
	}
	
	public static int getId() {
		return guiId;
	}
	
	public static String getNameOfSim(){
		return nameOfSimulation;
	}
	
	public static void setNameOfSim(String nos){
		nameOfSimulation = nos;
	}
	
	public static int getGridHeight(){
		return gridHeight;
	}
	
	public static void setGridHeight(String gh){
		try{
			gridHeight = Integer.parseInt(gh);
		} catch (java.lang.NumberFormatException nfe) { }
	}
	
	public static int getGridWidth(){
		return gridWidth;
	}
	
	public static void setGridWidth(String gw){
		try{
			gridWidth = Integer.parseInt(gw);
		} catch (java.lang.NumberFormatException nfe) { }
	}
}
=======
package edu.wheaton.simulator.gui;

public class GUIManager {

	private static String nameOfSimulation;
	private static int gridHeight;
	private static int gridWidth;
	private static int guiId;
	
	private GUIManager(){
		guiId++;
	}
	
	public static GUIManager getInstance(){
		if(guiId == 0)
			return new GUIManager();
		else 
			return null;
	}
	
	public static int getId() {
		return guiId;
	}
	
	public static String getNameOfSim(){
		return nameOfSimulation;
	}
	
	public static void setNameOfSim(String nos){
		nameOfSimulation = nos;
	}
	
	public static int getGridHeight(){
		return gridHeight;
	}
	
	public static void setGridHeight(int gh){
		try{
			gridHeight = gh;
		} catch (java.lang.NumberFormatException nfe) { }
	}
	
	public static int getGridWidth(){
		return gridWidth;
	}
	
	public static void setGridWidth(int gw){
		try{
			gridWidth = gw;
		} catch (java.lang.NumberFormatException nfe) { }
	}
}
>>>>>>> f9c788a45b8b22fa4087e4ee354f8dafcf6faf5b
