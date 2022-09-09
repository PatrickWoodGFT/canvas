package com.gft.pw.canvas.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.gft.pw.canvas.exception.EaselException;

/**
 * Class representing a canvas of a certain size that can have renderable objects, eg lines 
 * and rectangles, drawn on it. These figures are added to the canvas and tracked in a matrix
 * representing the pixels.
 * 
 * @author pkwd
 */
public class Canvas {

	private static char topEdge = '-';
	private static char bottomEdge = '-';
	private static char leftSide = '|';
	private static char rightSide = '|';
	private static char linePixel = 'X';
	private static int slowPrintSleepTime = 10;
	
	private static String red = "\u001B[31m";
	private static String reset = "\u001B[0m";
	
	private List<Renderable> renderables = new ArrayList<>();
	
	/**
	 * Internal map of pixels used by the Canvas object to track which of its
	 * cells should be filled in based on the figures that have been added to it. 
	 * The key is the line number; each item in the list for that line denotes 
	 * whether the x-coordinate at that point should be filled in or not. 
	 */
	private Map<Integer, Map<Integer, Boolean>> pixelMatrix;

	private CanvasConfig canvasConfig;

	/**
	 * Creates a Canvas object with the dimensions in the given configuration
	 * and populates a pixel matrix in which all the elements are false,
	 * denoting that the canvas starts off blank.
	 * 
	 * @param canvasConfig	The dimensions of the canvas
	 */
	public Canvas(CanvasConfig canvasConfig) {
		this.canvasConfig = canvasConfig;
		init();
	}

	private void init() {
		pixelMatrix = new HashMap<>();
		IntStream.range(1, canvasConfig.getHeight() + 1).forEach(i -> {
			Map<Integer, Boolean> map = new HashMap<>();
			IntStream.range(1, canvasConfig.getWidth() + 1).forEach(j -> map.put(j, false));
			pixelMatrix.put(i, map);	
		});
	}
	
	/**
	 * Prints out the frame of the canvas and fills in the pixels within based on
	 * the renderable objects that have been added to the canvas.
	 * 
	 * @param out	The stream used to write out the canvas, typically System.out
	 */
	public void render(PrintStream out) {
		// render upper edge of the frame
		IntStream.range(0, canvasConfig.getWidth() + 2).forEach(i -> slowPrint(out, topEdge));
		out.print("\n");
		// render left and right sides of the frame
		IntStream.range(1, canvasConfig.getHeight() + 1).forEach(i -> {
			out.print(leftSide);
			// for each cell, check whether it should show part of a line
			IntStream.range(1, canvasConfig.getWidth() + 1).forEach(j -> out.print(getMatrixValue(i, j)));
			slowPrint(out, rightSide);
			out.print("\n");
		});
		// render lower edge of the frame
		IntStream.range(0, canvasConfig.getWidth() + 2).forEach(i -> slowPrint(out, bottomEdge));
		out.print("\n");
	}
	
	private String getMatrixValue(int i, int j) {
		return decorate(pixelMatrix.get(i).get(j) ? linePixel : ' ');
	}
	
	private String decorate(char ch) {
		return canvasConfig.decorate() 
				? (' ' != ch ? red + ch : reset + ' ')
				: "" + ch;
	}

	public void add(Renderable renderable) throws EaselException {
		// check that the renderable object doesn't go beyond the boundary of the canvas
		if (exceedsCanvasBoundary(renderable)) {
			throw new EaselException("Canvas boundary exceeded");
		} else {
			renderables.add(renderable);
			renderable.render(pixelMatrix);
		}
	}
	
	public boolean isPopulated() {
		return renderables.size() > 0;
	}

	public void undo() {
		if (renderables != null && renderables.size() > 0) {
			init();
			renderables.remove(renderables.size() - 1);
			renderables.forEach(renderable -> renderable.render(pixelMatrix));
		}
	}
	
	private boolean exceedsCanvasBoundary(Renderable renderable) {
		return renderable.getMaxExtentX() > canvasConfig.getWidth()
			|| renderable.getMaxExtentY() > canvasConfig.getHeight();
	}

	/**
	 * Adds a slow-motion effect to the rendering. Purely cosmetic.
	 * 
	 * @param out	the stream to print to
	 * @param charToPrint	the character to print
	 */
	private static void slowPrint(PrintStream out, char charToPrint) {
		out.print(charToPrint);
		try {
			Thread.sleep(slowPrintSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
