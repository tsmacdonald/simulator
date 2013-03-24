<<<<<<< HEAD
/**
 * ViewSimScreen
 * 
 * Class representing the screen that displays the grid as
 * the simulation runs.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ViewSimScreen extends Screen {

	private JPanel gridPanel;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6872689283286800861L;
	
	public ViewSimScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
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
		
		this.add(gridPanel, BorderLayout.CENTER);
		panel.add(pauseButton);
		panel.add(backButton);
		this.add(label, BorderLayout.NORTH);
		this.add(panel, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public void createGrid(JPanel[][] grid){
		gridPanel.removeAll();
		gridPanel.setLayout(new GridLayout(grid.length, grid[0].length));
		System.out.println(grid[0].length-1);
		for (int j = grid[0].length-1; j >= 0; j--) {
			System.out.println(grid.length);
            for (int i = 0; i < grid.length; i++) {
            	System.out.println("i: "+i+" j: "+j);
            	gridPanel.add(grid[i][j]);
            }
		}
	}
	
	public void addComponents(JPanel panel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub

	}

}
=======
/**
 * ViewSimScreen
 * 
 * Class representing the screen that displays the grid as
 * the simulation runs.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6872689283286800861L;
	
	public ViewSimScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
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
		
		this.add(gridPanel, BorderLayout.CENTER);
		panel.add(pauseButton);
		panel.add(backButton);
		this.add(label, BorderLayout.NORTH);
		this.add(panel, BorderLayout.SOUTH);
		this.setVisible(true);
	}

//	public void createGrid(JPanel[][] grid){
//		gridPanel.removeAll();
//		gridPanel.setLayout(new GridLayout(grid.length, grid[0].length));
//		System.out.println(grid[0].length-1);
//		
//		for (int j = grid[0].length-1; j >= 0; j--) {
////			System.out.println(grid.length);
//            for (int i = 0; i < grid.length; i++) {
////         	System.out.println("i: "+i+" j: "+j);
//            	gridPanel.add(grid[i][j]);
//            }
//		}
		
	
	public void addComponents(JPanel panel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub

	}

}
>>>>>>> f9c788a45b8b22fa4087e4ee354f8dafcf6faf5b
