package com.gft.pw.canvas;

import com.gft.pw.canvas.exception.CanvasConfigurationException;
import com.gft.pw.canvas.exception.LineConfigurationException;

/**
 * Utility class collecting helpful messages that may need to be shown to the user.
 * 
 * @author pkwd
 */
public class Helper {
	
	static void showHelp() {
		System.out.println("To create a canvas, type C <width_in_chars> <height_in_lines>");
		System.out.println("Then, to draw a horizontal or vertical line, type L <start_x> <start_y> <end_x> <end_y>");
		System.out.println("To draw a rectangle, type R <top_left_x> <top_left_y> <bottom_right_x> <bottom_right_y>");
		System.out.println("To quit the program type Q");
	}

	static void showIntro() {
		System.out.println("Welcome to Canvas 1.0");
		prompt();
	}

	static void prompt() {
		System.out.print("Enter command or type h: ");
	}

	static void showCanvasCreationFormat() {
		System.out.println(new CanvasConfigurationException().getMessage());
	}

	static void showLineCreationFormat() {
		System.out.println(new LineConfigurationException().getMessage());
	}
}
