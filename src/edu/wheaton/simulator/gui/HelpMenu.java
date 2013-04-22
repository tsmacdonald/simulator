package edu.wheaton.simulator.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class HelpMenu extends JMenu {

	private static final long serialVersionUID = 4241879401458504042L;

	private JMenuItem about;
	private JMenuItem helpContents;
	
	public HelpMenu(){
		super("Help");

		getPopupMenu().setBorder(
				BorderFactory.createLineBorder(Color.black));

		about = Gui.makeMenuItem("About",new ActionListener(){ 
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Gui.getDisplay(),
						"Wheaton College. Software Development 2013.",
						"About",JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		helpContents = Gui.makeMenuItem("Help Contents",new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String htmlFilePath = "helpdocument/UniSIMHelp.html";
				File htmlFile = new File(htmlFilePath);
				try {
					Desktop.getDesktop().browse(htmlFile.toURI());
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
			}
		});
		
		add(about);
		add(helpContents);
	}
}
