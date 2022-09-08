package com.gft.pw.canvas.exception;

/**
 * @author pkwd
 */
public class RectConfigurationException extends EaselException {

	private static final long serialVersionUID = 2430851244265472346L;
	
	private static String defaultMessage = 
			"Rectangle drawing command should be of the form R <top_left_x> <top_left_y> <bottom_right_x> <bottom_right_y>\n" +
					"For example:\n" +
					"R 3 2 7 9";
	
	public RectConfigurationException(String message) {
		super(message);
	}
	
	public RectConfigurationException() {
		super(defaultMessage);
	}
}
