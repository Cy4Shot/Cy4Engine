package engine.modules.gpgpu;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import engine.core.shaders.Shader;
import engine.core.texturing.Texture2D;
import engine.core.utils.ResourceLoader;

public class SplatMapShader extends Shader {

	private static SplatMapShader instance = null;

	public static SplatMapShader getInstance() {
		if (instance == null) {
			instance = new SplatMapShader();
		}
		return instance;
	}

	protected SplatMapShader() {

		super();

		addComputeShader(ResourceLoader.loadShader("shaders/gpgpu/SplatMap.glsl"));
		compileShader();

		addUniform("normalmap");
		addUniform("N");
	}

	public void updateUniforms(Texture2D normalmap, int N) {
		glActiveTexture(GL_TEXTURE0);
		normalmap.bind();
		setUniformi("normalmap", 0);
		setUniformi("N", N);
	}
}
