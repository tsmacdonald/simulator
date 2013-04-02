package edu.wheaton.simulator.test.gui;

import javax.swing.JButton;
import javax.swing.JTextField;
import junit.framework.Assert;
import junit.framework.TestCase;
import edu.wheaton.simulator.gui.Display;
import edu.wheaton.simulator.gui.NewSimScreenFinishListener;

public class TestNSSFinishListener extends TestCase {

	private NewSimScreenFinishListener nssfl;
	private MockManager mm;
	private MockScreen ms;
	private Display display;
	private JButton finishButton;
	
	@Override
	public void setUp() {
		display = new Display();
		ms = new MockScreen("Edit Simulation", mm);
		mm = new MockManager(display, ms.getName(), ms);
		nssfl = new NewSimScreenFinishListener(new JTextField("Name"), new JTextField("10"), 
											   new JTextField("10"), mm);
		finishButton = new JButton();
		finishButton.addActionListener(nssfl);
	 	finishButton.doClick();		
	}
	public void testNameTextField(){
		Assert.assertEquals("Name", nssfl.getName());
	}
	public void testWidthTextField(){
		Assert.assertEquals(10, nssfl.getWidth());
	}
	public void testHeightField(){
		Assert.assertEquals(10, nssfl.getHeight());
	}
	public void testSetFacade(){
		Assert.assertTrue("setFacade() should be called", mm.getFacadeCalled());
	}
	public void testUpdateGUIManager(){
		Assert.assertTrue("updateGUImanager should be called", mm.getUpdateGUIManagerCalled());
	}
	public void testGetScreen(){
		Assert.assertTrue("Should return the \"Edit Simulation\" screen", 
					      mm.getScreen("Edit Simulation") instanceof MockScreen);
	}
	public void testUpdate(){
		Assert.assertTrue("The content should be the mock screen", 
						  display.getContentPane() instanceof MockScreen);
	}
	public void testLoad(){
		Assert.assertTrue("The screen's load method should be called", ms.getLoad());
	}
	
}
