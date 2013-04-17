package edu.wheaton.simulator.gui;

import javax.swing.SwingConstants;

public class HorizontalAlignment {

	public final int code;
	
	public HorizontalAlignment(final int code) {
		this.code = code;
	}
	
	public static final HorizontalAlignment CENTER = new HorizontalAlignment(SwingConstants.CENTER);
	public static final HorizontalAlignment LEFT = new HorizontalAlignment(SwingConstants.LEFT);
	public static final HorizontalAlignment RIGHT = new HorizontalAlignment(SwingConstants.RIGHT);
	public static final HorizontalAlignment NULL = null;
}
