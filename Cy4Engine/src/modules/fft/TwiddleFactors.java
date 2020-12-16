package modules.fft;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

import core.buffers.GLShaderStorageBuffer;
import core.texturing.Texture2D;
import core.texturing.TextureStorage2D;

public class TwiddleFactors {

	private Texture2D texture;

	public Texture2D getTexture() {
		return texture;
	}

	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int getLog_2_N() {
		return log_2_N;
	}

	public void setLog_2_N(int log_2_N) {
		this.log_2_N = log_2_N;
	}

	public TwiddleFactorsShader getShader() {
		return shader;
	}

	public void setShader(TwiddleFactorsShader shader) {
		this.shader = shader;
	}

	public GLShaderStorageBuffer getBitReversedSSBO() {
		return bitReversedSSBO;
	}

	public void setBitReversedSSBO(GLShaderStorageBuffer bitReversedSSBO) {
		this.bitReversedSSBO = bitReversedSSBO;
	}

	private int N;
	private int log_2_N;
	private TwiddleFactorsShader shader;
	private GLShaderStorageBuffer bitReversedSSBO;

	public TwiddleFactors(int N) {
		this.N = N;

		bitReversedSSBO = new GLShaderStorageBuffer();
		bitReversedSSBO.addData(initBitReversedIndices());

		log_2_N = (int) (Math.log(N) / Math.log(2));
		shader = TwiddleFactorsShader.getInstance();
		texture = new TextureStorage2D(log_2_N, N, 1, GL30.GL_RGBA32F);
	}

	public void render() {
		shader.bind();
		bitReversedSSBO.bindBufferBase(1);
		shader.updateUniforms(N);
		GL42.glBindImageTexture(0, texture.getId(), 0, false, 0, GL15.GL_WRITE_ONLY, GL30.GL_RGBA32F);
		GL43.glDispatchCompute(log_2_N, N / 16, 1);
	}

	private int[] initBitReversedIndices() {
		int[] bitReversedIndices = new int[N];
		int bits = (int) (Math.log(N) / Math.log(2));

		for (int i = 0; i < N; i++) {
			int x = Integer.reverse(i);
			x = Integer.rotateLeft(x, bits);
			bitReversedIndices[i] = x;
		}

		return bitReversedIndices;
	}

}