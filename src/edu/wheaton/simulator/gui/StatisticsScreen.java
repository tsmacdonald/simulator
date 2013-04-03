/**
 * StatisticsScreen
 * 
 * Class representing the screen that allows users to view statistics.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.*;

import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.statistics.StatisticsManager;

public class StatisticsScreen extends Screen {

	private JPanel dataPanel;

	private String[] entities;

	private String[] agentFields;
	
	private JComboBox cardSelector;

	private JComboBox popEntityBox;

	private JComboBox fieldEntityBox;

	private JComboBox lifeEntityBox;

	private HashMap<String, JComboBox> agentFieldsBoxes;

	private JPanel fieldCard;

	private StatisticsManager statMan;
	/**
	 * 
	 */
	private static final long serialVersionUID = 714636604315959167L;
	//TODO fix layout of this screen	
	//TODO make sure that correct fields box gets put on the panel when an agent is selected.
	public StatisticsScreen(final ScreenManager sm) {
		super(sm);
		statMan = sm.getStatManager();
		dataPanel = new JPanel();
		dataPanel.setLayout(new CardLayout());
		
		
		this.setLayout(new BorderLayout());
		
		final String populationsStr = "Populations";
		final String fieldsStr = "Fields";
		final String lifespansStr = "Lifespans";
		
		JPanel populationCard = makePopulationCard();
		dataPanel.add(populationCard, populationsStr);
		
		fieldCard = makeFieldCard();
		dataPanel.add(fieldCard, fieldsStr);
		
		JPanel lifespanCard = makeLifespanCard();
		dataPanel.add(lifespanCard, lifespansStr);
		
		JPanel boxPanel = new JPanel();
		String[] boxItems = {populationsStr, fieldsStr, lifespansStr};
		
		cardSelector = makeCardSelector(boxItems,dataPanel);
		
		boxPanel.add(cardSelector);

		entities = new String[0];
		popEntityBox = new JComboBox(entities);
		populationCard.add(popEntityBox);
		
		agentFields = new String[0];
		agentFieldsBoxes = new HashMap<String, JComboBox>();
		agentFieldsBoxes.put("", new JComboBox(agentFields));

		fieldEntityBox = makeFieldEntityBox(entities);
		fieldCard.add(fieldEntityBox);
		fieldCard.add(agentFieldsBoxes.get(""));

		lifeEntityBox = new JComboBox(entities);
		lifespanCard.add(lifeEntityBox);
		
		if(popEntityBox.getSelectedIndex() >= 0){
			int[] popVsTime = sm.getStatManager().getPopVsTime(sm.getFacade().
					getPrototype(popEntityBox.getSelectedItem().toString())
					.getPrototypeID()
					);
			
			Object[][] timePop = new Object[popVsTime.length][2];
			for(int i = 0; i < popVsTime.length; i++){
				Object[] array= {i, popVsTime[i]};
				timePop[i] = array;
			}

			String[] popTime = {"Population", "Time"};
			JTable jt = new JTable(timePop ,popTime);
			populationCard.add(jt);
		}

		JButton displayButton = makeDisplayButton(populationsStr,fieldsStr);
		
		JButton finishButton = makeFinishButton();

		

		//COMING SOON: Average Field Table Statistics

		//		if(fieldEntityTypes.getSelectedIndex() >= 0){
		//			double[] p = statMan.getAvgFieldValue((sm.getFacade().
		//					getPrototype(popEntityTypes.getSelectedItem().toString())
		//					.getPrototypeID()), (String) agentFieldsBox.getSelectedItem()
		//					);
		//			String[] popTime = {"Population", "Time"};
		//			Object[][] timePop = new Object[p.length][2];
		//			for(int i = 0; i < p.length; i++){
		//				Object[] array= {i, p[i]};
		//				timePop[i] = array;
		//			}
		//
		//			JTable jt = new JTable(timePop ,popTime);
		//			populationCard.add(jt);
		//		}
		//		JLabel label = new JLabel("Statistics");
		//		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//		label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		//		label.setPreferredSize(new Dimension(300, 150));
		//
		//		this.add(label, BorderLayout.NORTH);
		
		JPanel graphPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		//TODO MAJOR figure out how to make a graph or something!!
		buttonPanel.add(displayButton);
		buttonPanel.add(finishButton);
		graphPanel.add(new JLabel("Graph object goes here"));
		mainPanel.add(graphPanel);
		mainPanel.add(boxPanel);
		mainPanel.add(dataPanel);
		mainPanel.add(buttonPanel);
		this.add(mainPanel);

	}
	
	private JButton makeDisplayButton(final String populationsStr, final String fieldsStr){
		JButton displayButton = new JButton("Display");
		displayButton.setPreferredSize(new Dimension(150, 70));
		displayButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (cardSelector.getSelectedItem().equals(populationsStr)) {
							int[] pops = statMan.getPopVsTime(sm.getFacade().
														getPrototype((String)popEntityBox.getSelectedItem())
														.getPrototypeID());
							System.out.println("Population over time: ");
							//TODO temporary solution to demonstrate compatibility
							//TODO this loop displays nothing for a basic simulation
							for (int i = 0; i < pops.length; i++) {
								System.out.println(i + ":  " + pops[i]);
							}
						}
						else if (cardSelector.getSelectedItem().equals(fieldsStr)) {
							String s = (String)fieldEntityBox.getSelectedItem();
							Prototype p = sm.getFacade().getPrototype(s);
							double[] vals = statMan.getAvgFieldValue(p.getPrototypeID(),
									((String)agentFieldsBoxes.get(s).getSelectedItem()));
							//TODO temporary solution to demonstrate compatibility
							//TODO this loop displays nothing for a basic simulation
							System.out.println("Average field value over time: ");
							for (int i = 0; i < vals.length; i++) {
								System.out.println(i + ":  " + vals[i]);
							}
						}
						else {
							Prototype p = sm.getFacade().getPrototype((String)lifeEntityBox.getSelectedItem());
							//TODO temporary solution to demonstrate compatibility
							//getAvgLifespan returns NaN (not a number; 0 / 0 ?)
							System.out.println("Average lifespan: " + 
									statMan.getAvgLifespan(p.getPrototypeID()));
						}
					}
				}
				);
		return displayButton;
	}
	
	private JButton makeFinishButton(){
		JButton finishButton = new JButton("Finish");
		finishButton.setPreferredSize(new Dimension(150, 70));
		finishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sm.update(sm.getScreen("Edit Simulation")); 
			}
		});
		return finishButton;
	}
	
	private JComboBox makeFieldEntityBox(String[] entities){
		JComboBox box = new JComboBox(entities);
		box.addItemListener(
				new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						fieldCard.remove(1);
						fieldCard.add(agentFieldsBoxes.get(e.getItem()));
						validate();
						repaint();
					}
				}
				);
		return box;
	}
	
	private static JComboBox makeCardSelector(final String[] boxItems, final JPanel dataPanel){
		JComboBox selector = new JComboBox(boxItems);
		selector.addItemListener(
				new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						CardLayout cl = (CardLayout)dataPanel.getLayout();
						cl.show(dataPanel, (String)e.getItem());
					}
				}
				);
		return selector;
	}

	private static JPanel makePopulationCard(){
		JPanel popCard = new JPanel();
		popCard.setLayout(new BoxLayout(popCard, BoxLayout.X_AXIS));
		return popCard;
	}
	
	private static JPanel makeFieldCard(){
		JPanel fieldCard = new JPanel();
		fieldCard.setLayout(new BoxLayout(fieldCard, BoxLayout.X_AXIS));
		return fieldCard;
	}
	
	private static JPanel makeLifespanCard(){
		JPanel lifespanCard = new JPanel();
		lifespanCard.setLayout(new BoxLayout(lifespanCard, BoxLayout.X_AXIS));
		return lifespanCard;
	}
	
	//TODO finish this
	@Override
	public void load() {
		entities = new String[sm.getFacade().prototypeNames().size()];
		popEntityBox.removeAllItems();
		fieldEntityBox.removeAllItems();
		lifeEntityBox.removeAllItems();
		agentFieldsBoxes.clear();
		int i = 0;
		for (String s : sm.getFacade().prototypeNames()) {
			entities[i++] = s;
			agentFields = sm.getFacade().getPrototype(s).getCustomFieldMap().keySet().toArray(agentFields);
			agentFieldsBoxes.put(s, new JComboBox(agentFields));
			popEntityBox.addItem(s);
			fieldEntityBox.addItem(s);
			lifeEntityBox.addItem(s);
		}


	}

}
