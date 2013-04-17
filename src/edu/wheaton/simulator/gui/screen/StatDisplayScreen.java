package edu.wheaton.simulator.gui.screen;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import edu.wheaton.simulator.gui.SimulatorGuiManager;
import edu.wheaton.simulator.simulation.Simulator;
import edu.wheaton.simulator.statistics.StatisticsManager;
import javax.swing.JButton;

public class StatDisplayScreen extends Screen {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String DISPLAY_AVG_FIELD_VALUE_STR = "Average field value"; 
	private static String DISPLAY_AVG_LIFESPAN_STR = "Average lifespan"; 
	private static String DISPLAY_POP_OVER_TIME_STR = "Population over time";

	
	/**
	 * Source of simulation statistics. 
	 */
	private StatisticsManager statMan; 

	private JPanel displayPanel; 

	private JComboBox prototypes; 

	private JComboBox displayTypes; 

	private JComboBox fields; 

	/**
	 * Constructor. 
	 * Make the screen. 
	 * @param gm ScreenManager. 
	 */
	public StatDisplayScreen(SimulatorGuiManager gm) {
		super(gm);
		
		//Setup GridBagLayout & demensions.
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{69, 81, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		//Setup displayPanel -- The panel which displays the graph
		displayPanel = getDisplayPanel();
		
		//Setup displayPanel's GridBagConstraints
		GridBagConstraints gbc_displayPanel = new GridBagConstraints();
		gbc_displayPanel.gridwidth = 5;
		gbc_displayPanel.insets = new Insets(20, 30, 20, 30);
		gbc_displayPanel.fill = GridBagConstraints.BOTH;
		gbc_displayPanel.gridx = 0;
		gbc_displayPanel.gridy = 0;
		add(displayPanel, gbc_displayPanel);

		//Setup agentList's GridBagConstraints
		GridBagConstraints gbc_agentList = new GridBagConstraints();
		gbc_agentList.insets = new Insets(0, 30, 10, 5);
		gbc_agentList.fill = GridBagConstraints.HORIZONTAL;
		gbc_agentList.gridx = 0;
		gbc_agentList.gridy = 1;
		
		//Setup agentList -- The ComboBox which lists possible categories of agents to view.
		prototypes = new JComboBox();
		add(prototypes, gbc_agentList);
		setPrototypeListener(prototypes);
		
		//Setup displayList's GridBagConstraints
		GridBagConstraints gbc_displayList = new GridBagConstraints();
		gbc_displayList.insets = new Insets(0, -5, 10, 5);
		gbc_displayList.fill = GridBagConstraints.HORIZONTAL;
		gbc_displayList.gridx = 1;
		gbc_displayList.gridy = 1;
		
		//Setup displayList -- The ComboBox which lists possible ways of viewing the selected population.
		displayTypes = new JComboBox();
		add(displayTypes, gbc_displayList);
		setDisplayTypeListener(displayTypes);

		//Setup fieldList's GridBagConstraints
		GridBagConstraints gbc_fieldList = new GridBagConstraints();
		gbc_fieldList.insets = new Insets(0, -5, 10, 30);
		gbc_fieldList.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldList.gridx = 2;
		gbc_fieldList.gridy = 1;
		
		//Setup fieldList -- The ComboBox which lists possible fields to track. 
		fields = new JComboBox();
		add(fields, gbc_fieldList);
		setDisplayTypeListener(fields);

		
		
		//Setup backButton's GridBagConstraints
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.gridwidth = 2;
		gbc_backButton.insets = new Insets(-5, -30, 6, 30);
		gbc_backButton.gridx = 3;
		gbc_backButton.gridy = 1;
		
		//Setup the backButton. 
		JButton backButton = new JButton("Back");
		add(backButton, gbc_backButton);
		setBackButtonListener(backButton);
	}

	/**
	 * To be executed when a prototype has been selected. 
	 */
	private void onPrototypeSelected() { 
		String selectedPrototype = (String) prototypes.getSelectedItem();
		fields.removeAllItems();
		for (String fieldName : Simulator.getPrototype(selectedPrototype).getCustomFieldMap().keySet())
			fields.addItem(fieldName);
		onDisplayTypeSelected();
	}
	
	/**
	 * To be executed when a display type has been selected. 
	 */
	private void onDisplayTypeSelected() { 
		String selectedDisplayType = (String) displayTypes.getSelectedItem();
		if (selectedDisplayType != null && 
					selectedDisplayType.equals(DISPLAY_AVG_FIELD_VALUE_STR))
			onFieldSelected();

		else 
			displayPanel.paint(displayPanel.getGraphics());
	}
	
	/**
	 * To be executed when a field has been selected. 
	 */
	private void onFieldSelected() { 
		String selectedDisplayType = (String) displayTypes.getSelectedItem();
		if (selectedDisplayType != null &&
					selectedDisplayType.equals(DISPLAY_AVG_FIELD_VALUE_STR))
			displayPanel.paint(displayPanel.getGraphics());
	}

	private void setBackButtonListener(JButton backButton) { 
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getScreenManager().getScreen("View Simulation").load();
				getScreenManager().update(getScreenManager().getScreen("View Simulation"));
			}
		});
	}

	private void setPrototypeListener(JComboBox protBox) { 
		protBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onPrototypeSelected();
			}
		});
	}
	
	private void setDisplayTypeListener(JComboBox catBox) { 
		catBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onDisplayTypeSelected();
			}
		});
	}
	
	private void setFieldListener(JComboBox fieldBox) {
		fieldBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onFieldSelected();
			}
		});
	}

	@Override
	public void load() {
		prototypes.removeAllItems();
		displayTypes.removeAllItems();
		fields.removeAllItems();
		
		for (String name : Simulator.prototypeNames())
			prototypes.addItem(name);

		displayTypes.addItem(DISPLAY_AVG_FIELD_VALUE_STR);
		displayTypes.addItem(DISPLAY_AVG_LIFESPAN_STR);
		displayTypes.addItem(DISPLAY_POP_OVER_TIME_STR);
	}

	private JPanel getDisplayPanel() { 
		return new JPanel() { 

			private static final long serialVersionUID = -6748560344209099505L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				String selectedDisplayType = (String) displayTypes.getSelectedItem();
				g.drawLine(0, 0, getWidth(), 0);
				g.drawLine(0, 0, 0, getHeight());
				
				if (selectedDisplayType == null) 
					return; 
				else if (selectedDisplayType.equals(DISPLAY_AVG_LIFESPAN_STR)) 
					paintLife(g);
				else if (selectedDisplayType.equals(DISPLAY_POP_OVER_TIME_STR))
					paintPop(g);
				else if (selectedDisplayType.equals(DISPLAY_AVG_FIELD_VALUE_STR))
					paintField(g);
			}
			
			private void paintPop(Graphics g) { 
				String protName = (String) prototypes.getSelectedItem();
				String fieldName = (String) fields.getSelectedItem();
				double[] avgValues = statMan.getAvgFieldValue(protName, fieldName);
				int[] extremes = getHighLowIndex(avgValues);
				int maxIndex = extremes[0];
				int minIndex = extremes[1];
//				g.drawLine(2, 2, 40, 40);
			}
			
			private void paintField(Graphics g) { 
//				g.drawLine(displayPanel.getWidth(), displayPanel.getHeight(), 40, 40);
			}
			
			private void paintLife(Graphics g) { 
//				g.drawLine(2, 2, 40, 40);
			}
			
			private int[] getHighLowIndex(double[] values) { 
				if (values.length == 0) 
					return new int[] {-1, -1};
				int maxIndex = 0;
				int minIndex = 0;
				for (int index = 0; index < values.length; index++) { 
					double currentValue = values[index]; 
					if (currentValue > values[maxIndex])
						maxIndex = index; 
					else if (currentValue < values[minIndex])
						minIndex = index; 
				}
				return new int[] {maxIndex, minIndex}; 
			}
		};
	}
}
