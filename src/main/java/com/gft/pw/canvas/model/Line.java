package com.gft.pw.canvas.model;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.gft.pw.canvas.exception.EaselException;
import com.gft.pw.canvas.exception.LineConfigurationException;

/**
 * Class to create and hold information about a line. Parses a string of the form
 * L n1 n2 n3 n4
 * and ensures it represents a line that is either vertical or horizontal and
 * has start coordinates both greater than 0.
 * 
 * @author pkwd
 */
public class Line implements Renderable {

	private int startX, startY, endX, endY;
	private LineOrientation orientation;
	
	private static final Pattern FORMAT_PATTERN = Pattern.compile("^\\s*L\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)$");

	public Line(String lineConfigStr) throws LineConfigurationException, EaselException {
		Matcher matcher = FORMAT_PATTERN.matcher(lineConfigStr);
		if (!matcher.find()) {
			throw new LineConfigurationException();
		} else {
			this.startX = Integer.parseInt(matcher.group(1));
			this.startY = Integer.parseInt(matcher.group(2));
			this.endX = Integer.parseInt(matcher.group(3));
			this.endY = Integer.parseInt(matcher.group(4));
			setOrientation();
		}
	}

	@Override
	public void render(Map<Integer, Map<Integer, Boolean>> pixelMatrix) {
		if (getOrientation() == LineOrientation.HORIZONTAL) {
			Map<Integer, Boolean> lineMap = pixelMatrix.get(getStartY());
			IntStream.range(getStartX(), getEndX() + 1) .forEach(i -> lineMap.put(i, true));
		} else {
			IntStream.range(getStartY(), getEndY() + 1).forEach(i -> pixelMatrix.get(i).put(getStartX(), true));
		}
	}

	@Override
	public int getMaxExtentX() {
		return getEndX();
	}

	@Override
	public int getMaxExtentY() {
		return getEndY();
	}

	private void setOrientation() throws LineConfigurationException, EaselException {
		// check that the line is either vertical or horizontal
		if (startX != endX && startY != endY) {
			throw new LineConfigurationException("Line must be either vertical or horizontal");
		} else if (startX > endX || startY > endY) {
			throw new LineConfigurationException();
		} else if (startX < 1 || startY < 1) {
			throw new EaselException("Canvas boundary exceeded");
		} else {
			orientation = startX == endX ? LineOrientation.VERTICAL : LineOrientation.HORIZONTAL;
		}
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public LineOrientation getOrientation() {
		return orientation;
	}

	public void setOrientation(LineOrientation orientation) {
		this.orientation = orientation;
	}
}
