package com.github.tacong;

import java.awt.Color;

public class Player {

	public final static int SPRITE_SPEED = 4;
	public final static int SPRITE_RADIUS = 20;

	private int xCoordinate;
	private int yCoordinate;
	private final Color color;

	public Player(int xCoordinate, int yCoordinate, Color color) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
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

	public int[] getCoordinates() {
		return new int[] { xCoordinate, yCoordinate };
	}

	public Color getColor() {
		return color;
	}

}
