package game;

import java.util.Comparator;
import java.util.function.BinaryOperator;

import game.entities.Entity;
import game.entities.WISEnemy;
import game.extra_algorithms.WeightedIntervalSchedulingTask;
import graphs.CheapestPath;
import graphs.GraphMatrix;
import graphs.Position;

public class WISCheapestPath extends EnemyCheapestPath
		implements WeightedIntervalSchedulingTask<WISCheapestPath, Integer, Integer> {
	private final WISEnemy wisEnemy;
	private final CheapestPath<Position, Integer> path;
	private final Integer weight;
	private final Integer cost;
	private final Integer start;
	private final Integer end;
	private final Boolean valid;
	private final Comparator<Integer> timeComparator;
	private final Comparator<Integer> weightComparator;
	private final Comparator<WISCheapestPath> timeComparatorToSort;
	private GraphMatrix<Integer, Integer> grid;

	public WISCheapestPath(WISEnemy wisEnemy, Entity player, Integer start, GraphMatrix<Integer, Integer> grid) {
		super(wisEnemy, player, grid);
		this.wisEnemy = wisEnemy;
		this.grid = grid;
		this.path = this.grid.dijkstra(new Position(wisEnemy.getGridX(), wisEnemy.getGridY()),
				new Position(player.getGridX(), player.getGridY()));
		
		if (this.path == null) {
			this.weight = null;
			this.cost = null;
			this.start = start;
			this.end = null;
			this.valid = false;
		} else {
			this.weight = path.getPath().size();
			this.cost = path.getTotalCost();
			this.start = start;
			this.end = this.start + this.cost / wisEnemy.getMoves();
			this.valid = true;
		}

		this.weightComparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		};

		this.timeComparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		};

		this.timeComparatorToSort = new CompareWISCheapestPathWeight();
	}

	/**
	 * @return the cost
	 */
	public Integer getCost() {
		return cost;
	}

	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public Integer getEnd() {
		return end;
	}

	/**
	 * @return the greedyEnemy
	 */
	public WISEnemy getWisEnemy() {
		return wisEnemy;
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
	 * @return the valid
	 */
	@Override
	public Boolean getValid() {
		return valid;
	}

	@Override
	public Comparator<Integer> getTimeComparator() {
		return timeComparator;
	}

	@Override
	public Comparator<Integer> getWeightComparator() {
		return weightComparator;
	}

	@Override
	public Comparator<WISCheapestPath> getTimeComparatorToSort() {
		return timeComparatorToSort;
	}

	@Override
	public BinaryOperator<Integer> getWeightAdder() {
		return (Integer a, Integer b) -> Integer.sum(a, b);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WISCheapestPath [weight=");
		builder.append(weight);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", start=");
		builder.append(start);
		builder.append(", end=");
		builder.append(end);
		builder.append(", valid=");
		builder.append(valid);
		builder.append("]");
		return builder.toString();
	}
}
