package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class FieldAddListener implements ActionListener{

	private Manager sm;
	private JComboBox xPos;
	private JComboBox yPos;
	
	public FieldAddListener(JComboBox xPos, JComboBox yPos,
				  			Manager sm){
		this.xPos = xPos;
		this.yPos = yPos;
		this.sm = sm;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean editing = FieldScreen.getEditing();
		if(editing)
			editing = false;
		((EditFieldScreen) (sm.getScreen("Edit Fields"))).load(
				sm.getFacade().getGrid().getSlot(
						xPos.getSelectedIndex(), 
						yPos.getSelectedIndex()
						)
				);
		sm.update(sm.getScreen("Edit Fields"));
	}
}
