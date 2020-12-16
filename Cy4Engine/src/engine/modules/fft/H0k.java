package engine.modules.fft;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import org.lwjgl.opengl.GL30;

import engine.core.math.Vec2f;
import engine.core.texturing.Texture2D;
import engine.core.texturing.TextureStorage2D;

public class H0k {

	private Texture2D imageH0k;
	private Texture2D imageH0minusK;

	private int N;
	private int L;
	private Vec2f direction;
	private float intensity;
	private float amplitude;
	private float alignment;
	private float capillarSupressFactor;

	private Texture2D noise0;

	public Texture2D getImageH0k() {
		return imageH0k;
	}

	public void setImageH0k(Texture2D imageH0k) {
		this.imageH0k = imageH0k;
	}

	public Texture2D getImageH0minusK() {
		return imageH0minusK;
	}

	public void setImageH0minusK(Texture2D imageH0minusK) {
		this.imageH0minusK = imageH0minusK;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public Vec2f getDirection() {
		return direction;
	}

	public void setDirection(Vec2f direction) {
		this.direction = direction;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public float getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(float amplitude) {
		this.amplitude = amplitude;
	}

	public float getAlignment() {
		return alignment;
	}

	public void setAlignment(float alignment) {
		this.alignment = alignment;
	}

	public float getCapillarSupressFactor() {
		return capillarSupressFactor;
	}

	public void setCapillarSupressFactor(float capillarSupressFactor) {
		this.capillarSupressFactor = capillarSupressFactor;
	}

	public Texture2D getNoise0() {
		return noise0;
	}

	public void setNoise0(Texture2D noise0) {
		this.noise0 = noise0;
	}

	public Texture2D getNoise1() {
		return noise1;
	}

	public void setNoise1(Texture2D noise1) {
		this.noise1 = noise1;
	}

	public Texture2D getNoise2() {
		return noise2;
	}

	public void setNoise2(Texture2D noise2) {
		this.noise2 = noise2;
	}

	public Texture2D getNoise3() {
		return noise3;
	}

	public void setNoise3(Texture2D noise3) {
		this.noise3 = noise3;
	}

	public H0kShader getShader() {
		return shader;
	}

	public void setShader(H0kShader shader) {
		this.shader = shader;
	}

	private Texture2D noise1;
	private Texture2D noise2;
	private Texture2D noise3;

	protected H0kShader shader;

	public H0k(int N, int L, float amplitude, Vec2f direction, float alignment, float intensity,
			float capillarSupressFactor) {

		this.N = N;
		this.L = L;
		this.direction = direction;
		this.amplitude = amplitude;
		this.intensity = intensity;
		this.capillarSupressFactor = capillarSupressFactor;
		this.alignment = alignment;

		shader = H0kShader.getInstance();

		imageH0k = new TextureStorage2D(N, N, 1, GL30.GL_RGBA32F);
		imageH0minusK = new TextureStorage2D(N, N, 1, GL30.GL_RGBA32F);
		noise0 = new Texture2D("./res/textures/noise/Noise256_0.jpg");
		noise1 = new Texture2D("./res/textures/noise/Noise256_1.jpg");
		noise2 = new Texture2D("./res/textures/noise/Noise256_2.jpg");
		noise3 = new Texture2D("./res/textures/noise/Noise256_3.jpg");
	}

	public void render() {

		shader.bind();
		shader.updateUniforms(N, L, amplitude, direction, alignment, intensity, capillarSupressFactor);

		glActiveTexture(GL_TEXTURE0);
		noise0.bind();

		glActiveTexture(GL_TEXTURE1);
		noise1.bind();

		glActiveTexture(GL_TEXTURE2);
		noise2.bind();

		glActiveTexture(GL_TEXTURE3);
		noise3.bind();

		shader.updateUniforms(0, 1, 2, 3);

		glBindImageTexture(0, imageH0k.getId(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA32F);

		glBindImageTexture(1, imageH0minusK.getId(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA32F);

		glDispatchCompute(N / 16, N / 16, 1);
	}

}