package edu.wheaton.simulator.gui;

import java.awt.Color;
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
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				Agent agent;
				if((agent = sm.getFacade().getAgent(x, y)) instanceof Agent && agent != null) {
					System.out.println("\tg null " + (g == null));
					System.out.println("\tagent null " + (agent == null));
					System.out.println("\tcolor null " + (agent.getColor() == null));
					g.setColor(agent.getColor());
					
					g.fillRect(squareSize * x + (x + 1), squareSize * y + (y + 1), 
							squareSize, squareSize);
					
					/* TODO Remove when ready for design
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
					*/
				}
			}
		}
	}
	
	public void clearAgents(Graphics g) {
		width = this.getWidth();
		height = this.getHeight();
		gridWidth = sm.getGUIwidth();
		gridHeight = sm.getGUIheight();
		int pixelWidth = width / gridWidth;
		int pixelHeight = height / gridHeight;
		int squareSize = Math.min(pixelWidth, pixelHeight) - 1;
		g.setColor(Color.WHITE);
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				g.fillRect(squareSize * x + (x + 1), squareSize * y + (y + 1), squareSize, squareSize);
			}
		}
	}
	
}