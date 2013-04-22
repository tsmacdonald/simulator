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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.wheaton.simulator.gui.BoxLayoutAxis;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.HorizontalAlignment;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.MinSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorFacade;

public class EditFieldScreen extends Screen {

	private static final long serialVersionUID = 8001531208716520432L;

	private JDialog parentWindow;
	
	private JTextField nameField;

	private JTextField initValue;

	private String prevName;

	public EditFieldScreen(final SimulatorFacade gm, JDialog parentWindow) {
		super(gm);
		this.parentWindow = parentWindow;
		this.setLayout(new GridBagLayout());
		
		JLabel header = new JLabel("Edit Field");
		
		JLabel nameLabel = new JLabel("Field Name: ");
		nameField = Gui.makeTextField(null,40, MaxSize.NULL,null);
		nameField.setMinimumSize(new MinSize(100,30));
		
		JLabel valueLabel = new JLabel("Initial Value: ");
		initValue = Gui.makeTextField(null,40,MaxSize.NULL,null);
		initValue.setMinimumSize(new MinSize(100,30));
		
		JButton cancel = Gui.makeButton("Cancel",PrefSize.NULL,
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScreenManager sm = getScreenManager();
				sm.load(sm.getScreen("View Simulation")); 
			} 
		});
		
		JButton finish = Gui.makeButton("Finish",PrefSize.NULL,
				new FinishListener());
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(nameLabel,c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		this.add(nameField,c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		this.add(valueLabel,c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		this.add(initValue,c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		this.add(cancel,c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		this.add(finish,c);
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
		initValue.setText(getGuiManager().getGlobalField(n).getValue());
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
				SimulatorFacade gm = getGuiManager();
				String nameFieldText = nameField.getText();
				String initValueText = initValue.getText();
				if (nameFieldText.equals("") ||
						initValueText.equals("")) {
					throw new Exception("All fields must have input");
				}
				if (FieldScreen.getEditing()){
					gm.removeGlobalField(prevName);
					gm.addGlobalField(nameFieldText,initValueText);
				}
				else
					gm.addGlobalField(nameFieldText,initValueText);
			} catch (Exception e) {
				toMove = false;
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			if(toMove) {
				ScreenManager sm = getScreenManager();
				Screen viewSimScreen = sm.getScreen("View Simulation");
				viewSimScreen.load();
				parentWindow.dispose();
				//sm.load(viewSimScreen);
			}
		}
	}
}
