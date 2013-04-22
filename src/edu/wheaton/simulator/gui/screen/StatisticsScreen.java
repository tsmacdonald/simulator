
package edu.wheaton.simulator.gui.screen;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.wheaton.simulator.behavior.SetFieldBehavior;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorFacade;

public class StatisticsScreen extends Screen {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ANALYSIS_AVG_FIELD_VALUE_STR = "Average field value"; 
	private static final String ANALYSIS_AVG_LIFESPAN_STR = "Average lifespan"; 
	private static final String ANALYSIS_POP_OVER_TIME_STR = "Population over time";
	private static final String ANALYSIS_TRIGGER_FIRES = "Number of Behavior Executions";
	
	private static final String LIST_LABEL_ANALYSIS = "Type of Analysis"; 
	private static final String LIST_LABEL_AGENT_TYPE = "Type of Agent"; 
	private static final String LIST_LABEL_FIELDS = "Field";
	private static final String LIST_LABEL_TRIGGERS = "Trigger";  
	
	private static final int FIELDS_DISPLAY = 0; 
	private static final int TRIGGERS_DISPLAY = 1; 
	private static final int NO_TRIGGERS_OR_FIELDS = 2; 
	private static int FIELDS_TRIGGERS_DISPLAY_STATE = NO_TRIGGERS_OR_FIELDS;
	
	private JLabel fieldsTriggersLabel;
	private JComboBox analysisList;
	private JComboBox typeList;
	private JComboBox fieldsTriggersList;
	private JPanel gridPanel; 
	private JButton backButton;
	
	/**
	 * Constructor. 
	 * Make the screen. 
	 * @param gm ScreenManager. 
	 */
	public StatisticsScreen(SimulatorFacade gm) {
		super(gm);
		//Setup GridBagLayout & dimensions.
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0};
		setLayout(gridBagLayout);
		
		JLabel analysisLabel = new JLabel(LIST_LABEL_ANALYSIS);
		GridBagConstraints gbc_analysisLabel = new GridBagConstraints();
		gbc_analysisLabel.insets = new Insets(0, 0, 5, 5);
		gbc_analysisLabel.gridx = 1;
		gbc_analysisLabel.gridy = 1;
		gbc_analysisLabel.anchor = GridBagConstraints.SOUTHWEST;
		add(analysisLabel, gbc_analysisLabel);
		
		JLabel typeLabel = new JLabel(LIST_LABEL_AGENT_TYPE);
		GridBagConstraints gbc_typeLabel = new GridBagConstraints();
		gbc_typeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_typeLabel.gridx = 2;
		gbc_typeLabel.gridy = 1;
		gbc_typeLabel.anchor = GridBagConstraints.SOUTHWEST;
		add(typeLabel, gbc_typeLabel);
		
		fieldsTriggersLabel = new JLabel("DEFAULT");
		GridBagConstraints gbc_fieldsTriggersLabel = new GridBagConstraints();
		gbc_fieldsTriggersLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fieldsTriggersLabel.gridx = 3;
		gbc_fieldsTriggersLabel.gridy = 1;
		gbc_fieldsTriggersLabel.anchor = GridBagConstraints.SOUTHWEST;
		add(fieldsTriggersLabel, gbc_fieldsTriggersLabel);
		
		analysisList = new JComboBox();
		GridBagConstraints gbc_analysisList = new GridBagConstraints();
		gbc_analysisList.insets = new Insets(0, 0, 5, 5);
		gbc_analysisList.fill = GridBagConstraints.HORIZONTAL;
		gbc_analysisList.gridx = 1;
		gbc_analysisList.gridy = 2;
		gbc_analysisList.anchor = GridBagConstraints.WEST;
		add(analysisList, gbc_analysisList);
		
		typeList = new JComboBox();
		GridBagConstraints gbc_typeList = new GridBagConstraints();
		gbc_typeList.insets = new Insets(0, 0, 5, 5);
		gbc_typeList.fill = GridBagConstraints.HORIZONTAL;
		gbc_typeList.gridx = 2;
		gbc_typeList.gridy = 2;
		gbc_typeList.anchor = GridBagConstraints.WEST;
		add(typeList, gbc_typeList);
		
		fieldsTriggersList = new JComboBox();
		GridBagConstraints gbc_fieldsTriggersList = new GridBagConstraints();
		gbc_fieldsTriggersList.insets = new Insets(0, 0, 5, 5);
		gbc_fieldsTriggersList.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldsTriggersList.gridx = 3;
		gbc_fieldsTriggersList.gridy = 2;
		gbc_fieldsTriggersList.anchor = GridBagConstraints.WEST;
		add(fieldsTriggersList, gbc_fieldsTriggersList);
		
