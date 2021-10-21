package graphs;

import java.util.Objects;

public class Position {
	final private Integer posX;
	final private Integer posY;

	/**
	 * @param posX
	 * @param posY
	 */
	public Position(Integer posX, Integer posY) {
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public int hashCode() {
		return Objects.hash(posX, posY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Position)) {
			return false;
		}
		Position other = (Position) obj;
		return Objects.equals(posX, other.posX) && Objects.equals(posY, other.posY);
	}

	public Integer getPosX() {
		return posX;
	}

	public Integer getPosY() {
		return posY;
	}
}
