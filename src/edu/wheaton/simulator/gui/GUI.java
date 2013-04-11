package edu.wheaton.simulator.gui;

public final class GUI {

	private static String nameOfSimulation;
	private static int gridHeight = 0;
	private static int gridWidth = 0;
	
	
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
