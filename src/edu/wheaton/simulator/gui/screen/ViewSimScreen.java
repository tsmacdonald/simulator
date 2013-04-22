/**
 * ViewSimScreen
 * 
 * Class representing the screen that displays the grid as
 * the simulation runs.
 * 
 * @author Willy McHie and Ian Walling
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wheaton.simulator.gui.FileMenu;
import edu.wheaton.simulator.gui.GeneralButtonListener;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorFacade;

public class ViewSimScreen extends Screen {

	private static final long serialVersionUID = -6872689283286800861L;

	private GridBagConstraints c;

	private boolean canSpawn;

	private final EntityScreen entitiesScreen;

	private final Screen layerScreen;

	private final Screen globalFieldScreen;

	private final Screen optionsScreen;

	private final JTabbedPane tabs;
	
	private JButton startButton;

	public ViewSimScreen(final SimulatorFacade gm) {
		super(gm);
		this.setVisible(false);
		setSpawn(false);
		this.setLayout(new GridBagLayout());
		((GridBagLayout) this.getLayout()).columnWeights = new double[] { 0, 1 };

		tabs = new JTabbedPane();
		entitiesScreen = new EntityScreen(gm);
		layerScreen = new LayerScreen(gm);
		globalFieldScreen = new FieldScreen(gm);
		optionsScreen = new SetupScreen(gm);
		tabs.addTab("Agent", entitiesScreen);
		tabs.addTab("Layers", layerScreen);
		tabs.addTab("Global Fields", globalFieldScreen);
		tabs.addTab("Options", optionsScreen);
		tabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				if (tabs.getSelectedComponent().toString() == "Agent")
					setSpawn(true);
				else {
					setSpawn(false);
				}
				entitiesScreen.load();
				layerScreen.load();
				globalFieldScreen.load();
				optionsScreen.load();
			}
		});

		gm.getGridPanel().addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent me) {
				int standardSize = Math.min(
						gm.getGridPanel().getWidth() / gm.getGridWidth(),
						gm.getGridPanel().getHeight() / gm.getGridHeight());
				int x = me.getX() / standardSize;
				int y = me.getY() / standardSize;
				if (canSpawn) {
					if (gm.getAgent(x, y) == null) {
						gm.addAgent(entitiesScreen.getList()
								.getSelectedValue().toString(), x, y);
					}

					else {
						gm.removeAgent(x, y);
					}
					gm.getGridPanel().repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.weightx = 0;
		//c.insets = new Insets(5, 5, 5, 5);
		this.add(tabs, c);

		JPanel rightPanel = new JPanel(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 600;
		c.ipady = 600;
		rightPanel.add(gm.getGridPanel(), c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		rightPanel.add(makeButtonPanel(), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		this.add(rightPanel,c);

		this.setVisible(true);
	}

	private JPanel makeButtonPanel() {
		// TODO most of these will become tabs, adding temporarily for
		// navigation purposes
		ScreenManager sm = getScreenManager();
		startButton = makeStartButton();
		JPanel buttonPanel = Gui.makePanel((LayoutManager) null, MaxSize.NULL, PrefSize.NULL, startButton, Gui.makeButton(
				"Statistics", null,
				new GeneralButtonListener("Statistics", sm)));
		return buttonPanel;
	}

	public void setSpawn(boolean canSpawn) {
		this.canSpawn = canSpawn;
	}
	
	public void init(){
		startButton.setText("Start");
		initTabs();
	}

	private JButton makeStartButton() {
		final JButton b = Gui.makeButton("Start", null, null);
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimulatorFacade gm = getGuiManager();
				if(gm.isRunning()){
					getGuiManager().pause();
					canSpawn = true;
					b.setText("Resume");
				} 
				else if(gm.hasStarted() && gm.isRunning()==false){
					canSpawn = false;
					gm.getGridPanel().repaint();
					gm.start();
					b.setText("Pause");
				}
				
				else if (gm.hasStarted()==false){
					canSpawn = false;
					gm.getGridPanel().repaint();
					gm.start();
					b.setText("Pause");
				}
			}
		});
		return b;
	}
	
	public void initTabs(){
		tabs.setSelectedComponent(optionsScreen);
	}

	@Override
	public void load() {
		FileMenu fm = Gui.getFileMenu();
		fm.setNewSim(true);
		fm.setSaveSim(true);
		
		this.setVisible(false);
		entitiesScreen.load();
		layerScreen.load();
		globalFieldScreen.load();
		optionsScreen.load();
		if(tabs.getSelectedComponent()==null)
			initTabs();
		validate();
		this.setVisible(true);
	}
}
