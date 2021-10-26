package game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.event.ItemEvent;

public class Menu {
	private JFrame frame;
	private boolean started = false;
	private boolean stepMode = true;
	private boolean sounds = true;
	private int size;
	private JLabel label;

	public JFrame getFrame() {
		return frame;
	}

	public int getSize() {
		return size;
	}

	public boolean isSounds() {
		return sounds;
	}

	public boolean isStepMode() {
		return stepMode;
	}

	public boolean isStarted() {
		return started;
	}

	public Menu() {

		size = 20;

		frame = new JFrame();
		frame.setResizable(false);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JButton startButton = new JButton("Start");
		startButton.setActionCommand("Start");
		startButton.addActionListener(new ButtonEvent());
		panel.add(startButton);

		JPanel gridPanel = new JPanel();
		panel.add(gridPanel);
		gridPanel.setLayout(new BorderLayout(0, 0));

		JButton lowerButton = new JButton("-");
		lowerButton.setActionCommand("lowerGridSize");
		lowerButton.addActionListener(new ButtonEvent());
		gridPanel.add(lowerButton, BorderLayout.WEST);

		JButton raiseButton = new JButton("+");
		raiseButton.setActionCommand("raiseGridSize");
		raiseButton.addActionListener(new ButtonEvent());
		gridPanel.add(raiseButton, BorderLayout.EAST);

		label = new JLabel("Grid Size: 20");
		gridPanel.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);

		JCheckBox chckbxSounds = new JCheckBox("Sounds", true);
		chckbxSounds.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				sounds = e.getStateChange() == 1 ? true : false;
			}
		});
		panel.add(chckbxSounds);
		chckbxSounds.setHorizontalAlignment(SwingConstants.CENTER);

		JCheckBox chckbxStepmode = new JCheckBox("StepMode", true);
		chckbxStepmode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				stepMode = e.getStateChange() == 1 ? true : false;
			}
		});
		panel.add(chckbxStepmode);
		chckbxStepmode.setHorizontalAlignment(SwingConstants.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Enterprise Tactical RPG");
		frame.pack();
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private class ButtonEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String comando = e.getActionCommand();
			if (comando.equals("Start")) {
				started = true;
			} else if (comando.equals("raiseGridSize") && size < 30) {
				size++;
				label.setText("Grid Size: " + String.valueOf(size));
			} else if (comando.equals("lowerGridSize") && size > 16) {
				size--;
				label.setText("Grid Size: " + String.valueOf(size));
			} else if (comando.equals("toggleStepButton") && size > 16) {
				stepMode = !stepMode;
				label.setText("Grid Size: " + String.valueOf(size));
			}
		}
	}

}