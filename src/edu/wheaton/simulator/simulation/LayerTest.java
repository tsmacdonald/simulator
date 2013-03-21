package edu.wheaton.simulator.simulation;

import static org.junit.Assert.*;

import net.sourceforge.jeval.EvaluationException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wheaton.simulator.datastructure.Field;

import java.awt.Color;

public class LayerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHSBColorConvertion() {
		HSBColor testColor = new HSBColor(Color.magenta);
		System.out.println(testColor);
		
		Assert.assertTrue(testColor.getColor().getRed() == Color.magenta.getRed());
		Assert.assertTrue(testColor.getColor().getGreen() == Color.magenta.getGreen());
		Assert.assertTrue(testColor.getColor().getBlue() == Color.magenta.getBlue());
	}

	@Test
	public void testHSBColorBrightness() {
		HSBColor testColor = new HSBColor(Color.magenta);
		HSBColor lighterColor = new HSBColor(testColor.newBrightness(0.75f));
		HSBColor darkerColor = new HSBColor(testColor.newBrightness(0.25f));
		Assert.assertTrue(lighterColor.getBrightness() > darkerColor.getBrightness());
	}

	@Test
	public void testLayerCreation() throws EvaluationException{
		Layer.getInstance().setFieldName("TestField");
		Layer.getInstance().setColor(Color.red);
		Layer.getInstance().resetMinMax();

		Field max = new Field("TestField", 100);
		Field min = new Field("TestField", 1);
		Field test = new Field("TestField", 25);

		Layer.getInstance().setExtremes(max);
		Layer.getInstance().setExtremes(min);
		Layer.getInstance().setExtremes(test);
		
		Color maxLayerColor = Layer.getInstance().newShade(max);
		Color minLayerColor = Layer.getInstance().newShade(min);
		Color testLayerColor = Layer.getInstance().newShade(test);

		HSBColor maxLayerHSB = new HSBColor(maxLayerColor);
		HSBColor minLayerHSB = new HSBColor(minLayerColor);
		HSBColor testLayerHSB = new HSBColor(testLayerColor);
		
		Assert.assertTrue(testLayerHSB.getBrightness() < maxLayerHSB.getBrightness());
		Assert.assertTrue(testLayerHSB.getBrightness() > minLayerHSB.getBrightness());
	}
}
