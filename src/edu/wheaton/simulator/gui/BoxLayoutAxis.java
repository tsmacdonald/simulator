package edu.wheaton.simulator.gui;

import javax.swing.BoxLayout;

public final class BoxLayoutAxis {

	public final int code;
	
	private BoxLayoutAxis(int code) {
		this.code = code;
	}
	
	public static final BoxLayoutAxis X_AXIS = new BoxLayoutAxis(BoxLayout.X_AXIS);
	public static final BoxLayoutAxis Y_AXIS = new BoxLayoutAxis(BoxLayout.Y_AXIS);
	public static final BoxLayoutAxis LINE_AXIS = new BoxLayoutAxis(BoxLayout.LINE_AXIS);
	public static final BoxLayoutAxis PAGE_AXIS = new BoxLayoutAxis(BoxLayout.PAGE_AXIS);
}
