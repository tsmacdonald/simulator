package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.wheaton.simulator.gui.screen.EditEntityScreen;

public class IconGridPanel extends GridPanel {

	private static final long serialVersionUID = 8466121263560126226L;

	private ScreenManager sm;
	
	private int width;

	private int height;

	private int gridDimension;

	private int pixelWidth;

	private int pixelHeight;

	private int squareSize;

	private boolean[][] icon;

	public IconGridPanel(final SimulatorGuiManager gm) {
		super(gm);
		sm = gm.getScreenManager();
		width = this.getWidth();
		height = this.getHeight();
		gridDimension = 7;
		squareSize = Math.min(pixelWidth, pixelHeight);
		pixelWidth = width / gridDimension;
		pixelHeight = height / gridDimension;
		icon = new boolean[gridDimension][gridDimension];
		for (int i = 0; i < gridDimension; i++){
			for( int j = 0; j < gridDimension; j++){
				icon[i][j] = false;
			}
		}
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent me) {
				int x = me.getX();
				int y = me.getY();
				int xIndex = x/squareSize;
				int yIndex = y/squareSize;
				icon[xIndex][yIndex] = true;
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}

		});
	}

	@Override
	public void paint(Graphics g) {

		clearAgents(g);
		agentPaint(g);
		g.setColor(Color.BLACK);
		for (int i = 0; i < gridDimension; i++) {
			for (int j = 0; j < gridDimension; j++) {
				g.drawRect(squareSize * i, squareSize * j, 
						squareSize, squareSize);
			}
		}
	}

	@Override
	public void agentPaint(Graphics g){
		Color color = ((EditEntityScreen)sm.getScreen("Edit Entities")).getColor();
		g.setColor(color);
		for (int i = 0; i < gridDimension; i++) {
			for (int j = 0; j < gridDimension; j++) {
				if(icon[i][j] == true){
					g.drawRect(squareSize * i, squareSize * j, 
							squareSize, squareSize);
				}
			}
		}
	}
		

	@Override
	public void clearAgents(Graphics g) {
		squareSize = Math.min(pixelWidth, pixelHeight) - 1;
		g.setColor(Color.WHITE);
		for (int x = 0; x < gridDimension; x++) {
			for (int y = 0; y < gridDimension; y++) {
				g.fillRect(squareSize * x + (x + 1), squareSize * y + (y + 1), squareSize, squareSize);
			}
		}
	}
}

