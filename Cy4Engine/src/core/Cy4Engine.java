package core;

import core.kernel.Game;

public class Cy4Engine {
	public static void main(String[] args) {
		
		Game game = new Game();
		game.getEngine().createWindow(1920, 1080, "Cy4Engine");
		game.init();
		game.launch();
	}
}
