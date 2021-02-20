package snakeGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ScoreBoard extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<Long> scores;
	private long[] top10;
	private final int WIDTH = 300;
	private final int GamePanel_WIDTH;
	private final int GamePanel_HEIGHT;

	public ScoreBoard(int WIDTH, int HEIGHT) {
		GamePanel_WIDTH = WIDTH;
		GamePanel_HEIGHT = HEIGHT;
		scores = new ArrayList<Long>();
		top10 = new long[10];
		readLeaderBoard();
		repaint();
		
		JLabel leaderboard = new JLabel();
		leaderboard.setBackground(Color.BLACK);
		leaderboard.setForeground(Color.RED);
		leaderboard.setHorizontalAlignment(JLabel.CENTER);
		leaderboard.setVerticalAlignment(JLabel.TOP);
		leaderboard.setOpaque(true);
		
		leaderboard.setFont(new Font("Ink Free", Font.BOLD, 32));
		leaderboard.setText("LEADERBOARD");
		
		this.setLayout(new BorderLayout());
		this.setBounds(GamePanel_WIDTH, 0, this.WIDTH, GamePanel_HEIGHT);
		this.add(leaderboard);
		
	}
	
	@SuppressWarnings("unchecked")
	public void readLeaderBoard() {
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("Leaderboard.json"));
			JSONArray jsonArray = (JSONArray) jsonObject.get("score");
			scores.addAll(jsonArray);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Generate a new file");
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			
			jsonArray.add(0);
			jsonObject.put("score", jsonArray);
			try {
				PrintWriter out = new PrintWriter(new FileOutputStream("Leaderboard.json"));
				out.write(jsonObject.toString());
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long max = -1;
		int index = -1;
		ArrayList<Long> temp = new ArrayList<Long>();
		temp.addAll(scores);
		for (int i = 0; i < top10.length; i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (temp.get(j) > max) {
					max = temp.get(j);
					index = j;
				}
			}
			if (index == -1) {
				continue;
			}
			temp.remove(index);
			top10[i] = max;
			max = -1;
			index = -1;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void saveNewRecord(int score) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonArray.addAll(scores);
		jsonArray.add(score);
		jsonObject.put("score", jsonArray);
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream("LeaderBoard.json"));
			out.write(jsonObject.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 300, GamePanel_HEIGHT);
		
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("LeaderBoard", (WIDTH - metrics.stringWidth("LeaderBoard"))/2, 50);
		
		g.setFont(new Font("Arial", Font.PLAIN, 32));
		int y = 120;
		for (int i = 0; i < top10.length; i++) {
			g.drawString(""+top10[i], 120, y);
			y+=45;
		}
	}
	
}
