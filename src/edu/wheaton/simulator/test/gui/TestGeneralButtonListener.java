package edu.wheaton.simulator.test.gui;

import javax.swing.JButton;

import junit.framework.Assert;
import junit.framework.TestCase;
import edu.wheaton.simulator.gui.Display;
import edu.wheaton.simulator.gui.GeneralButtonListener;

public class TestGeneralButtonListener extends TestCase {

	private GeneralButtonListener gbl;
	private MockScreen ms;
	private MockManager mm;
	private Display display;
	
	public void setUp(){
		display = new Display();
		ms = new MockScreen("Mock Screen", mm);
		mm = new MockManager(display, ms.getName(), ms);
		gbl = new GeneralButtonListener(ms.getName(), mm);
	}
	public void testScreenNull(){
		Assert.assertNotNull(ms);
	}
	public void testGetScreen(){
		Assert.assertTrue(mm.getScreen("Mock Screen") instanceof MockScreen);
	}
	public void testUpdate(){
		JButton mockButton = new JButton();
		mockButton.addActionListener(gbl);
		mockButton.doClick();
		Assert.assertTrue(display.getContentPane() instanceof MockScreen);
	}
	
	public void testLoad(){
		JButton mockButton = new JButton();
		mockButton.addActionListener(gbl);
		mockButton.doClick();
		Assert.assertTrue(ms.getLoad());
	}

}
