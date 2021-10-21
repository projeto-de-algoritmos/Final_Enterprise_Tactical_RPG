package graphs;

public class DefaultMatrixElement<ValueT, CostT> {
	private ValueT value;
	private CostT cost;

	/**
	 * @param value
	 * @param cost
	 */
	public DefaultMatrixElement(ValueT value, CostT cost) {
		this.value = value;
		this.cost = cost;
	}

	/**
	 * @return the value
	 */
	public ValueT getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(ValueT value) {
		this.value = value;
	}

	/**
	 * @return the cost
	 */
	public CostT getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(CostT cost) {
		this.cost = cost;
	}

	/**
	 * @param value
	 * @param cost
	 */
	public void setElement(ValueT value, CostT cost) {
		setValue(value);
		setCost(cost);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[v=");
		builder.append(getValue());
		builder.append(", c=");
		builder.append(getCost());
		builder.append("]");
		return builder.toString();
	}
}
