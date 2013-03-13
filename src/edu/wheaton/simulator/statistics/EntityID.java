package edu.wheaton.simulator.statistics;

public class EntityID implements Comparable<EntityID> {

	String name;
	int id;
	
	public EntityID(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	@Override
	public int compareTo(EntityID arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
