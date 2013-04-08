package edu.wheaton.simulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;

public class FieldEditListener implements ActionListener {

	private Manager sm;
	private JList fields;
	
	public FieldEditListener(Manager sm, JList fields){
		this.sm = sm;
		this.fields = fields;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		FieldScreen.setEditing(true);
		((EditFieldScreen) sm.getScreen("Edit Fields")).load((String) fields.getSelectedValue());
		sm.update(sm.getScreen("Edit Fields"));
	}
}
