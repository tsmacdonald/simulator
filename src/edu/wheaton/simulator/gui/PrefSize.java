package edu.wheaton.simulator.gui;

import java.awt.Dimension;

public class PrefSize extends Dimension {
	private static final long serialVersionUID = -2422463360040406589L;

	public PrefSize(Dimension d) {
		super(d);
	}

	public PrefSize(int width, int height) {
		super(width, height);
	}
	
	public static final PrefSize NULL = null;
}
