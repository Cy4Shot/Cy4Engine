package modules.fft;

import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.GL_READ_WRITE;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import org.lwjgl.opengl.GL30;

import core.texturing.Texture2D;
import core.texturing.TextureStorage2D;

public class Hkt {

	public Texture2D getImageDyCoeficcients() {
		return imageDyCoeficcients;
	}

	public void setImageDyCoeficcients(Texture2D imageDyCoeficcients) {
		this.imageDyCoeficcients = imageDyCoeficcients;
	}

	public Texture2D getImageDxCoefficients() {
		return imageDxCoefficients;
	}

	public void setImageDxCoefficients(Texture2D imageDxCoefficients) {
		this.imageDxCoefficients = imageDxCoefficients;
	}

	public Texture2D getImageDzCoefficients() {
		return imageDzCoefficients;
	}

	public void setImageDzCoefficients(Texture2D imageDzCoefficients) {
		this.imageDzCoefficients = imageDzCoefficients;
	}

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

	public HktShader getShader() {
		return shader;
	}

	public void setShader(HktShader shader) {
		this.shader = shader;
	}

	private Texture2D imageDyCoeficcients;
	private Texture2D imageDxCoefficients;
	private Texture2D imageDzCoefficients;

	private Texture2D imageH0k;
	private Texture2D imageH0minusK;

	private int N;
	private int L;
	protected HktShader shader;

	public Hkt(int N, int L) {

		this.N = N;
		this.L = L;

		shader = HktShader.getInstance();

		imageDyCoeficcients = new TextureStorage2D(N, N, 1, GL30.GL_RGBA32F);
		imageDxCoefficients = new TextureStorage2D(N, N, 1, GL30.GL_RGBA32F);
		imageDzCoefficients = new TextureStorage2D(N, N, 1, GL30.GL_RGBA32F);

	}

	public void render(float t) {

		shader.bind();
		shader.updateUniforms(L, N, t);
		glBindImageTexture(0, imageDyCoeficcients.getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
		glBindImageTexture(1, imageDxCoefficients.getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
		glBindImageTexture(2, imageDzCoefficients.getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
		glBindImageTexture(3, imageH0k.getId(), 0, false, 0, GL_READ_ONLY, GL_RGBA32F);
		glBindImageTexture(4, imageH0minusK.getId(), 0, false, 0, GL_READ_ONLY, GL_RGBA32F);
		glDispatchCompute(N / 16, N / 16, 1);
		glFinish();
	}
}