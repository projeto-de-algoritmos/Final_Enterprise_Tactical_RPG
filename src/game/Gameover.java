package game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gameover {
	private JFrame frame;
	private JLabel label;
	private JPanel panel;
	private boolean restarted = false;
	private boolean closed = false;

	public JFrame getFrame() {
		return frame;
	}

	public Gameover(int score) {
		JLabel stdMsg = new JLabel("You have survived " + String.valueOf(score - 1) + " Rounds!", JLabel.CENTER);
		JLabel diedOn0th = new JLabel("You must die! Survived " + String.valueOf(0) + " Rounds!", JLabel.CENTER);

		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		label = score == 0 ? diedOn0th : stdMsg;
		panel.add(label);
		panel.setLayout(new GridLayout(3, 1));

		frame.setSize(500, 500);

		JButton restartButton = new JButton("Restart");
		JButton exitButton = new JButton("Exit");

		restartButton.setActionCommand("Restart");
		restartButton.addActionListener(new EventoBotao());

		exitButton.setActionCommand("Exit");
		exitButton.addActionListener(new EventoBotao());

		panel.setSize(500, 500);
		panel.add(restartButton);
		panel.add(exitButton);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Enterprise Tactical RPG");
		frame.add(panel);
		frame.pack();
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
	}

	private class EventoBotao implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String comando = e.getActionCommand();
			if (comando.equals("Restart")) {
				closed = true;
				restarted = true;
			} else if (comando.equals("Exit")) {
				closed = true;
			}
		}
	}

	public boolean isRestarted() {
		return restarted;
	}

	public boolean isClosed() {
		return closed;
	}
}
