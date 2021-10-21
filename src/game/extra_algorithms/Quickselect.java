package game.extra_algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Quickselect<ItemT> {
	final private Comparator<ItemT> comparator;

	/*
	 * Supõe comparador padrão para ordenar de forma ascendente
	 */
	public Quickselect(Comparator<ItemT> comparator) {
		this.comparator = comparator;
	}

	private Boolean lesseq(ItemT a, ItemT b) {
		return comparator.compare(a, b) <= 0;
	}

	/*
	 * Considera o pivô inicialmente no final do vetor
	 * Particiona de modo a que os elementos menores do vetor fiquem à
	 * esquerda do pivô, e os maiores, à direita
	 */
	private Integer partition(List<ItemT> v, Integer l, Integer r) {
		// Pivô inicia no final do vetor
		ItemT pivot = v.get(r);
		// Posição que o pivô deverá ocupar ao final
		Integer pivotIndex = l;
		for (Integer k = l; k < r; k++) {
			if (lesseq(v.get(k), pivot)) {
				Collections.swap(v, k, pivotIndex);
				pivotIndex++;
			}
		}

		// Colocar o pivô na posição correta
		Collections.swap(v, pivotIndex, r);

		return pivotIndex;
	}

	public void select(List<ItemT> v, Integer l, Integer r, Integer k) {
		if (r <= l)
			return;

		Integer pivotIndex = partition(v, l, r);

		if (pivotIndex > k)
			select(v, l, pivotIndex - 1, k);
		if (pivotIndex < k)
			select(v, pivotIndex + 1, r, k);
	}

	public void select(List<ItemT> v, Integer k) {
		select(v, 0, v.size() - 1, k);
	}

	public List<ItemT> getMedian(List<ItemT> v) {
		List<ItemT> values = new ArrayList<ItemT>();

		if (v.size() % 2 != 0) {
			select(v, v.size() / 2);
			values.add(v.get(v.size() / 2));
		} else {
			select(v, v.size() / 2);
			values.add(v.get(v.size() / 2));

			select(v, (v.size() - 1) / 2);
			values.add(v.get((v.size() - 1) / 2));
		}

		return values;
	}

}
