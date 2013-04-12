/**
 * Screen.java
 * 
 * Abstract class for creating the screens for the user interface
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Each window will have its own subclass of this abstract class, and one
 * instance of each will be created in the SimulatorMenu constructor. This
 * allows for private helper methods to be written to assist with handling
 * things like loading information for objects to be edited, as well as
 * instance variables to hold those objects, if necessary.
 */
public abstract class Screen extends JPanel {

	private static final long serialVersionUID = -720613104216646508L;

	protected Manager sm;
	
	public Screen(Manager sm) {
		this.sm = sm;
	}
	
	protected static JButton makeButton(String name, ActionListener al){
		JButton b = new JButton(name);
		b.addActionListener(al);
		return b;
	}
	
	protected static JLabel makeLabelMaxSize(String name, int maxWidth, int maxHeight){
		JLabel label = new JLabel(name);
		label.setMaximumSize(new Dimension(maxWidth, maxHeight));
		return label;
	}
	
	protected static JLabel makeLabelPreferredSize(String name, int prefWidth, int prefHeight){
		JLabel label = new JLabel(name);
		label.setPreferredSize(new Dimension(prefWidth, prefHeight));
		return label;
	}
	
	protected static JPanel makeBorderPanel(BorderLayout layout){
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		return panel;
	}
	
	protected static JPanel makeBoxPanel(int axis){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, axis));
		return panel;
	}
	
	public abstract void load();
	
}
