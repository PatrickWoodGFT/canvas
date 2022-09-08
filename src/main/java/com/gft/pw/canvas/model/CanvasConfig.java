package com.gft.pw.canvas.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gft.pw.canvas.exception.CanvasConfigurationException;

/**
 * Class to create and hold canvas information: that is, width and height.
 * 
 * @author pkwd
 */
public class CanvasConfig {

	// for example, C 20 10
	private static final Pattern FORMAT_PATTERN = Pattern.compile("^\\s*C\\s+(\\d+)\\s+(\\d+)$");
	private int width;
	private int height;

	public CanvasConfig(String canvasConfigStr) throws CanvasConfigurationException {
		Matcher matcher = FORMAT_PATTERN.matcher(canvasConfigStr);
		if (!matcher.find()) {
			throw new CanvasConfigurationException();
		} else {
			this.width = Integer.parseInt(matcher.group(1));
			this.height = Integer.parseInt(matcher.group(2));
		}
	}
	
	public CanvasConfig(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
}
