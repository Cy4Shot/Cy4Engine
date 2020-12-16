package engine.core.rendering;

import engine.core.buffers.BaseVBO;
import engine.core.scene.Component;

public class Renderer extends Component{
	private BaseVBO vbo;
	private RenderInfo info;
	
	public Renderer() {
		
	}
	
	public void render() {
		info.getConfig().enable();
		info.getShader().bind();
		info.getShader().updateUniforms(getParent());
		
		getVbo().draw();
		info.getConfig().disable();
	}

	public BaseVBO getVbo() {
		return vbo;
	}

	public void setVbo(BaseVBO vbo) {
		this.vbo = vbo;
	}

	public RenderInfo getInfo() {
		return info;
	}

	public void setInfo(RenderInfo info) {
		this.info = info;
	}
}
