package engine.core.configs;

import org.lwjgl.opengl.GL11;

public class Wireframe implements RenderConfig {

	@Override
	public void enable() {
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}

	@Override
	public void disable() {
		GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
	}

}
