package com.gft.pw.canvas.model;

import java.util.Map;

/**
 * Implementations know how to render themselves onto a two-dimensional
 * grid representing a canvas. They also know their extent in order to
 * prevent overflow. 
 * 
 * If new objects are required to be drawn, they should implement this 
 * interface for the Canvas object to be able to display them.
 * 
 * @author pkwd
 */
public interface Renderable {

	/**
	 * Fill in the supplied map according to how the Renderable is to be displayed on the grid.
	 * 
	 * @param pixelMatrix	A two-dimensional matrix representing the pixels of a rectangular display area or canvas.
	 * The keys are the line numbers, each value a map of the x-coords of that line against a boolean
	 * denoting whether or not the pixel represented should be filled in
	 */
	void render(Map<Integer, Map<Integer, Boolean>> pixelMatrix);
	int getMaxExtentX();
	int getMaxExtentY();
}
