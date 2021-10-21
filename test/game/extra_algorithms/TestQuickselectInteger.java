package game.extra_algorithms;

import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestQuickselectInteger {
	private Quickselect<Integer> quickselect;
	private List<Integer> elements;
	private List<Integer> elementsOrdered;
	private List<Integer> medianElements;
	private List<Integer> quickselectMedianElements;
	private Integer kthElement;
	final private Comparator<Integer> comparator = new Comparator<Integer>() {
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

	private void makeElements(Integer n) {
		SecureRandom random = new SecureRandom();
		for (Integer i = 0; i < n; i++) {
			elements.add(random.nextInt());
		}
	}

	private void getMedian() {
		if (elementsOrdered.size() % 2 != 0) {
			medianElements.add(elementsOrdered.get(elementsOrdered.size() / 2));
		} else {
			medianElements.add(elementsOrdered.get(elementsOrdered.size() / 2));
			medianElements.add(elementsOrdered.get((elementsOrdered.size() - 1) / 2));
		}
	}

	private void getKthElement(Integer k) {
		kthElement = elementsOrdered.get(k);
	}

	private void printList(List<Integer> list) {
		for (Integer i = 0; i < list.size(); i++) {
			System.out.println(i + ": " + list.get(i));
		}
	}

	@SuppressWarnings("unused")
	private void printList(String msg, List<Integer> list) {
		System.out.println(msg);
		printList(list);
	}

	@BeforeEach
	private void beforeEachTest() {
		quickselect = new Quickselect<Integer>(comparator);
		elements = new ArrayList<Integer>();
		elementsOrdered = new ArrayList<Integer>();
		medianElements = new ArrayList<Integer>();
		kthElement = null;
		quickselectMedianElements = null;
	}

	// even tests
	@Test
	void testEvenSmallK0() {
		Integer elementsNum = 20;
		Integer k = 0;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testEvenSmallKEnd() {
		Integer elementsNum = 20;
		Integer k = elementsNum - 1;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testEvenSmallK01() {
		Integer elementsNum = 20;
		Integer k = elementsNum / 10;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testEvenSmallMedian() {
		Integer elementsNum = 20;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getMedian();

		quickselectMedianElements = quickselect.getMedian(elements);

		quickselectMedianElements.sort(comparator);
		medianElements.sort(comparator);

		assertTrue(medianElements.equals(quickselectMedianElements));
	}

	// large
	@Test
	void testEvenLargeK0() {
		Integer elementsNum = 2000;
		Integer k = 0;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testEvenLargeKEnd() {
		Integer elementsNum = 2000;
		Integer k = elementsNum - 1;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testEvenLargeK01() {
		Integer elementsNum = 2000;
		Integer k = elementsNum / 10;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testEvenLargeMedian() {
		Integer elementsNum = 2000;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getMedian();

		quickselectMedianElements = quickselect.getMedian(elements);

		quickselectMedianElements.sort(comparator);
		medianElements.sort(comparator);

		assertTrue(medianElements.equals(quickselectMedianElements));
	}

	// odd tests
	@Test
	void testOddSmallK0() {
		Integer elementsNum = 21;
		Integer k = 0;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testOddSmallKEnd() {
		Integer elementsNum = 21;
		Integer k = elementsNum - 1;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testOddSmallK01() {
		Integer elementsNum = 21;
		Integer k = elementsNum / 10;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testOddSmallMedian() {
		Integer elementsNum = 21;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getMedian();

		quickselectMedianElements = quickselect.getMedian(elements);

		quickselectMedianElements.sort(comparator);
		medianElements.sort(comparator);

		assertTrue(medianElements.equals(quickselectMedianElements));
	}

	// large
	@Test
	void testOddLargeK0() {
		Integer elementsNum = 2001;
		Integer k = 0;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testOddLargeKEnd() {
		Integer elementsNum = 2001;
		Integer k = elementsNum - 1;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testOddLargeK01() {
		Integer elementsNum = 2001;
		Integer k = elementsNum / 10;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getKthElement(k);

		quickselect.select(elements, k);

		assertEquals(kthElement, elements.get(k));
	}

	@Test
	void testOddLargeMedian() {
		Integer elementsNum = 2001;
		makeElements(elementsNum);
		elementsOrdered.addAll(elements);

		elementsOrdered.sort(comparator);
		getMedian();

		quickselectMedianElements = quickselect.getMedian(elements);

		quickselectMedianElements.sort(comparator);
		medianElements.sort(comparator);

		assertTrue(medianElements.equals(quickselectMedianElements));
	}
}
