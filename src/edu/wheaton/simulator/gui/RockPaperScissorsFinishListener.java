package edu.wheaton.simulator.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
	

/**
 * There is a static method in the facade that will create a Rock Paper Scissors simulation.
 * 
 * @author David Emmanuel Pederson
 *
 */
public class RockPaperScissorsFinishListener implements ActionListener {
		
		private JTextField name;
		private JTextField width;
		private JTextField height;
		private Manager sm;
		
		public RockPaperScissorsFinishListener(JTextField name, JTextField width, JTextField height, 
										  Manager sm){
			this.name = name;
			this.width = width;
			this.height = height;
			this.sm = sm;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int heightInt = getHeight();
				int widthInt = getWidth();
				sm.setFacade(widthInt, heightInt);
				sm.updateGUIManager(getName(), widthInt, heightInt);
			} catch(java.lang.NumberFormatException nfe) { 
				System.err.println("Invalid input passed to RockPaperScissorsListener");
			}
			Screen upload = sm.getScreen("View Simulation");
			sm.getFacade().setPriorityUpdate();
			sm.getFacade().initRockPaperScissors();
			sm.getEnder().setStepLimit(1000);
			((ScreenManager)sm).setStarted(false);
			sm.update(upload);
			sm.loadScreen(upload);
		}
		public String getName(){
			return name.getText();
		}
		public int getHeight(){
			return Integer.parseInt(height.getText());
		}
		public int getWidth(){
			return Integer.parseInt(width.getText());
		}
}
