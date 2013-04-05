package edu.wheaton.simulator.test.gui;

import javax.swing.JButton;
import junit.framework.TestCase;

import org.junit.Assert;

import edu.wheaton.simulator.gui.Display;
import edu.wheaton.simulator.gui.FieldAddListener;
import edu.wheaton.simulator.gui.FieldScreen;

public class TestFieldAddListener extends TestCase{

	private Display display;
	private MockManager mm;
	private MockScreen ms1;
	private FieldAddListener fal;
	private JButton add;
	
	@Override
	public void setUp(){
		display = new Display();
		ms1 = new MockScreen("Edit Fields", mm);
		mm = new MockManager(display, ms1.getName(), ms1);
		fal = new FieldAddListener(mm);
		add = new JButton();
		add.addActionListener(fal);
		add.doClick();
	}
	
	public void testEditing(){
		Assert.assertFalse("Editing should be set to false", FieldScreen.getEditing());
	}
	public void testGetScreen(){
		Assert.assertEquals("Edit Fields returned from get screen", 0, ((MockScreen) mm.getScreen("Edit Fields")).compareTo(ms1));
	}
	public void testLoad(){
		Assert.assertTrue("Load method is called", ms1.getLoad());
	}
	public void testUpdate(){
		Assert.assertEquals("Content pane set to ms1", 0, ms1.compareTo((MockScreen) display.getContentPane()));
	}
}
