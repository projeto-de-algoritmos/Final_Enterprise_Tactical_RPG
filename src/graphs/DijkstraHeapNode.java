package graphs;

import java.util.Objects;

class DijkstraHeapNode<NodeT, CostT> {
	private NodeT node;
	private CostT cost;

	/**
	 * @param node
	 * @param cost
	 */
	DijkstraHeapNode(NodeT node, CostT cost) {
		this.node = node;
		this.cost = cost;
	}

	/**
	 * @return the node
	 */
	NodeT getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	void setNode(NodeT node) {
		this.node = node;
	}

	/**
	 * @return the cost
	 */
	CostT getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	void setCost(CostT cost) {
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
		DijkstraHeapNode<?, ?> other = (DijkstraHeapNode<?, ?>) obj;
		return Objects.equals(node, other.node);
	}

}
