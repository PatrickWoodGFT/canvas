package com.gft.pw.canvas.model;

import java.util.Arrays;
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
	private boolean decorate;

	public CanvasConfig(String canvasConfigStr, String[] args) throws CanvasConfigurationException {
		Matcher matcher = FORMAT_PATTERN.matcher(canvasConfigStr);
		if (!matcher.find()) {
			throw new CanvasConfigurationException();
		} else {
			this.width = Integer.parseInt(matcher.group(1));
			this.height = Integer.parseInt(matcher.group(2));
			this.decorate = args != null && args.length > 0 && Arrays.asList(args).contains("c");
		}
	}

	public CanvasConfig(String canvasConfigStr) throws CanvasConfigurationException {
		this(canvasConfigStr, null);
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

	public boolean decorate() {
		return decorate;
	}

	public void decorate(boolean decorate) {
		this.decorate = decorate;
	}
}
