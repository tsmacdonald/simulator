/**
 * Main class
 * 
 * Class containing the main method to run the simulation
 * Phase 2 Deliverable
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
				}
				catch(Exception e) {
					System.err.println("L&F trouble.");
					e.printStackTrace();
				}
				//DisplayManager dm = DisplayManager.getInstance(new Display());
				//if (dm == null)
					//System.exit(0);
				ScreenManager sm = new ScreenManager(new Display());
				sm.update(sm.getScreen("Title"));
			}
		}));

	}

}
