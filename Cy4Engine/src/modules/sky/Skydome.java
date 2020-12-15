package modules.sky;

import core.buffers.MeshVBO;
import core.configs.CCW;
import core.kernel.RenderContext;
import core.math.Vec3f;
import core.model.Mesh;
import core.rendering.RenderInfo;
import core.rendering.Renderer;
import core.scene.GameObject;
import core.utils.Constants;
import core.utils.objloader.OBJLoader;

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
