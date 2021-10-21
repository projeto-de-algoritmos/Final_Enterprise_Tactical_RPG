package game.entities;

import java.awt.Color;
import java.awt.Graphics;

public class GreedyEnemy extends Enemy {

	public GreedyEnemy(Integer moves, Integer initialGridX, Integer initialGridY, Integer grid2PosMultiplyer,
			Integer grid2PosBias, Integer height, Integer width, Color color) {
		super(moves, initialGridX, initialGridY, grid2PosMultiplyer, grid2PosBias, height, width, color);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(getX(), getY(), getWidth(), getHeight());
	}

}
