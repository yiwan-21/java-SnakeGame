package snakeGame;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameFrame() {
		JFrame frame = new JFrame();
		GamePanel gpanel = new GamePanel();
		ScoreBoard leaderboardPanel = new ScoreBoard(gpanel.getWIDTH(), gpanel.getHEIGHT());
		
		frame.add(leaderboardPanel);
		frame.add(gpanel);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setTitle("Snake Game");
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new GameFrame();
	}
}
