package graphs;

import java.util.List;

public class CheapestPath<NodeT, CostT> {
	private final List<NodeT> path;
	private final CostT totalCost;

	/**
	 * @param path
	 * @param totalCost
	 */
	public CheapestPath(List<NodeT> path, CostT totalCost) {
		this.path = path;
		this.totalCost = totalCost;
	}

	/**
	 * @return the path
	 */
	public List<NodeT> getPath() {
		return path;
	}

	/**
	 * @return the totalCost
	 */
	public CostT getTotalCost() {
		return totalCost;
	}

}
