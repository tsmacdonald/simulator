package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.wheaton.simulator.gui.screen.EditEntityScreen;
import edu.wheaton.simulator.gui.screen.EditFieldScreen;
import edu.wheaton.simulator.gui.screen.EntityScreen;
import edu.wheaton.simulator.gui.screen.FieldScreen;
import edu.wheaton.simulator.gui.screen.NewSimulationScreen;
import edu.wheaton.simulator.gui.screen.SetupScreen;
import edu.wheaton.simulator.gui.screen.SpawningScreen;
import edu.wheaton.simulator.gui.screen.StatDisplayScreen;
import edu.wheaton.simulator.gui.screen.TitleScreen;
import edu.wheaton.simulator.gui.screen.ViewSimScreen;
import edu.wheaton.simulator.simulation.Simulator;
import edu.wheaton.simulator.simulation.end.SimulationEnder;
import edu.wheaton.simulator.statistics.StatisticsManager;

public class SimulatorGuiManager {
	
	private ScreenManager sm;
	
	private JPanel[][] grid;
	
	private SimulationEnder se;
	
	private StatisticsManager statMan;
	
	private Simulator facade;
	
	private boolean simulationIsRunning;
	
	private ArrayList<SpawnCondition> spawnConditions;
	
	//for determining when components should be disabled while running a sim.
	private boolean hasStarted;

	public SimulatorGuiManager(Display d) {
		spawnConditions = new ArrayList<SpawnCondition>();
		
		sm = new ScreenManager(d);
		sm.putScreen("Title", new TitleScreen(this));
		sm.putScreen("New Simulation", new NewSimulationScreen(this));
		sm.putScreen("Fields", new FieldScreen(this));
		sm.putScreen("Edit Fields", new EditFieldScreen(this));
		sm.putScreen("Entities", new EntityScreen(this));
		sm.putScreen("Edit Entities", new EditEntityScreen(this));
		sm.putScreen("Spawning", new SpawningScreen(this));
		sm.putScreen("View Simulation", new ViewSimScreen(this));
		sm.putScreen("Statistics", new StatDisplayScreen(this));
		sm.putScreen("Grid Setup", new SetupScreen(this));
		
		sm.getDisplay().setJMenuBar(makeMenuBar());
		se = new SimulationEnder();
		statMan = StatisticsManager.getInstance();
	}

	public SimulatorGuiManager(){
		this(new Display());
	}
	
	public ScreenManager getScreenManager(){
		return sm;
	}
	
	public void setGrid(JPanel[][] grid){
		this.grid = grid;
	}
	
	public JPanel[][] getGrid(){
		return grid;
	}

	 
	public void setFacade(int x, int y) {
		facade = new Simulator("name",x, y);
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
		return getNameOfSim();
	}
	
	public static int getGUIheight(){
		return getGridHeight();
	}
	
	public static int getGUIwidth(){
		return getGridWidth();
	}

	 
	public static void updateGUIManager(String nos, int width, int height){
	
		setNameOfSim(nos);
		setGridWidth(width);
		setGridHeight(height);

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
	
	private JMenuBar makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = makeFileMenu(this);
		JMenu editMenu = makeEditMenu(sm);
		JMenu helpMenu = makeHelpMenu(sm);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	private static JMenu makeFileMenu(final SimulatorGuiManager guiManager) {
		JMenu menu = Gui.makeMenu("File");
		
		menu.add(Gui.makeMenuItem("New Simulation", 
				new GeneralButtonListener("New Simulation",guiManager.sm)));
		
		menu.add(Gui.makeMenuItem("Exit",new ActionListener(){
			 
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.setRunning(false);
				System.exit(0);
			}
		}));
		
		return menu;
	}
	
	private static JMenu makeEditMenu(final ScreenManager sm) {
		JMenu menu = Gui.makeMenu("Edit");
		
		menu.add(Gui.makeMenuItem("Edit Entities", 
				new GeneralButtonListener("Entities",sm)));

		menu.add(Gui.makeMenuItem("Edit Global Fields", 
				new GeneralButtonListener("Fields",sm)));
		
		return menu;
	}
	
	private static JMenu makeHelpMenu(final ScreenManager sm) {
		JMenu menu = Gui.makeMenu("Help");
		
		menu.add(Gui.makeMenuItem("About",new ActionListener(){
			 
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(sm.getDisplay(),
					    "Wheaton College. Software Development 2013.",
					    "About",
					    JOptionPane.PLAIN_MESSAGE);
			}
		}));
		
		menu.add(Gui.makeMenuItem("Help Contents",new ActionListener(){
			 
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(sm.getDisplay(),
					    "Wheaton College. Software Development 2013.\n Help Contents",
					    "Help Contents",
					    JOptionPane.PLAIN_MESSAGE);
			}
		}));
		
		return menu;
	}
	
	private static String nameOfSimulation;
	private static int gridHeight = 0;
	private static int gridWidth = 0;
	
	
	public static String getNameOfSim(){
		return nameOfSimulation;
	}
	
	public static void setNameOfSim(String nos){
		nameOfSimulation = nos;
	}
	
	public static int getGridHeight(){
		return gridHeight;
	}
	
	public static void setGridHeight(int gh){
			gridHeight = gh;
	}
	
	public static int getGridWidth(){
		return gridWidth;
	}
	
	public static void setGridWidth(int gw){
			gridWidth = gw;
	}
}
