package core.configs;

import org.lwjgl.opengl.GL11;

public class CCW implements RenderConfig {

	@Override
	public void enable() {
		GL11.glFrontFace(GL11.GL_CCW);
	}

	@Override
	public void disable() {
		GL11.glFrontFace(GL11.GL_CW);
	}

}
