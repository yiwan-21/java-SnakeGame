package snakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 500;
	private final int SCOREBOARD = 50;
	private final int HEIGHT = 500 + SCOREBOARD;
	private final int TILE_SIZE = 10;
	private boolean running = true;
	private int ticks;
	
	private int headX;
	private int headY;
	private snake newPart;
	private ArrayList<snake> snakes;
	private int snakeSize = 5;
	private int score = 0;
	private char direction = 'R';
	private boolean hasApple = false;
	private int appleX;
	private int appleY;
	
	Thread thread;
	Random random;
	
	public GamePanel() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH+300, HEIGHT));
		addKeyListener(this);
		setBackground(Color.BLACK);
		
		ticks = 0;
		headX = 10+SCOREBOARD;
		headY = 10+SCOREBOARD;
		snakes = new ArrayList<snake>();
		random = new Random();
		
		start();
	}
	
	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		running = false;
		ScoreBoard.saveNewRecord(score);
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (running) {
			tick();
			repaint();
		}
	}
	
	public void tick() {
		if (snakes.size() == 0) {
			newPart = new snake(headX, headY, TILE_SIZE);
			snakes.add(newPart);
		}
		ticks++;
		if (ticks > 300000) {
			ticks = 0;
			
			switch(direction) {
			case 'R':
				if (direction != 'L')
					headX += TILE_SIZE;
				break;
			case 'L':
				if (direction != 'R')
					headX -= TILE_SIZE;
				break;
			case 'U':
				if (direction != 'D')
					headY -= TILE_SIZE;
				break;
			case 'D':
				if (direction != 'U')
					headY += TILE_SIZE;
				break;
			}
			newPart = new snake(headX, headY, TILE_SIZE);
			snakes.add(newPart);
			
			if (snakes.size() > snakeSize) {
				snakes.remove(0);
			}
			
			if (!hasApple) {
				appleX = random.nextInt(WIDTH/TILE_SIZE)*TILE_SIZE;
				appleY = random.nextInt((HEIGHT-SCOREBOARD)/TILE_SIZE)*TILE_SIZE+SCOREBOARD;
				hasApple = true;
			}
			
			if (headX == appleX && headY == appleY) {
				snakeSize++;
				score+=100;
				hasApple = false;
			}
			
			//check collision
			if (headX < 0 || headX >= WIDTH || headY < SCOREBOARD || headY >= HEIGHT) {
				stop();
			}
			
			for (int i = 0; i < snakes.size(); i++) {
				
				if (headX == snakes.get(i).getxCoor() && headY == snakes.get(i).getyCoor()) {
					if (i != snakes.size()-1) {
						stop();
					}
				}
			}
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.WHITE);
		g.drawLine(0, SCOREBOARD, WIDTH, SCOREBOARD);
		g.drawLine(WIDTH, 0, WIDTH, HEIGHT);
		
		//paint snake
		g.setColor(Color.green);
		for (int i = 0 ; i < snakes.size(); i++) {
			snakes.get(i).draw(g);
		}
		
		//paint apple
		if (!hasApple)
			g.clearRect(appleX, appleY, TILE_SIZE, TILE_SIZE);
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, TILE_SIZE, TILE_SIZE);
		
		//paint score
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: "+score, (WIDTH - metrics.stringWidth("Score: "+score))/2, 30);
		
		//paint game over
		if (!running) {
			g.setFont(new Font("Arial", Font.BOLD, 72));
			metrics = getFontMetrics(g.getFont());
			g.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over"))/2, HEIGHT/2);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if (direction != 'R')
				direction = 'L';
			break;
		case KeyEvent.VK_RIGHT:
			if (direction != 'L')
				direction = 'R';
			break;
		case KeyEvent.VK_UP:
			if (direction != 'D')
				direction = 'U';
			break;
		case KeyEvent.VK_DOWN:
			if (direction != 'U')
				direction = 'D';
			break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public int getTILE_SIZE() {
		return TILE_SIZE;
	}

}
