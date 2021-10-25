package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.EnemyCheapestPath;

public class SimpleEnemyArmy extends EnemyArmy<SimpleEnemy, EnemyCheapestPath> {

	@Override
	public void findPath() {

		List<EnemyCheapestPath> paths = new ArrayList<EnemyCheapestPath>();

		for (SimpleEnemy enemy : getEnemies()) {
			// Impedir inimigos de entrarem uns nos outros
			lockOtherEnemies(enemy);

			// Caminho do inimigo
			EnemyCheapestPath enemyPath = new EnemyCheapestPath(enemy, getTargets().get(0), getGrid());
			if (enemyPath.getValid()) {
				paths.add(enemyPath);
			}

			// Reverter mudança
			unlockAllEnemies();

			getGrid().setVisitedToEmpty();
		}

		if (!paths.isEmpty()) {
			setOrderedPaths(paths);
		} else {
			getOrderedPaths().clear();
		}

	}

}
