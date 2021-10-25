package game;

import java.util.Comparator;

public class CompareGreedyCheapestPathCost implements Comparator<GreedyCheapestPath> {

	@Override
	public int compare(GreedyCheapestPath o1, GreedyCheapestPath o2) {
		if (o1.getPath().getTotalCost() < o2.getPath().getTotalCost()) {
			return -1;
		}

		if (o1.getPath().getTotalCost() > o2.getPath().getTotalCost()) {
			return 1;
		}

		return 0;
	}

}
