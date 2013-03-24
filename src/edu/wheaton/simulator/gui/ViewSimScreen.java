/**
 * ViewSimScreen
 * 
 * Class representing the screen that displays the grid as
 * the simulation runs.
 * 
 * @author Willy McHie and Ian Walling
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ViewSimScreen extends Screen {

	private JPanel gridPanel;
	
	private int height;
	
	private int width;

	private ScreenManager sm;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6872689283286800861L;

	//TODO handle case of no input grid size, either here or in newSim/setup
	public ViewSimScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		this.sm = sm;
		JLabel label = new JLabel("View Simulation", SwingConstants.CENTER);
		JPanel panel = new JPanel();
		gridPanel = new JPanel();
		JButton pauseButton = new JButton("Pause");
		JButton backButton = new JButton("Back");

		backButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.update(sm.getScreen("Edit Simulation")); 
					} 
				}
				);

		
		panel.add(pauseButton);
		panel.add(backButton);
		this.add(label, BorderLayout.NORTH);
		this.add(panel, BorderLayout.SOUTH);
		this.setVisible(true);		
	}

	//TODO write the program loop
	
	public void paint(){
		//TODO might want to rename Grid to avoid confusion with simulation Grid
		Grid grid = new Grid(sm);
		this.add(grid, BorderLayout.CENTER);
		grid.paint(grid.getGraphics());
	}

	public void sendInfo() {
		// TODO Auto-generated method stub

	}

}
