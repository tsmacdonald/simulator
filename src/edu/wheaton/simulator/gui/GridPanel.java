package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.simulation.GUIToAgentFacade;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = -20L;

	private ScreenManager sm;

	private int width;
	
	private int height;

	private int gridWidth;
	
	private int gridHeight;
	
	public GridPanel(ScreenManager sm) {
		this.sm = sm;
	}

	public void paint(Graphics g) {
		width = this.getWidth();
		height = this.getHeight();
		gridWidth = sm.getGUIwidth();
		gridHeight = sm.getGUIheight();
		int pixelWidth = width / gridWidth;
		int pixelHeight = height / gridHeight;
		int squareSize = Math.min(pixelWidth, pixelHeight);
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				g.drawRect(squareSize * i, squareSize * j, 
						   squareSize, squareSize);
			}
		}
	}

	public void agentPaint(Graphics g){
		width = this.getWidth();
		height = this.getHeight();
		gridWidth = sm.getGUIwidth();
		gridHeight = sm.getGUIheight();
		int pixelWidth = width / gridWidth;
		int pixelHeight = height / gridHeight;
		int squareSize = Math.min(pixelWidth, pixelHeight) - 1;
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				Agent a = sm.getFacade().getAgent(i, j);
				if(a instanceof Agent){
					g.setColor(a.getColor());
					g.fillRect(squareSize * i, squareSize * j, 
							   squareSize, squareSize);
				}
			}
		}
	}
}