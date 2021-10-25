package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;

import javax.swing.JPanel;

import game.entities.SimpleEnemy;
import game.entities.SimpleEnemyArmy;
import game.entities.Entity;
import game.entities.GreedyEnemy;
import game.entities.GreedyEnemyArmy;
import game.entities.MedianEnemy;
import game.entities.MedianEnemyArmy;
import game.entities.Player;
import game.entities.WISEnemy;
import game.entities.WISEnemyArmy;
import game.extra_algorithms.Quickselect;
import graphs.CheapestPath;
import graphs.GraphMatrix;
import graphs.Position;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {	
	private class CompareWISCheapestPathEnd implements Comparator<WISCheapestPath> {
		@Override
		public int compare(WISCheapestPath o1, WISCheapestPath o2) {
			if (o1.getEnd() < o2.getEnd()) {
				return -1;
			}

			if (o1.getEnd() > o2.getEnd()) {
				return 1;
			}

			return 0;
		}
	}

	private static final long serialVersionUID = 1L;
	private static final Integer FORBIDDEN = -1;
	private static final Integer EMPTY = 0;
	private static final Integer VISITED = 1;
	private static int WIDTH;
	private static int HEIGHT;
	private List<Player> playerArmy = new ArrayList<Player>();
	private int actualPlayer = 0;
	private Map map;
	private List<Position> preview;
	private GraphMatrix<Integer, Integer> grid;
	private boolean running;
	private int lastMouseX;
	private int lastMouseY;
	private boolean inPlayer;
	private int moveCost;
	private int tileSize;

	private final int initialCost = 1;
	private final int minimumCost = 0;
	private final int maximumCost = Integer.MAX_VALUE;

	private List<SimpleEnemy> simpleEnemies = new ArrayList<SimpleEnemy>();
	private List<GreedyEnemy> greedyEnemies = new ArrayList<GreedyEnemy>();
	private List<MedianEnemy> medianEnemies = new ArrayList<MedianEnemy>();
	private List<WISEnemy> wisEnemies = new ArrayList<WISEnemy>();
	private List<Entity> allEnemies = new ArrayList<Entity>();
	
	private WISEnemyArmy wisEnemyArmy = new WISEnemyArmy();
	private SimpleEnemyArmy simpleEnemyArmy = new SimpleEnemyArmy();
	private GreedyEnemyArmy greedyEnemyArmy = new GreedyEnemyArmy();
	private MedianEnemyArmy medianEnemyArmy = new MedianEnemyArmy();

	private int sizeX;
	private int sizeY;
	final private int playerMoves = 5;
	final private int safeOffset = 6;
	final private Comparator<Integer> costComparator = new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			if (o1 < o2) {
				return -1;
			}

			if (o1 > o2) {
				return 1;
			}

			return 0;
		}
	};
	final private BinaryOperator<Integer> costAdder = (Integer a, Integer b) -> a + b;

	private int enemyMoves = 3;
	private int greedyArmyMoveBudget = enemyMoves;

	int rounds = 0;
	private boolean stepMode;
	private boolean previewVisibility = true;

	private int msDelay = 60;
	private int msLongerDelay = 2 * msDelay;
	
	private SoundPlayer soundPlayer;
	private String message;

	public Panel(int size, int width, int height, boolean stepMode, boolean soundEnabled) {
		// Step Mode
		this.stepMode = stepMode;

		message = "Your Turn";
		
		// Define os tamanhos
		WIDTH = width;
		HEIGHT = height;
		sizeX = size;
		sizeY = size;
		tileSize = HEIGHT / size;

		// Configurações do Painel
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT+tileSize));

		// Mouse Listeners
		addMouseListener(this);
		addMouseMotionListener(this);
		
		// Inicializa Grafo do Mapa
		grid = new GraphMatrix<Integer, Integer>(sizeX, sizeY, EMPTY, VISITED, FORBIDDEN, initialCost, minimumCost,
				maximumCost, costComparator, costAdder);

		// Inicializar Jogador
		int h = (int) (tileSize * 0.8); // 80% do tileSize
		int w = (int) (tileSize * 0.8); // 80% do tileSize
		int off = (tileSize - w) / 2; // Meio do tile
		playerArmy.add(new Player(playerMoves, 5, 5, tileSize, off, h, w, Color.BLUE));
		playerArmy.add(new Player(playerMoves, 5, 10, tileSize, off, h, w, Color.BLUE));

		// Inicializar inimigos
		initializeEnemies();


		// Inicializa o Preview do movimento do Jogador
		preview = new ArrayList<Position>();

		// Cria dicionário de custo/cores
		HashMap<Integer, Color> hash = new HashMap<Integer, Color>();
		hash.put(initialCost, Color.GREEN);
		hash.put(initialCost + 1, Color.YELLOW);
		hash.put(initialCost + 2, Color.ORANGE);
		
		// Inicializa os Sons
		HashMap<String, String> sounds = new HashMap<String, String>();
		if(soundEnabled) {
			sounds.put("playerMove", "assets/playermove.wav");
			sounds.put("enemyMove", "assets/enemymove.wav");
			sounds.put("death", "assets/death.wav");
			
		}
		soundPlayer = new SoundPlayer(sounds);
		
		// Inicializa Mapa
		map = new Map(grid, hash, WIDTH, HEIGHT, sizeX, sizeY);
		addRandomCosts((sizeX * sizeY) / 2, hash.size() + 1);
		addRandomForbidden(sizeX);
		for (Player player : playerArmy) {
			grid.setElementCost(player.getGridX(), player.getGridY(), initialCost);
			grid.setElementValue(player.getGridX(), player.getGridY(), EMPTY);
		}
		

		// Inicia o Jogo
		start();
	}
	
	// Inicializa inimigos
	private void initializeEnemies() {
		int minX = playerArmy.get(0).getGridX() + safeOffset;
		int minY = playerArmy.get(0).getGridY() + safeOffset;
		int maxX = sizeX;
		int maxY = sizeY;
		
		// Inicializar inimigos comuns
		int h = (int) (tileSize * 0.6); // 60% do tileSize
		int w = (int) (tileSize * 0.6); // 60% do tileSize
		int off = (tileSize - w) / 2; // Meio do tile
		for(int i=0;i<3;i++) {
			Position p = generateRandomPos(minX, minY, maxX, maxY);
			int x = p.getPosX();
			int y = p.getPosY();
			SimpleEnemy e = new SimpleEnemy(enemyMoves, x, y, tileSize, off, h, w, Color.RED);
			simpleEnemies.add(e);
			allEnemies.add(e);			
		}
		// Inicializar inimigos ambiciosos
		h = (int) (tileSize * 0.95); // 95% do tileSize
		w = (int) (tileSize * 0.95); // 95% do tileSize
		off = (tileSize - w) / 2; // Meio do tile
		for(int i=0;i<3;i++) {
			Position p = generateRandomPos(minX, minY, maxX, maxY);
			int x = p.getPosX();
			int y = p.getPosY();
			GreedyEnemy e = new GreedyEnemy(enemyMoves, x, y, tileSize, off, h, w, Color.RED);
			greedyEnemies.add(e);
			allEnemies.add(e);
		}
		// Inicializar inimigos da mediana
		h = (int) (tileSize * 0.9); // 90% do tileSize
		w = (int) (tileSize * 0.9); // 90% do tileSize
		off = (tileSize - w) / 2; // Meio do tile
		for(int i=0;i<3;i++) {
			Position p = generateRandomPos(minX, minY, maxX, maxY);
			int x = p.getPosX();
			int y = p.getPosY();
			MedianEnemy e = new MedianEnemy(enemyMoves, x, y, tileSize, off, h, w, Color.RED);
			medianEnemies.add(e);
			allEnemies.add(e);
		}
		// Inicializar inimigos do agendamento com peso
		for(int i=0;i<3;i++) {
			Position p = generateRandomPos(minX, minY, maxX, maxY);
			int x = p.getPosX();
			int y = p.getPosY();
			WISEnemy e = new WISEnemy(enemyMoves, x, y, tileSize, off, h, w, Color.RED);
			wisEnemies.add(e);
			allEnemies.add(e);
		}
		wisEnemyArmy.setEnemies(wisEnemies);
		wisEnemyArmy.setAllEnemies(allEnemies);
		
		simpleEnemyArmy.setEnemies(simpleEnemies);
		simpleEnemyArmy.setAllEnemies(allEnemies);
		
		greedyEnemyArmy.setEnemies(greedyEnemies);
		greedyEnemyArmy.setAllEnemies(allEnemies);
		greedyEnemyArmy.setGreedyArmyMoveBudget(greedyArmyMoveBudget);
		
		medianEnemyArmy.setEnemies(medianEnemies);
		medianEnemyArmy.setAllEnemies(allEnemies);
	}
	
	// Gera uma posição válida
	private Position generateRandomPos(int minX, int minY, int maxX, int maxY) {
		int randomX = 0;
		int randomY = 0;
		boolean acceptable = false;
		
		while(!acceptable) {
			randomX = ThreadLocalRandom.current().nextInt(minX, sizeX);
			randomY = ThreadLocalRandom.current().nextInt(minY, sizeY);
			acceptable = !verifyCollision(randomX, randomY);
		}
		return new Position(randomX, randomY);
	}
	
	// Verifica a colisão com todas as entidades
	private boolean verifyCollision(int x, int y) {
		List<Entity> entities = new ArrayList<Entity>();
		entities.addAll(allEnemies);
		entities.addAll(playerArmy);
		
		for (Entity entity : entities) {
			if (checkOverride(x, y, entity.getGridX(), entity.getGridY())) {
				return true;
			}
		}
		return false;
	}

	// Altera o custo de até <number> casas aleatórias
	private void addRandomCosts(int number, int max) {
		for (int i = 0; i < number; i++) {
			int randomX = ThreadLocalRandom.current().nextInt(0, sizeX);
			int randomY = ThreadLocalRandom.current().nextInt(0, sizeY);
			int randomCost = ThreadLocalRandom.current().nextInt(initialCost, max);
			grid.setElementCost(randomX, randomY, randomCost);
		}
	}

	// Adiciona até <number> obstáculos intransponíveis
	private void addRandomForbidden(int number) {
		for (int i = 0; i < number; i++) {
			Position p = generateRandomPos(0, 0, sizeX, sizeY);
			int x = p.getPosX();
			int y = p.getPosY();
			
			grid.setElementValue(x, y, FORBIDDEN);
		}
	}

	private void start() {
		running = true;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		// Desenha a Grade
		map.draw(g2d);

		// Desenha a Preview do movimento do Jogador
		if (previewVisibility) {
			drawPreview(g2d);
		}
		// Desenha o Jogador
		for (Player player : playerArmy) {
			player.draw(g2d);
		}

		// Desenha inimigos
		for (Entity enemy : allEnemies) {
			enemy.draw(g2d);
		}
		
		// Desenha mensagens
		g2d.setColor(Color.BLACK);
		g2d.drawString(message, gridToCoord(1), gridToCoord(sizeY)+tileSize/2+5);
	}

	public void stop() {
		running = false;
	}

	@Override
	public void mouseMoved(MouseEvent m) {
		stopOnTKO();
		if(checkValidPos(m.getX(), m.getY())) {
			// Coordenadas atuais do mouse na grade
			int mx = coordToGrid(m.getX());
			int my = coordToGrid(m.getY());
	
			// Atualiza as coordenadas do Mouse
			if (lastMouseX != mx || lastMouseY != my) {
				lastMouseX = mx;
				lastMouseY = my;
				if (verifyCollision(mx,my))
					inPlayer = true;
				else {
					inPlayer = false;
					try {
						encontraCaminho();
					} catch (ArrayIndexOutOfBoundsException e) {
	
					}
				}
				repaint();
			}
		}
		else
			mouseExited(m);
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		stopOnTKO();

		// Move o Jogador Atual
		if (moveCost <= playerArmy.get(actualPlayer).getMoves() && !inPlayer && !isForbidden(m)
				&& checkValidPos(m.getX(), m.getY())) {
			if (stepMode) {
				// Movimentação passo a passo
				previewVisibility = false;
				movePlayer();
			} else {
				// Movimentação direta
				playerArmy.get(actualPlayer).setGridX((coordToGrid(m.getX())));
				playerArmy.get(actualPlayer).setGridY((coordToGrid(m.getY())));
				soundPlayer.play("playerMove");
			}
			inPlayer = true;
			actualPlayer++;

			if (actualPlayer == playerArmy.size()) {
				actualPlayer = 0;
				encontraCaminhoInimigos();
				rounds++;
				if (rounds % 10 == 0 && enemyMoves <= 2 * playerMoves) {
					enemyMoves++;
				}
				for (SimpleEnemy enemy : simpleEnemies) {
					enemy.setMoves(enemyMoves);
				}

				for (Player player : playerArmy) {
					for (Entity enemy : allEnemies) {
						if (enemy.getGridX().equals(player.getGridX()) && enemy.getGridY().equals(player.getGridY())) {
							soundPlayer.play("death");

							if (playerArmy.size() == 1) {
								stop();
							} else {
								synchronized (this) {
									playerArmy.remove(player);
								}
							}
						}
					}
				}

			} else {
				previewVisibility = true;
			}
			repaint();
		}
	}

	private void movePlayer() {
		int counter = 0;
		
		for (Position pos : preview) {
			if (counter > playerMoves)
				break;
			playerArmy.get(actualPlayer).setGridX(pos.getPosX());
			playerArmy.get(actualPlayer).setGridY(pos.getPosY());
			soundPlayer.play("playerMove");
			delayPaint(msDelay);
			counter++;
		}
		delayPaint(msLongerDelay);
	}

	@Override
	public void mouseEntered(MouseEvent m) {
		mouseMoved(m);
	}

	@Override
	public void mouseExited(MouseEvent m) {
		stopOnTKO();
		inPlayer = true;
		lastMouseX = playerArmy.get(actualPlayer).getGridX();
		lastMouseY = playerArmy.get(actualPlayer).getGridY();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent m) {
	}

	@Override
	public void mouseReleased(MouseEvent m) {
	}

	@Override
	public void mouseDragged(MouseEvent m) {
	}

	// Desenha a preview do movimento do Jogador
	private void drawPreview(Graphics2D g) {
		int x = -1;
		int y = -1;
		moveCost = 0;
		g.setColor(Color.RED);
		if (!preview.isEmpty() && !inPlayer) {
			for (Position e : preview) {
				if (moveCost > playerArmy.get(actualPlayer).getMoves())
					break;
				if (x != -1 && y != -1) {
					if (grid.getElementCost(e) + moveCost <= playerArmy.get(actualPlayer).getMoves())
						g.drawLine(gridToCoord(e.getPosX()) + tileSize / 2, gridToCoord(e.getPosY()) + tileSize / 2,
								gridToCoord(x) + tileSize / 2, gridToCoord(y) + tileSize / 2);
					moveCost += grid.getElementCost(e);
				}
				x = e.getPosX();
				y = e.getPosY();
			}
		}
	}

	private boolean isForbidden(MouseEvent m) {
		if (grid.getElementValue(coordToGrid(m.getX()), coordToGrid(m.getY())).equals(grid.getFORBIDDEN()))
			return true;
		else
			return false;
	}

	private List<Position> cheapestPathToList(CheapestPath<Position, Integer> cpt) {
		if (cpt == null) {
			return new ArrayList<Position>();
		}

		return cpt.getPath();
	}

	private void stopOnTKO() {
		grid.setVisitedToEmpty();
		// Ajuda a não entrar nos inimigos
		for (Entity e : allEnemies) {
			grid.setElementValue(e.getGridX(), e.getGridY(), FORBIDDEN);
		}

		List<Position> ps = grid.visitableNeighbours(playerArmy.get(actualPlayer).getGridX(), playerArmy.get(actualPlayer).getGridY());

		if (ps.isEmpty()) {
			stop();
		}

		// Reverter modificação
		for (Entity e : allEnemies) {
			grid.setElementValue(e.getGridX(), e.getGridY(), EMPTY);
		}
	}

	private void encontraCaminho() {
		grid.setVisitedToEmpty();

		// Ajuda a não entrar nos inimigos
		for (Entity e : allEnemies) {
			grid.setElementValue(e.getGridX(), e.getGridY(), FORBIDDEN);
		}

		CheapestPath<Position, Integer> cpt = grid.dijkstra(new Position(playerArmy.get(actualPlayer).getGridX(), 
				playerArmy.get(actualPlayer).getGridY()), new Position(lastMouseX, lastMouseY));
		preview = cheapestPathToList(cpt);

		// Reverter modificação
		for (Entity e : allEnemies) {
			grid.setElementValue(e.getGridX(), e.getGridY(), EMPTY);
		}

		grid.setVisitedToEmpty();
	}

	private void encontraCaminhoInimigos() {
		grid.setVisitedToEmpty();
		
		updateMessage("Enemy Turn");

		// Caminho dos inimigos comuns
		encontraCaminhoInimigosComuns();

		// Caminho dos inimigos Greedy
		encontraCaminhoInimigosGreedy();

		// Caminho dos inimigos medianos
		encontraCaminhoInimigosMedian();
		
		// Caminho dos inimigos agendados por peso
		encontraCaminhoInimigosWIS();

		// Reativa a visibilidade do preview
		previewVisibility = true;

		grid.setVisitedToEmpty();
		
		updateMessage("Your Turn");
	}
	
	private List<Entity> playerArmyToEntity() {
		List<Entity> convertedArmy = new ArrayList<Entity>();
		
		for (Entity e : playerArmy) {
			convertedArmy.add(e);
		}
		
		return convertedArmy;
	}

	private void encontraCaminhoInimigosComuns() {
		grid.setVisitedToEmpty();
		
		simpleEnemyArmy.setGrid(grid);
		simpleEnemyArmy.setTargets(playerArmyToEntity());
		simpleEnemyArmy.findPath();
		
		for (EnemyCheapestPath path : simpleEnemyArmy.getOrderedPaths()) {
			moveEnemyToPlayerWithCost(path.getEnemy(), path.getPath());
			
			// Atualiza a tela para mostrar o movimento individual de cada inimigo
			if (stepMode) {
				delayPaint(msDelay);
			}
		}
		
		grid.setVisitedToEmpty();
	}

	private void delayPaint(int delay) {
		this.paintImmediately(0, 0, WIDTH, HEIGHT);
		try {
			TimeUnit.MILLISECONDS.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void updateMessage(String message) {
		this.message = message; 
		this.paintImmediately(0, 0, WIDTH, HEIGHT+tileSize);
	}

	
	private void encontraCaminhoInimigosGreedy() {
		grid.setVisitedToEmpty();
		
		greedyEnemyArmy.setGreedyArmyMoveBudget(greedyArmyMoveBudget);
		greedyEnemyArmy.setGrid(grid);
		greedyEnemyArmy.setTargets(playerArmyToEntity());
		greedyEnemyArmy.findPath();
		
		for (GreedyCheapestPath path : greedyEnemyArmy.getOrderedPaths()) {		
			// Atualiza a tela para mostrar o movimento individual de cada inimigo
			Integer size = path.getPath().getPath().size() - 1;
			moveEnemyWthoutCost(path, path.getPath().getPath().get(size));
		}
		
		grid.setVisitedToEmpty();		
	}

	private void moveEnemyWthoutCost(GreedyCheapestPath item, Position finish) {
		if (stepMode) {
			for (Position p : item.getPath().getPath()) {
				item.getGreedyEnemy().setGridX(p.getPosX());
				item.getGreedyEnemy().setGridY(p.getPosY());
				soundPlayer.play("enemyMove");
				delayPaint(msDelay);
				if (p == finish) {
					break;
				}
			}
		} else {
			item.getGreedyEnemy().setGridX(finish.getPosX());
			item.getGreedyEnemy().setGridY(finish.getPosY());
		}
	}


	private void encontraCaminhoInimigosMedian() {
		grid.setVisitedToEmpty();

		medianEnemyArmy.setGrid(grid);
		medianEnemyArmy.setTargets(playerArmyToEntity());
		medianEnemyArmy.findPath();

		for (EnemyCheapestPath path : medianEnemyArmy.getOrderedPaths()) {
			moveEnemyToPlayerWithCost(path.getEnemy(), path.getPath());

			// Atualiza a tela para mostrar o movimento individual de cada inimigo
			if (stepMode) {
				delayPaint(msDelay);
			}
		}

		grid.setVisitedToEmpty();
	}
	
	private void encontraCaminhoInimigosWIS() {
		grid.setVisitedToEmpty();
		
		wisEnemyArmy.setGrid(grid);
		wisEnemyArmy.setTargets(playerArmyToEntity());
		wisEnemyArmy.findPath();
		
		for (WISCheapestPath path : wisEnemyArmy.getOrderedPaths()) {
			moveEnemyToPlayerWithCost(path.getEnemy(), path.getPath());
		}
		
		grid.setVisitedToEmpty();
	}

	/**
	 * Marca outros inimigos como casas proibidas, evitando que dois inimigos
	 * fiquem, ao mesmo tempo, em uma casa só
	 * 
	 * @param enemy
	 */
	public void lockOtherEnemies(SimpleEnemy enemy) {
		for (Entity otherEnemy : allEnemies) {
			grid.setElementValue(otherEnemy.getGridX(), otherEnemy.getGridY(), VISITED);
		}
		grid.setElementValue(enemy.getGridX(), enemy.getGridY(), EMPTY);
	}

	/**
	 * Libera a trava que impede os inimigos de estarem juntos em uma mesma casa.
	 * Sempre execute essa função após executar
	 * {@link #lockOtherEnemies(SimpleEnemy)}
	 */
	public void unlockAllEnemies() {
		for (Entity enemy : allEnemies) {
			grid.setElementValue(enemy.getGridX(), enemy.getGridY(), EMPTY);
		}
	}

	/**
	 * Move inimigo até o jogador respeitando o custo máximo permitido pelo inimigo
	 * 
	 * @param enemy
	 * @param path
	 * 
	 */
	private void moveEnemyToPlayerWithCost(SimpleEnemy enemy, CheapestPath<Position, Integer> path) {
		Integer actualCost = 0;
		Position endPosition = new Position(enemy.getGridX(), enemy.getGridY());
		Integer initialTileCost = grid.getElementCost(endPosition);
		Position playerPosition = new Position(playerArmy.get(actualPlayer).getGridX(), playerArmy.get(actualPlayer).getGridY());
		for (Position p : path.getPath()) {
			actualCost += grid.getElementCost(p);

			if (stepMode) {
				// Movimentação passo a passo
				enemy.setGridX(p.getPosX());
				enemy.setGridY(p.getPosY());
				soundPlayer.play("enemyMove");
				delayPaint(msDelay);
			}
			if (actualCost > enemy.getMoves() + initialTileCost) {
				break;
			} else if (p.equals(playerPosition)) {
				endPosition = playerPosition;
				break;
			} else {
				endPosition = p;
			}

		}
		if (!stepMode) {
			// Movimentação direta
			enemy.setGridX(endPosition.getPosX());
			enemy.setGridY(endPosition.getPosY());
		}
	}
	
	/**
	 * @return the grid
	 */
	public GraphMatrix<Integer, Integer> getGrid() {
		return grid;
	}

	private boolean checkOverride(int x1, int y1, int x2, int y2) {
		return (x1 == x2 && y1 == y2);
	}

	private int gridToCoord(int v) {
		return v * tileSize;
	}

	private int coordToGrid(int v) {
		return (v - 1) / tileSize;
	}
	
	private boolean checkValidPos(int x, int y) {
		return (coordToGrid(x) < sizeX && coordToGrid(y) < sizeY);
	}

	public boolean getRunning() {
		return running;
	}

	public int getScore() {
		return rounds;
	}
	
}
