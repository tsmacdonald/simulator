/**
 * SpawningScreen
 * 
 * Class representing the screen that allows to determine spawning conditions,
 * or spawn new entities during a simulation.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpawningScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6312784326472662829L;

	public SpawningScreen(SimulatorMenu m) {
		this.menu = m;
		
		//placeholder screen components
		this.add(new JLabel("Spawning"));
		this.setVisible(true);
	}

	@Override
	public void addComponents(JPanel panel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}