package game.entities;

import java.util.ArrayList;

import java.util.List;

import game.CompareEnemyCheapestPathCost;
import game.EnemyCheapestPath;

public class SimpleEnemyArmy extends EnemyArmy<SimpleEnemy, EnemyCheapestPath> {

	@Override
	public void findPath() {

		List<EnemyCheapestPath> paths = new ArrayList<EnemyCheapestPath>();

		for (SimpleEnemy enemy : getEnemies()) {
			// Impedir inimigos de entrarem uns nos outros
			lockOtherEnemies(enemy);

			// Caminho do inimigo
			for (Entity target : getTargets()) {
				EnemyCheapestPath enemyPath = new EnemyCheapestPath(enemy, target, getGrid());
				if (enemyPath.getValid()) {
					paths.add(enemyPath);
				}
			}

			// Reverter mudança
			unlockAllEnemies();

			getGrid().setVisitedToEmpty();
		}

		if (!paths.isEmpty()) {
			filterPathsByEnemies(paths, new CompareEnemyCheapestPathCost());
		} else {
			getOrderedPaths().clear();
		}

	}

}
