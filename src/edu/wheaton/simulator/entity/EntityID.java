package edu.wheaton.simulator.entity;

/**
 * TODO: Goddamnit brandon write some documentation. 
 */
public class EntityID implements Comparable<EntityID> {

	private static Integer nextID = 0;
	public final Integer id;

	private EntityID(Integer id) {
		this.id = id;
	}

	public static synchronized EntityID genID(Entity owner) {
		int id = nextID;
		++nextID;
		return new EntityID(id);
	}

	@Override
	public int compareTo(EntityID arg0) {
		return this.id.compareTo(arg0.id);
	}
	
	//TODO: Add a toString() method. 

}
