package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public final class Gui {
	
	private Gui() {
	}
	
	public static void init(){
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		getDisplay().setJMenuBar(new MenuBar());
	}
	
	public static Display getDisplay(){
		return Display.getInstance();
	}
	
	public static ScreenManager getScreenManager(){
		return ScreenManager.getInstance();
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
	
	public static JLabel makeLabel(String name, MinSize size){
		JLabel label = new JLabel(name);
		if(size!=null)
			label.setMinimumSize(size);
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
		if(components!=null)
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
		if(components != null)
		for(Component c : components)
			panel.add(c);
		return panel;
	}
	
	public static JPanel makePanel(Component... components){
		JPanel panel = new JPanel();
		if(components != null)
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
		JPanel panel = Gui.makePanel(new GridBagLayout(), MaxSize.NULL, null);
		GridBagConstraints constraints = new GridBagConstraints();
		panel.add(cc,constraints);
		return panel;
	}
	
	public static JMenuItem makeMenuItem(String name, ActionListener al){
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(al);
		return menuItem;
	}

//	private static JMenu makeEditMenu() {
//		JMenu menu = Gui.makeMenu("Edit");
//
//		menu.add(Gui.makeMenuItem("Edit Global Fields", 
//				new GeneralButtonListener("Fields",ScreenManager.getInstance())));
//
//		return menu;
//	}
}
