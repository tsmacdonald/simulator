package edu.wheaton.simulator.gui;

import java.awt.*;
import javax.swing.*;


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

	public void updateScreen(Screen s) {
		display.updateDisplay(s);
	}
}
