package com.gft.pw.canvas.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gft.pw.canvas.exception.EaselException;
import com.gft.pw.canvas.exception.LineConfigurationException;
import com.gft.pw.canvas.exception.RectConfigurationException;

/**
 * Class to create and hold information about a rectangle. Parses a string of the form
 * R n1 n2 n3 n4
 * where n1 and n2 are the coordinates of the top-left corner of the rectangle
 * and n3 and n4 are the coordinates of the bottom-right corner.
 * 
 * The top-left coordinates must both be greater than 0.
 * 
 * Degenerate rectangles, ie lines and points, are allowed.
 *
 * @author pkwd
 */
public class Rect implements Renderable {

	private int topLeftX, topLeftY, bottomRightX, bottomRightY;

	private static final Pattern FORMAT_PATTERN = Pattern.compile("^\\s*R\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)$");

	public Rect(String lineConfigStr) throws RectConfigurationException, EaselException {
		Matcher matcher = FORMAT_PATTERN.matcher(lineConfigStr);
		if (!matcher.find()) {
			throw new RectConfigurationException();
		} else {
			this.topLeftX = Integer.parseInt(matcher.group(1));
			this.topLeftY = Integer.parseInt(matcher.group(2));
			this.bottomRightX = Integer.parseInt(matcher.group(3));
			this.bottomRightY = Integer.parseInt(matcher.group(4));
			if (topLeftX > bottomRightX || topLeftY > bottomRightY) {
				throw new RectConfigurationException("The bottom-right coordinate of a rectangle should be south east of the top-left coordinate");
			} else if (topLeftX < 1 || topLeftY < 1) {
				throw new EaselException("Canvas boundary exceeded");
			}
		}
	}

	@Override
	public void render(Map<Integer, Map<Integer, Boolean>> pixelMatrix) {
		// treat a rectangle as four lines
		try {
			// top
			new Line(String.format("L %s %s %s %s", getTopLeftX(), getTopLeftY(), getBottomRightX(), getTopLeftY())).render(pixelMatrix);
			// left
			new Line(String.format("L %s %s %s %s", getTopLeftX(), getTopLeftY(), getTopLeftX(), getBottomRightY())).render(pixelMatrix);
			// right
			new Line(String.format("L %s %s %s %s", getBottomRightX(), getTopLeftY(), getBottomRightX(), getBottomRightY())).render(pixelMatrix);
			// bottom
			new Line(String.format("L %s %s %s %s", getTopLeftX(), getBottomRightY(), getBottomRightX(), getBottomRightY())).render(pixelMatrix);
		} catch (EaselException e) {
			// shouldn't happen because the rectangle is already known to be correctly configured
			e.printStackTrace();
		}
	}

	@Override
	public int getMaxExtentX() {
		return getBottomRightX();
	}

	@Override
	public int getMaxExtentY() {
		return getBottomRightY();
	}

	public int getTopLeftX() {
		return topLeftX;
	}

	public void setTopLeftX(int topLeftX) {
		this.topLeftX = topLeftX;
	}

	public int getTopLeftY() {
		return topLeftY;
	}

	public void setTopLeftY(int topLeftY) {
		this.topLeftY = topLeftY;
	}

	public int getBottomRightX() {
		return bottomRightX;
	}

	public void setBottomRightX(int bottomRightX) {
		this.bottomRightX = bottomRightX;
	}

	public int getBottomRightY() {
		return bottomRightY;
	}

	public void setBottomRightY(int bottomRightY) {
		this.bottomRightY = bottomRightY;
	}
}
