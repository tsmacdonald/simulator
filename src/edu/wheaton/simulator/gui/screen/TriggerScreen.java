package edu.wheaton.simulator.gui.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;
import edu.wheaton.simulator.gui.SimulatorGuiManager;

public class TriggerScreen extends Screen {

	private static final long serialVersionUID = 1L;
	
	private EditTriggerScreen edit;
	
	private JScrollPane listScrollView;
	
	private JPanel triggerListView;
	
	private JLabel triggerLabel;
	
	private Prototype agent;
	
	private JList triggers;
	
	private JButton addButton;
	
	public TriggerScreen(SimulatorGuiManager sm) {
		super(sm);
		setLayout(new GridBagLayout());
		addTriggerLabel(new GridBagConstraints());
		addEditLayout(new GridBagConstraints());
		addListLayout(new GridBagConstraints());
		addTriggerList(new GridBagConstraints());
		addAddButton(new GridBagConstraints());
	}

	private void addTriggerList(GridBagConstraints constraints) {
		triggers = new JList();
		triggers.setLayoutOrientation(JList.VERTICAL_WRAP);
		triggers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		triggers.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(triggers.getSelectedIndex() > 0)
					edit.load(new Trigger.Builder(agent), (Trigger)triggers.getSelectedValue());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}
		});
		triggers.setBorder(BorderFactory.createLineBorder(Color.magenta));
		triggers.setLayoutOrientation(JList.VERTICAL);
		triggers.setPreferredSize(new Dimension(200, 425));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		triggerListView.add(triggers, constraints);
	}

	private void addTriggerLabel(GridBagConstraints constraints) {
		triggerLabel = new JLabel("Triggers:");
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(triggerLabel, constraints);		
	}

	private void addListLayout(GridBagConstraints constraints) {
		listScrollView = new JScrollPane();
		triggerListView = new JPanel();
		listScrollView.getViewport().add(triggerListView);
		triggerListView.setLayout(new GridBagLayout());
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.ipadx = 200;
		constraints.ipady = 425;
		constraints.insets = new Insets(0, 0, 0, 50);
		triggerListView.setBackground(Color.white);
		add(listScrollView, constraints);
	}

	private void addEditLayout(GridBagConstraints constraints){
		edit = new EditTriggerScreen(gm);
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		add(edit, constraints);
	}
	
	private void addAddButton(GridBagConstraints constraints){
		addButton = new JButton("Add Trigger");
		addButton.addActionListener(new AddTriggerListener());
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.BASELINE_LEADING;
		add(addButton, constraints);
	}
	
	private class AddTriggerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// Sets default to 100 because that is the lowest possible priority and the 
			// added trigger should appear at the end of the trigger list
			Trigger t = new Trigger("", 100, null, null);
			edit.load(new Trigger.Builder(agent), t);
			agent.addTrigger(t);
			triggers.setSelectedIndex(triggers.getLastVisibleIndex());
			load(agent);
		}
	}
	
	public void reset(){
		edit.reset();
		agent = null;
		triggers.removeAll();
	}
	
	public void load(Prototype p){
		agent = p;
		triggers.setListData(agent.getTriggers().toArray());
		validate();
	}

	@Override
	public void load() {
	}
	
	public boolean sendInfo(){
		if(edit.sendInfo() == null){
			JOptionPane.showMessageDialog(null, "Invalid trigger input");
			return false;
		}
		return true;
	}
}