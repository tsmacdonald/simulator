package edu.wheaton.simulator.demo;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;



public class DemoMain {

	/**
	 * @param args
	 */
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
				@SuppressWarnings("unused")
				DemoMenu dm = new DemoMenu();
			}
		}));
	}

}
