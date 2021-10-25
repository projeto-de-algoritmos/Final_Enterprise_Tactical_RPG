package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.GreedyCheapestPath;

public class GreedyEnemyArmy extends EnemyArmy<GreedyEnemy, GreedyCheapestPath> {
	private Integer greedyArmyMoveBudget;

	/**
	 * Encontra caminho para os inimigos ambiciosos usando o algoritmo da mochila
	 * com itens divisíveis (fractional knapsack) O algoritmo considera como peso o
	 * número de casas a mover, o valor é o custo do movimento (quanto mais alto o
	 * custo, mais perto do jogador, pois o caminho traçado é o mais curto), e o
	 * valor específico é a divisão entre o valor e o peso (é mais valioso um
	 * movimento que chegue o mais perto do jogador no menor número de casas)
	 */
	@Override
	public void findPath() {
		Integer maxWeight = getGreedyArmyMoveBudget();
		
		// used moves
		Integer currWeight = 0;

		List<GreedyCheapestPath> items = new ArrayList<GreedyCheapestPath>();
		List<GreedyCheapestPath> paths = new ArrayList<GreedyCheapestPath>();

		for (GreedyEnemy enemy : getEnemies()) {
			// Impedir inimigos de entrarem uns nos outros
			lockOtherEnemies(enemy);

			GreedyCheapestPath item = new GreedyCheapestPath(enemy, getTargets().get(0), getGrid());

			if (item.getValid()) {
				items.add(item);
			}

			// Reverter mudança
			unlockAllEnemies();
		}

		items.sort(null);

		for (GreedyCheapestPath item : items) {
			Integer lastPos;
			if (currWeight + item.getWeight() <= maxWeight) {
				lastPos = item.getPath().getPath().size() - 1;

				// pega o caminho inteiro e adiciona o peso
				paths.add(item);

				currWeight += item.getWeight();
			} else {
				Integer remainder = maxWeight - currWeight;
				lastPos = item.getPath().getPath().size() > remainder ? remainder : item.getPath().getPath().size();
				
				Integer size = item.getPath().getPath().size() - 1;
				
				// aparar posições extras
				for (int i = size; i > lastPos; i--) {
					item.getPath().getPath().remove(i);
				}

				paths.add(item);

				break;
			}
		}

		if (!paths.isEmpty()) {
			setOrderedPaths(paths);
		} else {
			getOrderedPaths().clear();
		}

	}

	/**
	 * @return the greedyArmyMoveBudget
	 */
	public Integer getGreedyArmyMoveBudget() {
		return greedyArmyMoveBudget;
	}

	/**
	 * @param greedyArmyMoveBudget the greedyArmyMoveBudget to set
	 */
	public void setGreedyArmyMoveBudget(Integer greedyArmyMoveBudget) {
		this.greedyArmyMoveBudget = greedyArmyMoveBudget;
	}

}
