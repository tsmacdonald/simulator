package edu.wheaton.simulator.entity;

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

}
