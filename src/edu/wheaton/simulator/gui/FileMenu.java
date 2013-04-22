package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

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
				new GeneralButtonListener("New Simulation",ScreenManager.getInstance()));

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
}
