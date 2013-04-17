package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalBorders;

import edu.wheaton.simulator.simulation.Simulator;
import edu.wheaton.simulator.simulation.end.SimulationEnder;
import edu.wheaton.simulator.statistics.StatisticsManager;

public class ScreenManager implements Manager{

	private HashMap<String, Screen> screens;

	private Display d;
	
	private JPanel[][] grid;
	
	private SimulationEnder se;
	
	private StatisticsManager statMan;
	
	private Simulator facade;
	
	private boolean simulationIsRunning;
	
	private ArrayList<SpawnCondition> spawnConditions;
	
	//for determining when components should be disabled while running a sim.
	private boolean hasStarted;

	public ScreenManager(Display d) {
		spawnConditions = new ArrayList<SpawnCondition>();
		screens = new HashMap<String, Screen>();
		this.d = d;
		this.d.setJMenuBar(makeMenuBar());
		se = new SimulationEnder();
		statMan = new StatisticsManager();
		screens.put("Title", new TitleScreen(this));
		screens.put("New Simulation", new NewSimulationScreen(this));
		//screens.put("Edit Simulation", new EditSimScreen(this));
		screens.put("Fields", new FieldScreen(this));
		screens.put("Edit Fields", new EditFieldScreen(this));
		screens.put("Entities", new EntityScreen(this));
		screens.put("Edit Entities", new EditEntityScreen(this));
		screens.put("Spawning", new SpawningScreen(this));
		screens.put("View Simulation", new ViewSimScreen(this));
		screens.put("Statistics", new StatDisplayScreen(this));
		screens.put("Grid Setup", new SetupScreen(this));
	}

	public ScreenManager(){
		this(new Display());
	}

	@Override
	public Screen getScreen(String screenName) {
		return screens.get(screenName);
	}
	@Override
	public void update(Screen update) {
		d.updateDisplay(update);
	}
	
	public void setGrid(JPanel[][] grid){
		this.grid = grid;
	}
	
	public JPanel[][] getGrid(){
		return grid;
	}

	@Override
	public void setFacade(int x, int y) {
		facade = new Simulator(x, y);
	}
	
	@Override
	public Simulator getFacade() {
		return facade;
	}
	
	@Override
	public SimulationEnder getEnder() {
		return se;
	}
	
	public StatisticsManager getStatManager(){
		return statMan;
	}
	
	public static String getGUIname(){
		return GUI.getNameOfSim();
	}
	
	public static int getGUIheight(){
		return GUI.getGridHeight();
	}
	
	public static int getGUIwidth(){
		return GUI.getGridWidth();
	}

	@Override
	public void updateGUIManager(String nos, int width, int height){
	
		GUI.setNameOfSim(nos);
		GUI.setGridWidth(width);
		GUI.setGridHeight(height);

	}
	
	public boolean isRunning() {
		return simulationIsRunning;
	}
	
	public void setRunning(boolean b) {
		simulationIsRunning = b;
	}
	
	public void setStarted(boolean b) {
		hasStarted = b;
	}
	
	@Override
	public boolean hasStarted() {
		return hasStarted;
	}

	@Override
	public ArrayList<SpawnCondition> getSpawnConditions() { 
		return spawnConditions; 
	}
	
	@Override
	public void loadScreen(Screen s){
		s.load();
	}
	
	private JMenuBar makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		Border border = BorderFactory.createLineBorder(Color.black);
		
		JMenu fileMenu = makeFileMenu(this);
		fileMenu.getPopupMenu().setBorder(border);
		fileMenu.setOpaque(true);
		fileMenu.setForeground(Color.white);
		fileMenu.setBackground(Color.darkGray);
		
		
		JMenu editMenu = makeEditMenu(this);
		editMenu.getPopupMenu().setBorder(border);
		editMenu.setOpaque(true);
		editMenu.setForeground(Color.white);
		editMenu.setBackground(Color.darkGray);
		
		JMenu helpMenu = makeHelpMenu(this);
		helpMenu.getPopupMenu().setBorder(border);
		helpMenu.setOpaque(true);
		helpMenu.setForeground(Color.white);
		helpMenu.setBackground(Color.darkGray);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	private static JMenu makeFileMenu(final ScreenManager sm) {
		JMenu menu = new JMenu("File");
		
		JMenuItem newSimulation = new JMenuItem("New Simulation");
		newSimulation.addActionListener(new GeneralButtonListener("New Simulation",sm));
		menu.add(newSimulation);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sm.setRunning(false);
				System.exit(0);
			}
		});
		menu.add(exit);
		
		return menu;
	}
	
	private static JMenu makeEditMenu(final ScreenManager sm) {
		JMenu menu = new JMenu("Edit");
		
		JMenuItem editEntities = new JMenuItem("Edit Entities");
		editEntities.addActionListener(new GeneralButtonListener("Entities",sm));
		menu.add(editEntities);
		
		JMenuItem editGlobalFields = new JMenuItem("Edit Global Fields");
		editGlobalFields.addActionListener(new GeneralButtonListener("Fields",sm));
		menu.add(editGlobalFields);
		
		return menu;
	}
	
	private static JMenu makeHelpMenu(final ScreenManager sm) {
		JMenu menu = new JMenu("Help");
		
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(sm.d,
					    "Wheaton College. Software Development 2013.",
					    "About",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		menu.add(about);
		
		return menu;
	}
}
