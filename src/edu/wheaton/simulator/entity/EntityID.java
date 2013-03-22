package edu.wheaton.simulator.entity;

public class EntityID {
	private static Integer nextID = 0;
	
	private final Integer value;
	
	protected EntityID(){
		value = genIDValue();
	}
	
	public final Integer getInt(){
		return value;
	}
	
	@Override
	public String toString(){
		return value.toString();
	}

	private static synchronized final Integer genIDValue() {
		Integer id = nextID;
		++nextID;
		return id;
	}

	public int compareTo(EntityID o) {
		return this.getInt().compareTo(o.getInt());
	}
	
	public boolean equals(EntityID o){
		return this.getInt().equals(o.getInt());
	}
}
