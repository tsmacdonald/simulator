/**
 * Screen.java
 * 
 * Abstract class for creating the screens for the user interface
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Each window will have its own subclass of this abstract class, and one
 * instance of each will be created in the SimulatorMenu constructor. This
 * allows for private helper methods to be written to assist with handling
 * things like loading information for objects to be edited, as well as
 * instance variables to hold those objects, if necessary.
 */
public abstract class Screen extends JComponent implements ActionListener {

	private static final long serialVersionUID = -720613104216646508L;
	
	protected LayoutManager layout;
	protected ScreenManager sm;
	protected JComponent[] components;
	
	public Screen(ScreenManager sm){
		this.sm = sm;
	}
	
	public JComponent[] getComponents(){
		return components;
	}
	public LayoutManager getLayout(){
		return layout;
	}
	public abstract void sendInfo();

	@Override
	public abstract void actionPerformed(ActionEvent e);

}
