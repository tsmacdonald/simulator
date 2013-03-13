package edu.wheaton.simulator.statistics;

public class EntityID implements Comparable<EntityID> {

	private String name;
	private Integer id;
	
	public EntityID(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String name(){
		return name;
	}
	
	public Integer id(){
		return id;
	}
	
	@Override
	public int compareTo(EntityID arg0) {
		return this.id().compareTo(arg0.id());
	}

}
