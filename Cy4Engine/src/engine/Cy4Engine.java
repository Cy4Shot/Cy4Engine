package engine;

import engine.core.kernel.Game;

public class Cy4Engine {

	private static Game instance;

	public Cy4Engine(String[] args) {

		Game game = new Game();
		game.getEngine().createWindow(1920, 1080, "Cy4Engine");
		game.init();
		setInstance(game);
		game.launch();
	}

	public static Game getInstance() {
		return instance;
	}

	public static void setInstance(Game instance) {
		Cy4Engine.instance = instance;
	}
}
