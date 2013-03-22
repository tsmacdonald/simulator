package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class SetupScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8347080877399964861L;

	public SetupScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		JLabel label = new JLabel("Simulation Setup");
		label.setPreferredSize(new Dimension(300, 150));
		this.add(label, BorderLayout.NORTH);
		JButton backButton = new JButton("Back");
		backButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.update(sm.getScreen("Edit Simulation"));
					}
				}
		);
		this.add(backButton);
	}

	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
