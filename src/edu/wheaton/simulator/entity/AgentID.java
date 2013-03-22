package edu.wheaton.simulator.entity;

/**
 * BRANDON's README:
 * 
 * Today is a very busy day and I was unable to sort out the 'ideal'
 * ID interface setup. So I will give you the intended rundown:
 * 
 * Rename: Agent.getID() -> Agent.getAgentID()
 * New Method: Entity.getEntityID()
 * New Method: Prototype.getPrototypeID()
 * 
 * New class: EntityID
 * New class PrototypeID
 * 
 * These changes will allow for a clear distinction between Agents,
 * Prototypes, and other subclasses of Entity while at the same time
 * allowing for a higher level of genericness by having two ID's: an EntityID
 * and then a Prototype or Agent ID. The database aspect of Entity has been commented
 * out because it is not being used and will cause foreseeable garbage collection issues.
 * Again, sorry about the instability caused by my API changes. I will try to communicate
 * more effectively in the future.
 * /

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
		return value.toString();
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
