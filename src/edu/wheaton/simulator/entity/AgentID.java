package edu.wheaton.simulator.entity;

public class AgentID extends EntityID{
	
	public int compareTo(AgentID o){
		return this.getInt().compareTo(o.getInt());
	}
	
	@Override // should not be called
	public int compareTo(EntityID o){
		throw new UnsupportedOperationException();
	}
	
	public boolean equals(AgentID o){
		return this.getInt().equals(o.getInt());
	}
	
	@Override // should not be called
	public boolean equals(EntityID o){
		throw new UnsupportedOperationException();
	}
}
