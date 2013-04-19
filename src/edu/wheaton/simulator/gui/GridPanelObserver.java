package edu.wheaton.simulator.gui;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.datastructure.GridObserver;

public class GridPanelObserver implements GridObserver{

	private GridPanel gp;
	
	public GridPanelObserver(GridPanel gp){
		this.gp = gp;
	}
	
	public void update(Grid grid){
		gp.setGrid(grid);
		gp.repaint();
	}
}