		gridPanel = getDisplayPanel();
		GridBagConstraints gbc_gridPanel = new GridBagConstraints();
		gbc_gridPanel.gridheight = 2;
		gbc_gridPanel.gridwidth = 3;
		gbc_gridPanel.insets = new Insets(0, 0, 5, 5);
		gbc_gridPanel.fill = GridBagConstraints.BOTH;
		gbc_gridPanel.gridx = 1;
		gbc_gridPanel.gridy = 3;
		add(gridPanel, gbc_gridPanel);
		gridPanel.setMinimumSize(new Dimension(400, 300));
		gridPanel.setPreferredSize(new Dimension(600, 500));
		
		backButton = new JButton("Back");
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(0, 0, 0, 5);
		gbc_backButton.gridx = 3;
		gbc_backButton.gridy = 5;
		gbc_backButton.anchor = GridBagConstraints.NORTHEAST;
		add(backButton, gbc_backButton);
		setBackButtonListener(backButton);
		
		setAnalysisListListener(analysisList);

		setTypeListListener(typeList);

		setFieldsTriggersListListener(fieldsTriggersList);

	}
	
	
	private void setFieldsTriggersListListener(JComboBox fieldsTriggersList) {
		fieldsTriggersList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onFieldOrTriggerSelected();
			}
		});
	}


	private void displayFieldsTriggersListAndLabel() { 
		fieldsTriggersList.setVisible(false);
		String agentTypeName;
		Prototype selectedType;
		switch (FIELDS_TRIGGERS_DISPLAY_STATE) {
		default:
		case NO_TRIGGERS_OR_FIELDS: 
			fieldsTriggersLabel.setText("");
			break;
		case FIELDS_DISPLAY: 
			fieldsTriggersLabel.setText(LIST_LABEL_FIELDS);
			fieldsTriggersList.setVisible(true);
			fieldsTriggersList.removeAllItems();
			agentTypeName = (String) typeList.getSelectedItem();
			selectedType = getGuiManager().getPrototype(agentTypeName);
			if (selectedType != null)
				for (String fieldName : selectedType.getCustomFieldMap().keySet())
					fieldsTriggersList.addItem(fieldName);
			break; 
		case TRIGGERS_DISPLAY: 
			fieldsTriggersLabel.setText(LIST_LABEL_TRIGGERS);
			fieldsTriggersList.setVisible(true);
			fieldsTriggersList.removeAllItems();
			agentTypeName = (String) typeList.getSelectedItem();
			for (String triggerName : Prototype.getTriggerNames(agentTypeName))
				fieldsTriggersList.addItem(triggerName);
			break; 
		}
	}

	/**
	 * To be executed when a type of agent has been selected. 
	 */
	private void onAgentTypeSelected() { 
		displayFieldsTriggersListAndLabel();
		makeGridPanelPaint();
	}
	
	/**
	 * To be executed when a display type has been selected. 
	 */
	private void onAnalysisSelected() { 
		String selectedDisplayType = (String) analysisList.getSelectedItem();
		if (selectedDisplayType != null && 
				selectedDisplayType.equals(ANALYSIS_AVG_FIELD_VALUE_STR)) { 
			FIELDS_TRIGGERS_DISPLAY_STATE = FIELDS_DISPLAY;
			displayFieldsTriggersListAndLabel();
		} else if (selectedDisplayType != null && 
				selectedDisplayType.equals(ANALYSIS_TRIGGER_FIRES)){ 
			FIELDS_TRIGGERS_DISPLAY_STATE = TRIGGERS_DISPLAY;
			displayFieldsTriggersListAndLabel();
		} else {
			FIELDS_TRIGGERS_DISPLAY_STATE = NO_TRIGGERS_OR_FIELDS;
			displayFieldsTriggersListAndLabel();
		}
		makeGridPanelPaint();
	}

	private void makeGridPanelPaint() { 
		Graphics g = gridPanel.getGraphics();
		if (g != null)
			gridPanel.paint(g);
	}

	private void onFieldOrTriggerSelected() { 
		if (FIELDS_TRIGGERS_DISPLAY_STATE != NO_TRIGGERS_OR_FIELDS)
			makeGridPanelPaint();
	}

	private static void setBackButtonListener(JButton backButton) { 
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScreenManager sm = Gui.getScreenManager();
				Screen toDisplay = sm.getScreen("View Simulation");
				sm.load(toDisplay);
			}
		});
	}
