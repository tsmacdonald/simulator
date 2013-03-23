package edu.wheaton.simulator.entity;

public class PrototypeID implements Comparable<PrototypeID>{
	private static Integer nextID = 0;
	
	private final Integer value;
	
	protected PrototypeID(){
		value = genIDValue();
	}
	
	public final Integer getInt(){
		return value;
	}
	
	@Override
	public String toString(){
		return "PrototypeID:" + value.toString();
	}

	private static synchronized final Integer genIDValue() {
		Integer id = nextID;
		++nextID;
		return id;
	}

	@Override
	public int compareTo(PrototypeID o) {
		return this.getInt().compareTo(o.getInt());
	}

	public boolean equals(PrototypeID o){
		return this.getInt().equals(o.getInt());
	}

}
