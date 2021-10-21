package graphs.adjlist.dir;

import java.util.Objects;

class AdjListDirGraphDijkstraHeapNode<NodeT, CostT> {

	private NodeT node;
	private CostT cost;

	protected AdjListDirGraphDijkstraHeapNode(NodeT node, CostT cost) {
		this.node = node;
		this.cost = cost;
	}

	/**
	 * @return the node
	 */
	protected NodeT getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	protected void setNode(NodeT node) {
		this.node = node;
	}

	/**
	 * @return the cost
	 */
	protected CostT getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	protected void setCost(CostT cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(node);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdjListDirGraphDijkstraHeapNode<?, ?> other = (AdjListDirGraphDijkstraHeapNode<?, ?>) obj;
		return Objects.equals(node, other.node);
	}
}
