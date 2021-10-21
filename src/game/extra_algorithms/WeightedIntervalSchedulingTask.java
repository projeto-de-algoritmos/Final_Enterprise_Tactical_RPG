package game.extra_algorithms;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public interface WeightedIntervalSchedulingTask<TaskT, TimeT, WeightT> {
	TimeT getStart();

	TimeT getEnd();

	WeightT getWeight();

	Comparator<TimeT> getTimeComparator();

	Comparator<WeightT> getWeightComparator();

	Comparator<TaskT> getTimeComparatorToSort();

	BinaryOperator<WeightT> getWeightAdder();
}