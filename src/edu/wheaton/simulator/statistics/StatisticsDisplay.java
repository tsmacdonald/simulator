package edu.wheaton.simulator.statistics;

public interface StatisticsDisplay {
	
	public void displayPopulationOverTime(int[] popOverTime);
	
	public void displayAverageFieldValueOverTime(double[] avgFieldValues);
	
	public void displayTriggerExecutionsOverTime(double[] tiggerFires);
	
	public void displayAverageLifespan(double avgLifespan);
	
}
