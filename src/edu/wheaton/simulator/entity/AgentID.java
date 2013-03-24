package edu.wheaton.simulator.entity;


public class AgentID implements Comparable<AgentID>{
	private static Integer nextID = 0;
	
	private final Integer value;
	
	protected AgentID(){
		value = genIDValue();
	}
	
	public final Integer getInt(){
		return value;
	}
	
	@Override
	public String toString(){
		return "AgentID:" + value.toString();
	}

	private static synchronized final Integer genIDValue() {
		Integer id = nextID;
		++nextID;
		return id;
	}

	@Override
	public int compareTo(AgentID o) {
		return this.getInt().compareTo(o.getInt());
	}
	
	public boolean equals(AgentID o){
		return this.getInt().equals(o.getInt());
	}
}
