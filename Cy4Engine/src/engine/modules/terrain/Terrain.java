package engine.modules.terrain;

import engine.core.kernel.Camera;
import engine.core.scene.GameObject;

public class Terrain extends GameObject {

	private TerrainConfig configuration;

	public void init(String config) {
		configuration = new TerrainConfig();
		configuration.loadFile(config);

		addChild(new TerrainQuadTree(configuration));
	}

	public void updateQuadtree() {
		if (Camera.getInstance().isCameraMoved()) {
			((TerrainQuadTree) getChildren().get(0)).updateQuadTree();
		}
	}

	public TerrainConfig getConfiguration() {
		return configuration;
	}

	public void setConfiguration(TerrainConfig configuration) {
		this.configuration = configuration;
	}

}
