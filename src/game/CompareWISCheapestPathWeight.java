package game;

import java.util.Comparator;

public class CompareWISCheapestPathWeight implements Comparator<WISCheapestPath> {
	@Override
	public int compare(WISCheapestPath o1, WISCheapestPath o2) {
		if (o1.getWeight() < o2.getWeight()) {
			return -1;
		}

		if (o1.getWeight() > o2.getWeight()) {
			return 1;
		}

		return 0;
	}
}
