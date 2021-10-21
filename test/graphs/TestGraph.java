package graphs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import graphs.adjlist.AdjListGraph;
import graphs.adjlist.UndirectedEdge;
import graphs.exceptions.NodeAlreadyExists;

class TestGraph {

	@Test
	void testNodeInsertion(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		AdjListGraph<Integer> graph = new AdjListGraph<Integer>();

		assertDoesNotThrow(() -> {
			graph.addNode(Integer.valueOf(1));
			graph.addNode(Integer.valueOf(2));
			graph.addNode(Integer.valueOf(3));
		});

		List<Integer> graphNodes = graph.getNodes();

		assertEquals(graphNodes.size(), graph.getNodesAmount());

		assertTrue(graphNodes.contains(Integer.valueOf(1)));
		assertTrue(graphNodes.contains(Integer.valueOf(2)));
		assertTrue(graphNodes.contains(Integer.valueOf(3)));
	}

	@Test
	void testNodeInsertionRepeated(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		AdjListGraph<Integer> graph = new AdjListGraph<Integer>();

		assertThrows(NodeAlreadyExists.class, () -> {
			graph.addNode(Integer.valueOf(1));
			graph.addNode(Integer.valueOf(1));
		});
	}

	@Test
	void testSimpleGraph(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		AdjListGraph<Integer> graph = new AdjListGraph<Integer>();

		Integer n0 = Integer.valueOf(0);
		Integer n1 = Integer.valueOf(1);
		Integer n2 = Integer.valueOf(2);

		assertDoesNotThrow(() -> {
			graph.addNode(n0);
			graph.addNode(n1);
			graph.addNode(n2);
		});

		List<UndirectedEdge<Integer>> edges = new ArrayList<UndirectedEdge<Integer>>();

		edges.add(new UndirectedEdge<Integer>(n0, n1));
		edges.add(new UndirectedEdge<Integer>(n1, n2));
		edges.add(new UndirectedEdge<Integer>(n2, n0));

		for (UndirectedEdge<Integer> edge : edges) {
			assertDoesNotThrow(() -> {
				graph.addEdge(edge);
			});
		}

		List<UndirectedEdge<Integer>> expectedEdges = new ArrayList<UndirectedEdge<Integer>>();
		for (UndirectedEdge<Integer> e : edges) {
			expectedEdges.add(new UndirectedEdge<Integer>(e.getV(), e.getW()));
			expectedEdges.add(new UndirectedEdge<Integer>(e.getW(), e.getV()));
		}

		List<UndirectedEdge<Integer>> caughtEdges = graph.getEdges();

		assertEquals(edges.size(), graph.getEdgesAmount());

		graph.printDotFile();

		Boolean allFound = true;

		for (UndirectedEdge<Integer> e0 : expectedEdges) {
			Integer v0 = e0.getV();
			Integer w0 = e0.getW();
			Boolean edgeFound = false;

			for (UndirectedEdge<Integer> e1 : caughtEdges) {
				Integer v1 = e1.getV();
				Integer w1 = e1.getW();

				if (v0.equals(v1) && w0.equals(w1)) {
					edgeFound = true;
				}
			}

			if (!edgeFound) {
				allFound = false;
			}
		}

		assertTrue(allFound);
	}

	@Test
	void traverseByBFS(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		AdjListGraph<Integer> graph = new AdjListGraph<Integer>();
		AdjListGraph<Integer> tree = null;

		List<Integer> nodes = new ArrayList<Integer>();

		for (int i = 0; i < 8; i++) {
			nodes.add(Integer.valueOf(i));
		}

		assertDoesNotThrow(() -> {
			graph.addNodes(nodes);
		});

		List<UndirectedEdge<Integer>> edges = new ArrayList<UndirectedEdge<Integer>>();
		edges.add(new UndirectedEdge<Integer>(nodes.get(0), nodes.get(1)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(0), nodes.get(2)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(2)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(3)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(4)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(2), nodes.get(4)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(2), nodes.get(6)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(2), nodes.get(7)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(3), nodes.get(4)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(4), nodes.get(5)));

		for (UndirectedEdge<Integer> edge : edges) {
			assertDoesNotThrow(() -> {
				graph.addEdge(edge);
			});
		}

		tree = graph.bfs();

		assertNotNull(tree);

		tree.printDotFile();
	}

