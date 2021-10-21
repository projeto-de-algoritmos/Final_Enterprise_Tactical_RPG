package graphs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import graphs.adjlist.dir.AdjListDirGraph;
import graphs.adjlist.dir.AdjListDirGraphCheapestPath;
import graphs.adjlist.dir.DirectedEdge;
import graphs.exceptions.GraphException;

class TestDirGraph {

	@Test
	void testSimpleGraph(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		AdjListDirGraph<Integer, Integer> graph = new AdjListDirGraph<Integer, Integer>();

		Integer n0 = Integer.valueOf(0);
		Integer n1 = Integer.valueOf(1);
		Integer n2 = Integer.valueOf(2);

		Integer cn0n1 = Integer.valueOf(10);
		Integer cn1n2 = Integer.valueOf(20);
		Integer cn2n0 = Integer.valueOf(30);

		assertDoesNotThrow(() -> {
			graph.addNode(n0);
			graph.addNode(n1);
			graph.addNode(n2);
		});

		List<DirectedEdge<Integer, Integer>> edges = new ArrayList<DirectedEdge<Integer, Integer>>();

		edges.add(new DirectedEdge<Integer, Integer>(n0, n1, cn0n1));
		edges.add(new DirectedEdge<Integer, Integer>(n1, n2, cn1n2));
		edges.add(new DirectedEdge<Integer, Integer>(n2, n0, cn2n0));

		for (DirectedEdge<Integer, Integer> edge : edges) {
			assertDoesNotThrow(() -> {
				graph.addEdge(edge);
			});
		}

		List<DirectedEdge<Integer, Integer>> expectedEdges = new ArrayList<DirectedEdge<Integer, Integer>>();
		for (DirectedEdge<Integer, Integer> e : edges) {
			expectedEdges.add(new DirectedEdge<Integer, Integer>(e.getV(), e.getW(), e.getC()));
		}

		List<DirectedEdge<Integer, Integer>> caughtEdges = graph.getEdges();

		assertEquals(edges.size(), graph.getEdgesAmount());

		graph.printDotFile();

		Boolean allFound = true;

		for (DirectedEdge<Integer, Integer> e0 : expectedEdges) {
			Integer v0 = e0.getV();
			Integer w0 = e0.getW();
			Integer c0 = e0.getC();
			Boolean edgeFound = false;

			for (DirectedEdge<Integer, Integer> e1 : caughtEdges) {
				Integer v1 = e1.getV();
				Integer w1 = e1.getW();
				Integer c1 = e1.getC();

				if (v0.equals(v1) && w0.equals(w1) && c0.equals(c1)) {
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
	void testDijkstraTreeEx1(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		assertDoesNotThrow(() -> dijkstraTreeEx1());
	}

	private void dijkstraTreeEx1() throws GraphException {
		AdjListDirGraph<Integer, Integer> graph = new AdjListDirGraph<Integer, Integer>();

		Integer n0 = Integer.valueOf(0);
		Integer n1 = Integer.valueOf(1);
		Integer n2 = Integer.valueOf(2);
		Integer n3 = Integer.valueOf(3);
		Integer n4 = Integer.valueOf(4);
		Integer n5 = Integer.valueOf(5);

		Integer cn0n1 = Integer.valueOf(10);
		Integer cn0n2 = Integer.valueOf(11);

		Integer cn1n3 = Integer.valueOf(20);
		Integer cn1n4 = Integer.valueOf(15);

		Integer cn2n3 = Integer.valueOf(20);
		Integer cn2n4 = Integer.valueOf(5);

		Integer cn3n5 = Integer.valueOf(1);
		Integer cn4n5 = Integer.valueOf(6);

		assertDoesNotThrow(() -> {
			graph.addNode(n0);
			graph.addNode(n1);
			graph.addNode(n2);
			graph.addNode(n3);
			graph.addNode(n4);
			graph.addNode(n5);
		});

		List<DirectedEdge<Integer, Integer>> edges = new ArrayList<DirectedEdge<Integer, Integer>>();

		edges.add(new DirectedEdge<Integer, Integer>(n0, n1, cn0n1));
		edges.add(new DirectedEdge<Integer, Integer>(n0, n2, cn0n2));

		edges.add(new DirectedEdge<Integer, Integer>(n1, n3, cn1n3));
		edges.add(new DirectedEdge<Integer, Integer>(n1, n4, cn1n4));

		edges.add(new DirectedEdge<Integer, Integer>(n2, n3, cn2n3));
		edges.add(new DirectedEdge<Integer, Integer>(n2, n4, cn2n4));

		edges.add(new DirectedEdge<Integer, Integer>(n3, n5, cn3n5));
		edges.add(new DirectedEdge<Integer, Integer>(n4, n5, cn4n5));

		for (DirectedEdge<Integer, Integer> edge : edges) {
			assertDoesNotThrow(() -> {
				graph.addEdge(edge);
			});
		}

		List<DirectedEdge<Integer, Integer>> expectedEdges = new ArrayList<DirectedEdge<Integer, Integer>>();
		for (DirectedEdge<Integer, Integer> e : edges) {
			expectedEdges.add(new DirectedEdge<Integer, Integer>(e.getV(), e.getW(), e.getC()));
		}

		List<DirectedEdge<Integer, Integer>> caughtEdges = graph.getEdges();

		assertEquals(edges.size(), graph.getEdgesAmount());

		graph.printDotFile();

		Boolean allFound = true;

		for (DirectedEdge<Integer, Integer> e0 : expectedEdges) {
			Integer v0 = e0.getV();
			Integer w0 = e0.getW();
			Integer c0 = e0.getC();
			Boolean edgeFound = false;

			for (DirectedEdge<Integer, Integer> e1 : caughtEdges) {
				Integer v1 = e1.getV();
				Integer w1 = e1.getW();
				Integer c1 = e1.getC();

				if (v0.equals(v1) && w0.equals(w1) && c0.equals(c1)) {
					edgeFound = true;
				}
			}

			if (!edgeFound) {
				allFound = false;
			}
		}

		assertTrue(allFound);

		System.out.println("Dijkstra CPT");

		AdjListDirGraph<Integer, Integer> tree = null;

		tree = graph.dijkstra(n0, 0, Integer.MAX_VALUE, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 < o2) {
					return -1;
				}

				if (o1 > o2) {
					return 1;
				}

				return 0;
			}

		}, (Integer a, Integer b) -> a + b);

		assertNotNull(tree);

		assertTrue(tree.getNodesAmount() > 0);
		assertTrue(tree.getEdgesAmount() > 0);

		tree.printDotFile();

		System.out.println("Dijkstra CPT to node " + n5);
		AdjListDirGraphCheapestPath<Integer, Integer> cpt = graph.dijkstraPath(n0, n5, 0, Integer.MAX_VALUE,
				new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						if (o1 < o2) {
							return -1;
						}

						if (o1 > o2) {
							return 1;
						}

						return 0;
					}

				}, (Integer a, Integer b) -> a + b);

		assertNotNull(cpt);
		cpt.getTree().printDotFile();

		assertEquals(22, cpt.getTotalCost());

		System.out.println("cost to " + n0 + " to " + n5 + ":" + cpt.getTotalCost());
	}

}
