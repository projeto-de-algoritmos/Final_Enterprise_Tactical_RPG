package graphs.adjlist.dir;

public class AdjListDirGraphCheapestPath<NodeT, CostT> {
	private final AdjListDirGraph<NodeT, CostT> tree;
	private final CostT totalCost;

	/**
	 * @param tree
	 * @param totalCost
	 */
	public AdjListDirGraphCheapestPath(AdjListDirGraph<NodeT, CostT> tree, CostT totalCost) {
		this.tree = tree;
		this.totalCost = totalCost;
	}

	/**
	 * @return the tree
	 */
	public AdjListDirGraph<NodeT, CostT> getTree() {
		return tree;
	}

	/**
	 * @return the totalCost
	 */
	public CostT getTotalCost() {
		return totalCost;
	}
}
