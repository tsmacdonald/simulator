package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import edu.wheaton.simulator.statistics.StatisticsManager;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;

public class StatDisplayScreen extends Screen {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Source of simulation statistics. 
	 */
	private StatisticsManager statMan; 
	
	private JPanel displayPanel; 
	
	/**
	 * Constructor. 
	 * Make the screen. 
	 * @param sm ScreenManager. 
	 */
	public StatDisplayScreen(Manager sm) {
		super(sm);
		//Setup GridBagLayout & demensions.
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{69, 81, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		//Setup displayPanel -- The panel which displays the graph
		JPanel displayPanel = new JPanel();
		displayPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		//Setup displayPanel's GridBagConstraints
		GridBagConstraints gbc_displayPanel = new GridBagConstraints();
		gbc_displayPanel.gridwidth = 5;
		gbc_displayPanel.insets = new Insets(20, 30, 20, 30);
		gbc_displayPanel.fill = GridBagConstraints.BOTH;
		gbc_displayPanel.gridx = 0;
		gbc_displayPanel.gridy = 0;
		add(displayPanel, gbc_displayPanel);
		
		//Setup agentList -- The ComboBox which lists possible categories of agents to view.
		JComboBox agentList = new JComboBox();
		GridBagConstraints gbc_agentList = new GridBagConstraints();
		//Setup agentList's GridBagConstraints
		gbc_agentList.insets = new Insets(0, 30, 10, 5);
		gbc_agentList.fill = GridBagConstraints.HORIZONTAL;
		gbc_agentList.gridx = 0;
		gbc_agentList.gridy = 1;
		add(agentList, gbc_agentList);
		
		//Setup displayList -- The ComboBox which lists possible ways of viewing the selected population.
		JComboBox displayList = new JComboBox();
		GridBagConstraints gbc_displayList = new GridBagConstraints();
		//Setup displayList's GridBagConstraints
		gbc_displayList.insets = new Insets(0, -5, 10, 5);
		gbc_displayList.fill = GridBagConstraints.HORIZONTAL;
		gbc_displayList.gridx = 1;
		gbc_displayList.gridy = 1;
		add(displayList, gbc_displayList);
		
		//Setup fieldList -- The ComboBox which lists possible fields to track. 
		JComboBox fieldList = new JComboBox();
		GridBagConstraints gbc_fieldList = new GridBagConstraints();
		//Setup fieldList's GridBagConstraints
		gbc_fieldList.insets = new Insets(0, -5, 10, 30);
		gbc_fieldList.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldList.gridx = 2;
		gbc_fieldList.gridy = 1;
		add(fieldList, gbc_fieldList);
		
		//Setup the backButton. 
		JButton backButton = new JButton("Back");
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.gridwidth = 2;
		//Setup backButton's GridBagConstraints
		gbc_backButton.insets = new Insets(-5, -30, 6, 30);
		gbc_backButton.gridx = 3;
		gbc_backButton.gridy = 1;
		add(backButton, gbc_backButton);
	}
	
	private void displayAvgFieldValue() { 
		
	}
	
	private void displayPopOverTime() { 
		
	}
	
	private void displayAvgLifespan() { 
		
	}
	
	private void setBackButtonListener(JButton backButton) { 
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sm.getScreen("View Simulation").load();
				sm.update(sm.getScreen("View Simulation"));
			}
		});
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
	}
	
	class DisplayPanel extends JPanel {

	    /**
		 * 
		 */
		private static final long serialVersionUID = -3096004693285149953L;

		public DisplayPanel() {
	        setBorder(BorderFactory.createLineBorder(Color.black));
	    }

	    @Override
		public Dimension getPreferredSize() {
	        return new Dimension(250,200);
	    }

	    @Override
		public void paintComponent(Graphics g) {
	        super.paintComponent(g);       

	        // Draw Text
	        g.drawString("This is my custom Panel!",10,20);
	    }  
	}
}