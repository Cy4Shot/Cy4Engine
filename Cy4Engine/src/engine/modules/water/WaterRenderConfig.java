package engine.modules.water;

import org.lwjgl.opengl.GL11;

import engine.core.configs.RenderConfig;

public class WaterRenderConfig implements RenderConfig {

	@Override
	public void enable() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	@Override
	public void disable() {
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public void clearScreenDeepOcean() {
		GL11.glClearColor(0.0f,0.0f,0.0f,1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

}
