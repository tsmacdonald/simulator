package edu.wheaton.simulator.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 3819447300809632744L;

	private FileMenu fileMenu;
	private HelpMenu helpMenu;
	
	public MenuBar() {
		fileMenu = new FileMenu();
		helpMenu = new HelpMenu();

		add(fileMenu);
		add(helpMenu);
	}

}
