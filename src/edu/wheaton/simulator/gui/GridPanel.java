package edu.wheaton.simulator.gui;

import java.awt.Graphics;
import javax.swing.JPanel;
import edu.wheaton.simulator.entity.Agent;

public class GridPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6168906849044462629L;

	private ScreenManager sm;

	private int width;

	private int height;

	private int gridWidth;

	private int gridHeight;

	public GridPanel(ScreenManager sm) {
		this.sm = sm;
	}

	@Override
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
				Agent agent = sm.getFacade().getAgent(i, j);
				if(true/*agent instanceof Agent*/){
					g.setColor(agent.getColor());
					//If the square is going to be too small
					//for an icon don't make icons
					if(squareSize < 10){
						g.fillRect(squareSize * i, squareSize * j, 
								squareSize, squareSize);
					}
					//Otherwise make icons
					else{
						int iconSize = squareSize/8;
						for (int a = 0; i < squareSize; i+=iconSize) {
							for (int b = 0; j <  squareSize; j+=iconSize) {
								byte[] icon = agent.getDesign();
								byte val = new Byte("00000001");
								if((icon[a]&(val<<b)) == 1){
									g.fillRect((squareSize * i) + a*iconSize,
											(squareSize * j) + b*iconSize, 
											iconSize, iconSize);
								}
							}
						}
					}
				}
			}
		}
	}
}