package modules.gpgpu;

import org.lwjgl.opengl.GL13;

import core.shaders.Shader;
import core.texturing.Texture2D;
import core.utils.ResourceLoader;

public class NormalMapShader extends Shader {
	private static NormalMapShader instance = null;

	public static NormalMapShader getInstance() {
		if (instance == null) {
			instance = new NormalMapShader();
		}
		return instance;
	}

	protected NormalMapShader() {
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/gpgpu/NormalMap.glsl"));
		compileShader();
		
		addUniform("heightmap");
		addUniform("N");
		addUniform("strength");
	}
	
	public void updateUniforms(Texture2D heightmap, int N, float strength) {
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		heightmap.bind();
		setUniformi("heightmap", 0);
		
		setUniformi("N", N);
		setUniformf("strength", strength);
	}
}
