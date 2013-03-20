/**
 * StatisticsScreen
 * 
 * Class representing the screen that allows users to view statistics.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatisticsScreen extends Screen {

	public StatisticsScreen(SimulatorMenu m) {
		this.menu = m;
		this.setLayout(new BorderLayout());
		JLabel label = new JLabel("Statistics", JLabel.CENTER);
		label.setPreferredSize(new Dimension(300, 150));
		JPanel panel = new JPanel();
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						menu.setScreen(menu.getScreen("editSim"));
					}
				}
				);
		panel.add(finishButton);
		this.add(label, BorderLayout.NORTH);
		this.add(panel, BorderLayout.CENTER);
		this.setVisible(true);
	}

}