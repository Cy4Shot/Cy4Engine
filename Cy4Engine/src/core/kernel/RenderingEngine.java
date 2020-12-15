package core.kernel;

import core.configs.Default;
import modules.sky.Skydome;
import modules.terrain.Terrain;

/**
 * 
 * @author oreon3D The RenderingEngine manages the render calls of all 3D
 *         entities with shadow rendering and post processing effects
 *
 */
public class RenderingEngine {

	private Window window;
	private Skydome sky;
	private Terrain terrain;

	public RenderingEngine() {
		window = Window.getInstance();
		sky = new Skydome();
		terrain = new Terrain();
	}

	public void init() {
		window.init();
		terrain.init("./res/settings/terrain_settings.txt");
	}

	public void render() {
		Camera.getInstance().update();
		
		Default.clearScreen();
		
		sky.render();
		
		terrain.updateQuadtree();
		terrain.render();

		// draw into OpenGL window
		window.render();
	}

	public void update() {
	}

	public void shutdown() {
	}
}
