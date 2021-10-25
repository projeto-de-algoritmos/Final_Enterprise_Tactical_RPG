package game;

import java.util.Comparator;

public class CompareEnemyCheapestPathCost implements Comparator<EnemyCheapestPath> {
	@Override
	public int compare(EnemyCheapestPath o1, EnemyCheapestPath o2) {
		if (o1.getPath().getTotalCost() < o2.getPath().getTotalCost()) {
			return -1;
		}

		if (o1.getPath().getTotalCost() > o2.getPath().getTotalCost()) {
			return 1;
		}

		return 0;
	}
}