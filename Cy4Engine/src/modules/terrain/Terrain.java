package modules.terrain;

import core.kernel.Camera;
import core.scene.Node;

public class Terrain extends Node {

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
