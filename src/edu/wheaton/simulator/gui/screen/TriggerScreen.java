package edu.wheaton.simulator.gui.screen;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.gui.FileMenu;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.SimulatorFacade;

public class TriggerScreen extends Screen {

	private static final long serialVersionUID = 1L;

	/**
	 * The screen that allows the user to edit the selected trigger
	 */
	private EditTriggerScreen edit;

	/**
	 * Allows the trigger list to scroll
	 */
	private JScrollPane listScrollView;

	/**
	 * The title label
	 */
	private JLabel triggerLabel;

	/**
	 * The prototype whose triggers are being updated
	 */
	private Prototype prototype;

	/**
	 * The list of triggers of the prototype
	 */
	private JList triggers;

	/**
	 * Button that adds a trigger to the prototype
	 */
	private JButton addButton;

	/**
	 * Deletes a trigger from the prototype
	 */
	private JButton deleteButton;

	/**
	 * Saves the changes to the currently selected trigger
	 */
	private JButton saveButton;

	/**
	 * int counter that prevents added triggers from having the same name
	 */
	private int untitledCounter = 1;

	public TriggerScreen(SimulatorFacade sm) {
		super(sm);
		setLayout(new GridBagLayout());
		addTriggerLabel(new GridBagConstraints());
		addEditLayout(new GridBagConstraints());
		addListLayout(new GridBagConstraints());
		addTriggerList(new GridBagConstraints());
		addAddButton(new GridBagConstraints());
		addDeleteButton(new GridBagConstraints());
		addSaveButton(new GridBagConstraints());
	}

	/**
	 * Adds the JList in a JScrollPane to the view
	 * @param constraints
	 */
	private void addTriggerList(GridBagConstraints constraints) {
		triggers = new JList();
		triggers.setToolTipText("The list of triggers");
		listScrollView.setViewportView(triggers);
		triggers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		triggers.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try{
					if(triggers.getSelectedIndex() >= 0){
						Trigger t = (Trigger) triggers.getSelectedValue();
						edit.load(new Trigger.Builder(t,  prototype), t);
					}
				}catch(Exception e){

				}
			}
		});
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.ipadx = 200;
		constraints.ipady = 425;
		constraints.insets = new Insets(0, 0, 0, 50);
		add(listScrollView, constraints);
	}

	/**
	 * Title of the trigger list
	 * @param constraints
	 */
	private void addTriggerLabel(GridBagConstraints constraints) {
		triggerLabel = new JLabel("Triggers:");
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(triggerLabel, constraints);		
	}

	/**
	 * Generates and adds the JScrollPane
	 * @param constraints
	 */
	private void addListLayout(GridBagConstraints constraints) {
		listScrollView = new JScrollPane(null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.ipadx = 200;
		constraints.ipady = 425;
		constraints.insets = new Insets(0, 0, 0, 50);
		add(listScrollView, constraints);
	}

	/**
	 * Adds the EditTriggerScreen screen to the view
	 * @param constraints
	 */
	private void addEditLayout(GridBagConstraints constraints){
		edit = new EditTriggerScreen(gm);
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		add(edit, constraints);
	}

	/**
	 * Adds the button that allows for the addition of a new trigger to the prototype
	 * @param constraints
	 */
	private void addAddButton(GridBagConstraints constraints){
		addButton = new JButton("Add Trigger");
		addButton.addActionListener(new AddTriggerListener());
		addButton.setToolTipText("Adds a blank trigger to the prototype");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		add(addButton, constraints);
	}

	/**
	 * Adds the button that allows for the deletion of a trigger from the prototype
	 * @param constraints
	 */
	private void addDeleteButton(GridBagConstraints constraints) {
		deleteButton = new JButton("Delete Trigger");
		deleteButton.addActionListener(new DeleteTriggerListener());
		deleteButton.setToolTipText("Deletes the selected trigger");
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(0, 0, 0, 50);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		add(deleteButton, constraints);
	}

	/**
	 * Adds the button that allows the user to save the changes to a selected trigger
	 * @param constraints
	 */
	private void addSaveButton(GridBagConstraints constraints) {
		saveButton = new JButton("Save Current Trigger");
		saveButton.addActionListener(new SaveListener());
		saveButton.setToolTipText("Saves the changes to the selected trigger");
		constraints.anchor = GridBagConstraints.BASELINE_TRAILING;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(0,  0,  0, 50);
		add(saveButton, constraints);
	}

	/**
	 * Listener that adds a trigger to the prototype when the add button is pressed
	 */
	private class AddTriggerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// Sets default to 100 because that is the lowest possible priority and the 
			// added trigger should appear at the end of the trigger list
			Trigger t = new Trigger("Untitled" + untitledCounter++, 100, null, null);
			edit.load(new Trigger.Builder(prototype), t);
			prototype.addTrigger(t);
			load(prototype);
			triggers.setSelectedIndex(triggers.getLastVisibleIndex());
		}
	}

	/**
	 * Listener that deletes the selected trigger from the prototype when the delete button is pressed
	 */
	private class DeleteTriggerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e){
			Trigger toDelete = (Trigger)triggers.getSelectedValue();
			if(toDelete != null){
				prototype.removeTrigger(toDelete.toString());
				edit.reset();
				load(prototype);
			}
			else
				JOptionPane.showMessageDialog(null, "No trigger selected");
		}
	}

	/**
	 * Listener that saves the changes to the selected listener to the prototype
	 */
	private class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(triggers.getSelectedValue() != null){
				Trigger toAdd = edit.sendInfo();
				if(toAdd != null){
					prototype.updateTrigger(triggers.getSelectedValue().toString(), toAdd);
					load(prototype);
					edit.reset();
				}
			}
			else
				JOptionPane.showMessageDialog(null, "You must select a trigger to save");
		}
	}

	/**
	 * Clears all information from the screen (except the untitledCounter 
	 * which is only reset when the page is reinitialized
	 */
	public void reset(){
		edit.reset();
		prototype = null;
		triggers.removeAll();
	}

	/**
	 * Loads the prototype trigger information to the screen
	 * @param p, the prototype whose information is loaded
	 */
	public void load(Prototype p){
		FileMenu fm = Gui.getFileMenu();
		fm.setSaveSim(false);
		prototype = p;
		triggers.setListData(prototype.getTriggers().toArray());
		listScrollView.setViewportView(triggers);
		listScrollView.updateUI();
		validate();
		repaint();
	}

	@Override
	public void load() {
		FileMenu fm = Gui.getFileMenu();
		fm.setSaveSim(false);
	}

	/**
	 * Returns the updated prototype
	 * @return The prototype with the saved trigger changes
	 */
	public Prototype sendInfo(){
		return prototype;
	}
}