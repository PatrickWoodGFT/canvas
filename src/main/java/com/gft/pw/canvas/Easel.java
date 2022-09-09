package com.gft.pw.canvas;

import java.util.Scanner;

import com.gft.pw.canvas.exception.EaselException;
import com.gft.pw.canvas.model.Canvas;
import com.gft.pw.canvas.model.CanvasConfig;
import com.gft.pw.canvas.model.Line;
import com.gft.pw.canvas.model.Rect;

import static com.gft.pw.canvas.Helper.showIntro;
import static com.gft.pw.canvas.Helper.prompt;
import static com.gft.pw.canvas.Helper.showCanvasCreationFormat;
import static com.gft.pw.canvas.Helper.showLineCreationFormat;
import static com.gft.pw.canvas.Helper.showHelp;

/**
 * Main class for the Canvas app. Asks for input from the command line
 * to draw a canvas and make lines and rectangles on it.
 * 
 * @author pkwd
 */
public class Easel {

	private Canvas canvas;
	private String[] args;
	
	public Easel(String[] args) {
		this.args = args;
	}

	public static void main(String[] args) {
		showIntro();
		new Easel(args).setup();
	}
	
	/**
	 * Ask for input from the user; parse it and draw a canvas, a line or a rectangle
	 * to stdout. 
	 */
	private void setup() {
		try (Scanner scanner = new Scanner(System.in)) {
			String input = null;
			while (!"Q".equals((input = scanner.nextLine().trim().toUpperCase()))) {
				if (input != null && input.length() > 0) {
					processInput(input);
				}
				prompt();
			}
			System.out.println("Bye");
		}
	}
	
	/**
	 * Switch according to the first character of the trimmed input.
	 * 
	 * @param input	The input from the command line
	 */
	private void processInput(String input) {
		try {
			switch (input.charAt(0)) {
				case 'H' -> {
					showHelp();
				}
				case 'C' -> {
					canvas = new Canvas(new CanvasConfig(input, args));
					canvas.render(System.out);
				}
				case 'L' -> {
					if (canvas == null) {
						showCanvasCreationFormat();
					} else {
						canvas.add(new Line(input));
						canvas.render(System.out);
					}
				}
				case 'R' -> {
					if (canvas == null) {
						showCanvasCreationFormat();
					} else {
						canvas.add(new Rect(input));
						canvas.render(System.out);
					}
				}
				case 'U' -> {
					if (canvas == null) {
						showCanvasCreationFormat();
					} else if (!canvas.isPopulated()) {
						canvas = null;
					} else {
						canvas.undo();
						canvas.render(System.out);
					}
				}
				default -> {
					if (canvas == null) {
						showCanvasCreationFormat();
					} else {
						showLineCreationFormat();
					}
				}
			}
		} catch (EaselException e) {
			System.out.println(e.getMessage());
		}
	}
}
