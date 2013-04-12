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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditFieldScreen extends Screen {

	private static final long serialVersionUID = 8001531208716520432L;

	private JTextField nameField;

	//private String[] typeNames =  {"Integer", "Double", "String", "Boolean"};

	//private JComboBox fieldType;

	private JTextField initValue;

	private String prevName;

	public EditFieldScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		JLabel label = makeLabelPreferredSize("Edit Field",300, 150);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
		JPanel buttonPanel = new JPanel();
		JLabel nameLabel = new JLabel("Field Name: ");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameField = new JTextField(40);
		nameField.setMaximumSize(new Dimension(300, 40));
		//		JLabel typeLabel = new JLabel("Field Type: ");
		//		typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		//		fieldType = new JComboBox(typeNames);
		//		fieldType.setMaximumSize(new Dimension(300, 40));
		JLabel valueLabel = new JLabel("Initial Value: ");
		valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		initValue = new JTextField(40);
		initValue.setMaximumSize(new Dimension(300, 40));
		JButton cancelButton = makeButton("Cancel",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.update(sm.getScreen("Fields")); 
					} 
				}
				);
		cancelButton.setPreferredSize(new Dimension(120, 60));
		JButton finishButton = makeButton("Finish",new FinishListener());
		finishButton.setPreferredSize(new Dimension(120, 60));
		panel1.add(nameLabel);
		panel1.add(nameField);
		//		panel2.add(typeLabel);
		//		panel2.add(fieldType);
		panel3.add(valueLabel);
		panel3.add(initValue);
		buttonPanel.add(cancelButton);
		buttonPanel.add(finishButton);
		mainPanel.add(panel1);
		mainPanel.add(Box.createRigidArea(new Dimension (0, 15)));
		mainPanel.add(panel2);
		mainPanel.add(Box.createRigidArea(new Dimension (0, 15)));
		mainPanel.add(panel3);
		mainPanel.add(Box.createRigidArea(new Dimension (0, 15)));
		mainPanel.add(buttonPanel);
		this.add(label, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
	}

	public void reset() {
		nameField.setText("");
		initValue.setText("");
		prevName = "";
	}

	public void load(String n) {
		//edit listener should call this?
		reset();
		nameField.setText(n);
		initValue.setText(sm.getFacade().getGlobalField(n).getValue());
		prevName = n;
	}

	@Override
	public void load() {
		//What is this here for?
		//GUIToAgentFacade facade = sm.getFacade();
		//facade.getPrototype(nameField.getText());
		//add listener should call this
		reset();
	}

	private class FinishListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			boolean toMove = true;
			try {
				if (nameField.getText().equals("") ||
						initValue.getText().equals("")) {
					throw new Exception("All fields must have input");
				}
				if (FieldScreen.getEditing()){
					sm.getFacade().removeGlobalField(prevName);
					sm.getFacade().addGlobalField(nameField.getText(), initValue.getText());
				}
				else{
					sm.getFacade().addGlobalField(nameField.getText(), initValue.getText());
				}
			} catch (Exception e) {
				toMove = false;
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			if(toMove) {
				sm.getScreen("Fields").load();
				sm.update(sm.getScreen("Fields"));
				//TODO should not switch screens if the error message was shown.
			}

		}
	}
}
