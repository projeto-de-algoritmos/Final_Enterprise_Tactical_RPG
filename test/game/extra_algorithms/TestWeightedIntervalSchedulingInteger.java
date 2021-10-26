package game.extra_algorithms;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestWeightedIntervalSchedulingInteger {
	private class Task implements WeightedIntervalSchedulingTask<Task, Integer, Integer> {
		final private Integer start;
		final private Integer end;
		final private Integer weight;
		final String desc;

		Task(String desc, Integer start, Integer end, Integer weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
			this.desc = desc;
		}

		/**
		 * @return the desc
		 */
		public String getDesc() {
			return desc;
		}

		@Override
		public Integer getStart() {
			return start;
		}

		@Override
		public Integer getEnd() {
			return end;
		}

		@Override
		public Integer getWeight() {
			return weight;
		}

		@Override
		public Comparator<Integer> getTimeComparator() {
			return new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return Integer.compare(o1, o2);
				}
			};
		}

		@Override
		public Comparator<Integer> getWeightComparator() {
			return new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return Integer.compare(o1, o2);
				}
			};
		}

		@Override
		public Comparator<Task> getTimeComparatorToSort() {
			return new Comparator<Task>() {
				@Override
				public int compare(Task o1, Task o2) {
					if (o1.getEnd() < o2.getEnd()) {
						return -1;
					}

					if (o1.getEnd() > o2.getEnd()) {
						return 1;
					}

					return 0;
				}
			};
		}

		@Override
		public BinaryOperator<Integer> getWeightAdder() {
			return (Integer a, Integer b) -> Integer.sum(a, b);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Task [start=");
			builder.append(start);
			builder.append(", end=");
			builder.append(end);
			builder.append(", weight=");
			builder.append(weight);
			builder.append(", desc=");
			builder.append(getDesc());
			builder.append("]");
			return builder.toString();
		}
	}

	private WeightedIntervalScheduling<Task, Integer, Integer> wis;
	private List<Task> tasks;
	private List<Task> resultTasks;
	private List<Task> expectedTasks;
	private Integer expectedOptimalWeight;
	private Integer resultOptimalWeight;

	private void printList(List<?> l) {
		for (int i = 0; i < l.size(); i++) {
			System.out.println("[" + i + "] " + (l.get(i) == null ? null : l.get(i).toString()));
		}
	}

	@BeforeEach
	void setUp() throws Exception {
		wis = null;
		tasks = null;
		resultTasks = null;
		expectedTasks = null;
		expectedOptimalWeight = null;
		resultOptimalWeight = null;
	}

	@Test
	void testOrderedTasks() {
		tasks = new ArrayList<Task>();

		tasks.add(new Task("T " + (tasks.size() + 1), 1, 2, 50));
		tasks.add(new Task("T " + (tasks.size() + 1), 3, 5, 20));
		tasks.add(new Task("T " + (tasks.size() + 1), 6, 19, 100));
		tasks.add(new Task("T " + (tasks.size() + 1), 2, 100, 200));

		System.out.println("Tasks");
		printList(tasks);

		wis = new WeightedIntervalScheduling<Task, Integer, Integer>(tasks, 0);

		for (Task t : tasks) {
			assertNotNull(t.getTimeComparator());
			assertNotNull(t.getTimeComparatorToSort());
			assertNotNull(t.getWeightComparator());
		}

		wis.compute();

		resultTasks = wis.getOrderedTasks();

		System.out.println("result");
		printList(resultTasks);

		expectedTasks = new ArrayList<Task>();

		expectedTasks.add(tasks.get(3));
		expectedTasks.add(tasks.get(0));

		System.out.println("expected");
		printList(expectedTasks);

		assertEquals(expectedTasks, resultTasks);

		resultOptimalWeight = wis.getOptimalWeight();
		System.out.println("resultOptimalWeight");
		System.out.println(resultOptimalWeight);

		expectedOptimalWeight = 250;

		assertEquals(expectedOptimalWeight, resultOptimalWeight);
	}

}
