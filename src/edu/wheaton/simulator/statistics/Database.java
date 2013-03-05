package edu.wheaton.simulator.statistics;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.TreeBasedTable;

public class Database {

	/**
	 * TODO: Make a TreeBasedtable of Snapshots
	 * TODO: Create instance of ImmutableTable from treetable
	 * TODO: Outside access to database will refer to ImmutableTable 
	 * 		 to ensure database is not changed
	 */
	
	private TreeBasedTable<EntityID, Integer, EntitySnapshot> treeTable;
	
	private ImmutableTable<EntityID, Integer, EntitySnapshot> database;
	
	public Database() {
		treeTable = TreeBasedTable.create();
		EntityID id = new EntityID("akon", 1);
		Map<String, FieldSnapshot> defaults = new HashMap<String, FieldSnapshot>();
		EntityPrototype pro = new EntityPrototype("agent", defaults);
		InteractionDescription inter = new InteractionDescription();
		EntitySnapshot snap = new AgentSnapshot(id, new HashMap<String, Object>(), 1, pro, inter);
		
		treeTable.put(id, 1, snap);
		
		database = new ImmutableTable.Builder<EntityID, Integer, EntitySnapshot>().putAll(treeTable).build();
		
		database.get(id, 1).id();
	}
	
	public static void main(String[] args) {
		Database db = new Database();
	}
}
