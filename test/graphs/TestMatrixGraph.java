package graphs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class TestMatrixGraph {

	@Test
	void simpleMatrixAllDFS(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());

		final int sizeX = 3;
		final int sizeY = 3;
		final int EMPTY = 0;
		final int VISITED = 1;
		final int FORBIDDEN = 2;
		final int initialCost = 0;
		final int minimumCost = 0;
		final int maximumCost = Integer.MAX_VALUE;
		final Comparator<Integer> costComparator = new Comparator<Integer>() {
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
		};
		final BinaryOperator<Integer> costAdder = (Integer a, Integer b) -> a + b;

		GraphMatrix<Integer, Integer> gm = new GraphMatrix<Integer, Integer>(sizeX, sizeY, EMPTY, VISITED, FORBIDDEN,
				initialCost, minimumCost, maximumCost, costComparator, costAdder);

		assertEquals(EMPTY, gm.getEMPTY());
		assertEquals(VISITED, gm.getVISITED());
		assertEquals(FORBIDDEN, gm.getFORBIDDEN());
		assertEquals(sizeX, gm.getSizeX());
		assertEquals(sizeY, gm.getSizeY());

		gm.printMatrix();
		List<Position> visited = gm.bfs();
		gm.printMatrix();

		assertNotNull(visited);
		System.out.println("visited");
		for (Position p : visited) {
			System.out.println(p.getPosX() + " " + p.getPosY());
		}

		assertEquals(sizeX * sizeY, visited.size());
	}

	@Test
	void simpleMatrixDFSStart(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());

		final int sizeX = 10;
		final int sizeY = 10;
		final int EMPTY = 0;
		final int VISITED = 1;
		final int FORBIDDEN = 2;
		final int initialCost = 0;
		final int minimumCost = 0;
		final int maximumCost = Integer.MAX_VALUE;
		final Comparator<Integer> costComparator = new Comparator<Integer>() {
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
		};
		final BinaryOperator<Integer> costAdder = (Integer a, Integer b) -> a + b;

		GraphMatrix<Integer, Integer> gm = new GraphMatrix<Integer, Integer>(sizeX, sizeY, EMPTY, VISITED, FORBIDDEN,
				initialCost, minimumCost, maximumCost, costComparator, costAdder);

		assertEquals(EMPTY, gm.getEMPTY());
		assertEquals(VISITED, gm.getVISITED());
		assertEquals(FORBIDDEN, gm.getFORBIDDEN());
		assertEquals(sizeX, gm.getSizeX());
		assertEquals(sizeY, gm.getSizeY());

		gm.printMatrix();
		List<Position> visited = gm.bfs(new Position(sizeX / 2, sizeY / 2));
		gm.printMatrix();

		assertNotNull(visited);
		System.out.println("visited");
		for (Position p : visited) {
			System.out.println(p.getPosX() + " " + p.getPosY());
		}

		assertEquals(sizeX * sizeY, visited.size());
	}

	@Test
	void simpleMatrixDFSStartEnd(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());

		final int sizeX = 10;
		final int sizeY = 10;
		final int EMPTY = 0;
		final int VISITED = 1;
		final int FORBIDDEN = 2;
		final int initialCost = 0;
		final int minimumCost = 0;
		final int maximumCost = Integer.MAX_VALUE;
		final Comparator<Integer> costComparator = new Comparator<Integer>() {
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
		};
		final BinaryOperator<Integer> costAdder = (Integer a, Integer b) -> a + b;

		GraphMatrix<Integer, Integer> gm = new GraphMatrix<Integer, Integer>(sizeX, sizeY, EMPTY, VISITED, FORBIDDEN,
				initialCost, minimumCost, maximumCost, costComparator, costAdder);

		assertEquals(EMPTY, gm.getEMPTY());
		assertEquals(VISITED, gm.getVISITED());
		assertEquals(FORBIDDEN, gm.getFORBIDDEN());
		assertEquals(sizeX, gm.getSizeX());
		assertEquals(sizeY, gm.getSizeY());

		gm.printMatrix();
		List<Position> path = gm.bfs(new Position(0, 0), new Position(sizeX / 2, sizeY / 2));
		gm.printMatrix();

		assertNotNull(path);
		System.out.println("visited");
		for (Position p : path) {
			System.out.println(p.getPosX() + " " + p.getPosY());
		}

		assertEquals(false, path.isEmpty());

		System.out.println("path");
		gm.setVisitedToEmpty();

		for (Position p : path) {
			gm.setElementValue(p.getPosX(), p.getPosY(), gm.getVISITED());
		}

		gm.printMatrix();
	}
}
