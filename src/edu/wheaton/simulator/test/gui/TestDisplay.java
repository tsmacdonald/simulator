package edu.wheaton.simulator.test.gui;

import junit.framework.TestCase;

import org.junit.Assert;
import edu.wheaton.simulator.gui.Display;

/**
 * Tests to see if the updateDisplay method sets the correct content
 * pane, and if that content pane is set to visible.
 * @author calebdemoss
 *
 */
public class TestDisplay extends TestCase{
	/**
	 * Reference to the display
	 */
	private Display display;
	/**
	 * A mock object representing the screen manager
	 */
	private MockManager mm;
	/**
	 * A mock screen
	 */
	private MockScreen ms;
	/**
	 * Initial setup  
	 */
	public void setUp(){
		display = new Display();
		ms = new MockScreen("Mock Screen", mm);
		mm = new MockManager(display, ms.getName(), ms);
		mm.update(ms);
	}
	/**
	 * Check the content pane
	 */
	public void testContentPane() {
		Assert.assertEquals(display.getContentPane(), ms);
	}
	/**
	 * Test Visibility
	 */
	public void testIsVisible(){
		Assert.assertEquals(true, display.isVisible());
	}
	


}
