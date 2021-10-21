package graphs.adjlist.dir;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.function.BinaryOperator;

import graphs.exceptions.GraphException;
import graphs.exceptions.InexistentNode;
import graphs.exceptions.NodeAlreadyExists;

public class AdjListDirGraph<NodeT, CostT> {
	private Integer nodesAmount = 0;
	private Integer edgesAmount = 0;

	private Map<NodeT, List<DirectedEdge<NodeT, CostT>>> adj = new HashMap<NodeT, List<DirectedEdge<NodeT, CostT>>>();

	public void addNodes(List<NodeT> nodeDatas) throws GraphException {
		for (NodeT nodeData : nodeDatas) {
			addNode(nodeData);
		}
	}

	public void addNode(NodeT nodeData) throws GraphException {
		if (adj.containsKey(nodeData)) {
			throw new NodeAlreadyExists("Node " + nodeData + " already exists");
		}

		adj.put(nodeData, new LinkedList<DirectedEdge<NodeT, CostT>>());
		nodesAmount++;
	}

	public List<NodeT> getNodes() {
		List<NodeT> nodes = new ArrayList<NodeT>();

		for (NodeT node : adj.keySet()) {
			nodes.add(node);
		}

		return nodes;
	}

	public void addEdge(DirectedEdge<NodeT, CostT> edge) throws GraphException {
		if (!adj.containsKey(edge.getV())) {
			throw new InexistentNode("Node " + edge.getV().toString() + " does not exist");
		}

		if (!adj.containsKey(edge.getW())) {
			throw new InexistentNode("Node " + edge.getW().toString() + " does not exist");
		}

		adj.get(edge.getV()).add(edge);

		edgesAmount++;
	}

	public void addEdges(List<DirectedEdge<NodeT, CostT>> edges) throws GraphException {
		for (DirectedEdge<NodeT, CostT> edge : edges) {
			addEdge(edge);
		}
	}

	public List<DirectedEdge<NodeT, CostT>> getEdges() {
		ArrayList<DirectedEdge<NodeT, CostT>> edges = new ArrayList<DirectedEdge<NodeT, CostT>>();

		for (NodeT v : adj.keySet()) {
			for (DirectedEdge<NodeT, CostT> edge : adj.get(v)) {
				edges.add(edge);
			}
		}

		return edges;
	}

	public String getDotFile() {
		List<DirectedEdge<NodeT, CostT>> edges = getEdges();

		StringBuilder temp = new StringBuilder();

		temp.append("digraph G {\n");

		for (DirectedEdge<NodeT, CostT> edge : edges) {
			if (edge.getC() == null) {
				temp.append("\t" + edge.getV() + " -> " + edge.getW() + ";\n");
			} else {
				temp.append("\t" + edge.getV() + " -> " + edge.getW() + " [label=\"" + edge.getC() + "\"]" + ";\n");
			}
		}

		temp.append("}");

		return temp.toString();
	}

	public void printDotFile() {
		System.out.println(getDotFile());
	}

	public AdjListDirGraph<NodeT, CostT> dfs() {
		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Stack<NodeT> nodeStack = new Stack<NodeT>();

		for (NodeT node : nodeVisited.keySet()) {
			if (!nodeVisited.get(node)) {
				nodeStack.push(node);
				nodeVisited.put(node, true);

				while (!nodeStack.empty()) {
					NodeT w = nodeStack.pop();

					for (DirectedEdge<NodeT, CostT> edge : adj.get(w)) {
						if (!nodeVisited.get(edge.getW())) {
							try {
								tree.addEdge(edge);
							} catch (GraphException e) {
								e.printStackTrace();
							}

							nodeVisited.put(edge.getW(), true);
							nodeStack.push(edge.getW());
						}
					}
				}
			}
		}

		return tree;
	}

	public AdjListDirGraph<NodeT, CostT> dfs(NodeT startNode) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Stack<NodeT> nodeStack = new Stack<NodeT>();

		nodeStack.push(startNode);
		nodeVisited.put(startNode, true);

