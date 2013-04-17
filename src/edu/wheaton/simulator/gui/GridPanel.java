package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.entity.Agent;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 6168906849044462629L;

	private SimulatorGuiManager gm;

	private int width;

	private int height;
	
	private boolean layers;

	public GridPanel(SimulatorGuiManager gm) {
		this.gm = gm;
		layers = false;
	}

	@Override
	public void paint(Graphics g) {
		width = this.getWidth();
		height = this.getHeight();
		
		int gridWidth = gm.getSimGridWidth();
		int gridHeight = gm.getSimGridHeight();
		
		int pixelWidth = width / gridWidth;
		int pixelHeight = height / gridHeight;
		
		clearAgents(g);
		agentPaint(g);
		
		g.setColor(Color.BLACK);
		int squareSize = Math.min(pixelWidth, pixelHeight);
		for (int i = 0; i < gridWidth; i++)
			for (int j = 0; j < gridHeight; j++)
				g.drawRect(squareSize * i, squareSize * j, 
						squareSize, squareSize);
	}

	public void agentPaint(Graphics g){
		
		width = this.getWidth();
		height = this.getHeight();
		
		int gridWidth = gm.getSimGridWidth();
		int gridHeight = gm.getSimGridHeight();
		
		int pixelWidth = width / gridWidth;
		int pixelHeight = height / gridHeight;
		
		int squareSize = Math.min(pixelWidth, pixelHeight);
		
		if(layers) {
			try {
				gm.setSimLayerExtremes();
			} catch (EvaluationException e) {
				e.printStackTrace();
			}
		}
		
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				Agent agent = gm.getSimAgent(x, y);
				if(agent != null) {
					if(layers){
						try {
							g.setColor(agent.getLayerColor());
						} catch (EvaluationException e) {
							e.printStackTrace();
						}
					}
					else{g.setColor(agent.getColor());}
					if(squareSize < 9){
						g.fillRect(squareSize * x + (x + 1), squareSize * y + (y + 1), 
								squareSize, squareSize);
					}
					else{
						int iconSize = squareSize/7;
						for (int a = 0; a < 7; a+=1) {
							for (int b = 0; b <  7; b+=1) {
								byte[] icon = agent.getDesign();
								byte val = new Byte("0000001");
								if((icon[a]&(val<<b)) > 0){
									g.fillRect((squareSize * x + 1) + (6-b)*iconSize,
											(squareSize * y + 1) + (a)*iconSize, 
											iconSize, iconSize);
								}
							}
						}
					}
				}
			}
		}
	}

	public void clearAgents(Graphics g) {
		width = this.getWidth();
		height = this.getHeight();
		
		int gridWidth = gm.getSimGridWidth();
		int gridHeight = gm.getSimGridHeight();
		
		int pixelWidth = width / gridWidth;
		int pixelHeight = height / gridHeight;
		
		int squareSize = Math.min(pixelWidth, pixelHeight) - 1;
		
		g.setColor(Color.WHITE);
		
		for (int x = 0; x < gridWidth; x++)
			for (int y = 0; y < gridHeight; y++)
				g.fillRect(squareSize * x + (x + 1), squareSize * y + (y + 1), squareSize, squareSize);
	}
	
	public void setLayers(boolean args){
		layers = args;
	}
}