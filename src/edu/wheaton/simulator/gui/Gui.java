package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class Gui {

	
	private Gui() {
		// Auto-generated constructor stub
	}
	
	public static JButton makeButton(String name, PrefSize prefSize, ActionListener al){
		JButton b = new JButton(name);
		if(al != null)
			b.addActionListener(al);
		if(prefSize != null)
			b.setPreferredSize(prefSize);
		return b;
	}
	
	public static JLabel makeLabel(String name, MaxSize size, HorizontalAlignment alignment){
		JLabel label = new JLabel(name);
		if(alignment!=null)
			label.setHorizontalAlignment(alignment.code);
		if(size != null)
			label.setMaximumSize(size);
		return label;
	}
	
	public static JLabel makeLabel(String name, PrefSize size, HorizontalAlignment alignment){
		JLabel label = new JLabel(name);
		if(alignment!=null)
			label.setHorizontalAlignment(alignment.code);
		if(size!=null)
			label.setPreferredSize(size);
		return label;
	}
	
	public static JPanel makePanel(LayoutManager layout, MaxSize maxSize, PrefSize prefSize, Component... components){
		JPanel panel = new JPanel();
		
		if(layout != null)
			panel.setLayout(layout);
		if(maxSize != null)
			panel.setMaximumSize(maxSize);
		if(prefSize != null)
			panel.setPreferredSize(prefSize);
		for(Component c : components)
			panel.add(c);
		return panel;
	}
	
	public static JPanel makePanel(BoxLayoutAxis axis, MaxSize maxSize, PrefSize prefSize, Component... components){
		JPanel panel = new JPanel();
		
		if(axis != null)
			panel.setLayout(new BoxLayout(panel, axis.code));
		if(maxSize != null)
			panel.setMaximumSize(maxSize);
		if(prefSize != null)
			panel.setPreferredSize(prefSize);
		for(Component c : components)
			panel.add(c);
		return panel;
	}
	
	public static JPanel makePanel(Component... components){
		JPanel panel = new JPanel();
		for(Component c : components)
			panel.add(c);
		return panel;
	}
	
	public static JComboBox makeComboBox(String[] items, MaxSize size){
		JComboBox cb;
		if(items != null)
			cb = new JComboBox(items);
		else
			cb = new JComboBox();
		if(size != null)
			cb.setMaximumSize(size);
		return cb;	
	}
	
	public static JTextField makeTextField(String text, int columns, MaxSize maxSize, MinSize minSize){
		JTextField tf;
		if(text != null)
			tf = new JTextField(text,columns);
		else
			tf = new JTextField(columns);
		if(maxSize!=null)
			tf.setMaximumSize(maxSize);
		if(minSize!=null)
			tf.setMinimumSize(minSize);
		return tf;
	}
	
	public static JColorChooser makeColorChooser(){
		JColorChooser cc = new JColorChooser();
		cc.setPreviewPanel(new JPanel());
		return cc;
	}
	
	public static JPanel makeColorChooserPanel(JColorChooser cc){
		JPanel panel = Gui.makePanel(new GridBagLayout(), new MaxSize(550,140), null);
		GridBagConstraints constraints = new GridBagConstraints();
		panel.add(cc,constraints);
		return panel;
	}
	
	public static JMenu makeMenu(String name){
		JMenu menu = new JMenu(name);
		menu.setOpaque(true);
		menu.setForeground(Color.white);
		menu.setBackground(Color.darkGray);
		
		menu.getPopupMenu().setBorder(
			BorderFactory.createLineBorder(Color.black));
		
		return menu;
	}
	
	public static JMenuItem makeMenuItem(String name, ActionListener al){
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(al);
		menuItem.setOpaque(true);
		menuItem.setBackground(Color.gray);
		menuItem.setForeground(Color.white);
		return menuItem;
	}
}
