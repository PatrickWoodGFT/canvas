package com.gft.pw.canvas.exception;

/**
 * @author pkwd
 */
public class LineConfigurationException extends EaselException {

	private static final long serialVersionUID = -5703144056052482047L;
	
	private static String defaultMessage = 
		"Line drawing command should be of the form L <start_x> <start_y> <end_x> <end_y>\n" +
		"For example:\n" +
		"L 3 2 3 9";
	
	public LineConfigurationException(String message) {
		super(message);
	}
	
	public LineConfigurationException() {
		super(defaultMessage);
	}
}
