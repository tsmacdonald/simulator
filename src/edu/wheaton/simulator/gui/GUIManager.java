package edu.wheaton.simulator.gui;

public final class GUIManager {

	private static GUIManager instance = null;
	private static String nameOfSimulation;
	private static int gridHeight;
	private static int gridWidth;
	private static int guiId;
	
	//TODO what is the point of GuiId?
	private GUIManager(){
		guiId++;
	}
	
	
	public static GUIManager getInstance(){
		if(instance == null)
			instance = new GUIManager();
		return instance;
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
			gridHeight = gh;
	}
	
	public static int getGridWidth(){
		return gridWidth;
	}
	
	public static void setGridWidth(int gw){
			gridWidth = gw;
	}
}
