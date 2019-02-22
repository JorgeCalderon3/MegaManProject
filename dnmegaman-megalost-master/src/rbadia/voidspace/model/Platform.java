package rbadia.voidspace.model;

import java.awt.Rectangle;

public class Platform extends Rectangle {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPEED = 2;
	private static final int WIDTH = 44;
	private static final int HEIGHT = 4;

	public Platform(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
	}
	
	public double getWidth() {
		return WIDTH;
	}
	
	public double getHeight() {
		return HEIGHT;
	}
	
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
