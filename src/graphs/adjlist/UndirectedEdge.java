package graphs.adjlist;

public class UndirectedEdge<NodeT> {
	private NodeT v;
	private NodeT w;

	public UndirectedEdge(NodeT v, NodeT w) {
		this.v = v;
		this.w = w;
	}

	public NodeT getV() {
		return v;
	}

	public NodeT getW() {
		return w;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof UndirectedEdge<?>)) {
			return false;
		}

		@SuppressWarnings("unchecked")
		UndirectedEdge<NodeT> e = (UndirectedEdge<NodeT>) obj;

		return (e.getV().equals(getV()) && e.getW().equals(getW()))
				|| (e.getV().equals(getW()) && e.getW().equals(getV()));
	}
}