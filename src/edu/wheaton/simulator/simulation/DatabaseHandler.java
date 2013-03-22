package edu.wheaton.simulator.simulation;

import edu.wheaton.simulator.statistics.Database;
import edu.wheaton.simulator.statistics.EntitySnapshot;

public class DatabaseHandler {

	private Database database;

	public static final DatabaseHandler instance = new DatabaseHandler();

	private DatabaseHandler() {
		database = new Database();
	}

	public static DatabaseHandler instance() {
		return instance;
	}

	public void addSnapshot(EntitySnapshot snap, int step) {
		database.putEntity(snap, step);
	}
}
