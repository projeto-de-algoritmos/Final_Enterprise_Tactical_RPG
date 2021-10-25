package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.EnemyCheapestPath;
import graphs.GraphMatrix;

public abstract class EnemyArmy<EnemyT extends SimpleEnemy, PathT extends EnemyCheapestPath> {

	private List<EnemyT> enemies;
	private List<Entity> allEnemies;
	private GraphMatrix<Integer, Integer> grid;
	private List<Entity> targets;
	private List<PathT> orderedPaths;

	public EnemyArmy() {
		this.setOrderedPaths(new ArrayList<PathT>());
	}

	/**
	 * Marca outros inimigos como casas proibidas, evitando que dois inimigos
	 * fiquem, ao mesmo tempo, em uma casa só
	 * 
	 * @param enemy
	 */
	protected void lockOtherEnemies(SimpleEnemy enemy) {
		for (Entity otherEnemy : getAllEnemies()) {
			getGrid().setElementValue(otherEnemy.getGridX(), otherEnemy.getGridY(), getGrid().getVISITED());
		}
		getGrid().setElementValue(enemy.getGridX(), enemy.getGridY(), getGrid().getEMPTY());
	}

	/**
	 * Libera a trava que impede os inimigos de estarem juntos em uma mesma casa.
	 * Sempre execute essa função após executar {@link #lockOtherEnemies(SimpleEnemy)}
	 */
	protected void unlockAllEnemies() {
		for (Entity enemy : getAllEnemies()) {
			getGrid().setElementValue(enemy.getGridX(), enemy.getGridY(), getGrid().getEMPTY());
		}
	}

	abstract public void findPath();

	/**
	 * @return the enemies
	 */
	public List<EnemyT> getEnemies() {
		return enemies;
	}

	/**
	 * @param enemies the enemies to set
	 */
	public void setEnemies(List<EnemyT> enemies) {
		this.enemies = enemies;
	}

	/**
	 * @return the grid
	 */
	public GraphMatrix<Integer, Integer> getGrid() {
		return grid;
	}

	/**
	 * @param grid the grid to set
	 */
	public void setGrid(GraphMatrix<Integer, Integer> grid) {
		this.grid = grid;
	}

	/**
	 * @return the targets
	 */
	public List<Entity> getTargets() {
		return targets;
	}

	/**
	 * @param targets the targets to set
	 */
	public void setTargets(List<Entity> targets) {
		this.targets = targets;
	}
	
	/**
	 * @param target the target to set
	 */
	public void setTarget(Entity target) {
		this.targets = new ArrayList<Entity>();
		this.targets.add(target);
	}

	/**
	 * @return the orderedPaths
	 */
	public List<PathT> getOrderedPaths() {
		return orderedPaths;
	}

	/**
	 * @param orderedPaths the orderedPaths to set
	 */
	protected void setOrderedPaths(List<PathT> orderedPaths) {
		this.orderedPaths = orderedPaths;
	}

	/**
	 * @return the allEnemies
	 */
	public List<Entity> getAllEnemies() {
		return allEnemies;
	}

	/**
	 * @param allEnemies the allEnemies to set
	 */
	public void setAllEnemies(List<Entity> allEnemies) {
		this.allEnemies = allEnemies;
	}

}