		while (!nodeStack.empty()) {
			NodeT w = nodeStack.pop();

			for (DirectedEdge<NodeT, CostT> edge : adj.get(w)) {
				if (!nodeVisited.get(edge.getW())) {
					try {
						tree.addEdge(edge);
					} catch (GraphException e) {
						e.printStackTrace();
					}

					nodeVisited.put(edge.getW(), true);
					nodeStack.push(edge.getW());
				}
			}
		}

		return tree;
	}

	public AdjListDirGraph<NodeT, CostT> dfs(NodeT startNode, NodeT endNode) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		if (!adj.containsKey(endNode)) {
			throw new InexistentNode("Node " + endNode.toString() + " does not exist");
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();
		Boolean foundEndNode = false;

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Stack<NodeT> nodeStack = new Stack<NodeT>();

		nodeStack.push(startNode);
		nodeVisited.put(startNode, true);

		while (!nodeStack.empty() && !foundEndNode) {
			NodeT w = nodeStack.pop();

			for (DirectedEdge<NodeT, CostT> edge : adj.get(w)) {
				if (!nodeVisited.get(edge.getW())) {
					try {
						tree.addEdge(edge);
					} catch (GraphException e) {
						e.printStackTrace();
					}

					nodeVisited.put(edge.getW(), true);
					nodeStack.push(edge.getW());

					if (edge.equals(endNode)) {
						foundEndNode = true;
						break;
					}
				}
			}
		}

		return tree;
	}

	public AdjListDirGraph<NodeT, CostT> dfsPath(NodeT startNode, NodeT endNode) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		if (!adj.containsKey(endNode)) {
			throw new InexistentNode("Node " + endNode.toString() + " does not exist");
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();
		Map<NodeT, NodeT> parents = new HashMap<NodeT, NodeT>();
		Boolean foundEndNode = false;

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Stack<NodeT> nodeStack = new Stack<NodeT>();

		nodeStack.push(startNode);
		nodeVisited.put(startNode, true);

		while (!nodeStack.isEmpty() && !foundEndNode) {
			NodeT w = nodeStack.pop();

			for (DirectedEdge<NodeT, CostT> edge : adj.get(w)) {
				if (!nodeVisited.get(edge.getW())) {
					nodeVisited.put(edge.getW(), true);
					nodeStack.push(edge.getW());

					parents.put(edge.getW(), w);

					if (edge.getW().equals(endNode)) {
						foundEndNode = true;
						break;
					}
				}
			}
		}

		if (foundEndNode) {
			NodeT first = endNode;
			NodeT last = parents.get(endNode);

			while (!last.equals(startNode)) {
				tree.addEdge(new DirectedEdge<NodeT, CostT>(last, first));
				first = last;
				last = parents.get(last);
			}
		}

		return tree;
	}

	public AdjListDirGraph<NodeT, CostT> bfs() {
		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Queue<NodeT> nodeQueue = new LinkedList<NodeT>();

		for (NodeT node : nodeVisited.keySet()) {
			if (!nodeVisited.get(node)) {
				nodeQueue.add(node);
				nodeVisited.put(node, true);

				while (!nodeQueue.isEmpty()) {
					NodeT w = nodeQueue.remove();

					for (DirectedEdge<NodeT, CostT> edge : adj.get(w)) {
						if (!nodeVisited.get(edge.getW())) {
							try {
								tree.addEdge(edge);
							} catch (GraphException e) {
								e.printStackTrace();
							}

							nodeVisited.put(edge.getW(), true);
							nodeQueue.add(edge.getW());
						}
					}
				}
			}
		}

		return tree;
	}

	public AdjListDirGraph<NodeT, CostT> bfs(NodeT startNode) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Queue<NodeT> nodeQueue = new LinkedList<NodeT>();

		nodeQueue.add(startNode);
		nodeVisited.put(startNode, true);

		while (!nodeQueue.isEmpty()) {
			NodeT w = nodeQueue.remove();

			for (DirectedEdge<NodeT, CostT> edge : adj.get(w)) {
				if (!nodeVisited.get(edge.getW())) {
					try {
						tree.addEdge(edge);
					} catch (GraphException e) {
						e.printStackTrace();
					}

					nodeVisited.put(edge.getW(), true);
					nodeQueue.add(edge.getW());
				}
			}
		}

		return tree;
	}

	public AdjListDirGraph<NodeT, CostT> bfs(NodeT startNode, NodeT endNode) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		if (!adj.containsKey(endNode)) {
			throw new InexistentNode("Node " + endNode.toString() + " does not exist");
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();
		Boolean foundEndNode = false;

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Queue<NodeT> nodeQueue = new LinkedList<NodeT>();

		nodeQueue.add(startNode);
		nodeVisited.put(startNode, true);

		while (!nodeQueue.isEmpty() && !foundEndNode) {
			NodeT w = nodeQueue.remove();

			for (DirectedEdge<NodeT, CostT> edge : adj.get(w)) {
				if (!nodeVisited.get(edge.getW())) {
					try {
						tree.addEdge(edge);
					} catch (GraphException e) {
						e.printStackTrace();
					}

					nodeVisited.put(edge.getW(), true);
					nodeQueue.add(edge.getW());

					if (edge.getW().equals(endNode)) {
						foundEndNode = true;
						break;
					}
				}
			}
		}

		return tree;
	}

	public AdjListDirGraph<NodeT, CostT> bfsPath(NodeT startNode, NodeT endNode) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		if (!adj.containsKey(endNode)) {
			throw new InexistentNode("Node " + endNode.toString() + " does not exist");
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();
		Map<NodeT, NodeT> parents = new HashMap<NodeT, NodeT>();
		Boolean foundEndNode = false;

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Queue<NodeT> nodeQueue = new LinkedList<NodeT>();

		nodeQueue.add(startNode);
		nodeVisited.put(startNode, true);

		while (!nodeQueue.isEmpty() && !foundEndNode) {
			NodeT w = nodeQueue.remove();

			for (DirectedEdge<NodeT, CostT> edge : adj.get(w)) {
				if (!nodeVisited.get(edge.getW())) {
					nodeVisited.put(edge.getW(), true);
					nodeQueue.add(edge.getW());

					parents.put(edge.getW(), w);

					if (edge.getW().equals(endNode)) {
						foundEndNode = true;
						break;
					}
				}
			}
		}

		if (foundEndNode) {
			NodeT first = endNode;
			NodeT last = parents.get(endNode);

			while (!last.equals(startNode)) {
				tree.addEdge(new DirectedEdge<NodeT, CostT>(last, first));
				first = last;
				last = parents.get(last);
			}
		}

		return tree;
	}

	public AdjListDirGraph<NodeT, CostT> getAnyCycle() {
		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();
		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();
		Map<NodeT, NodeT> parents = new HashMap<NodeT, NodeT>();
		NodeT firstNode = null;
		Boolean first = true;

		// In the beginning all nodes are their own parents
		for (NodeT node : getNodes()) {
			parents.put(node, node);
		}

		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		Queue<NodeT> nodeQueue = new LinkedList<NodeT>();

		for (NodeT node : nodeVisited.keySet()) {
			if (!nodeVisited.get(node)) {
				if (first) {
					first = false;
					firstNode = node;
				}

				nodeQueue.add(node);
				nodeVisited.put(node, true);

				while (!nodeQueue.isEmpty()) {
					NodeT w = nodeQueue.remove();

					for (DirectedEdge<NodeT, CostT> edge : adj.get(w)) {
						if (!nodeVisited.get(edge.getW())) {
							try {
								tree.addEdge(edge);
							} catch (GraphException e) {
								e.printStackTrace();
							}

							parents.put(edge.getW(), w);

							nodeVisited.put(edge.getW(), true);
							nodeQueue.add(edge.getW());
						} else if (!parents.get(w).equals(edge.getW())) {
							List<DirectedEdge<NodeT, CostT>> edgesV = new ArrayList<DirectedEdge<NodeT, CostT>>();
							List<DirectedEdge<NodeT, CostT>> edgesW = new ArrayList<DirectedEdge<NodeT, CostT>>();
							List<DirectedEdge<NodeT, CostT>> edgesCycles = new ArrayList<DirectedEdge<NodeT, CostT>>();

							List<DirectedEdge<NodeT, CostT>> edgesVCopy = null;
							List<DirectedEdge<NodeT, CostT>> edgesWCopy = null;

							tree.printDotFile();

							try {
								edgesV = tree.bfsPath(firstNode, edge.getW()).getEdges();
								edgesW = tree.bfsPath(firstNode, w).getEdges();
							} catch (GraphException e) {
								e.printStackTrace();
							}

							// add the current connection to one of the trees
							edgesV.add(new DirectedEdge<NodeT, CostT>(w, edge.getW()));

							edgesVCopy = new ArrayList<DirectedEdge<NodeT, CostT>>(edgesV);
							edgesWCopy = new ArrayList<DirectedEdge<NodeT, CostT>>(edgesW);

							// Remove the equal edges
							edgesVCopy.removeAll(edgesW);
							edgesWCopy.removeAll(edgesV);

							edgesCycles.addAll(edgesVCopy);
							edgesCycles.addAll(edgesWCopy);

							AdjListDirGraph<NodeT, CostT> cycle = new AdjListDirGraph<NodeT, CostT>();

							try {
								cycle.addNodes(getNodes());
								cycle.addEdges(edgesCycles);
							} catch (GraphException e) {
								e.printStackTrace();
							}

							return cycle;
						}
					}
				}
			}
		}

		return null;
	}

	public AdjListDirGraph<NodeT, CostT> dijkstra(NodeT startNode, CostT minimumCost, CostT infinteCost,
			Comparator<CostT> costComparator, BinaryOperator<CostT> sumFunc) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		Map<NodeT, NodeT> parents = new HashMap<NodeT, NodeT>();
		for (NodeT node : getNodes()) {
			parents.put(node, null);
		}

		Map<NodeT, CostT> costs = new HashMap<NodeT, CostT>();
		for (NodeT node : getNodes()) {
			costs.put(node, infinteCost);
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();
		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		PriorityQueue<AdjListDirGraphDijkstraHeapNode<NodeT, CostT>> minHeap = new PriorityQueue<AdjListDirGraphDijkstraHeapNode<NodeT, CostT>>(
				new Comparator<AdjListDirGraphDijkstraHeapNode<NodeT, CostT>>() {
					@Override
					public int compare(AdjListDirGraphDijkstraHeapNode<NodeT, CostT> o1,
							AdjListDirGraphDijkstraHeapNode<NodeT, CostT> o2) {
						return costComparator.compare(o1.getCost(), o2.getCost());
					}
				});

		parents.put(startNode, startNode);
		costs.put(startNode, minimumCost);
		minHeap.add(new AdjListDirGraphDijkstraHeapNode<NodeT, CostT>(startNode, costs.get(startNode)));

		while (!minHeap.isEmpty()) {
			AdjListDirGraphDijkstraHeapNode<NodeT, CostT> heapElement = minHeap.remove();
			NodeT nodeV = heapElement.getNode();
			CostT nodeVCost = heapElement.getCost();

			for (DirectedEdge<NodeT, CostT> edge : adj.get(nodeV)) {
				NodeT nodeW = edge.getW();
				if (nodeVisited.get(nodeW)) {
					continue;
				}

				if (costComparator.compare(sumFunc.apply(nodeVCost, edge.getC()), costs.get(nodeW)) < 0) {
					costs.put(nodeW, sumFunc.apply(nodeVCost, edge.getC()));
					parents.put(nodeW, nodeV);

					AdjListDirGraphDijkstraHeapNode<NodeT, CostT> toAdd = new AdjListDirGraphDijkstraHeapNode<NodeT, CostT>(
							nodeW, costs.get(nodeW));
					if (minHeap.contains(toAdd)) {
						// forçar a atualização da heap (PQ do Java é meio estranha)
						minHeap.remove(toAdd);
						minHeap.add(toAdd);
					} else {
						minHeap.add(toAdd);
					}
				}
			}

			nodeVisited.put(nodeV, true);
		}

		for (NodeT node : getNodes()) {
			if (parents.get(node) != null) {
				tree.addEdge(new DirectedEdge<NodeT, CostT>(parents.get(node), node, costs.get(node)));
			}
		}

		return tree;
	}

	public AdjListDirGraphCheapestPath<NodeT, CostT> dijkstraPath(NodeT startNode, NodeT endNode, CostT minimumCost,
			CostT infinteCost, Comparator<CostT> costComparator, BinaryOperator<CostT> sumFunc) throws GraphException {
		if (!adj.containsKey(startNode)) {
			throw new InexistentNode("Node " + startNode.toString() + " does not exist");
		}

		if (!adj.containsKey(endNode)) {
			throw new InexistentNode("Node " + endNode.toString() + " does not exist");
		}

		AdjListDirGraphCheapestPath<NodeT, CostT> cpt = null;

		Boolean foundEndNode = false;

		Map<NodeT, NodeT> parents = new HashMap<NodeT, NodeT>();
		for (NodeT node : getNodes()) {
			parents.put(node, null);
		}

		Map<NodeT, CostT> costs = new HashMap<NodeT, CostT>();
		for (NodeT node : getNodes()) {
			costs.put(node, infinteCost);
		}

		Map<NodeT, Boolean> nodeVisited = new HashMap<NodeT, Boolean>();

		for (NodeT node : getNodes()) {
			nodeVisited.put(node, false);
		}

		AdjListDirGraph<NodeT, CostT> tree = new AdjListDirGraph<NodeT, CostT>();
		try {
			tree.addNodes(getNodes());
		} catch (GraphException e) {
			e.printStackTrace();
		}

		PriorityQueue<AdjListDirGraphDijkstraHeapNode<NodeT, CostT>> minHeap = new PriorityQueue<AdjListDirGraphDijkstraHeapNode<NodeT, CostT>>(
				new Comparator<AdjListDirGraphDijkstraHeapNode<NodeT, CostT>>() {
					@Override
					public int compare(AdjListDirGraphDijkstraHeapNode<NodeT, CostT> o1,
							AdjListDirGraphDijkstraHeapNode<NodeT, CostT> o2) {
						return costComparator.compare(o1.getCost(), o2.getCost());
					}
				});

		parents.put(startNode, startNode);
		costs.put(startNode, minimumCost);
		minHeap.add(new AdjListDirGraphDijkstraHeapNode<NodeT, CostT>(startNode, costs.get(startNode)));

		while (!minHeap.isEmpty() && !foundEndNode) {
			AdjListDirGraphDijkstraHeapNode<NodeT, CostT> heapElement = minHeap.remove();
			NodeT nodeV = heapElement.getNode();
			CostT nodeVCost = heapElement.getCost();

			for (DirectedEdge<NodeT, CostT> edge : adj.get(nodeV)) {
				NodeT nodeW = edge.getW();
				if (nodeVisited.get(nodeW)) {
					continue;
				}

				if (costComparator.compare(sumFunc.apply(nodeVCost, edge.getC()), costs.get(nodeW)) < 0) {
					costs.put(nodeW, sumFunc.apply(nodeVCost, edge.getC()));
					parents.put(nodeW, nodeV);

					AdjListDirGraphDijkstraHeapNode<NodeT, CostT> toAdd = new AdjListDirGraphDijkstraHeapNode<NodeT, CostT>(
							nodeW, costs.get(nodeW));
					if (minHeap.contains(toAdd)) {
						// forçar a atualização da heap (PQ do Java é meio estranha)
						minHeap.remove(toAdd);
						minHeap.add(toAdd);
					} else {
						minHeap.add(toAdd);
					}
				}
			}

			nodeVisited.put(nodeV, true);

			if (nodeV.equals(endNode)) {
				foundEndNode = true;
				break;
			}
		}

		if (foundEndNode) {
			NodeT first = endNode;
			NodeT last = parents.get(endNode);

			while (!last.equals(startNode)) {
				tree.addEdge(new DirectedEdge<NodeT, CostT>(last, first, costs.get(first)));
				first = last;
				last = parents.get(last);
			}

			tree.addEdge(new DirectedEdge<NodeT, CostT>(last, first, costs.get(first)));

			cpt = new AdjListDirGraphCheapestPath<NodeT, CostT>(tree, costs.get(endNode));
		}

		return cpt;
	}

	public Integer getNodesAmount() {
		return nodesAmount;
	}

	public Integer getEdgesAmount() {
		return edgesAmount;
	}
}
