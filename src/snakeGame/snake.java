package snakeGame;

import java.awt.Color;
import java.awt.Graphics;

public class snake {

	private int xCoor;
	private int yCoor;
	private int TILE_SIZE;
	
	public snake(int xCoor, int yCoor, int TILE_SIZE) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.TILE_SIZE = TILE_SIZE;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(xCoor, yCoor, TILE_SIZE, TILE_SIZE);
	}

	public int getxCoor() {
		return xCoor;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public int getyCoor() {
		return yCoor;
	}

	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}
	
	
}
