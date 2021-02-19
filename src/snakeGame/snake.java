package snakeGame;

import java.awt.Color;
import java.awt.Graphics;

public class snake {

	private int xCoor;
	private int yCoor;
	
	public snake(int xCoor, int yCoor) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(xCoor, yCoor, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
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
