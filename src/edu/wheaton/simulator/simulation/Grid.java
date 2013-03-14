/**
 * Grid.java
 * 
 * Models a cartesian-based coordinate plane for the actors to interact within.
 * 
 * @author Daniel Davenport, Grant Hensel, Elliot Penson, and Simon Swenson
 * 
 * Wheaton College, CSCI 335, Spring 2013
 */
package edu.wheaton.simulator.simulation;

import java.awt.Color;
import java.util.Iterator;

import edu.wheaton.simulator.datastructure.Field;
import edu.wheaton.simulator.entity.Entity;
import edu.wheaton.simulator.entity.Slot;

public class Grid implements Iterable<Slot> {

	/**
	 * The grid of all slots containing all Entity objects Total # slots =
	 * Width x Height
	 */
	private Slot[][] grid;
	private Integer width;
	private Integer height;

	/**
	 * Creates a grid with the given width and height specifications
	 */
	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new Slot[height()][width()];
	}

	public Integer width() {
		return width;
	}

	public Integer height() {
		return height;
	}

	public Slot getSlot(int x, int y) {
		return grid[y][x];
	}

	/**
	 * Causes all entities in the grid to act()
	 * 
	 * TODO Private method is never used locally TODO parameters sent to method
	 * "act" are not valid
	 */
	private void updateEntities() {
		for (Slot[] sArr : grid)
			for (Slot s : sArr) {
				s.getEntity().act(null, null);
			}
	}

	/**
	 * Adds an entity to the slot at the given coordinates
	 * 
	 * TODO Private method is never used locally
	 */
	private void addEntity(Entity a, int x, int y) {
		getSlot(x, y).setEntity(a);
	}

	/**
	 * Returns the Entity in the slot at the given coordinates
	 * 
	 * TODO Private method is never used locally
	 */
	private Entity getEntity(int x, int y) {
		return getSlot(x, y).getEntity();
	}

	/**
	 * Removes a Entity from the slot at the given coordinates
	 * 
	 * TODO Private method is never used locally
	 */
	private void removeEntity(int x, int y) {
		getSlot(x, y).setEntity(null);
	}

	/**
	 * Removes the given entity from the grid.
	 * 
	 * @param ge
	 *            The Entity to remove.
	 */
	public void removeEntity(Entity ge) {
		// TODO Method stub
		throw new UnsupportedOperationException();
	}

	/**
	 * Adds the given entity to the grid.
	 * 
	 * @param ge
	 *            The Entity to add.
	 */
	public void addEntity(Entity ge) {
		// TODO Method stub
		throw new UnsupportedOperationException();
	}

	public Field getField(String fieldName) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/**
	 * Makes a new Layer.
	 * 
	 * @param fieldName
	 *            The name of the Field that the Layer will represent
	 * @param c
	 *            The Color that will be shaded differently to represent Field
	 *            values
	 */
	public void newLayer(String fieldName, Color c) {
		Layer.getInstance().setFieldName(fieldName);
		Layer.getInstance().setColor(c);
		Layer.getInstance().resetMinMax();
	}

	/**
	 * Returns an iterator that goes through the Slots in the Grid
	 * 
	 * @return Iterator<Slot>
	 */
	@Override
	public Iterator<Slot> iterator() {
		return new Iterator<Slot>() {

			int x = 0;
			int y = 0;

			@Override
			public boolean hasNext() {
				return y < height();
			}

			@Override
			public Slot next() {
				Slot toReturn = getSlot(x, y);
				if (x < width() - 1) {
					x++;
				} else {
					x = 0;
					y++;
				}
				return toReturn;
			}

			@Override
			public void remove() {
				// TODO method stub
				throw new UnsupportedOperationException();
			}
		};
	}

}
