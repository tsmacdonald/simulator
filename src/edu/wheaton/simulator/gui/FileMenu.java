package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import edu.wheaton.simulator.gui.screen.Screen;
import edu.wheaton.simulator.gui.screen.ViewSimScreen;

public class FileMenu extends JMenu {

	private static final long serialVersionUID = -2839513939759498799L;

	private JMenuItem newSim;
	private JMenuItem saveSim;
	private JMenuItem loadSim;
	private JMenuItem exit;
	
	public FileMenu() {
		super("File");

		getPopupMenu().setBorder(
				BorderFactory.createLineBorder(Color.black));

		newSim = Gui.makeMenuItem("New Simulation", 
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						SimulatorFacade gm = SimulatorFacade.getInstance();
						gm.load("Untitled Simulation", 10, 10);
						gm.updateGuiManager("Untitled Simulation", 10, 10);
						gm.setStepLimit(1000);
						Screen upload = Gui.getScreenManager().getScreen("View Simulation");
						((ViewSimScreen)upload).init();
						gm.setStarted(false);
						Gui.getScreenManager().load(upload);
					}
			
		});

		saveSim = Gui.makeMenuItem("Save Simulation", 
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//String fileName = JOptionPane.showInputDialog("Please enter file name: ");
				SimulatorFacade.getInstance().save();
			}

		}
				);
		
		loadSim = Gui.makeMenuItem("Load Simulation", 
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SimulatorFacade.getInstance().load();
			}
		}
				);

		exit = Gui.makeMenuItem("Exit",new ActionListener(){ 
			@Override
			public void actionPerformed(ActionEvent e) {
				SimulatorFacade.getInstance().setRunning(false);
				System.exit(0);
			}
		});
		
		add(newSim);
		add(saveSim);
		add(loadSim);
		add(exit);
	}
	
	public void setNewSim(boolean enable){
		newSim.setEnabled(enable);
	}
	
	public void setSaveSim(boolean enable){
		saveSim.setEnabled(enable);
	}
}
