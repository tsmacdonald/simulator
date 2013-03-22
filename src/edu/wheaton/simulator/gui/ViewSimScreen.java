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
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ViewSimScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6872689283286800861L;

	public ViewSimScreen(ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		JLabel label = new JLabel("View Simulation", SwingConstants.CENTER);
		JPanel panel = new JPanel();
		JButton pauseButton = new JButton("Pause");
		JButton backButton = new JButton("Back");
		/**
		 * backButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 *           menu.setScreen(menu.getScreen("editSim")); } });
		 */
		panel.add(pauseButton);
		panel.add(backButton);
		this.add(label, BorderLayout.NORTH);
		this.add(panel, BorderLayout.SOUTH);
		this.setVisible(true);
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
