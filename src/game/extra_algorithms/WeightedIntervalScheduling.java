package game.extra_algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

public class WeightedIntervalScheduling<TaskT extends WeightedIntervalSchedulingTask<TaskT, TimeT, WeightT>, TimeT, WeightT> {
	private List<WeightT> cache;
	private List<Integer> lastCompatible;
	private List<TaskT> orderedtasks;
	private final BinaryOperator<WeightT> weightAdder;
	private WeightT emptyWeight;
	private List<TaskT> tasks;
	private WeightT optimalWeight;
	private final Comparator<TimeT> timeComparator;
	private final Comparator<WeightT> weightComparator;
	private final Comparator<TaskT> timeComparatorToSort;

	public WeightedIntervalScheduling(List<TaskT> tasks, WeightT emptyWeight) {
		this.emptyWeight = emptyWeight;

		// the zero-th index is invalid
		this.tasks = new ArrayList<TaskT>(tasks.size() + 1);
		this.tasks = tasks;

		this.timeComparator = tasks.get(0).getTimeComparator();
		this.weightComparator = tasks.get(0).getWeightComparator();
		this.timeComparatorToSort = tasks.get(0).getTimeComparatorToSort();

		this.weightAdder = tasks.get(0).getWeightAdder();

		this.cache = new ArrayList<WeightT>(this.tasks.size() + 1);
		this.lastCompatible = new ArrayList<Integer>(this.tasks.size() + 1);
		this.orderedtasks = new ArrayList<TaskT>(this.tasks.size());

		// empty set
		this.cache.add(0, this.emptyWeight);
		this.lastCompatible.add(0, 0);
	}

	private Integer findLatestCompatibleTask(Integer taskIndex) {
		for (int i = taskIndex - 1; i >= 1; i--) {
			if (timeComparator.compare(tasks.get(i).getEnd(), tasks.get(taskIndex).getStart()) <= 0) {
				return i;
			}
		}

		return 0;
	}

	private WeightT getThisTask(Integer i) {
		return weightAdder.apply(tasks.get(i).getWeight(), cache.get(lastCompatible.get(i)));
	}

	private WeightT doNotGetThisTask(Integer i) {
		return cache.get(i - 1);
	}

	private WeightT chooseTheBest(Integer i) {
		WeightT gotTask = getThisTask(i);
		WeightT didNotGetTask = doNotGetThisTask(i);
		return weightComparator.compare(gotTask, didNotGetTask) > 0 ? gotTask : didNotGetTask;
	}

	public void compute() {
		// order by finishing time
		// the 0-th index is invalid
		List<TaskT> newTasks = new ArrayList<TaskT>();

		tasks.sort(timeComparatorToSort);

		newTasks.add(0, null);
		newTasks.addAll(1, tasks);

		tasks = newTasks;

		// calculate compatibility
		for (int i = 1; i < tasks.size(); i++) {
			lastCompatible.add(i, findLatestCompatibleTask(i));
		}

		// compute
		for (int i = 1; i < tasks.size(); i++) {
			cache.add(i, chooseTheBest(i));
		}

		optimalWeight = cache.get(tasks.size() - 1);
	}

	private void findSolution(Integer i) {
		if (i == 0) {
			// do nothing
		} else if (weightComparator.compare(getThisTask(i), doNotGetThisTask(i)) > 0) {
			orderedtasks.add(tasks.get(i));
			findSolution(lastCompatible.get(i));
		} else {
			findSolution(i - 1);
		}
	}

	private void computeIfNecessary() {
		if (cache.size() != tasks.size()) {
			orderedtasks.clear();
			optimalWeight = emptyWeight;
			compute();
		}
	}

	public List<TaskT> getOrderedTasks() {
		computeIfNecessary();

		findSolution(tasks.size() - 1);

		return orderedtasks;
	}

	public WeightT getOptimalWeight() {
		computeIfNecessary();

		return optimalWeight;
	}

}