	@Test
	void traverseByDFS(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		AdjListGraph<Integer> graph = new AdjListGraph<Integer>();
		AdjListGraph<Integer> tree = null;

		List<Integer> nodes = new ArrayList<Integer>();

		for (int i = 0; i < 8; i++) {
			nodes.add(Integer.valueOf(i));
		}

		assertDoesNotThrow(() -> {
			graph.addNodes(nodes);
		});

		List<UndirectedEdge<Integer>> edges = new ArrayList<UndirectedEdge<Integer>>();
		edges.add(new UndirectedEdge<Integer>(nodes.get(0), nodes.get(1)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(0), nodes.get(2)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(2)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(3)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(4)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(2), nodes.get(4)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(2), nodes.get(6)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(2), nodes.get(7)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(3), nodes.get(4)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(4), nodes.get(5)));

		for (UndirectedEdge<Integer> edge : edges) {
			assertDoesNotThrow(() -> {
				graph.addEdge(edge);
			});
		}

		tree = graph.dfs();

		assertNotNull(tree);

		tree.printDotFile();
	}

	@Test
	void getAnyCycleOnGraphWithInitialCycle(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		AdjListGraph<Integer> graph = new AdjListGraph<Integer>();
		AdjListGraph<Integer> tree = null;

		List<Integer> nodes = new ArrayList<Integer>();

		for (int i = 0; i < 8; i++) {
			nodes.add(Integer.valueOf(i));
		}

		assertDoesNotThrow(() -> {
			graph.addNodes(nodes);
		});

		List<UndirectedEdge<Integer>> edges = new ArrayList<UndirectedEdge<Integer>>();
		edges.add(new UndirectedEdge<Integer>(nodes.get(0), nodes.get(1)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(0), nodes.get(2)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(2)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(3)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(4)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(2), nodes.get(4)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(2), nodes.get(6)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(2), nodes.get(7)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(3), nodes.get(4)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(4), nodes.get(5)));

		for (UndirectedEdge<Integer> edge : edges) {
			assertDoesNotThrow(() -> {
				graph.addEdge(edge);
			});
		}

		tree = graph.getAnyCycle();

		assertNotNull(tree);

		tree.printDotFile();
	}

	@Test
	void getAnyCycleOnGraphWithNoCycles(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		AdjListGraph<Integer> graph = new AdjListGraph<Integer>();
		AdjListGraph<Integer> tree = null;

		List<Integer> nodes = new ArrayList<Integer>();

		for (int i = 0; i < 6; i++) {
			nodes.add(Integer.valueOf(i));
		}

		assertDoesNotThrow(() -> {
			graph.addNodes(nodes);
		});

		List<UndirectedEdge<Integer>> edges = new ArrayList<UndirectedEdge<Integer>>();
		edges.add(new UndirectedEdge<Integer>(nodes.get(0), nodes.get(1)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(2)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(1), nodes.get(3)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(2), nodes.get(4)));
		edges.add(new UndirectedEdge<Integer>(nodes.get(3), nodes.get(5)));

		for (UndirectedEdge<Integer> edge : edges) {
			assertDoesNotThrow(() -> {
				graph.addEdge(edge);
			});
		}

		tree = graph.getAnyCycle();

		assertNull(tree);
	}

	@Test
	void getAnyCycleOnCyclicGraph(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		AdjListGraph<Integer> graph = new AdjListGraph<Integer>();
		AdjListGraph<Integer> tree = null;

		List<Integer> nodes = new ArrayList<Integer>();

		for (int i = 0; i < 10; i++) {
			nodes.add(Integer.valueOf(i));
		}

		assertDoesNotThrow(() -> {
			graph.addNodes(nodes);
		});

		List<UndirectedEdge<Integer>> edges = new ArrayList<UndirectedEdge<Integer>>();

		for (int i = 0; i < nodes.size() - 1; i++) {
			edges.add(new UndirectedEdge<Integer>(nodes.get(i), nodes.get(i + 1)));
		}

		edges.add(new UndirectedEdge<Integer>(nodes.size() - 1, nodes.size() / 2));

		for (UndirectedEdge<Integer> edge : edges) {
			assertDoesNotThrow(() -> {
				graph.addEdge(edge);
			});
		}

		System.out.println("Original graph");
		graph.printDotFile();

		tree = graph.getAnyCycle();

		assertNotNull(tree);

		System.out.println("Cycle detected");
		tree.printDotFile();
	}
}
