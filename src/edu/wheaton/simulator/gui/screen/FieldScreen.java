/**
 * FieldScreen
 * 
 * Class representing the screen that manages all user interactions 
 * pertaining to local and global fields.
 * 
 * @author Willy McHie
 * Wheaton College, CSCI 335, Spring 2013
 */

package edu.wheaton.simulator.gui.screen;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.GuiList;
import edu.wheaton.simulator.gui.HorizontalAlignment;
import edu.wheaton.simulator.gui.MinSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorFacade;

public class FieldScreen extends Screen {

	private static final long serialVersionUID = -4286820591194407735L;

	private GuiList fields;

	private JButton delete;

	private JButton add;

	private JButton edit;

	private static boolean editing;

	//TODO prevent clicking edit when no object is selected 

	public FieldScreen(final SimulatorFacade gm) {
		super(gm);
		editing = false;
		this.setLayout(new GridBagLayout());
		
		fields = new GuiList();
		fields.addListSelectionListener(new ListListener());
		
		delete = Gui.makeButton("Delete",null,new DeleteListener(fields));
		add = Gui.makeButton("Add",null,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				FieldScreen.setEditing(false);
				ScreenManager sm = getScreenManager();
				JDialog dialogWindow = new JDialog();
				Screen screen = new EditFieldScreen(gm,dialogWindow);
				dialogWindow.add(screen);
				screen.load();
				dialogWindow.setLocationRelativeTo(null);
				dialogWindow.setTitle("New Field");
				dialogWindow.setMinimumSize(new MinSize(225,100));
				dialogWindow.setVisible(true);
			}
		});
		edit = Gui.makeButton("Edit",null,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				ScreenManager sm = getScreenManager();
				FieldScreen.setEditing(true);
				JDialog dialogWindow = new JDialog();
				EditFieldScreen screen = new EditFieldScreen(gm,dialogWindow);
				dialogWindow.add(screen);
				screen.load((String)fields.getSelectedValue());
				dialogWindow.setLocationRelativeTo(null);
				dialogWindow.setTitle("Edit Field");
				dialogWindow.setMinimumSize(new MinSize(225,100));
				dialogWindow.setVisible(true);
			}
		});
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		this.add(add,c);
		
		c.gridx = 1;
		this.add(edit, c);
		
		c.gridx = 2;
		this.add(delete, c);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.insets = new Insets(5,0,10,0);
		this.add(
			Gui.makeLabel("Global Fields",PrefSize.NULL,HorizontalAlignment.CENTER), 
			c);
		
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		this.add(fields, c);
	}

	public void reset() {
		fields.clearItems();
	}
	@Override
	public void load() {
		reset();
		Object[] fieldsA = getGuiManager().getGlobalFieldMap().keySet().toArray();
		for(Object s: fieldsA){
			fields.addItem(s.toString());
		}
		edit.setEnabled(false);
		delete.setEnabled(false);

	}

	private class ListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			edit.setEnabled(getGuiManager().hasStarted() ? false : true); 
			delete.setEnabled(getGuiManager().hasStarted() ? false : true); 
		}
	}

	private class DeleteListener implements ActionListener {

		private GuiList fields;

		public DeleteListener(GuiList fields){
			this.fields = fields;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			int index = fields.getSelectedIndex();
			getGuiManager().removeGlobalField((String)fields.getSelectedValue());
			fields.removeItem(index);
			int size = fields.getNumItems();
			if(size == 0) {
				edit.setEnabled(false);
				delete.setEnabled(false);	
			}
			if(index == size)
				index--;
			fields.setSelectedIndex(index);
			fields.ensureIndexIsVisible(index);
		}	
	}

	public static boolean getEditing(){
		return editing;
	}
	public static void setEditing(boolean e){
		editing = e;
	}
}