/**
 * EditFieldScreen
 * 
 * Class representing the screen that allows users to edit
 * the properties of a local or global field.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;

import javax.swing.*;

public class EditFieldScreen extends Screen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8001531208716520432L;

	private JPanel panel;
	
	private JTextField nameField;
	
	private String[] typeNames =  {"Integer", "Double", "String"};
	
	private JComboBox fieldType;
	
	private JTextField initValue;
	
	private JTextField xLoc;
	
	private JTextField yLoc;
	
	private JButton cancelButton;
	
	private JButton finishButton;
	
	public EditFieldScreen(ScreenManager sm) {
		super(sm);
		//TODO finish this
		panel = new JPanel();
		nameField = new JTextField();
		fieldType = new JComboBox(typeNames);
		initValue = new JTextField();
		xLoc = new JTextField();
		yLoc = new JTextField();
		cancelButton = new JButton("Cancel");
		finishButton = new JButton("Finish");
		
		panel.add(nameField);
		
	}


	public void addComponents(JPanel panel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub

	}

}
