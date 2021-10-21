package game;

import java.util.concurrent.TimeUnit;

public class Main {
	public Main() {
		boolean restart = true;

		// Menu
		Menu menu = new Menu();
		while (!menu.isStarted()) {
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Recebe o tamanho da grade
		int size = menu.getSize();
		boolean stepMode = menu.isStepMode();
		boolean sounds = menu.isSounds();
		// Remove o menu
		menu.getFrame().dispose();

		// Loop de Restart
		while (restart) {
			// Jogo
			Game game = new Game(size, stepMode, sounds);
			while (game.isRunning()) {
				game.update();
			}
			int score = game.getScore();
			game.getFrame().dispose();

			// Tela de Gameover
			Gameover gameover = new Gameover(score);
			while (!gameover.isClosed()) {
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			restart = gameover.isRestarted();
			gameover.getFrame().dispose();
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
