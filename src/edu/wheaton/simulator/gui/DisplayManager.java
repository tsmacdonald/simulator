package edu.wheaton.simulator.gui;

import java.awt.LayoutManager;
import javax.swing.JComponent;


public class DisplayManager {

	private static Display display;

	private static int id = 0;

	private DisplayManager(Display d) {
		display = d;
		id++;
	}

	public static DisplayManager getInstance(Display d) {
		if (getId() == 0)
			return new DisplayManager(d);
		return null;
	}

	public static int getId() {
		return id;
	}

	public void updateScreen(JComponent[] comp, LayoutManager l) {
		display.updateDisplay(comp, l);
	}
}
