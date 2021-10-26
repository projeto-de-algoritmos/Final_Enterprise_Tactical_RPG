package game.entities;

import java.awt.Color;

public class Player extends Entity {

	private boolean moved;

	public Player(Integer moves, Integer initialGridX, Integer initialGridY, Integer grid2PosMultiplyer,
			Integer grid2PosBias, Integer height, Integer width, Color color) {
		super(moves, initialGridX, initialGridY, grid2PosMultiplyer, grid2PosBias, height, width, color);
	}

	public void changeState() {
		moved = moved ? false : true;
		if (moved) {
			this.setColor(this.getColor().darker().darker());
		} else {
			this.setColor(this.getColor().brighter().brighter());
		}

	}
}
