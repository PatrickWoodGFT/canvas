package com.gft.pw.canvas;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.gft.pw.canvas.exception.CanvasConfigurationException;
import com.gft.pw.canvas.exception.EaselException;
import com.gft.pw.canvas.exception.LineConfigurationException;
import com.gft.pw.canvas.exception.RectConfigurationException;
import com.gft.pw.canvas.model.Canvas;
import com.gft.pw.canvas.model.CanvasConfig;
import com.gft.pw.canvas.model.Line;
import com.gft.pw.canvas.model.Rect;

/**
 * Canvas tests.
 * 
 * @author pkwd
 */
public class CanvasTest {

	@Test
	public void testCanvasRegExp() throws CanvasConfigurationException {
		Stream.of("", "xyz", "c 1 2", "C 1", "C one 2", "C -10 6").forEach(badConfigStr ->
			Assertions.assertThrows(CanvasConfigurationException.class, () -> {
				new CanvasConfig(badConfigStr);
			}, "CanvasConfigurationException expected for " + badConfigStr)
		);
		CanvasConfig canvasConfig = new CanvasConfig("C 100 50");
		Assertions.assertEquals(100, canvasConfig.getWidth());
		Assertions.assertEquals(50, canvasConfig.getHeight());
		canvasConfig = new CanvasConfig(" C  300    150");
		Assertions.assertEquals(300, canvasConfig.getWidth());
		Assertions.assertEquals(150, canvasConfig.getHeight());
	}

	@Test
	public void testLineRegExp() throws LineConfigurationException {
		Stream.of("", "xyz", "c 1 2", "L 1 2 3", "L one 2 3 4", "L -1 2 -1 4").forEach(badConfigStr ->
			Assertions.assertThrows(LineConfigurationException.class, () -> {
				new Line(badConfigStr);
			}, "LineConfigurationException expected for " + badConfigStr)
		);
		// lines must be vertical or horizontal
		Stream.of("L 1 2 3 4").forEach(badConfigStr ->
			Assertions.assertThrows(LineConfigurationException.class, () -> {
				new Line(badConfigStr);
			}, "CanvasConfigurationException expected for " + badConfigStr)
		);
		Line line = new Line("L   2 6 2  10");
		Assertions.assertEquals(2, line.getStartX());
		Assertions.assertEquals(6, line.getStartY());
		Assertions.assertEquals(2, line.getEndX());
		Assertions.assertEquals(10, line.getEndY());
	}

	@Test
	public void testRectRegExp() throws RectConfigurationException {
		Stream.of("", "xyz", "C 1 2", "L 1", "L 1 2 3 4", "R one 2 3 4", "R 1 2 3").forEach(badConfigStr ->
			Assertions.assertThrows(RectConfigurationException.class, () -> {
				new Rect(badConfigStr);
			}, "RectConfigurationException expected for " + badConfigStr)
		);
		Rect rect = new Rect("R 3 10 10 14");
		Assertions.assertEquals(3, rect.getTopLeftX());
		Assertions.assertEquals(10, rect.getTopLeftY());
		Assertions.assertEquals(10, rect.getBottomRightX());
		Assertions.assertEquals(14, rect.getBottomRightY());
		// expect second coord to be south west of the first
		Assertions.assertThrows(RectConfigurationException.class, () -> {
			new Rect("R 5 3 7 1");
		}, "RectConfigurationException expected");
	}

	@Test
	public void testCanvas() throws EaselException {
		Canvas canvas = new Canvas(new CanvasConfig("C 18 14"));
		canvas.add(new Line("L 1 1 1 1"));
		canvas.render(System.out);
		canvas.add(new Line("L 8 1 8 3"));
		canvas.render(System.out);
		canvas.add(new Line("L 2 1 2 4"));
		canvas.render(System.out);
		canvas.add(new Rect("R 10 10 14 13"));
		canvas.render(System.out);
		// a rectangle can be a line
		canvas.add(new Rect("R 8 8 8 9"));
		canvas.render(System.out);
		// or even a point
		canvas.add(new Rect("R 2 12 2 12"));
		canvas.render(System.out);
	}

	@Test
	public void testSizes() throws CanvasConfigurationException, LineConfigurationException, RectConfigurationException {
		Canvas canvas = new Canvas(new CanvasConfig("C 18 14"));
		Assertions.assertThrows(EaselException.class, () -> {
			canvas.add(new Line("L 1 1 1 19"));
		}, "EaselException expected");
	}
}
