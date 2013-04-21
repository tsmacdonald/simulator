package edu.wheaton.simulator.gui;

import java.awt.Dimension;

public class MaxSize extends Dimension {

	private static final long serialVersionUID = 7913321320078340541L;

	public MaxSize(Dimension d) {
		super(d);
	}

	public MaxSize(int width, int height) {
		super(width, height);
	}
	
	public static final MaxSize NULL = null;
}
