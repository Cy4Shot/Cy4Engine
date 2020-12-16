package engine.modules.fft;

import engine.core.shaders.Shader;
import engine.core.utils.ResourceLoader;

public class FFTButterflyShader extends Shader {
	private static FFTButterflyShader instance = null;

	public static FFTButterflyShader getInstance() {
		if (instance == null) {
			instance = new FFTButterflyShader();
		}
		return instance;
	}

	protected FFTButterflyShader() {
		super();

		addComputeShader(ResourceLoader.loadShader("shaders/fft/butterfly.comp"));
		compileShader();

		addUniform("direction");
		addUniform("pingpong");
		addUniform("stage");
	}

	public void updateUniforms(int pingpong, int direction, int stage) {
		setUniformi("pingpong", pingpong);
		setUniformi("direction", direction);
		setUniformi("stage", stage);
	}
}