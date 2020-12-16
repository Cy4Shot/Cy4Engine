package engine.modules.sky;

import engine.core.scene.GameObject;
import engine.core.shaders.Shader;
import engine.core.utils.ResourceLoader;

public class AtmosphereShader extends Shader {

	private static AtmosphereShader instance = null;

	public static AtmosphereShader getInstance() {
		if (instance == null)
			instance = new AtmosphereShader();
		return instance;
	}
	
	protected AtmosphereShader() {
		super();
		
		addVertexShader(ResourceLoader.loadShader("shaders/sky/atmosphere_VS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/sky/atmosphere_FS.glsl"));
		compileShader();
		
		addUniform("m_MVP");
		addUniform("m_World");
	}
	
	@Override
	public void updateUniforms(GameObject obj) {
		setUniform("m_MVP", obj.getTransform().getMVP());
		setUniform("m_World", obj.getTransform().getWorldMatrix());
	}

}
