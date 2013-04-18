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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.wheaton.simulator.gui.BoxLayoutAxis;
import edu.wheaton.simulator.gui.Gui;
import edu.wheaton.simulator.gui.GuiList;
import edu.wheaton.simulator.gui.HorizontalAlignment;
import edu.wheaton.simulator.gui.MaxSize;
import edu.wheaton.simulator.gui.PrefSize;
import edu.wheaton.simulator.gui.ScreenManager;
import edu.wheaton.simulator.gui.SimulatorGuiManager;

public class FieldScreen extends Screen {

	private static final long serialVersionUID = -4286820591194407735L;

	private GuiList fields;

	private JButton delete;

	private JButton add;

	private JButton edit;

	private static boolean editing;

	//TODO prevent clicking edit when no object is selected 

	public FieldScreen(final SimulatorGuiManager gm) {
		super(gm);
		editing = false;
		this.setLayout(new BorderLayout());
		
		fields = new GuiList();
		fields.addListSelectionListener(new ListListener());

		
		JPanel panel = Gui.makePanel((LayoutManager)null , MaxSize.NULL, new PrefSize(450,550));
		panel.add(fields);
		
		delete = Gui.makeButton("Delete",null,new DeleteListener(fields));
		add = Gui.makeButton("Add",null,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae){
				FieldScreen.setEditing(false);
				ScreenManager sm = getScreenManager();
				Screen screen = sm.getScreen("Edit Fields");
				screen.load();
				sm.update(screen);
			}
		});
		edit = Gui.makeButton("Edit",null,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				ScreenManager sm = getScreenManager();
				FieldScreen.setEditing(true);
				EditFieldScreen screen = (EditFieldScreen) sm.getScreen("Edit Fields");
				screen.load((String)fields.getSelectedValue());
				sm.update(screen);
			}
		});
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(add);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(edit);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(delete);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel mainPanel = Gui.makePanel(BoxLayoutAxis.Y_AXIS,null,null);
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(panel);
		mainPanel.add(buttonPanel);
		
		this.add(
			Gui.makeLabel("Global Fields",new PrefSize(300, 100),HorizontalAlignment.CENTER), 
			BorderLayout.NORTH
		);
		this.add(mainPanel, BorderLayout.CENTER);
	}

	public void reset() {
		fields.clearItems();
	}
	@Override
	public void load() {
		reset();
		Object[] fieldsA = getGuiManager().getSimGridFieldMap().keySet().toArray();
		for(Object s: fieldsA){
			fields.addItem(s.toString());
		}
		edit.setEnabled(false);
		delete.setEnabled(false);

	}

	private class ListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			edit.setEnabled(getGuiManager().hasSimStarted() ? false : true); 
			delete.setEnabled(getGuiManager().hasSimStarted() ? false : true); 
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
			getGuiManager().removeSimGlobalField((String)fields.getSelectedValue());
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