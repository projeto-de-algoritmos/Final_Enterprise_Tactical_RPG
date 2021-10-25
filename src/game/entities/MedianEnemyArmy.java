package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.CompareEnemyCheapestPathCost;
import game.EnemyCheapestPath;
import game.extra_algorithms.Quickselect;

public class MedianEnemyArmy extends EnemyArmy<MedianEnemy, EnemyCheapestPath> {

	/**
	 * Encontra caminho para o inimigo que está na mediana dos movimentos No caso de
	 * mediana de número par de inimigos, será escolhido aquele com o maior custo de
	 * movimentos
	 */
	@Override
	public void findPath() {
		List<EnemyCheapestPath> items = new ArrayList<EnemyCheapestPath>();
		List<EnemyCheapestPath> paths = new ArrayList<EnemyCheapestPath>();

		for (MedianEnemy enemy : getEnemies()) {
			// Impedir inimigos de entrarem uns nos outros
			lockOtherEnemies(enemy);

			for (Entity target : getTargets()) {
				EnemyCheapestPath enemyPath = new EnemyCheapestPath(enemy, target, getGrid());

				if (enemyPath.getValid()) {
					items.add(enemyPath);
				}
			}

			// Reverter mudança
			unlockAllEnemies();
		}

		if (!items.isEmpty()) {
			Quickselect<EnemyCheapestPath> quickselect = new Quickselect<EnemyCheapestPath>(
					new CompareEnemyCheapestPathCost());
			List<EnemyCheapestPath> median = quickselect.getMedian(items);
			EnemyCheapestPath choosen = median.get(median.size() - 1);

			paths.add(choosen);

			setOrderedPaths(paths);
		}

	}

}
