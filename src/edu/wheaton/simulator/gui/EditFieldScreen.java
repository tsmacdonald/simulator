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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditFieldScreen extends Screen {

	private static final long serialVersionUID = 8001531208716520432L;

	private JTextField nameField;

	private JTextField initValue;

	private String prevName;

	public EditFieldScreen(final SimulatorGuiManager gm) {
		super(gm);
		this.setLayout(new BorderLayout());
		JLabel label = GuiUtility.makeLabel("Edit Field",new PrefSize(300, 150),HorizontalAlignment.CENTER);
		
		JPanel mainPanel = GuiUtility.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		JPanel panel1 = GuiUtility.makePanel(BoxLayoutAxis.X_AXIS,null,null);
		JPanel panel2 = GuiUtility.makePanel(BoxLayoutAxis.X_AXIS,null,null);
		JPanel panel3 = GuiUtility.makePanel(BoxLayoutAxis.X_AXIS,null,null);
		JPanel buttonPanel = new JPanel();
		JLabel nameLabel = GuiUtility.makeLabel("Field Name: ",MaxSize.NULL,HorizontalAlignment.RIGHT);
		nameField = GuiUtility.makeTextField(null,40, new MaxSize(300,40),null);
		JLabel valueLabel = new JLabel("Initial Value: ",SwingConstants.RIGHT);
		initValue = GuiUtility.makeTextField(null,40,new MaxSize(300,40),null);
		JButton cancelButton = GuiUtility.makeButton("Cancel",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						gm.getScreenManager().update(gm.getScreenManager().getScreen("Fields")); 
					} 
				}
				);
		cancelButton.setPreferredSize(new Dimension(120, 60));
		JButton finishButton = GuiUtility.makeButton("Finish",new FinishListener());
		finishButton.setPreferredSize(new Dimension(120, 60));
		panel1.add(nameLabel);
		panel1.add(nameField);
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
		initValue.setText(getGuiManager().getFacade().getGlobalField(n).getValue());
		prevName = n;
	}

	@Override
	public void load() {
		//What is this here for?
		//GUIToAgentFacade facade = gm.getFacade();
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
					getGuiManager().getFacade().removeGlobalField(prevName);
					getGuiManager().getFacade().addGlobalField(nameField.getText(), initValue.getText());
				}
				else{
					getGuiManager().getFacade().addGlobalField(nameField.getText(), initValue.getText());
				}
			} catch (Exception e) {
				toMove = false;
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			if(toMove) {
				getScreenManager().getScreen("Fields").load();
				getScreenManager().update(getScreenManager().getScreen("Fields"));
				//TODO should not switch screens if the error message was shown.
			}

		}
	}
}
