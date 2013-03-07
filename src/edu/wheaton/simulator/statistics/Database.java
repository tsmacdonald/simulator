package edu.wheaton.simulator.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.TreeBasedTable;
import com.sun.org.apache.bcel.internal.classfile.Field;

import edu.wheaton.simulator.gridentities.GridEntity;

/**
 * This class is an abstraction of a database which is represented by
 * a Table, provided by the Guava libraries
 * 
 * @author akonwi
 */
 
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
		treeTable = TreeBasedTable.create(); // create table
		 /**
		 * the following is a foobar EntitySnapshot and details
		 */
		EntityID id = new EntityID("akon", 1);
		HashMap<String, FieldSnapshot> defaults = new HashMap<String, FieldSnapshot>();
		HashMap<String, FieldSnapshot> fields = new HashMap<String, FieldSnapshot>();
		EntityPrototype prototype = new EntityPrototype("agent", defaults);
		InteractionDescription interaction = new InteractionDescription();
		EntitySnapshot snap = new AgentSnapshot(id, fields, 1, prototype, interaction);
		
		treeTable.put(id, 1, snap); // put snapshot into table
		
		/**
		 * once finished adding all snapshots, build Immutable table 
		 */
		database = new ImmutableTable.Builder<EntityID, Integer, EntitySnapshot>().putAll(treeTable).build();
		
		database.get(id, 1).id(); // Test database
	}
	
	public static void main(String[] args) {
		Database db = new Database();
	}	
	
	public void putEntity(GridEntity entity, int step) {
		EntityID id = entity.getID();
		
		/**
		 * all the fields of the agent
		 */
		ArrayList<Field> entityFields = entity.getFields();
		
		/*
		 * map of the fields to save in entitySnapShot
		 */
		HashMap<String, FieldSnapshot> fieldsForSnap = new HashMap<String, FieldSnapshot>();
		
		/*
		 * fill the fieldsForSnap		
		 */
		Iterator<Field> it = entityFields.iterator();
		while(it.hasNext()) {
			Field current = it.next();
			FieldSnapshot snap = new FieldSnapshot(current.getName(), current.getType(), current.getValue());
			fieldsForSnap.put(current.getName(), snap);
		}
		
		/*
		 * Create protoype
		 * Because this requires me having the initial set of fields,
		 * I think the Observer class should record those on when the
		 * user defines them. Then have a getter method that returns
		 * the 'default fields'
		 * 
		 * Observer class could also possibly create a prototype
		 * because it will have first access to the 'categoryName' and
		 * with the Fields, it can make a map<String, FieldSnapshot>
		 */
	}
}
