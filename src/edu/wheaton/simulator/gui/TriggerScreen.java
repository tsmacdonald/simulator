package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.entity.Trigger;

public class TriggerScreen extends Screen {

	private static final long serialVersionUID = 1L;
	
	private EditTriggerScreen edit;
	
	private JScrollPane listScrollView;
	
	private JPanel triggerListView;
	
	private JLabel triggerLabel;
	
	private Prototype agent;
	
	private JList triggers;
	
	public TriggerScreen(ScreenManager sm) {
		super(sm);
		setLayout(new GridBagLayout());
		addTriggerLabel(new GridBagConstraints());
		addEditLayout(new GridBagConstraints());
		addListLayout(new GridBagConstraints());
	}

	private void addTriggerList(GridBagConstraints constraints) {
		triggers = new JList();	
		triggers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		triggers.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseClicked(MouseEvent e) {
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
		triggers.setListData(agent.getTriggers().toArray());
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		triggerListView.add(triggers, constraints);
		triggers.validate();
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

	private void addEditLayout(GridBagConstraints constraints) {
		edit = new EditTriggerScreen(sm);
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;	
		add(edit, constraints);
	}
	
	public void reset(){
		triggerListView.removeAll();
		edit.reset();
		agent = null;
		triggers = null;
	}
	
	public void load(Prototype p){
		reset();
		agent = p;
		addTriggerList(new GridBagConstraints());
		validate();
	}

	@Override
	public void load() {
		
	}
}