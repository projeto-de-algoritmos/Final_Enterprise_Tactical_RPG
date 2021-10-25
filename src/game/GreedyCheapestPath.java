package game;

import game.entities.Entity;
import game.entities.GreedyEnemy;
import graphs.CheapestPath;
import graphs.GraphMatrix;
import graphs.Position;

public class GreedyCheapestPath extends EnemyCheapestPath implements Comparable<GreedyCheapestPath> {
	private final GreedyEnemy greedyEnemy;
	private final CheapestPath<Position, Integer> path;
	private final Integer weight;
	private final Integer value;
	private final Double specificValue;
	private final Boolean valid;
	private GraphMatrix<Integer, Integer> grid;

	public GreedyCheapestPath(GreedyEnemy greedyEnemy, Entity player, GraphMatrix<Integer, Integer> grid) {
		super(greedyEnemy, player, grid);
		this.greedyEnemy = greedyEnemy;
		this.grid = grid;
		this.path = this.grid.dijkstra(new Position(greedyEnemy.getGridX(), greedyEnemy.getGridY()),
				new Position(player.getGridX(), player.getGridY()));
		this.weight = this.path == null ? null : path.getPath().size();
		this.value = this.path == null ? null : path.getTotalCost();
		this.specificValue = this.path == null ? null : (double) value / (double) weight;
		this.valid = this.path == null ? false : true;
	}

	/**
	 * @return the greedyEnemy
	 */
	GreedyEnemy getGreedyEnemy() {
		return greedyEnemy;
	}

	/**
	 * @return the path
	 */
	@Override
	public CheapestPath<Position, Integer> getPath() {
		return path;
	}

	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * @return the specificValue
	 */
	Double getSpecificValue() {
		return specificValue;
	}

	/**
	 * @return the valid
	 */
	@Override
	public Boolean getValid() {
		return valid;
	}

	@Override
	public int compareTo(GreedyCheapestPath o) {
		return Double.compare(this.getSpecificValue(), o.getSpecificValue());
	}
}