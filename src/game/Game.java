package game;

import java.awt.BorderLayout;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class Game {

	private boolean running;

	private JFrame frame;
	private Panel gamePanel;
	private int score;

	public Game(int size, boolean stepMode, boolean sounds) {
		this.setRunning(true);
		
		int mod = 500 % size;
		
		frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Enterprise Tactical RPG");
		frame.pack();
		
		
		frame.setSize(500 - mod, 530 - mod + 530/size);
		frame.setLocationRelativeTo(null);
		
		gamePanel = new Panel(size, 500-mod, 500-mod, stepMode, sounds);
		frame.add(gamePanel, BorderLayout.CENTER);
		
		frame.setVisible(true);
		frame.setResizable(false);

		score = 0;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void update() {
		try {
			TimeUnit.MILLISECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!gamePanel.getRunning()) {
			setRunning(false);
			score = gamePanel.getScore();
		}
	}

	public JFrame getFrame() {
		return frame;
	}

	public int getScore() {
		return score;
	}
}
