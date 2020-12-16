package core.kernel;

import org.lwjgl.glfw.GLFW;

import core.configs.Default;
import core.scene.Scenegraph;
import modules.sky.Skydome;
import modules.terrain.Terrain;
import modules.water.Ocean;

/**
 * 
 * @author oreon3D The RenderingEngine manages the render calls of all 3D
 *         entities with shadow rendering and post processing effects
 *
 */
public class RenderingEngine {

	private Window window;
	private Skydome sky;
	public Ocean ocean;
	private Terrain terrain;
	private Scenegraph sceneGraph;

	public Scenegraph getSceneGraph() {
		return sceneGraph;
	}

	public void setSceneGraph(Scenegraph sceneGraph) {
		this.sceneGraph = sceneGraph;
	}

	public RenderingEngine() {
		window = Window.getInstance();
		sceneGraph = new Scenegraph();
		sky = new Skydome();
		terrain = new Terrain();
		ocean = new Ocean();
	}

	public void init() {

		window.init();
		terrain.init("./res/settings/terrain_settings.txt");
		ocean.initShaderBuffer();
		sceneGraph.addObject(ocean);
		sceneGraph.addObject(terrain);
		sceneGraph.addObject(sky);
	}

	public void render() {
		Camera.getInstance().update();

		Default.clearScreen();

		sky.render();

		terrain.updateQuadtree();
		terrain.render();
		
		ocean.render();

		window.render();
	}

	public void update() {
		if (Input.getInstance().isKeyPushed(GLFW.GLFW_KEY_E)) {
			if (RenderContext.getInstance().isWireframe())
				RenderContext.getInstance().setWireframe(false);
			else
				RenderContext.getInstance().setWireframe(true);
		}
	}

	public void shutdown() {
	}
}
