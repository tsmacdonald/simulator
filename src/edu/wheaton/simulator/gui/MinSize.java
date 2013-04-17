package edu.wheaton.simulator.gui;

import java.awt.Dimension;

public class MinSize extends Dimension {
	private static final long serialVersionUID = 1197480088860604515L;

	public MinSize(Dimension d) {
		super(d);
	}

	public MinSize(int width, int height) {
		super(width, height);
	}

	public static final MinSize NULL = null;
}
