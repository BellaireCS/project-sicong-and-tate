package com.github.tacong;

import java.awt.Color;

public class Player {

	public final static int spriteSpeed = 5;

	private int xCoordinate;
	private int yCoordinate;
	private final int radius;
	private final Color color;

	public Player(int xCoordinate, int yCoordinate, int radius, Color color) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.radius = radius;
		this.color = color;
	}

	public int getX() {
		return xCoordinate;
	}

	public void setX(int x) {
		xCoordinate = x;
	}

	public int getY() {
		return yCoordinate;
	}

	public void setY(int y) {
		yCoordinate = y;
	}

	public int getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

}
