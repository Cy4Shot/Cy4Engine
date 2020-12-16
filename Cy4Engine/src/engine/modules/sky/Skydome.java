package engine.modules.sky;

import engine.core.buffers.MeshVBO;
import engine.core.configs.CCW;
import engine.core.kernel.RenderContext;
import engine.core.math.Vec3f;
import engine.core.model.Mesh;
import engine.core.rendering.RenderInfo;
import engine.core.rendering.Renderer;
import engine.core.scene.GameObject;
import engine.core.utils.Constants;
import engine.core.utils.objloader.OBJLoader;

public class Skydome extends GameObject {
	public Skydome() {
		Mesh mesh = new OBJLoader().load("./res/models/dome", "dome.obj", null)[0].getMesh();
		MeshVBO meshBuffer = new MeshVBO();
		meshBuffer.allocate(mesh);

		Renderer renderer = new Renderer();
		renderer.setVbo(meshBuffer);
		renderer.setInfo(new RenderInfo(new CCW(), AtmosphereShader.getInstance()));
		addComponent(Constants.RENDERER_COMPONENT, renderer);

		getTransform().setScale(new Vec3f(Constants.ZFAR * 0.5f, Constants.ZFAR * 0.5f, Constants.ZFAR * 0.5f));
	}
	
	@Override
	public void render() {
		if (!RenderContext.getInstance().isWireframe()) {
			super.render();
		}
	}
}
