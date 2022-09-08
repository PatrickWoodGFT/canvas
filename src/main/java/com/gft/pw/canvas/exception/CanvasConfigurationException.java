package com.gft.pw.canvas.exception;

/**
 * @author pkwd
 */
public class CanvasConfigurationException extends EaselException {

	private static final long serialVersionUID = 5118606000103170118L;
	
	@Override
	public String getMessage() {
		return  "Canvas creation command should be of the form C <width_in_chars> <height_in_lines>\n" +
				"For example:\n" +
				"C 40 15";
	}
}
