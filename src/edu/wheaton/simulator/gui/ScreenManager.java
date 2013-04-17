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

public class ScreenManager {

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

	 
	public Screen getScreen(String screenName) {
		return screens.get(screenName);
	}
	 
	public void update(Screen update) {
		d.updateDisplay(update);
	}
	
	public void setGrid(JPanel[][] grid){
		this.grid = grid;
	}
	
	public JPanel[][] getGrid(){
		return grid;
	}

	 
	public void setFacade(int x, int y) {
		facade = new Simulator(x, y);
	}
	
	 
	public Simulator getFacade() {
		return facade;
	}
	
	 
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
	
	 
	public boolean hasStarted() {
		return hasStarted;
	}

	 
	public ArrayList<SpawnCondition> getSpawnConditions() { 
		return spawnConditions; 
	}
	
	 
	public void loadScreen(Screen s){
		s.load();
	}
	
	private JMenuBar makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = makeFileMenu(this);
		JMenu editMenu = makeEditMenu(this);
		JMenu helpMenu = makeHelpMenu(this);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	private static JMenu makeFileMenu(final ScreenManager sm) {
		JMenu menu = GuiUtility.makeMenu("File");
		
		menu.add(GuiUtility.makeMenuItem("New Simulation", 
				new GeneralButtonListener("New Simulation",sm)));
		
		menu.add(GuiUtility.makeMenuItem("Exit",new ActionListener(){
			 
			public void actionPerformed(ActionEvent e) {
				sm.setRunning(false);
				System.exit(0);
			}
		}));
		
		return menu;
	}
	
	private static JMenu makeEditMenu(final ScreenManager sm) {
		JMenu menu = GuiUtility.makeMenu("Edit");
		
		menu.add(GuiUtility.makeMenuItem("Edit Entities", 
				new GeneralButtonListener("Entities",sm)));

		menu.add(GuiUtility.makeMenuItem("Edit Global Fields", 
				new GeneralButtonListener("Fields",sm)));
		
		return menu;
	}
	
	private static JMenu makeHelpMenu(final ScreenManager sm) {
		JMenu menu = GuiUtility.makeMenu("Help");
		
		menu.add(GuiUtility.makeMenuItem("About",new ActionListener(){
			 
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(sm.d,
					    "Wheaton College. Software Development 2013.",
					    "About",
					    JOptionPane.PLAIN_MESSAGE);
			}
		}));
		
		menu.add(GuiUtility.makeMenuItem("Help Contents",new ActionListener(){
			 
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(sm.d,
					    "Wheaton College. Software Development 2013.\n Help Contents",
					    "Help Contents",
					    JOptionPane.PLAIN_MESSAGE);
			}
		}));
		
		return menu;
	}
}
