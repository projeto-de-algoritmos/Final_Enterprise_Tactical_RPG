package game;

import game.entities.SimpleEnemy;
import game.entities.Entity;
import graphs.CheapestPath;
import graphs.GraphMatrix;
import graphs.Position;

public class EnemyCheapestPath {
	private final SimpleEnemy enemy;
	private final CheapestPath<Position, Integer> path;
	private final Boolean valid;
	private GraphMatrix<Integer, Integer> grid;

	public EnemyCheapestPath(SimpleEnemy enemy, Entity player, GraphMatrix<Integer, Integer> grid) {
		this.enemy = enemy;
		this.grid = grid;
		this.path = this.grid.dijkstra(new Position(enemy.getGridX(), enemy.getGridY()),
				new Position(player.getGridX(), player.getGridY()));
		this.valid = this.path == null ? false : true;
	}

	/**
	 * @return the Enemy
	 */
	public SimpleEnemy getEnemy() {
		return enemy;
	}

	/**
	 * @return the path
	 */
	public CheapestPath<Position, Integer> getPath() {
		return path;
	}

	/**
	 * @return the valid
	 */
	public Boolean getValid() {
		return valid;
	}
}
