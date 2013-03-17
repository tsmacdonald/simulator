package edu.wheaton.simulator.statistics;

public class EntityID implements Comparable<EntityID> {

	public final String name;
	public final Integer id;

	public EntityID(String name, int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public int compareTo(EntityID arg0) {
		return this.id.compareTo(arg0.id);
	}

}
