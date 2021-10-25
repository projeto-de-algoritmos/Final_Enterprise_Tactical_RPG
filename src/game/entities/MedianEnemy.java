package game.entities;

import java.awt.Color;
import java.awt.Graphics;

public class MedianEnemy extends SimpleEnemy {

	public MedianEnemy(Integer moves, Integer initialGridX, Integer initialGridY, Integer grid2PosMultiplyer,
			Integer grid2PosBias, Integer height, Integer width, Color color) {
		super(moves, initialGridX, initialGridY, grid2PosMultiplyer, grid2PosBias, height, width, color);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillArc(getX(), getY(), getWidth(), getHeight(), 0, 180);
	}
}
