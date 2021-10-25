package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.WISCheapestPath;
import game.extra_algorithms.WeightedIntervalScheduling;

public class WISEnemyArmy extends EnemyArmy<WISEnemy, WISCheapestPath> {
	public WISEnemyArmy() {
		this.setOrderedPaths(new ArrayList<WISCheapestPath>());
	}

	public void findPath() {

		List<WISCheapestPath> paths = new ArrayList<WISCheapestPath>();

		for (WISEnemy enemy : getEnemies()) {
			// Impedir inimigos de entrarem uns nos outros
			lockOtherEnemies(enemy);

			for (Entity target : getTargets()) {
				WISCheapestPath enemyPath = new WISCheapestPath(enemy, target,
						paths.isEmpty() ? 0 : paths.get(paths.size() - 1).getEnd() + 1, getGrid());

				if (enemyPath.getValid()) {
					paths.add(enemyPath);
				}
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
			getOrderedPaths().clear();
		}
	}
}
