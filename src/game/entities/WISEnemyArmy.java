package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.WISCheapestPath;
import game.extra_algorithms.WeightedIntervalScheduling;
import graphs.GraphMatrix;

public class WISEnemyArmy {
	private List<WISEnemy> enemies;
	private List<Entity> allEnemies;
	private GraphMatrix<Integer, Integer> grid;
	private Entity target;
	private List<WISCheapestPath> orderedPaths;

	public WISEnemyArmy() {
		this.setOrderedPaths(new ArrayList<WISCheapestPath>());
	}

	/**
	 * Marca outros inimigos como casas proibidas, evitando que dois inimigos
	 * fiquem, ao mesmo tempo, em uma casa só
	 * 
	 * @param enemy
	 */
	private void lockOtherEnemies(Enemy enemy) {
		for (Entity otherEnemy : allEnemies) {
			grid.setElementValue(otherEnemy.getGridX(), otherEnemy.getGridY(), grid.getVISITED());
		}
		grid.setElementValue(enemy.getGridX(), enemy.getGridY(), grid.getEMPTY());
	}

	/**
	 * Libera a trava que impede os inimigos de estarem juntos em uma mesma casa.
	 * Sempre execute essa função após executar {@link #lockOtherEnemies(Enemy)}
	 */
	private void unlockAllEnemies() {
		for (Entity enemy : allEnemies) {
			grid.setElementValue(enemy.getGridX(), enemy.getGridY(), grid.getEMPTY());
		}
	}

	public void findPath() {

		List<WISCheapestPath> paths = new ArrayList<WISCheapestPath>();

		for (WISEnemy enemy : enemies) {
			// Impedir inimigos de entrarem uns nos outros
			lockOtherEnemies(enemy);

			WISCheapestPath enemyPath = new WISCheapestPath(enemy, getTarget(),
					paths.isEmpty() ? 0 : paths.get(paths.size() - 1).getEnd() + 1, grid);

			if (enemyPath.getValid()) {
				paths.add(enemyPath);
			}

			// Reverter mudança
			unlockAllEnemies();
		}

		if (!paths.isEmpty()) {
			WeightedIntervalScheduling<WISCheapestPath, Integer, Integer> wis = new WeightedIntervalScheduling<WISCheapestPath, Integer, Integer>(
					paths, 0);
			wis.compute();
			setOrderedPaths(wis.getOrderedTasks());
		} else {
			orderedPaths.clear();
		}
	}

	/**
	 * @return the enemies
	 */
	public List<WISEnemy> getEnemies() {
		return enemies;
	}

	/**
	 * @param enemies the enemies to set
	 */
	public void setEnemies(List<WISEnemy> enemies) {
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
	 * @return the target
	 */
	public Entity getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(Entity target) {
		this.target = target;
	}

	/**
	 * @return the orderedPaths
	 */
	public List<WISCheapestPath> getOrderedPaths() {
		return orderedPaths;
	}

	/**
	 * @param orderedPaths the orderedPaths to set
	 */
	private void setOrderedPaths(List<WISCheapestPath> orderedPaths) {
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
