package engine.modules.terrain;

import engine.core.buffers.PatchVBO;
import engine.core.math.Vec2f;
import engine.core.scene.Node;

public class TerrainQuadTree extends Node {

	private static int rootNodes = 8;

	public TerrainQuadTree(TerrainConfig terrConfig) {

		PatchVBO buffer = new PatchVBO();
		buffer.allocate(generatePatch(), 16);

		for (int i = 0; i < rootNodes; i++) {
			for (int j = 0; j < rootNodes; j++) {
				addChild(new TerrainNode(buffer, terrConfig, new Vec2f(i / (float) rootNodes, j / (float) rootNodes), 0,
						new Vec2f(i, j)));
			}
		}
	}

	public void updateQuadTree() {
		for (Node node : getChildren()) {
			((TerrainNode) node).updateQuadtree();
		}
	}

	public Vec2f[] generatePatch() {

		// 16 vertices for each patch
		Vec2f[] vertices = new Vec2f[16];

		int index = 0;

		vertices[index++] = new Vec2f(0, 0);
		vertices[index++] = new Vec2f(0.333f, 0);
		vertices[index++] = new Vec2f(0.666f, 0);
		vertices[index++] = new Vec2f(1, 0);

		vertices[index++] = new Vec2f(0, 0.333f);
		vertices[index++] = new Vec2f(0.333f, 0.333f);
		vertices[index++] = new Vec2f(0.666f, 0.333f);
		vertices[index++] = new Vec2f(1, 0.333f);

		vertices[index++] = new Vec2f(0, 0.666f);
		vertices[index++] = new Vec2f(0.333f, 0.666f);
		vertices[index++] = new Vec2f(0.666f, 0.666f);
		vertices[index++] = new Vec2f(1, 0.666f);

		vertices[index++] = new Vec2f(0, 1);
		vertices[index++] = new Vec2f(0.333f, 1);
		vertices[index++] = new Vec2f(0.666f, 1);
		vertices[index++] = new Vec2f(1, 1);

		return vertices;
	}

	public static int getRootNodes() {
		return rootNodes;
	}
}
