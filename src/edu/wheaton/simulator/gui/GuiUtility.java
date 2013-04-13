package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class GuiUtility {

	//private static final Component horizontalGlue = Box.createHorizontalGlue();
	//private static final Component verticalGlue = Box.createVerticalGlue();
	
	private GuiUtility() {
		// Auto-generated constructor stub
	}
	
	public static final Component createHorizontalGlue(){
		return Box.createHorizontalGlue();
	}
	
	public static final Component createVerticalGlue(){
		return Box.createVerticalGlue();
	}
	
	public static JButton makeButton(String name, ActionListener al){
		JButton b = new JButton(name);
		if(al != null)
			b.addActionListener(al);
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
	
	public static JPanel makeBorderPanel(BorderLayout layout){
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		return panel;
	}
	
	public static JPanel makeBoxPanel(int axis){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, axis));
		return panel;
	}

}
