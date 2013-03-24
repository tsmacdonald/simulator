package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class GridPanel extends Component {

	private static final long serialVersionUID = -20L;

	private ScreenManager sm;

	private int width;
	
	private int height;
	
	public GridPanel(ScreenManager sm) {
		this.sm = sm;
	}

	public void paint(Graphics g) {
		width = this.getWidth();
		height = this.getHeight();
		int gridWidth = sm.getGUIwidth();
		int gridHeight = sm.getGUIheight();
		int pixelWidth = width / gridWidth;
		int pixelHeight = height / gridHeight;
		int squareSize = Math.min(pixelWidth, pixelHeight) - 1;
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				g.drawRect(squareSize * i, squareSize * j, 
						   squareSize, squareSize);
			}
		}
	}

	public void agentPaint(){
		width = this.getWidth();
		height = this.getHeight();
		
	}
}
