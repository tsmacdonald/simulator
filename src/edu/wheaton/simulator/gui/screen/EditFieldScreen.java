/**
 * EditFieldScreen
 * 
 * Class representing the screen that allows users to edit
 * the properties of a local or global field.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui.screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.wheaton.simulator.gui.BoxLayoutAxis;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.HorizontalAlignment;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorGuiManager;

public class EditFieldScreen extends Screen {

	private static final long serialVersionUID = 8001531208716520432L;

	private JTextField nameField;

	private JTextField initValue;

	private String prevName;

	public EditFieldScreen(final SimulatorGuiManager gm) {
		super(gm);
		this.setLayout(new BorderLayout());
		
		nameField = Gui.makeTextField(null,40, new MaxSize(300,40),null);
		JPanel panel1 = Gui.makePanel(BoxLayoutAxis.X_AXIS,null,null);
		panel1.add(Gui.makeLabel("Field Name: ",MaxSize.NULL,HorizontalAlignment.RIGHT));
		panel1.add(nameField);
		
		JPanel panel2 = Gui.makePanel(BoxLayoutAxis.X_AXIS,null,null);
		
		initValue = Gui.makeTextField(null,40,new MaxSize(300,40),null);
		JPanel panel3 = Gui.makePanel(BoxLayoutAxis.X_AXIS,null,null);
		panel3.add(new JLabel("Initial Value: ",SwingConstants.RIGHT));
		panel3.add(initValue);
		
		Dimension size = new Dimension(0,15);
		JPanel mainPanel = Gui.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		mainPanel.add(panel1);
		mainPanel.add(Box.createRigidArea(size));
		mainPanel.add(panel2);
		mainPanel.add(Box.createRigidArea(size));
		mainPanel.add(panel3);
		mainPanel.add(Box.createRigidArea(size));
		
		PrefSize prefSize = new PrefSize(120,60);
		mainPanel.add(Gui.makePanel(
			Gui.makeButton("Cancel",prefSize,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ScreenManager sm = getScreenManager();
						sm.update(sm.getScreen("View Simulation")); 
					} 
				}),
			Gui.makeButton("Finish",prefSize,
				new FinishListener())));
		
		this.add(Gui.makeLabel("Edit Field",new PrefSize(300, 150),HorizontalAlignment.CENTER), 
			BorderLayout.NORTH);
		
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
		initValue.setText(getGuiManager().getSimGlobalField(n).getValue());
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
				SimulatorGuiManager gm = getGuiManager();
				String nameFieldText = nameField.getText();
				String initValueText = initValue.getText();
				if (nameFieldText.equals("") ||
						initValueText.equals("")) {
					throw new Exception("All fields must have input");
				}
				if (FieldScreen.getEditing()){
					gm.removeSimGlobalField(prevName);
					gm.addSimGlobalField(nameFieldText,initValueText);
				}
				else
					gm.addSimGlobalField(nameFieldText,initValueText);
			} catch (Exception e) {
				toMove = false;
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			if(toMove) {
				ScreenManager sm = getScreenManager();
				Screen fieldsScreen = sm.getScreen("Fields");
				fieldsScreen.load();
				sm.update(fieldsScreen);
				//TODO should not switch screens if the error message was shown.
			}
		}
	}
}
