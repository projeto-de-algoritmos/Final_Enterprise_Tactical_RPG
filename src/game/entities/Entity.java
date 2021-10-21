package game.entities;

import java.awt.Color;
import java.awt.Graphics;

public class Entity {

	private Integer x;
	private Integer gridX;

	private Integer y;
	private Integer gridY;
	private Integer moves;

	private Integer grid2PosMultiplyer;
	private Integer grid2PosBias;

	private Integer width;
	private Integer height;

	private Color color;

	public Entity(Integer moves, Integer initialGridX, Integer initialGridY, Integer grid2PosMultiplyer,
			Integer grid2PosBias, Integer height, Integer width, Color color) {
		this.moves = moves;
		this.grid2PosBias = grid2PosBias;
		this.grid2PosMultiplyer = grid2PosMultiplyer;
		this.width = width;
		this.height = height;
		this.color = color;
		setGridX(initialGridX);
		setGridY(initialGridY);
	}

	public Integer getGridX() {
		return gridX;
	}

	public void setGridX(Integer gridX) {
		this.gridX = gridX;
		x = gridX * getGrid2PosMultiplyer() + getGrid2PosBias();
	}

	public Integer getGridY() {
		return gridY;
	}

	public void setGridY(Integer gridY) {
		this.gridY = gridY;
		y = gridY * getGrid2PosMultiplyer() + getGrid2PosBias();
	}

	public void setMoves(Integer moves) {
		this.moves = moves;
	}

	public Integer getMoves() {
		return moves;
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	public Integer getGrid2PosMultiplyer() {
		return grid2PosMultiplyer;
	}

	public Integer getGrid2PosBias() {
		return grid2PosBias;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
