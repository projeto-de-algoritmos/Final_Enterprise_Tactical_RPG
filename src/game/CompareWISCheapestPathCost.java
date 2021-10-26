package game;

import java.util.Comparator;

public class CompareWISCheapestPathCost implements Comparator<WISCheapestPath> {

	@Override
	public int compare(WISCheapestPath o1, WISCheapestPath o2) {
		if (o1.getPath().getTotalCost() < o2.getPath().getTotalCost()) {
			return -1;
		}

		if (o1.getPath().getTotalCost() > o2.getPath().getTotalCost()) {
			return 1;
		}

		return 0;
	}

}
