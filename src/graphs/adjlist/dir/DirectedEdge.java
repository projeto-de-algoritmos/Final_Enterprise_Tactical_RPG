package graphs.adjlist.dir;

public class DirectedEdge<NodeT, CostT> {
	private NodeT v;
	private NodeT w;
	private CostT c;

	public DirectedEdge(NodeT v, NodeT w, CostT c) {
		this.v = v;
		this.w = w;
		this.c = c;
	}

	public DirectedEdge(NodeT v, NodeT w) {
		this.v = v;
		this.w = w;
		this.c = null;
	}

	public NodeT getV() {
		return v;
	}

	public NodeT getW() {
		return w;
	}

	public CostT getC() {
		return c;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof DirectedEdge<?, ?>)) {
			return false;
		}

		@SuppressWarnings("unchecked")
		DirectedEdge<NodeT, CostT> e = (DirectedEdge<NodeT, CostT>) obj;

		return e.getV().equals(getV()) && e.getW().equals(getW()) && e.getC().equals(getC());
	}
}