//
	private void setTypeListListener(JComboBox agentList) { 
		agentList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAgentTypeSelected();
			}
		});
	}
	
	private void setAnalysisListListener(JComboBox analysisList) { 
		analysisList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAnalysisSelected();
			}
		});
	}

	@Override
	public void load() {
		typeList.removeAllItems();
		analysisList.removeAllItems();
		displayFieldsTriggersListAndLabel();
		for (String name : gm.getPrototypeNames()) { 
			typeList.addItem(name);
		}
		FIELDS_TRIGGERS_DISPLAY_STATE = FIELDS_DISPLAY;
		analysisList.addItem(ANALYSIS_AVG_FIELD_VALUE_STR);
		analysisList.addItem(ANALYSIS_AVG_LIFESPAN_STR);
		analysisList.addItem(ANALYSIS_POP_OVER_TIME_STR);
		analysisList.addItem(ANALYSIS_TRIGGER_FIRES);
	}

	private JPanel getDisplayPanel() { 
		return new JPanel() { 
			
			private static final long serialVersionUID = -4375342368896479163L;

			@Override
			public void paint(final Graphics g) {
				final int width = getWidth() -1; 
				final int height = getHeight() -1;
				
				String selectedDisplayType = (String) analysisList.getSelectedItem();
				if (selectedDisplayType == null) 
					return; 
				else if (selectedDisplayType.equals(ANALYSIS_AVG_LIFESPAN_STR)) 
					paintLife(g, width, height);
				else if (selectedDisplayType.equals(ANALYSIS_POP_OVER_TIME_STR))
					paintPop(g, width, height);
				else if (selectedDisplayType.equals(ANALYSIS_AVG_FIELD_VALUE_STR))
					paintField(g, width, height);
				else if (selectedDisplayType.equals(ANALYSIS_TRIGGER_FIRES))
					paintTriggers(g, width, height); 
			}
			
			private void paintTriggers(Graphics g, int width, int height) {
//				double[] fires = getGuiManager().getStatManager()
//						.getTriggerExecutionsFor(
//								(String) typeList.getSelectedItem(),
//								(String) fieldsTriggersList.getSelectedItem());
//				if (fires.length < 1)
//					return; 
//
//				int[] extremes = getHighLowIndex(fires);
//				double max = fires[extremes[0]];
//				double min = 0;
//
//				g.setColor(Color.BLACK);
//				g.fillRect(0, 0, width, height);
//				int lastX = 0; 
//				int lastY = getAppropriateY(fires[0], max, min, height);
//
//				g.setColor(Color.GREEN);
//				for (int index = 1; index < fires.length; index++) { 
//					int currentX = getAppropriateX(index, fires.length-1, width);
//					int currentY = getAppropriateY(fires[index], max, min, height);
//					g.drawLine(lastX, lastY, currentX, currentY);
//					lastX = currentX;
//					lastY = currentY;
//				}
			}

			private void paintPop(Graphics g, int width, int height) { 
				int[] pops = gm.getPopVsTime((String) typeList.getSelectedItem());
				if (pops.length < 1)
					return; 

				g.setColor(Color.BLACK);
				g.fillRect(0, 0, width, height);

				g.setColor(Color.WHITE);
				g.drawString("Time", width - (width / 13), height - (height / 48));
				g.drawString("Population", (width / 35), (height / 28));
				
				g.setColor(Color.RED);
				int increment = width / 10; 
				for (int i = increment; i < width; i += increment) 
					g.drawLine(i, height - 1, i, height - 10);
				
				
				int[] extremes = getHighLowIndex(pops);
				double max = pops[extremes[0]];
				double min = 0;

				int lastX = 0; 
				int lastY = getAppropriateY(pops[0], max, min, height);
				
				g.setColor(Color.GREEN);
				for (int index = 1; index < pops.length; index++) { 
					int currentX = getAppropriateX(index, pops.length-1, width);
					int currentY = getAppropriateY(pops[index], max, min, height);
					g.drawLine(lastX, lastY, currentX, currentY);
					lastX = currentX;
					lastY = currentY;
				}
			}

			private void paintField(Graphics g, int width, int height) { 
				String protName = (String) typeList.getSelectedItem();
				String fieldName = (String) fieldsTriggersList.getSelectedItem();
				double[] avgValues = gm.getAvgFieldValue(protName, fieldName);
				if (avgValues.length < 1)
					return; 
				
				int[] extremes = getHighLowIndex(avgValues);
				double maxYValue = avgValues[extremes[0]];
				double minYValue = avgValues[extremes[1]];
				
				int zeroY = getAppropriateY(0, maxYValue, minYValue, height);
				g.setColor(Color.WHITE);
				g.drawLine(0, zeroY, width, zeroY);
				
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, width, height);
				g.setColor(Color.RED);
				int lastX = 0; 
				int lastY = getAppropriateY(avgValues[0], maxYValue, minYValue, height);
				for (int index = 1; index < avgValues.length; index++) { 
					int currentX = getAppropriateX(index, avgValues.length-1, width);
					int currentY = getAppropriateY(avgValues[index], maxYValue, minYValue, height);
					g.drawLine(lastX, lastY, currentX, currentY);
					lastX = currentX;
					lastY = currentY;
				}
			}
			
			private void paintLife(Graphics g, int width, int height) { 
				String protName = (String) typeList.getSelectedItem();
				double avgLifespan = gm.getAvgLifespan(protName);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, width, height);
				g.setColor(Color.CYAN);
				g.drawString("" + avgLifespan, width/2, height/2);
			}

			private int getAppropriateY(double currentValue, double maxValue, double minValue, int height) { 
				return (int) (height - ((currentValue - minValue)/(maxValue - minValue)) * height); 
			}
			
			private int getAppropriateX(double currentIndex, double maxIndex, double width) { 
				return (int) ((currentIndex / maxIndex) * width);
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
			
			private int[] getHighLowIndex(int[] values) { 
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
